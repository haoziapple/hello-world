package com.haozi.trymybatis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public ThirdpartyOauthDO item(int id) {
        ThirdpartyOauthDO thirdpartyOauthDO = thirdpartyOauthMapper.item(id);
        return thirdpartyOauthDO;
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public ThirdpartyOauthDO updateRemarkById(int id, String remark) {
        ThirdpartyOauthDO thirdpartyOauthDO = thirdpartyOauthMapper.item(id);
        Optional.ofNullable(thirdpartyOauthDO).ifPresent(a -> {
                    System.out.println(a);
                    a.setRemark(remark);
                    thirdpartyOauthMapper.updateRemarkById(a);
                }
        );

        return thirdpartyOauthDO;
    }

    @GetMapping("/list")
    public List list() {
        return thirdpartyOauthMapper.list();
    }

    @GetMapping("/page")
    public PageInfo page(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(thirdpartyOauthMapper.list());
    }
}
