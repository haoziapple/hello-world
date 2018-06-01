package com.fast.spider.controller;

import com.fast.spider.dao.PresticideRepo;
import com.fast.spider.icama.IcamaHttpInvoker;
import com.fast.spider.icama.IcamaHttpTemplate;
import com.fast.spider.icama.PageInfo;
import com.fast.spider.icama.PresticideDetail;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-07 14:28
 */
@RestController
@RequestMapping("/icama")
public class IcamaController {
    private static final Logger log = LoggerFactory.getLogger(IcamaController.class);

    @Autowired
    private IcamaHttpInvoker httpInvoker;
    @Autowired
    private IcamaHttpTemplate httpTemplate;
    @Autowired
    private PresticideRepo presticideRepo;

    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    @ApiOperation("获取证书列表，按有效开始日期降序排序")
    public Page<PresticideDetail> getList(Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "validStartDay");
        return presticideRepo.findAll(PageRequest.of(page, size, sort));
    }

    @RequestMapping(value = "/getListByCode", method = RequestMethod.GET)
    @ApiOperation("获取证书列表，按农药登记证号模糊匹配")
    public Page<PresticideDetail> getListByCode(Integer page, Integer size, String certificateCode) {
        Sort sort = new Sort(Sort.Direction.DESC, "validStartDay");
        return presticideRepo.findAllByCertificateCodeLike(PageRequest.of(page, size, sort), certificateCode);
    }

    @RequestMapping(value = "/getListWithoutPage", method = RequestMethod.GET)
    @ApiOperation("获取证书列表，按农药登记证号模糊匹配，无分页，证号至少6位")
    public List<PresticideDetail> getListWithoutPage(String certificateCode) {
        if (certificateCode == null || certificateCode.length() < 6) {
            throw new IllegalArgumentException("证号至少6位");
        }
        return presticideRepo.findByCertificateCodeLike(certificateCode);
    }

    @GetMapping("/printAll")
    public void printAll() throws Exception{
        List<PresticideDetail> list = presticideRepo.findAll();
        File file = new File("E:/pesticide.txt");
        OutputStream os  = new FileOutputStream(file);
        for (PresticideDetail detail : list) {
            os.write((detail.toString()+ '\n').getBytes());
        }
    }


    /**
     * 初始化证书列表并保存到mongodb
     *
     * @param startPage
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/initialList", method = RequestMethod.GET)
    public PresticideDetail initialList(Integer startPage) throws Exception {
        String queryJson = "{\"groupOp\":\"AND\",\"rules\":[{\"name\":\"region_nation\",\"field\":\"countryCode\",\"op\":\"eq\",\"type\":\"int\",\"data\":\"1029\"},{\"field\":\"status\",\"op\":\"eq\",\"type\":\"int\",\"data\":\"1\"}]}";
        PageInfo<PresticideDetail> pageInfo = httpTemplate.search(false, 1525673310261L, 50, startPage, "asc", 2, queryJson);
        Iterable<PresticideDetail> iterator = pageInfo.getRows();
        presticideRepo.saveAll(iterator);

        for (int i = startPage + 1; i <= pageInfo.getTotal(); i++) {
            Thread.sleep(1000L);
            PageInfo<PresticideDetail> list = httpTemplate.search(false, 1525673310261L, 50, i, "asc", 2, queryJson);
            presticideRepo.saveAll(list.getRows());
        }
        return pageInfo.getRows().get(0);
    }

//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    public void test() {
//        List<PresticideDetail> list = presticideRepo.findAll();
//        for (PresticideDetail detail : list) {
//            log.warn(detail.toString());
//        }
//    }

}
