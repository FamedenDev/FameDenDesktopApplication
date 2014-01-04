package com.fameden.util.encrypt;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Encoder;


public class RSAAlgorithmImpl {

    private static RSAAlgorithmImpl SINGLETON;

    private static final String ALGORITHM = "RSA";

    private static Cipher cipher = null;

    private static final String PUBLIC_KEY_FILE = "bin/Support/public.key";

    private RSAAlgorithmImpl() throws Exception,
            NoSuchAlgorithmException, NoSuchPaddingException {
        if (SINGLETON != null) {
            throw new Exception(RSAAlgorithmImpl.class.getName());
        } else {
            cipher = Cipher.getInstance(ALGORITHM);
        }
    }

    public static RSAAlgorithmImpl getInstance()
            throws Exception, NoSuchAlgorithmException,
            NoSuchPaddingException {
        if (SINGLETON == null) {
            SINGLETON = new RSAAlgorithmImpl();
        }

        return SINGLETON;
    }

    private byte[] encrypt(String text) {

        ObjectInputStream inputStream = null;
        byte[] cipherText = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(
                    PUBLIC_KEY_FILE));
            PublicKey key = (PublicKey) inputStream.readObject();

            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cipherText;
    }

    public String encryptText(String text) {
        return new BASE64Encoder().encode(encrypt(text));
    }

}
