package com.fast.spider.controller;

import com.fast.spider.dao.PresticideCertRepo;
import com.fast.spider.dao.PresticideRepo;
import com.fast.spider.icama.PresticideDetail;
import com.fast.spider.webmagic.PresticidePipeline;
import com.fast.spider.webmagic.PresticideProcesser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

import java.util.List;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-08 10:48
 */
@Controller
@RequestMapping("/webmagic")
public class WebMagicController {
    @Autowired
    private PresticideRepo presticideRepo;
    @Autowired
    private PresticideCertRepo presticideCertRepo;


    @RequestMapping(value = "startPresticide", method = RequestMethod.GET)
    public void startPresticide() {
        Spider spider = Spider.create(new PresticideProcesser())
                //保存到本地文件
                .addPipeline(new JsonFilePipeline("E:\\webmagic\\"))
                // 保存到mongodb
                .addPipeline(new PresticidePipeline(presticideCertRepo))
                //开启5个线程抓取
                .thread(5);
        List<PresticideDetail> list = presticideRepo.findAll();
        for (PresticideDetail detail : list) {
            spider.addUrl(PresticideProcesser.BASE_URL + detail.getId());
        }
        //启动爬虫
        spider.run();
    }
}
