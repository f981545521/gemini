package cn.acyou.gemini.reptile.processor;

import cn.acyou.gemini.reptile.pipeline.SecuritiesTimesHuShenAPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * 证券时报网 - 沪市A股
 * @author youfang
 * @version [1.0.0, 2019-04-22 下午 03:56]
 **/
@Component
public class SecuritiesTimesHuShenAProcessor implements PageProcessor {

    @Autowired
    private SecuritiesTimesHuShenAPipeline securitiesTimesHuShenAPipeline;

    private final static String START_URL = "http://data.stcn.com/common/data/fiveDayTurnVolume/fsFiveDayTurnvolumeMore.html";

    /**
     * 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
     *
     * 第一部分关于爬虫的配置，包括编码、抓取间隔、超时时间、重试次数等，也包括一些模拟的参数，例如User Agent、cookie，以及代理的设置，
     * 重试次数为3次，抓取间隔为一秒。
     */
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    /**
     * process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     */
    @Override
    public void process(Page page) {

        //代码
        List<String> code = page.getHtml().xpath("//*[@id=\"main\"]/div[1]/div[2]/table/tbody/tr/td[1]/text()").all();
        //简称
        List<String> abbreviation = page.getHtml().xpath("//*[@id=\"main\"]/div[1]/div[2]/table/tbody/tr/td[2]/text()").all();
        //今日 - 2018.11.16
        List<String> clinchToday = page.getHtml().xpath("//*[@id=\"main\"]/div[1]/div[2]/table/tbody/tr/td[3]/text()").all();

        //columnOne
        List<String> columnOne = page.getHtml().xpath("//*[@id=\"main\"]/div[1]/div[2]/table/tbody/tr/td[4]/text()").all();
        //columnTwo
        List<String> columnTwo = page.getHtml().xpath("//*[@id=\"main\"]/div[1]/div[2]/table/tbody/tr/td[5]/text()").all();
        //columnThree
        List<String> columnThree = page.getHtml().xpath("//*[@id=\"main\"]/div[1]/div[2]/table/tbody/tr/td[6]/text()").all();
        //columnFour
        List<String> columnFour = page.getHtml().xpath("//*[@id=\"main\"]/div[1]/div[2]/table/tbody/tr/td[7]/text()").all();

        page.putField("code", code);
        page.putField("abbreviation", abbreviation);
        page.putField("clinchToday", clinchToday);
        page.putField("columnOne", columnOne);
        page.putField("columnTwo", columnTwo);
        page.putField("columnThree", columnThree);
        page.putField("columnFour", columnFour);

    }

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * 运行方法
     */
    public void start(){
        Spider.create(new SecuritiesTimesHuShenAProcessor())
                .addUrl(START_URL)
                .addPipeline(securitiesTimesHuShenAPipeline).thread(5).run();
    }

    public static void main(String[] args) {
        Spider.create(new SecuritiesTimesHuShenAProcessor())
                .addUrl(START_URL)
                .addPipeline(new SecuritiesTimesHuShenAPipeline()).thread(5).run();
    }
}
