package com.tsgk.lab;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;
import java.security.MessageDigest;

public class ApkSignatureDetection {

    private static final String TAG = "SignatureVerification";
    private static final String EXPECTED_SIGNATURE = "expected_signature_hash";

    public static boolean isSignatureValid(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signatures = packageInfo.signatures;

            for (Signature signature : signatures) {
                byte[] signatureBytes = signature.toByteArray();
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] digest = md.digest(signatureBytes);
                StringBuilder sb = new StringBuilder();
                for (byte b : digest) {
                    sb.append(String.format("%02x", b));
                }
                String signatureHash = sb.toString();
                Log.i(TAG, "APK Signature Hash: " + signatureHash);
                if (signatureHash.equals(EXPECTED_SIGNATURE)) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error checking APK signature: " + e.getMessage(), e);
        }
        return false;
    }
}
