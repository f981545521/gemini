package cn.acyou.gemini.reptile.pipeline;

import cn.acyou.gemini.entity.MutualMarket;
import cn.acyou.gemini.exception.ServiceException;
import cn.acyou.gemini.mapper.MutualMarketMapper;
import cn.acyou.gemini.mapper.ReptileMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;

/**
 * @author youfang
 * @version [1.0.0, 2019-04-22 下午 04:25]
 **/
@Slf4j
@Component
public class MutualMarketPipeline implements Pipeline {
    // 每批commit的个数
    private static final int batchCount = 100;

    @Autowired
    private MutualMarketMapper mutualMarketMapper;
    @Autowired
    private ReptileMapper reptileMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> allInfo = resultItems.getAll();
        System.out.println("数据量：" + allInfo.size() + "条");
        String tableName = (String) allInfo.get("tableName");
        //创建表
        List<String> exists = reptileMapper.checkTableExists(tableName);
        if (exists.size() == 0){
            reptileMapper.createMutualMarketTable(tableName);
        }else {
            Long count = reptileMapper.getTableTotalCount(tableName);
            throw new ServiceException("已经存在数据：" + count + "条");
        }

        List<String> codeList = (List) allInfo.get("code");
        List<String> nameList = (List) allInfo.get("name");
        List<String> columnOneList = (List) allInfo.get("columnOne");
        List<String> columnTwoList = (List) allInfo.get("columnTwo");

        List<MutualMarket> addList = Lists.newArrayList();
        //选取偏移量为0的集合
        for (int i = 0; i<codeList.size(); i++){
            MutualMarket huShenA = new MutualMarket();
            huShenA.setCode(codeList.get(i));
            huShenA.setName(nameList.get(i));
            huShenA.setColumnOne(columnOneList.get(i));
            huShenA.setColumnTwo(columnTwoList.get(i));
            addList.add(huShenA);
            //批量添加
            if (addList.size() >= batchCount){
                mutualMarketMapper.insertBatch(tableName, addList);
                addList.clear();
            }
        }
        mutualMarketMapper.insertBatch(tableName, addList);
        addList.clear();
        log.info("执行结束");
    }

}
