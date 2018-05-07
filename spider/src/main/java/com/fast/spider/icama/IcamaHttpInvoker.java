package com.fast.spider.icama;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-07 14:05
 */
@FeignClient(value = "icama", url = "https://www.icama.cn/PublicQuery")
public interface IcamaHttpInvoker {
    @RequestMapping(value = "/pesticide/jqGrid.do", method = RequestMethod.GET)
    PageInfo<PresticideDetail> search(@RequestParam("_search") Boolean search,
                                      @RequestParam("nd") Long nd,
                                      @RequestParam("rows") Integer rows,
                                      @RequestParam("page") Integer page,
                                      @RequestParam("sord") String sort,
                                      @RequestParam("flage") Integer flage,
                                      @RequestParam("queryJson") String queryJson);
}
