package cn.atong.leek.easyexcel.leekeasyexcel.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @program: leek-easyexcel
 * @description: Excel Download
 * @author: atong
 * @create: 2021-07-11 10:29
 */
@Data
public class DownloadData {
    @ExcelProperty(index = 10)
    private String result;
    @ExcelProperty(index = 11)
    private String comment;
}

