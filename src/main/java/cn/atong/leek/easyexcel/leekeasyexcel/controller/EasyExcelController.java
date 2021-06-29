package cn.atong.leek.easyexcel.leekeasyexcel.controller;

import cn.atong.leek.easyexcel.leekeasyexcel.domain.dto.ImportDto;
import cn.atong.leek.easyexcel.leekeasyexcel.domain.excel.UserTemplate;
import cn.atong.leek.easyexcel.leekeasyexcel.easy.ExcelListener;
import cn.atong.leek.easyexcel.leekeasyexcel.mapper.UserMapper;
import cn.atong.leek.easyexcel.leekeasyexcel.utils.FileDownloadUtil;
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
        InputStream excelInputStream = FileDownloadUtil.getStreamByUrl(importUrl);
        EasyExcel.read(excelInputStream, UserTemplate.class, new ExcelListener(userMapper)).sheet().doRead();
        return "success";
    }
}
