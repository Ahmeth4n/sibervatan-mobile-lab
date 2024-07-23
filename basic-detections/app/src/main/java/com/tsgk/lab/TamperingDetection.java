package com.tsgk.lab;

import android.content.Context;
import android.util.Log;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class TamperingDetection {

    private static final String TAG = "TamperingDetection";
    private static final String EXPECTED_HASH = "your_obtained_apk_hash_value";

    public static boolean isTampered(Context context) {
        try {
            String apkPath = context.getPackageCodePath();
            InputStream is = new FileInputStream(apkPath);

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[100 * 1024 * 1024];
            int read;
            while ((read = is.read(buffer)) != -1) {
                md.update(buffer, 0, read);
            }
            byte[] digest = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            String apkHash = sb.toString();
            Log.i(TAG, "APK Hash: " + apkHash);

            return !apkHash.equals(EXPECTED_HASH);
        } catch (Exception e) {
            Log.e(TAG, "Error checking APK hash: " + e.getMessage(), e);
            return true;
        }
    }
}
