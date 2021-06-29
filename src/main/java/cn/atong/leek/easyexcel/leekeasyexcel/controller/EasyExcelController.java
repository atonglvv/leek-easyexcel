package cn.atong.leek.easyexcel.leekeasyexcel.controller;

import cn.atong.leek.easyexcel.leekeasyexcel.domain.dto.ImportDto;
import cn.atong.leek.easyexcel.leekeasyexcel.domain.excel.UserTemplate;
import cn.atong.leek.easyexcel.leekeasyexcel.easy.ExcelListener;
import cn.atong.leek.easyexcel.leekeasyexcel.mapper.UserMapper;
import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * @description: EasyExcel Controller
 * @author: atong
 * @create: 2021-06-29 14:31
 */
@Slf4j
@RestController
public class EasyExcelController {

    private UserMapper userMapper;

    @GetMapping()
    public String importExcel(ImportDto importDto) {
        String importUrl = importDto.getImportUrl();
        InputStream excelInputStream = getStreamByUrl(importUrl);
        EasyExcel.read(excelInputStream, UserTemplate.class, new ExcelListener(userMapper)).sheet().doRead();
        return "success";
    }

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
