package com.cloud.cipher.app.beans;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AlgorithmKey implements Serializable{
    private final transient KeyGenerator keyGenerator;
    private final SecretKey secretKey;
    
    public AlgorithmKey () throws NoSuchAlgorithmException {
        keyGenerator = KeyGenerator.getInstance("RC4");
        secretKey = keyGenerator.generateKey();
    }
    
    public SecretKey getSecretKey () {
        return secretKey;
    }
}
