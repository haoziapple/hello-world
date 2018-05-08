package com.fast.spider.webmagic;


import com.fast.spider.dao.PresticideCertRepo;
import com.fast.spider.icama.PresticideCert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-08 13:55
 */
public class PresticidePipeline implements Pipeline {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private PresticideCertRepo presticideCertRepo;

    public PresticidePipeline(PresticideCertRepo presticideCertRepo) {
        this.presticideCertRepo = presticideCertRepo;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        PresticideCert cert = new PresticideCert();
        String id = resultItems.getRequest().getUrl().replaceAll(PresticideProcesser.BASE_URL, "");
        cert.setId(id);
        cert.setAttention(resultItems.get("attention"));
        cert.setProductPerformance(resultItems.get("productPerformance"));
        cert.setEmergency(resultItems.get("emergency"));
        cert.setStorageTransportation(resultItems.get("storageTransportation"));
        cert.setUseMethod(resultItems.get("useMethod"));
        cert.setInstructions(resultItems.get("instructions"));
        this.presticideCertRepo.save(cert);
    }
}
