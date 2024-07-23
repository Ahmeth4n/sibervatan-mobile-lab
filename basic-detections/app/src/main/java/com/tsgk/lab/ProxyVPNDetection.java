package com.tsgk.lab;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import java.net.Proxy;
import java.util.List;

public class ProxyVPNDetection {

    private static final String TAG = "ProxyVPNDetection";

    public static boolean isProxyEnabled(Context context) {
        try {
            // Check for proxy settings
            String proxyAddress;
            int proxyPort;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                proxyAddress = System.getProperty("http.proxyHost");
                String portStr = System.getProperty("http.proxyPort");
                proxyPort = (portStr != null) ? Integer.parseInt(portStr) : -1;
            } else {
                proxyAddress = android.net.Proxy.getHost(context);
                proxyPort = android.net.Proxy.getPort(context);
            }

            if (proxyAddress != null && proxyPort != -1) {
                return true;
            }

        } catch (Exception ignored) {
        }
        return false;
    }

    public static boolean isVPNConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (Network network : cm.getAllNetworks()) {
                    NetworkCapabilities caps = cm.getNetworkCapabilities(network);
                    if (caps != null && caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                        return true;
                    }
                }
            } else {
                Network[] networks = cm.getAllNetworks();
                for (Network network : networks) {
                    NetworkInfo networkInfo = cm.getNetworkInfo(network);
                    if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_VPN) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
