package cn.acyou.gemini.reptile.processor;

import cn.acyou.gemini.constant.GaminiConstant;
import cn.acyou.gemini.reptile.pipeline.ConsolePipeline;
import cn.acyou.gemini.reptile.pipeline.MutualMarketPipeline;
import cn.acyou.gemini.util.DateUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 证券时报网 - 沪市A股
 * @author youfang
 * @version [1.0.0, 2019-04-22 下午 03:56]
 **/
@Component
public class MutualMarketProcessor implements PageProcessor {

    @Autowired
    private MutualMarketPipeline mutualMarketPipeline;

    private final static String START_URL = "http://www.hkexnews.hk/sdw/search/mutualmarket_c.aspx?t=sh";

    /**
     * process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     */
    @Override
    public void process(Page page) {

        //代码
        List<String> code = page.getHtml().xpath("//*[@id=\"mutualmarket-result\"]/tbody/tr/td[1]/div[2]/text()").all();
        //简称
        List<String> name = page.getHtml().xpath("//*[@id=\"mutualmarket-result\"]/tbody/tr/td[2]/div[2]/text()").all();
        //columnOne
        List<String> columnOne = page.getHtml().xpath("//*[@id=\"mutualmarket-result\"]/tbody/tr/td[3]/div[2]/text()").all();
        //columnTwo
        List<String> columnTwo = page.getHtml().xpath("//*[@id=\"mutualmarket-result\"]/tbody/tr/td[4]/div[2]/text()").all();

        page.putField("code", code);
        page.putField("name", name);
        page.putField("columnOne", columnOne);
        page.putField("columnTwo", columnTwo);
        //表名
        String txtShareholdingDate = (String) page.getRequest().getExtra("txtShareholdingDate");
        txtShareholdingDate = txtShareholdingDate.replaceAll("/", "");
        String tableName =  GaminiConstant.MUTUAL_MARKET_SH + txtShareholdingDate;
        page.putField("tableName", tableName);


        Boolean isInit = (Boolean) page.getRequest().getExtra("init");
        if (isInit){
            //爬取近一年的数据
            List<String> dataStr = Lists.newArrayListWithCapacity(365);
            DateTime cur = new DateTime();
            for (int i=0; i<365; i++){
                dataStr.add(cur.toString("yyyy/MM/dd"));
                cur = cur.minusDays(1);
            }
            for (String s: dataStr){
                //额外的参数
                Request request = new Request(START_URL);
                Map<String, Object> extras = new HashMap<>();
                extras.put("init", true);
                extras.put("txtShareholdingDate", s);
                extras.put("__VIEWSTATE", "/wEPDwUJNjIxMTYzMDAwZGQ79IjpLOM+JXdffc28A8BMMA9+yg==");
                extras.put("__VIEWSTATEGENERATOR", "EC4ACD6F");
                extras.put("__EVENTVALIDATION", "/wEdAAdtFULLXu4cXg1Ju23kPkBZVobCVrNyCM2j+bEk3ygqmn1KZjrCXCJtWs9HrcHg6Q64ro36uTSn/Z2SUlkm9HsG7WOv0RDD9teZWjlyl84iRMtpPncyBi1FXkZsaSW6dwqO1N1XNFmfsMXJasjxX85jz8PxJxwgNJLTNVe2Bh/bcg5jDf8=");
                extras.put("today", "20190423");
                extras.put("sortBy", "stockcode");
                extras.put("sortDirection", "asc");
                extras.put("alertMsg", "");
                extras.put("btnSearch", "搜尋");
                request.setExtras(extras);
                request.setMethod(HttpConstant.Method.POST);
                request.setRequestBody(HttpRequestBody.form(extras, "utf-8"));
                request.setExtras(extras);
                request.setMethod(HttpConstant.Method.POST);
                page.addTargetRequest(request);
            }
        }
    }

    @Override
    public Site getSite() {
        /*
         * 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
         *
         * 第一部分关于爬虫的配置，包括编码、抓取间隔、超时时间、重试次数等，也包括一些模拟的参数，例如User Agent、cookie，以及代理的设置，
         * 重试次数为3次，抓取间隔为一秒。
         */
        return Site.me().setRetryTimes(3).setSleepTime(30000).setTimeOut(50000);
    }

    /**
     * 运行方法
     */
    public void start(String dataStr){

        Spider spider = Spider.create(new MutualMarketProcessor());
        //额外的参数
        Request request = new Request(START_URL);
        Map<String, Object> extras = new HashMap<>();
        extras.put("init", false);
        extras.put("txtShareholdingDate", dataStr);
        extras.put("__VIEWSTATE", "/wEPDwUJNjIxMTYzMDAwZGQ79IjpLOM+JXdffc28A8BMMA9+yg==");
        extras.put("__VIEWSTATEGENERATOR", "EC4ACD6F");
        extras.put("__EVENTVALIDATION", "/wEdAAdtFULLXu4cXg1Ju23kPkBZVobCVrNyCM2j+bEk3ygqmn1KZjrCXCJtWs9HrcHg6Q64ro36uTSn/Z2SUlkm9HsG7WOv0RDD9teZWjlyl84iRMtpPncyBi1FXkZsaSW6dwqO1N1XNFmfsMXJasjxX85jz8PxJxwgNJLTNVe2Bh/bcg5jDf8=");
        extras.put("today", DateUtil.getCurrentDateShortFormat());
        extras.put("sortBy", "stockcode");
        extras.put("sortDirection", "asc");
        extras.put("alertMsg", "");
        extras.put("btnSearch", "搜尋");
        request.setExtras(extras);
        request.setMethod(HttpConstant.Method.POST);
        request.setRequestBody(HttpRequestBody.form(extras, "utf-8"));
        spider.addRequest(request);
        spider.addPipeline(mutualMarketPipeline).thread(5).run();
    }
    /**
     * 运行方法
     */
    public void init(){

        Spider spider = Spider.create(new MutualMarketProcessor());
        //额外的参数
        Request request = new Request(START_URL);
        Map<String, Object> extras = new HashMap<>();
        extras.put("init", true);
        extras.put("txtShareholdingDate", DateUtil.getDateFormat(new Date(), "yyyy/MM/dd"));
        request.setExtras(extras);
        request.setMethod(HttpConstant.Method.POST);
        request.setRequestBody(HttpRequestBody.form(extras, "utf-8"));
        spider.addRequest(request);
        spider.addPipeline(mutualMarketPipeline).thread(5).run();
    }

    public static void main(String[] args) {
        Spider.create(new MutualMarketProcessor())
                .addUrl(START_URL)
                .addPipeline(new ConsolePipeline()).thread(5).run();
    }
}
