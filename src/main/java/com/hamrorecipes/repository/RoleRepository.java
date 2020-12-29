package com.hamrorecipes.repository;

import java.util.Optional;

import com.hamrorecipes.enums.ERole;
import com.hamrorecipes.models.RoleModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<RoleModel, String> {
  Optional<RoleModel> findByName(ERole name);
}
