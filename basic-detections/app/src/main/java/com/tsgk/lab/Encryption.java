package com.tsgk.lab;

import android.util.Base64;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {

    public static void encryptTest() throws Exception{
        String originalText = "Hello, World!";
        byte[] originalData = originalText.getBytes();

        // AES GCM
        byte[] aesKey = new byte[16]; // 128-bit key
        byte[] aesIv = new byte[12]; // 96-bit IV for GCM
        byte[] aesGcmEncrypted = Encryption.encryptAESGCM(aesKey, aesIv, originalData);
        byte[] aesGcmDecrypted = Encryption.decryptAESGCM(aesKey, aesIv, aesGcmEncrypted);
        System.out.println("AES GCM Encrypted: " + Encryption.encodeBase64(aesGcmEncrypted));
        System.out.println("AES GCM Decrypted: " + new String(aesGcmDecrypted));

        // AES ECB
        byte[] aesEcbEncrypted = Encryption.encryptAESECB(aesKey, originalData);
        byte[] aesEcbDecrypted = Encryption.decryptAESECB(aesKey, aesEcbEncrypted);
        System.out.println("AES ECB Encrypted: " + Encryption.encodeBase64(aesEcbEncrypted));
        System.out.println("AES ECB Decrypted: " + new String(aesEcbDecrypted));

        // AES CBC
        byte[] aesCbcIv = new byte[16]; // 128-bit IV for CBC
        byte[] aesCbcEncrypted = Encryption.encryptAESCBC(aesKey, aesCbcIv, originalData);
        byte[] aesCbcDecrypted = Encryption.decryptAESCBC(aesKey, aesCbcIv, aesCbcEncrypted);
        System.out.println("AES CBC Encrypted: " + Encryption.encodeBase64(aesCbcEncrypted));
        System.out.println("AES CBC Decrypted: " + new String(aesCbcDecrypted));

        // RSA
        KeyPair rsaKeyPair = Encryption.generateRSAKeyPair();
        byte[] rsaEncrypted = Encryption.encryptRSA(rsaKeyPair.getPublic(), originalData);
        byte[] rsaDecrypted = Encryption.decryptRSA(rsaKeyPair.getPrivate(), rsaEncrypted);
        System.out.println("RSA Encrypted: " + Encryption.encodeBase64(rsaEncrypted));
        System.out.println("RSA Decrypted: " + new String(rsaDecrypted));

        // SHA256
        byte[] sha256Hash = Encryption.hashSHA256(originalData);
        System.out.println("SHA-256 Hash: " + Encryption.encodeBase64(sha256Hash));

        // Base64
        String base64Encoded = Encryption.encodeBase64(originalData);
        byte[] base64Decoded = Encryption.decodeBase64(base64Encoded);
        System.out.println("Base64 Encoded: " + base64Encoded);
        System.out.println("Base64 Decoded: " + new String(base64Decoded));

        // MD5
        byte[] md5Hash = Encryption.hashMD5(originalData);
        System.out.println("MD5 Hash: " + Encryption.encodeBase64(md5Hash));

        // 3-DES
        byte[] desKey = new byte[24]; // 192-bit key for 3DES
        byte[] desIv = new byte[8]; // 64-bit IV for 3DES
        byte[] desEncrypted = Encryption.encrypt3DES(desKey, desIv, originalData);
        byte[] desDecrypted = Encryption.decrypt3DES(desKey, desIv, desEncrypted);
        System.out.println("3-DES Encrypted: " + Encryption.encodeBase64(desEncrypted));
        System.out.println("3-DES Decrypted: " + new String(desDecrypted));

        // Blowfish
        byte[] blowfishKey = new byte[16]; // 128-bit key for Blowfish
        byte[] blowfishIv = new byte[8]; // 64-bit IV for Blowfish
        byte[] blowfishEncrypted = Encryption.encryptBlowfish(blowfishKey, blowfishIv, originalData);
        byte[] blowfishDecrypted = Encryption.decryptBlowfish(blowfishKey, blowfishIv, blowfishEncrypted);
        System.out.println("Blowfish Encrypted: " + Encryption.encodeBase64(blowfishEncrypted));
        System.out.println("Blowfish Decrypted: " + new String(blowfishDecrypted));

        // HMAC SHA256
        byte[] hmacKey = new byte[16]; // 128-bit key for HMAC
        byte[] hmacSha256 = Encryption.hmacSHA256(hmacKey, originalData);
        System.out.println("HMAC SHA-256: " + Encryption.encodeBase64(hmacSha256));

        // PBKDF2
        char[] password = "password".toCharArray();
        byte[] salt = new byte[16];
        int iterations = 10000;
        int keyLength = 256;
        byte[] pbkdf2Key = Encryption.pbkdf2(password, salt, iterations, keyLength);
        System.out.println("PBKDF2 Key: " + Encryption.encodeBase64(pbkdf2Key));

        // XOR
        byte[] xorKey = "key".getBytes();
        byte[] xorEncrypted = Encryption.xorEncrypt(xorKey, originalData);
        byte[] xorDecrypted = Encryption.xorDecrypt(xorKey, xorEncrypted);
        System.out.println("XOR Encrypted: " + Encryption.encodeBase64(xorEncrypted));
        System.out.println("XOR Decrypted: " + new String(xorDecrypted));

    }

    // AES GCM
    public static byte[] encryptAESGCM(byte[] key, byte[] iv, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);
        return cipher.doFinal(data);
    }

    public static byte[] decryptAESGCM(byte[] key, byte[] iv, byte[] encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);
        return cipher.doFinal(encryptedData);
    }

    // AES ECB
    public static byte[] encryptAESECB(byte[] key, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(data);
    }

    public static byte[] decryptAESECB(byte[] key, byte[] encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return cipher.doFinal(encryptedData);
    }

    // AES CBC
    public static byte[] encryptAESCBC(byte[] key, byte[] iv, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        return cipher.doFinal(data);
    }

    public static byte[] decryptAESCBC(byte[] key, byte[] iv, byte[] encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        return cipher.doFinal(encryptedData);
    }

    public static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }
    // RSA
    public static byte[] encryptRSA(PublicKey key, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] decryptRSA(PrivateKey key, byte[] encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encryptedData);
    }

    // SHA256
    public static byte[] hashSHA256(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(data);
    }

    // Base64
    public static String encodeBase64(byte[] data) {
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    public static byte[] decodeBase64(String data) {
        return Base64.decode(data, Base64.DEFAULT);
    }

    // MD5
    public static byte[] hashMD5(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        return digest.digest(data);
    }

    // 3-DES
    public static byte[] encrypt3DES(byte[] key, byte[] iv, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "DESede");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt3DES(byte[] key, byte[] iv, byte[] encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "DESede");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        return cipher.doFinal(encryptedData);
    }

    // Blowfish
    public static byte[] encryptBlowfish(byte[] key, byte[] iv, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "Blowfish");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        return cipher.doFinal(data);
    }

    public static byte[] decryptBlowfish(byte[] key, byte[] iv, byte[] encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "Blowfish");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        return cipher.doFinal(encryptedData);
    }

    // HMAC SHA256
    public static byte[] hmacSHA256(byte[] key, byte[] data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec keySpec = new SecretKeySpec(key, "HmacSHA256");
        mac.init(keySpec);
        return mac.doFinal(data);
    }

    // PBKDF2
    public static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int keyLength) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();
    }

    public static byte[] xorEncrypt(byte[] key, byte[] data) {
        byte[] encrypted = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            encrypted[i] = (byte) (data[i] ^ key[i % key.length]);
        }
        return encrypted;
    }

    public static byte[] xorDecrypt(byte[] key, byte[] encryptedData) {
        byte[] decrypted = new byte[encryptedData.length];
        for (int i = 0; i < encryptedData.length; i++) {
            decrypted[i] = (byte) (encryptedData[i] ^ key[i % key.length]);
        }
        return decrypted;
    }


}
