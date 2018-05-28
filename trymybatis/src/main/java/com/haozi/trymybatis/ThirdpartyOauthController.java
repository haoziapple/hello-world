package com.haozi.trymybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-28 14:55
 */
@RestController
@RequestMapping("/third")
public class ThirdpartyOauthController {
    @Autowired
    private ThirdpartyOauthMapper thirdpartyOauthMapper;

    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public ThirdpartyOauthDO item(int id) {
        return thirdpartyOauthMapper.item(id);
    }
}
