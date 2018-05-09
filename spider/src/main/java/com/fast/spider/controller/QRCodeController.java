package com.fast.spider.controller;

import com.fast.spider.service.PssQueryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-09 15:58
 */
@RestController
@RequestMapping("/qrcode")
public class QRCodeController {

    @Autowired
    private PssQueryService pssQueryService;

    @ApiOperation("根据QRCode查询农药产品信息")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ActionResult<ProductInfo> query(@RequestParam("qrUrl") String qrUrl) {
        if (qrUrl.startsWith(PssQueryService.WEB_URL)) {
            String traceNo = qrUrl.substring(PssQueryService.WEB_URL.length());
            return new ActionResult(pssQueryService.queryProduct(traceNo));
        } else {
            return new ActionResult(100011, "暂不支持该厂家查询");
        }
    }
}
