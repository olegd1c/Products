package ua.in.devapp.products.Api;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.in.devapp.products.R;

public class MyRetrofit {
    private static Gson gson = new GsonBuilder().create();
    private final static String URL_HTTP = URL.URL_HTTP.getValue();
    private final static String URL_HTTPS = URL.URL_HTTPS.getValue();
    private final static boolean USESSL = false;
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(Context context) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        if (USESSL) {
            Retrofit.Builder builder = new Retrofit.Builder().baseUrl(URL_HTTPS);

            OkHttpClient okHttp = new OkHttpClient.Builder()
                    .sslSocketFactory(getSSLConfig(context).getSocketFactory())
                    .build();

            retrofit = builder.client(okHttp).build();
            //retrofit.create(serviceClass);
        } else {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(URL_HTTP)
                    .build();
        }

        return retrofit;
    }
    public static Link getLink(Context context) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        getRetrofit(context);
        return retrofit.create(Link.class);
    }

    public static Gson getGson() {
        return gson;
    }

    private static SSLContext getSSLConfig(Context context) throws CertificateException, IOException,
            KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        KeyStore ks = null;
        try
        {
            ks = KeyStore.getInstance("BKS");
            //final InputStream in = context.getResources().getAssets().open("mystore.bks");
            final InputStream in = context.getResources().openRawResource(R.raw.devapp_in_ua);
            try
            {
                String pass = "1";
                ks.load(in, pass.toCharArray());
            }
            finally
            {
                in.close();
            }
        }
        catch( Exception e )
        {
            Log.i("Your_Function_Name", "Exception - " + e.toString());
        }

        TrustManagerFactory Main_TMF= null;
        try
        {
            Main_TMF= TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            Main_TMF.init(ks);
        }
        catch(Exception e)
        {
            Log.i("Your_Function_Name", "Exception - " + e.toString());
        }

        X509TrustManager Main_Trust_Manager = null;

        for( TrustManager tm : Main_TMF.getTrustManagers() )
            if (tm instanceof X509TrustManager)
            {
                Main_Trust_Manager = (X509TrustManager) tm;
                break;
            }

        final X509TrustManager Cur_Trust_Manager = Main_Trust_Manager;
        SSLContext sslContext = null;
        try
        {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{Cur_Trust_Manager}, new SecureRandom());
        }
        catch (NoSuchAlgorithmException e)
        {
            Log.i("Your_Function_Name", "NoSuchAlgorithmException - " + e.toString());
        }
        catch (KeyManagementException e)
        {
            Log.i("Your_Function_Name", "KeyManagementException - " + e.toString());
        }
        catch (Exception e)
        {
            Log.i("Your_Function_Name", "Exception - " + e.toString());
        }

//        // Loading CAs from an InputStream
//        CertificateFactory cf = null;
//        cf = CertificateFactory.getInstance("X.509");
//
//        java.security.cert.Certificate ca = null;
//        // I'm using Java7. If you used Java6 close it manually with finally.
//        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT){
//            try (InputStream cert = context.getResources().openRawResource(R.raw.your_certificate)) {
//                ca = cf.generateCertificate(cert);
//            }
//        }
//
//        // Creating a KeyStore containing our trusted CAs
//        String keyStoreType = KeyStore.getDefaultType();
//        KeyStore keyStore   = KeyStore.getInstance(keyStoreType);
//        keyStore.load(null, null);
//        keyStore.setCertificateEntry("ca", ca);
//
//        // Creating a TrustManager that trusts the CAs in our KeyStore.
//        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
//        tmf.init(keyStore);
//
//        // Creating an SSLSocketFactory that uses our TrustManager
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, tmf.getTrustManagers(), null);

        return sslContext;
    }
}
