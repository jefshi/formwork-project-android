package com.csp.formwork.network.okhttp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.zqyl.utillib.LogCat;
import com.zqylapp.lianxin.SaveDataApplication;
import com.zqylapp.utils.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Intent 相关工具类
 * Created by csp on 2018/06/18.
 * Modified by csp on 2019/03/15.
 *
 * @version 1.0.5
 */
public class BaseRetrofit {

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final int TYPE_TEST = -10;

    private Retrofit mRetrofit;

    private static class Instance {
        private static final BaseRetrofit instance = new BaseRetrofit();
    }

    private BaseRetrofit() {
        OkHttpClient client = newOkHttpClient(true, false);
        String url = "http://172.16.10.124:9081"; // TODO ???
        // mRetrofit = newRetrofit(client, Constant.URL.MAIN_URL);
        mRetrofit = newRetrofit(client, url);
    }

    private BaseRetrofit(int type) {
        OkHttpClient client;
        String url;

        switch (type) {
            case TYPE_TEST:
                client = newOkHttpClient(true, false);
                url = "http://172.16.10.124:9081"; // TODO ???
                break;
            default:
                client = newOkHttpClient(true, false);
                url = ""; // TODO Constant.URL.MAIN_URL;
                break;
        }
        mRetrofit = newRetrofit(client, url);
    }

    public static BaseRetrofit getInstance() {
        return Instance.instance;
    }

    public static BaseRetrofit getInstance(int type) {
        return Instance.instance;
    }

    /**
     * @return OkHttpClient
     */
    private OkHttpClient newOkHttpClient(boolean useCookie, boolean useHttps) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // HTTP 日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(@NonNull String message) {
                        if (Constant.Debug.NETWORK_LOG)
                            LogCat.w("（网络请求）" + message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY);

        // Header 拦截器
        if (useCookie) {
            Interceptor headerInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    SaveDataApplication application = (SaveDataApplication) SaveDataApplication.context;
                    String sessionid = application.getSessionId();

                    Request.Builder builder = chain.request().newBuilder();
                    if (sessionid != null && !sessionid.isEmpty())
                        builder.addHeader("Cookie", sessionid);

                    builder.addHeader("Connection", "Keep-Alive");
                    Request request = builder.build();
                    return chain.proceed(request);
                }
            };

            builder.addInterceptor(headerInterceptor);
        }

        builder.addInterceptor(httpLoggingInterceptor)
                .connectTimeout(Constant.Network.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constant.Network.READ_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        if (!useHttps)
            return builder.build();

        // HTTPS 验证
        final Context context = SaveDataApplication.context;
        X509TrustManager trustManager = null;
        SSLSocketFactory sslSocketFactory = null;
        InputStream inputStream;
        try {
            inputStream = context.getAssets().open("CFCA_TEST_CS_CA.cer"); // 得到证书的输入流
            trustManager = SslUtil.trustManagerForCertificates(inputStream);//以流的方式读入证书
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Throwable t) {
            LogCat.printStackTrace(t);
        }

        if (sslSocketFactory != null && trustManager != null) {
            builder.sslSocketFactory(sslSocketFactory, trustManager)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            try {
                                X509Certificate[] certificates = session.getPeerCertificateChain();
                                if (certificates == null || certificates.length == 0)
                                    return false;

                                String certName = "yljr.cer";
                                InputStream inputStream = context.getResources().getAssets().open(certName);
                                X509Certificate cert = X509Certificate.getInstance(inputStream);

                                if (cert != null)
                                    return cert.equals(certificates[0]);
                            } catch (Throwable t) {
                                LogCat.printStackTrace(t);
                            }
                            return false;
                        }
                    });

        }
        return builder.build();
    }

    /**
     * @return Retrofit
     */
    private Retrofit newRetrofit(OkHttpClient client, String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    public NetService createNetService() {
        return mRetrofit.create(NetService.class);
    }
}
