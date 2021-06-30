package cn.atong.leek.easyexcel.leekeasyexcel.controller;

import cn.atong.leek.easyexcel.leekeasyexcel.domain.dto.ImportDto;
import cn.atong.leek.easyexcel.leekeasyexcel.domain.excel.UserTemplate;
import cn.atong.leek.easyexcel.leekeasyexcel.easy.ExcelListener;
import cn.atong.leek.easyexcel.leekeasyexcel.service.UserService;
import cn.atong.leek.easyexcel.leekeasyexcel.utils.FileDownloadUtil;
import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

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
        InputStream excelInputStream = FileDownloadUtil.getStreamByUrl(importUrl);
        EasyExcel.read(excelInputStream, UserTemplate.class, new ExcelListener(userService)).sheet().doRead();
        return "success";
    }
}
