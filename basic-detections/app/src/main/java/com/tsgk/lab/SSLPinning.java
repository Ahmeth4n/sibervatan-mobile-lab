package com.tsgk.lab;

import android.content.Context;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SSLPinning {

    public static OkHttpClient getPinnedHttpClient(Context context, String hostname) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = context.getResources().openRawResource(R.raw.server_cert);
            Certificate ca = cf.generateCertificate(caInput);
            caInput.close();

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            CertificatePinner certificatePinner = new CertificatePinner.Builder()
                    .add("*.badssl.com", "sha256/NcSDsufA3zJox027HJiO80AaUrYTCDJrBqf2lQLzG+A=")
                    .build();

            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) tmf.getTrustManagers()[0])
                    .certificatePinner(certificatePinner)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

