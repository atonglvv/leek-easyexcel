package cn.atong.leek.easyexcel.leekeasyexcel.easy;

import cn.atong.leek.easyexcel.leekeasyexcel.domain.excel.UserTemplate;
import cn.atong.leek.easyexcel.leekeasyexcel.exception.ExcelHeadMatchException;
import cn.atong.leek.easyexcel.leekeasyexcel.service.UserService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: leek-easyexcel
 * @description: excel Listener
 * @author: atong
 * @create: 2021-06-29 17:17
 */
@Slf4j
public class ExcelListener extends AnalysisEventListener<UserTemplate> {
    /**
     * 每隔500条做业务处理，建议不超过3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 500;
    /** 导入的数据 */
    List<UserTemplate> list = new ArrayList<UserTemplate>();
    /** 表头行数 */
    AtomicInteger headNumber = new AtomicInteger(1);

    /**
     * 处理导入数据业务逻辑的 Service
     */
    private UserService userService;

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     * @param userService userService
     */
    public ExcelListener(UserService userService) {
        this.userService = userService;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data
     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(UserTemplate data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        // 达到BATCH_COUNT了，需要去处理一次业务数据，防止OOM
        if (list.size() >= BATCH_COUNT) {
            // 存储完成清理 list
            list.clear();
            throw new ExcelAnalysisException("导入的数据超过500条，请将导入数据控制在500条以内");
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        dataProcess();
        log.info("所有数据解析完成！");
    }


    /**
     * @description 重写方法,校验表头是否与模板一致
     * @param headMap: Map
     * @param context: AnalysisContext
     * @return void
     * @author atong
     * @date 2021/7/1 10:45
     * @version 1.0.0.1
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) throws ExcelHeadMatchException{
        if (headNumber.get() == 1) {
            super.invokeHeadMap(headMap, context);
            headNumber.incrementAndGet();
            return;
        }
        log.info("表头数据:{}", JSON.toJSONString(headMap));
        // 模板表头
        String[] tips = {
                "商品名称", "规格编码", "商品条码", "商品类目", "商品分类",
                "商品标签", "计量单位", "规格1", "规格值", "规格2",
                "规格值", "规格3", "规格值", "成本价", "售卖价",
                "售价区间\n最小值", "售价区间\n最大值", "重量", "体积", "是否支持普通快递",
                "是否支持上门自提", "是否支持同城配送", "运费模板", "是否包邮", "商品主图链接",
                "导入结果", "备注"};
        //如果 headMap为空或者 headMap跟tips 数量不一致, 则直接throw
        if (MapUtils.isEmpty(headMap) || tips.length != headMap.size()) {
            throw new ExcelHeadMatchException("上传文件格式有误，请下载最新模板按要求填写后上传");
        }
        for (int i = 0; i < headMap.size(); i++) {
            String headValue = headMap.get(i);
            //如果headValue为空 则直接throw
            if (StringUtils.isBlank(headValue)) {
                throw new ExcelHeadMatchException("上传文件格式有误，请下载最新模板按要求填写后上传");
            }
            //如果headValue的值不等于tips[i] 则直接throw
            if (! headValue.equals(tips[i])) {
                throw new ExcelHeadMatchException("上传文件格式有误，请下载最新模板按要求填写后上传");
            }
        }
    }

    /**
     * 导入数据处理
     */
    private void dataProcess() {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        log.info("{}条数据，开始处理导入数据！", list.size());
        log.info("导入的数据为{}", JSON.toJSONString(list));
        userService.importDataProcess(list);
        log.info("处理导入数据成功！");
    }
}
