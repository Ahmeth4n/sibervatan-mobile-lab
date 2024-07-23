package com.tsgk.lab;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.*;
import java.util.Base64;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHPublicKeySpec;


public class KeyExchange {

    private static String TAG = "KeyExchange";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void startClient()  {
        try (Socket socket = new Socket(Constants.EXCHANGE_HOST, Constants.EXCHANGE_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            Log.i(TAG, "Connected to server");

            String serverPublicKeyStr = in.readLine();
            Log.i(TAG, "Server public key received: " + serverPublicKeyStr);

            BigInteger serverPublicKey = new BigInteger(serverPublicKeyStr);

            BigInteger g = BigInteger.valueOf(2);
            BigInteger p = new BigInteger(Constants.EXCHANGE_PRIME_NUMBER); // Sunucuda kullanılan prime number

            Log.i(TAG, "DH parameters set");

            DHParameterSpec dhSpec = new DHParameterSpec(p, g);
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DH");
            keyPairGen.initialize(dhSpec);
            KeyPair keyPair = keyPairGen.generateKeyPair();

            Log.i(TAG, "Client key pair generated");

            byte[] clientPublicKeyBytes = ((DHPublicKey) keyPair.getPublic()).getY().toByteArray();
            out.println(new BigInteger(clientPublicKeyBytes).toString());

            Log.i(TAG, "Client public key sent");

            KeyAgreement keyAgree = KeyAgreement.getInstance("DH");
            keyAgree.init(keyPair.getPrivate());
            keyAgree.doPhase(KeyFactory.getInstance("DH").generatePublic(new DHPublicKeySpec(serverPublicKey, p, g)), true);

            Log.i(TAG, "Key agreement phase completed");

            byte[] sharedSecret = keyAgree.generateSecret();
            Log.i(TAG, "Shared secret: " + new BigInteger(sharedSecret).toString() );

        } catch (Exception e) {
            Log.e(TAG, "Error during key exchange: "+ e.getMessage(), e );
        }
    }



}
