package cn.atong.leek.easyexcel.leekeasyexcel.easy;

import cn.atong.leek.easyexcel.leekeasyexcel.domain.excel.UserTemplate;
import cn.atong.leek.easyexcel.leekeasyexcel.service.UserService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: leek-easyexcel
 * @description: excel Listener
 * @author: atong
 * @create: 2021-06-29 17:17
 */
@Slf4j
public class ExcelListener extends AnalysisEventListener<UserTemplate> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<UserTemplate> list = new ArrayList<UserTemplate>();

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
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            dataProcess();
            // 存储完成清理 list
            list.clear();
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
     * @description 校验表头是否与模板一致
     * @param headMap: Map
     * @param context: AnalysisContext
     * @return void
     * @author atong
     * @date 2021/7/1 10:45
     * @version 1.0.0.1
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
        // 根据自己的情况去做表头的判断即可
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
