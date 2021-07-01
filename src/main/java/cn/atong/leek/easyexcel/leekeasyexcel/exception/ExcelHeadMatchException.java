package cn.atong.leek.easyexcel.leekeasyexcel.exception;

import com.alibaba.excel.exception.ExcelAnalysisException;

/**
 * @program: leek-easyexcel
 * @description: Excel表头匹配异常
 * @author: atong
 * @create: 2021-07-01 11:20
 */
public class ExcelHeadMatchException extends ExcelAnalysisException {

    public ExcelHeadMatchException () {
        super();
    }

    public ExcelHeadMatchException(String message){
        super(message);
    }

    public ExcelHeadMatchException(Throwable e){
        super(e);
    }

    public ExcelHeadMatchException(String message, Throwable e){
        super(message,e);
    }
}
