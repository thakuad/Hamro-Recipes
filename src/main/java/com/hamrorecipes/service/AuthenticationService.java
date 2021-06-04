package com.hamrorecipes.service;

import com.hamrorecipes.enums.ERole;
import com.hamrorecipes.models.RoleModel;
import com.hamrorecipes.models.UserModel;
import com.hamrorecipes.repository.RoleRepository;
import com.hamrorecipes.repository.UserRepository;
import com.hamrorecipes.security.jwt.JwtUtils;
import com.hamrorecipes.security.payload.request.LoginRequest;
import com.hamrorecipes.security.payload.request.SignupRequest;
import com.hamrorecipes.security.payload.response.JwtResponse;
import com.hamrorecipes.security.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<?> authenticateUser(@NotNull LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserService userDetails = (UserService) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    public ResponseEntity<?> registerUser(@NotNull SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        UserModel user = new UserModel(signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRoles();
        Set<RoleModel> roles = new HashSet<>();

        if (strRoles == null) {
            RoleModel userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {

            strRoles.forEach(role -> {

                switch (role) {
                    case "admin":
                        RoleModel adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        RoleModel modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow((

                                ) -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        RoleModel roleModels = new RoleModel();
                        Optional<RoleModel> roleOpt = roleRepository.findByName(ERole.ROLE_USER);
                        roleOpt.ifPresent(roles::add);
                        if (!roleOpt.isPresent()) {
                            roleModels.setName(ERole.ROLE_USER);
                            roles.add(roleModels);
                            roleRepository.save(roleModels);

                        }

                }
            });
            Optional<RoleModel> roleOpt = roleRepository.findByName(ERole.ROLE_USER);
            RoleModel userRole = new RoleModel();
            roleOpt.ifPresent(roles::add);
            if (!roleOpt.isPresent()) {
                userRole.setName(ERole.ROLE_USER);
                roles.add(userRole);
                roleRepository.save(userRole);
            }

        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
