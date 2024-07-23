package com.tsgk.lab;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String HOSTNAME = "ecc384.badssl.com";

    String degisken = "frida-gadget";
    static {
        System.loadLibrary("port_checker");
        System.loadLibrary("frida_checker");
        System.loadLibrary("frida_process_checker");
        System.loadLibrary("frida_library_checker");
        System.loadLibrary("root_checker");
    }

    private native boolean checkPort(int port);
    private native boolean checkFrida();
    private native boolean checkFridaProcess();
    private native boolean checkFridaLibrary();
    private native boolean checkRoot();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        new Thread(KeyExchange::startClient).start();

        // ----------------------------------- //

        try {
            Encryption.encryptTest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // ----------------------------------- //


        OkHttpClient client = SSLPinning.getPinnedHttpClient(getApplicationContext(),HOSTNAME);

        Request request = new Request.Builder()
                .url("https://"+HOSTNAME)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    System.out.println(responseData);
                }
            }
        });

        boolean isFridaProcessRunning = checkFridaProcess();
        Toast.makeText(this, "Frida process is " + (isFridaProcessRunning ? "running" : "not running"), Toast.LENGTH_LONG).show();


        int portToCheck = 27042;
        boolean isPortInUse = checkPort(portToCheck);
        Toast.makeText(this, "Port " + portToCheck + " is " + (isPortInUse ? "in use" : "not in use"), Toast.LENGTH_LONG).show();

        boolean isFridaPresent = checkFrida();
        Toast.makeText(this, "Frida is " + (isFridaPresent ? "present" : "not present") + " in /data/local/tmp", Toast.LENGTH_LONG).show();

        boolean xposedDetection = XposedDetection.detect();
        Toast.makeText(this, "Xposed is " + (xposedDetection ? "installed" : "not installed"), Toast.LENGTH_LONG).show();

        boolean fridaDetection = FridaDetection.detectFrida();
        Toast.makeText(this, "Frida is " + (fridaDetection ? "detected" : "not detected"), Toast.LENGTH_LONG).show();


        boolean isFridaLibraryRunning = checkFridaLibrary();
        Log.e("IsFridaRunning", "Frida library is " + (isFridaLibraryRunning ? "running" : "not running" ));
        Toast.makeText(this, "Frida library is " + (isFridaLibraryRunning ? "running" : "not running"), Toast.LENGTH_LONG).show();

        boolean isDeviceRooted = checkRoot();
        Toast.makeText(this, "Device is " + (isDeviceRooted ? "rooted" : "not rooted"), Toast.LENGTH_LONG).show();

        boolean isEmulator = EmulatorDetection.isEmulator();
        Toast.makeText(this, "Device is " + (isEmulator ? "emulator" : "not emulator"), Toast.LENGTH_LONG).show();

        boolean isDebugging = AntiDebugging.isDeviceBeingDebugged(this);
        Toast.makeText(this, "App is " + (isDebugging ? "debugging" : "not debugging"), Toast.LENGTH_LONG).show();

        boolean isProxyEnabled = ProxyVPNDetection.isProxyEnabled(this);
        Toast.makeText(this, "Device is " + (isProxyEnabled ? "using proxy" : "not using proxy"), Toast.LENGTH_LONG).show();

        boolean isVPNConnected = ProxyVPNDetection.isVPNConnected(this);
        Toast.makeText(this, "Device is " + (isVPNConnected ? "using vpn" : "not using vpn"), Toast.LENGTH_LONG).show();

        boolean isTampered = TamperingDetection.isTampered(this);
        Toast.makeText(this, "App is " + (isTampered ? "tampered" : "not tampered"), Toast.LENGTH_LONG).show();

        boolean isSignatureValid = ApkSignatureDetection.isSignatureValid(this);
        Toast.makeText(this, "App signature is " + (isSignatureValid ? "valid" : "not valid"), Toast.LENGTH_LONG).show();



    }

}