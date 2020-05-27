package com.cloud.cipher.app.beans;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class RC4CipherImpl {
    private final KeyGenerator rc4KeyGenerator;
    private final SecretKey key;
    private final Cipher cipher;

    public RC4CipherImpl() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        rc4KeyGenerator = KeyGenerator.getInstance("RC4");
        key = rc4KeyGenerator.generateKey();
        cipher = Cipher.getInstance("RC4");
        cipher.init(Cipher.ENCRYPT_MODE, key);
    }
    
    public byte[] encrypt (String password) throws IllegalBlockSizeException, BadPaddingException {
        return cipher.doFinal(password.getBytes());
    }
    
    public String decode (byte[] b) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(b));
    }
}
