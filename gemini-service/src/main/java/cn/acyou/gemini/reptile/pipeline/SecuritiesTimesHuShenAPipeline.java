package cn.acyou.gemini.reptile.pipeline;

import cn.acyou.gemini.constant.GaminiConstant;
import cn.acyou.gemini.entity.SecuritiesTimesHuShenA;
import cn.acyou.gemini.mapper.SecuritiesTimesHuShenAMapper;
import cn.acyou.gemini.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author youfang
 * @version [1.0.0, 2019-04-22 下午 04:25]
 **/
@Slf4j
@Component
public class SecuritiesTimesHuShenAPipeline implements Pipeline {

    private static final int batchCount = 100;// 每批commit的个数

    @Autowired
    private SecuritiesTimesHuShenAMapper shenAMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        String dateStr = DateUtil.getDateShortFormat(new Date());
        String tableName = GaminiConstant.SECURITIES_TIMES_HS_A + dateStr;
        Map<String, Object> allInfo = resultItems.getAll();
        System.out.println("数据量：" + allInfo.size() + "条");
        //偏移2
        List<String> codeList = (List) allInfo.get("code");
        List<String> abbreviationList = (List) allInfo.get("abbreviation");
        List<String> clinchTodayList = (List) allInfo.get("clinchToday");
        //偏移1
        List<String> columnOneList = (List) allInfo.get("columnOne");
        List<String> columnTwoList = (List) allInfo.get("columnTwo");
        //偏移0
        List<String> columnThreeList = (List) allInfo.get("columnThree");
        List<String> columnFourList = (List) allInfo.get("columnFour");

        List<SecuritiesTimesHuShenA> addList = Lists.newArrayList();
        for (int i = 0; i<columnThreeList.size()-1; i++){//选取偏移量为0的集合
            int offset2 = i + 2;
            int offset1 = i + 1;
            SecuritiesTimesHuShenA huShenA = new SecuritiesTimesHuShenA();
            huShenA.setCode(codeList.get(offset2));
            huShenA.setAbbreviation(abbreviationList.get(offset2));
            try {
                if (NumberUtils.isDigits(clinchTodayList.get(offset2))){
                    huShenA.setClinchToday(new BigDecimal(clinchTodayList.get(offset2)));
                    huShenA.setColumnOne(new BigDecimal(columnOneList.get(offset1)));
                    huShenA.setColumnTwo(new BigDecimal(columnTwoList.get(offset1)));
                    huShenA.setColumnThree(new BigDecimal(columnThreeList.get(i)));
                    huShenA.setColumnFour(new BigDecimal(columnFourList.get(i)));
                } else {
                    continue;
                }
                addList.add(huShenA);

                //批量添加
                if (addList.size() >= batchCount){
                    shenAMapper.insertBatch(tableName, addList);
                    addList.clear();
                }
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
        shenAMapper.insertBatch(tableName, addList);
        addList.clear();
        log.info("执行结束");
    }

}
