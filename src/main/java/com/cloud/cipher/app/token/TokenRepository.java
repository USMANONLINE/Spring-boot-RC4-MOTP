package com.cloud.cipher.app.token;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
public interface TokenRepository extends CrudRepository<Token, Long> {
    Optional<Token> findByToken (String token);
}
