package com.ecochain.ledger.service.impl;

import com.ecochain.ledger.dao.DaoSupport;
import com.ecochain.ledger.model.PageData;
import com.ecochain.ledger.service.ShopSupplierService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component("shopSupplierService")
public class ShopSupplierServiceImpl implements ShopSupplierService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Override
    public PageData getSupplierByUserId(String user_id, String versionNo) throws Exception {
        return (PageData)dao.findForObject("com.qkl.wlsc.provider.dao.ShopSupplierMapper.getSupplierByUserId", user_id);
    }

}
