package cn.atong.leek.easyexcel.leekeasyexcel.utils;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

/**
 * @program: leek-easyexcel
 * @description: file Util
 * @author: atong
 * @create: 2021-06-29 22:08
 */
@Slf4j
public class FileDownloadUtil {

    /**
     * @description 根据url获取InputStream
     * @param url: excel url
     * @return java.io.InputStream
     * @author atong
     * @date 2021/6/29 15:01
     * @version 1.0.0.1
     */
    public static InputStream getStreamByUrl(String url) {
        long startTime = System.currentTimeMillis();
        HttpURLConnection conn;
        InputStream is = null;
        try {
            trustAllHosts();
            URL realUrl = new URL(url);
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.connect();
            is = conn.getInputStream();
        }catch (Exception e) {
            e.printStackTrace();
        }
        long entTime = System.currentTimeMillis();
        log.info("商品导入下载获取excel耗时时长：" + (entTime - startTime));
        return is;
    }

    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        }};
        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
