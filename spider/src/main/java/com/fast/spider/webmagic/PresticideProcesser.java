package com.fast.spider.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-08 10:47
 */
public class PresticideProcesser implements PageProcessor {
    public static final String BASE_URL = "https://www.icama.cn/PublicQuery/pesticide/certificateView.do?r=0.2415489078426829&id=";

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).addCookie("www.icama.cn", "JSESSIONID", "316D0FFF34777EB152D58D68308E7DAB");
    @Override
    public void process(Page page) {
        //农药名称
        page.putField("pesticideName", page.getHtml().xpath("//*[@id=\"pesticideName\"]/text()").toString());
        //农药登记证号
        page.putField("certificateCode", page.getHtml().xpath("//*[@id=\"certificateCode\"]/text()").toString());
        //有效期开始日期
        page.putField("validStartDay", page.getHtml().xpath("//*[@id=\"validStartDay\"]/text()").toString());
        //有效期截止日期
        page.putField("validLastDay", page.getHtml().xpath("//*[@id=\"validLastDay\"]/text()").toString());
        //农药类别
        page.putField("pesticideCategoryCode", page.getHtml().xpath("//*[@id=\"pesticideCategoryCode\"]/text()").toString());
        //剂型
        page.putField("dosageCode", page.getHtml().xpath("//*[@id=\"dosageCode\"]/text()").toString());
        //毒性
        page.putField("toxicityCode", page.getHtml().xpath("//*[@id=\"toxicityCode\"]/text()").toString());
        //状态
        page.putField("status", page.getHtml().xpath("//*[@id=\"status\"]/text()").toString());
        //总含量
        page.putField("totalContent", page.getHtml().xpath("//*[@id=\"totalContent\"]/text()").toString());
        //持有人名称
        page.putField("holderName", page.getHtml().xpath("//*[@id=\"holderName\"]/text()").toString());
        //持有人统一社会信用代码
        page.putField("creditCode", page.getHtml().xpath("//*[@id=\"creditCode\"]/text()").toString());


        //注意事项
        page.putField("attention", page.getHtml().xpath("//*[@id=\"attention\"]/text()").toString());
        //产品性能
        page.putField("productPerformance", page.getHtml().xpath("//*[@id=\"productPerformance\"]/text()").toString());
        //中毒急救
        page.putField("emergency", page.getHtml().xpath("//*[@id=\"emergency\"]/text()").toString());
        //存储和运输
        page.putField("storageTransportation", page.getHtml().xpath("//*[@id=\"storageTransportation\"]/text()").toString());
        //使用技术和使用方法
        page.putField("useMethod", page.getHtml().xpath("//*[@id=\"useMethod\"]/text()").toString());
        //其他说明
        page.putField("instructions", page.getHtml().xpath("//*[@id=\"instructions\"]/text()").toString());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new PresticideProcesser())
                //从"https://github.com/code4craft"开始抓
                .addUrl("https://www.icama.cn/PublicQuery/pesticide/certificateView.do?r=0.2415489078426829&id=705be76307d54982935016bbb8117503")
                .addPipeline(new JsonFilePipeline("E:\\webmagic\\"))
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }
}
