package com.cloud.cipher.app;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail (String username);
}
