package cn.atong.leek.easyexcel.leekeasyexcel.controller;

import cn.atong.leek.easyexcel.leekeasyexcel.domain.dto.ImportDto;
import cn.atong.leek.easyexcel.leekeasyexcel.domain.excel.UserTemplate;
import cn.atong.leek.easyexcel.leekeasyexcel.easy.ExcelListener;
import cn.atong.leek.easyexcel.leekeasyexcel.exception.ExcelHeadMatchException;
import cn.atong.leek.easyexcel.leekeasyexcel.service.UserService;
import cn.atong.leek.easyexcel.leekeasyexcel.utils.FileDownloadUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.exception.ExcelAnalysisException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        if (StringUtils.isBlank(importUrl)) {
            return "false";
        }
        InputStream excelInputStream = FileDownloadUtil.getStreamByUrl(importUrl);
        try {
            //head设为两行
            EasyExcel.read(excelInputStream, UserTemplate.class, new ExcelListener(userService))
                    .sheet().headRowNumber(2).doRead();
        }catch (ExcelHeadMatchException excelHeadMatchException) {
            return "fail";
        }catch (ExcelAnalysisException excelAnalysisException) {
            return "too large";
        }
        return "success";
    }
}
