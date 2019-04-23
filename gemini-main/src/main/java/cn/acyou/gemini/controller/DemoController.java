package cn.acyou.gemini.controller;

import cn.acyou.gemini.constant.GaminiConstant;
import cn.acyou.gemini.entity.Boss;
import cn.acyou.gemini.mapper.BossMapper;
import cn.acyou.gemini.mapper.ReptileMapper;
import cn.acyou.gemini.mapper.SecuritiesTimesHuShenAMapper;
import cn.acyou.gemini.reptile.processor.SecuritiesTimesHuShenAProcessor;
import cn.acyou.gemini.util.DateUtil;
import cn.acyou.gemini.util.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
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
    private BossMapper bossMapper;
    @Autowired
    private ReptileMapper reptileMapper;
    @Autowired
    private SecuritiesTimesHuShenAProcessor huShenAProcessor;

    @ResponseBody
    @RequestMapping("index")
    public List<Boss> index(){
        List<Boss> bosses = bossMapper.selectList(null);
        bosses.forEach(x-> log.info(x.toString()));
        return bosses;
    }
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
}
