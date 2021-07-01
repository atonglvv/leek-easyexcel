package cn.atong.leek.easyexcel.leekeasyexcel.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: leek-easyexcel
 * @description: Excel Template
 * @author: atong
 * @create: 2021-06-29 17:05
 */
@Data
public class UserTemplate {

    @ExcelProperty("商品名称")
    private String name;

    @ExcelProperty("规格编码")
    private Integer code;

    @ExcelProperty("商品条码")
    private String barcode;

    @ExcelProperty("商品类目")
    private String productCategoryName;

    @ExcelProperty("商品分类")
    private String productCatalogName;

    @ExcelProperty("商品标签")
    private String productLabelName;

    @ExcelProperty("计量单位")
    private String productUnit;

    @ExcelProperty("规格1")
    private String specification1Key;

    @ExcelProperty("规格值")
    private String specification1Value;

    @ExcelProperty("规格2")
    private String specification2Key;

    @ExcelProperty("规格值")
    private String specification2Value;

    @ExcelProperty("规格3")
    private String specification3Key;

    @ExcelProperty("规格值")
    private String specification3Value;


    @ExcelProperty("成本价")
    private BigDecimal costPrice;

    @ExcelProperty("售卖价")
    private BigDecimal salePrice;

    @ExcelProperty("库存")
    private Integer stock;

    @ExcelProperty("库存预警")
    private Integer warningStock;

    @ExcelProperty("重量")
    private BigDecimal weight;

    @ExcelProperty("体积")
    private BigDecimal volume;

    @ExcelProperty("是否支持普通快递")
    private String isGeneralExpressType;

    @ExcelProperty("是否支持上门自提")
    private String isShopExtractType;

    @ExcelProperty("是否支持同城配送")
    private String isBusinessDispatchType;

    @ExcelProperty("运费模板")
    private String freightTemplateName;

    @ExcelProperty("是否包邮")
    private String isFreeFreight;

    @ExcelProperty("商品主图链接")
    private String image;

    @ExcelProperty("导入结果")
    private String importResult;

    @ExcelProperty("备注")
    private String comment;

}
