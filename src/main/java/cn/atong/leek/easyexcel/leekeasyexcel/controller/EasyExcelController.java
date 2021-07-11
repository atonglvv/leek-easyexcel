package cn.atong.leek.easyexcel.leekeasyexcel.controller;

import cn.atong.leek.easyexcel.leekeasyexcel.domain.dto.ImportDto;
import cn.atong.leek.easyexcel.leekeasyexcel.domain.excel.DownloadData;
import cn.atong.leek.easyexcel.leekeasyexcel.domain.excel.UserTemplate;
import cn.atong.leek.easyexcel.leekeasyexcel.easy.ExcelListener;
import cn.atong.leek.easyexcel.leekeasyexcel.exception.ExcelHeadMatchException;
import cn.atong.leek.easyexcel.leekeasyexcel.service.UserService;
import cn.atong.leek.easyexcel.leekeasyexcel.utils.FileDownloadUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.excel.exception.ExcelAnalysisException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * @program: leek-easyexcel
 * @description: EasyExcel Controller
 * @author: atong
 * @create: 2021-06-29 14:31
 */
@Slf4j
@RestController
public class EasyExcelController {

    @Autowired
    private UserService userService;

    @GetMapping("import")
    public String importExcel(ImportDto importDto) {
        String importUrl = importDto.getImportUrl();
        if (StringUtils.isBlank(importUrl)) {
            return "false";
        }
        InputStream excelInputStream = FileDownloadUtil.getStreamByUrl(importUrl);
        try {
            /**
             * head设为两行
             * sheet 从1开始
             */
            EasyExcel.read(excelInputStream, UserTemplate.class, new ExcelListener(userService))
                    .sheet(3).headRowNumber(2).doRead();
        }catch (ExcelHeadMatchException excelHeadMatchException) {
            return "fail";
        }catch (ExcelAnalysisException excelAnalysisException) {
            return excelAnalysisException.getMessage();
        }
        return "success";
    }


    @GetMapping("downloadFailedUsingJson")
    public void downloadFailedUsingJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("商品-业务属性0705", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            File excelFile = new File("src\\main\\resources\\excel\\商品-业务属性0705.xlsx");
            InputStream inputStream = new FileInputStream(excelFile);

//            int ch;
//            while ((ch = inputStream.read()) != -1) {
//                response.getOutputStream().write(ch);
//            }


            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), DownloadData.class).withTemplate(inputStream).needHead(false).relativeHeadRowIndex(2).
                    autoCloseStream(Boolean.FALSE).sheet(3)
                    .doWrite(data());



        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }


    @GetMapping("downloadFailedUsingJsona")
    public void downloadFailedUsingJsona(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), DownloadData.class).autoCloseStream(Boolean.FALSE).sheet("模板")
                    .doWrite(data());
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }



    private List<DownloadData> data() {
        List<DownloadData> list = new ArrayList<DownloadData>();
        for (int i = 0; i < 10; i++) {
            DownloadData data = new DownloadData();
            data.setResult("成功");
            data.setComment("成功了");
            list.add(data);
        }
        return list;
    }


}
