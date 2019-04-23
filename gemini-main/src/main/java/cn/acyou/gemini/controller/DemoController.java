package cn.acyou.gemini.controller;

import cn.acyou.gemini.constant.GaminiConstant;
import cn.acyou.gemini.mapper.ReptileMapper;
import cn.acyou.gemini.reptile.processor.MutualMarketProcessor;
import cn.acyou.gemini.reptile.processor.SecuritiesTimesHuShenAProcessor;
import cn.acyou.gemini.util.DateUtil;
import cn.acyou.gemini.util.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2019-04-12 上午 09:36]
 **/
@Controller
@Slf4j
@RequestMapping("demo")
public class DemoController {
    @Autowired
    private ReptileMapper reptileMapper;
    @Autowired
    private SecuritiesTimesHuShenAProcessor huShenAProcessor;
    @Autowired
    private MutualMarketProcessor mutualMarketProcessor;

    @ResponseBody
    @RequestMapping("start")
    public ResultInfo start(){
        String dateStr = DateUtil.getDateShortFormat(new Date());
        String tableName = GaminiConstant.SECURITIES_TIMES_HS_A + dateStr;
        List<String> exists = reptileMapper.checkTableExists(tableName);
        if (exists.size() == 0){
            reptileMapper.createSecuritiesTimesHSATable(dateStr);
            huShenAProcessor.start();
            return ResultInfo.success("操作成功");
        }else {
            Long count = reptileMapper.getTableTotalCount(tableName);
            return ResultInfo.success("已经存在数据：" + count + "条");
        }
    }

    @ResponseBody
    @RequestMapping("start2")
    public ResultInfo start2(){
        String dateStr = "20180427";
        String tableName = GaminiConstant.MUTUAL_MARKET_SH + dateStr;
        List<String> exists = reptileMapper.checkTableExists(tableName);
        if (exists.size() == 0){
            reptileMapper.createMutualMarketTable(tableName);
            mutualMarketProcessor.start("2018/04/27");
            return ResultInfo.success("操作成功");
        }else {
            Long count = reptileMapper.getTableTotalCount(tableName);
            return ResultInfo.success("已经存在数据：" + count + "条");
        }
    }
    @ResponseBody
    @RequestMapping("init")
    public ResultInfo init(){
        mutualMarketProcessor.init();
        return ResultInfo.success("操作成功");
    }


}
