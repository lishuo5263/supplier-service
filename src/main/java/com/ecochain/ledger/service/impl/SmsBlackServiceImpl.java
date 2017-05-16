package com.ecochain.ledger.service.impl;


import com.ecochain.ledger.dao.DaoSupport;
import com.ecochain.ledger.service.SmsBlackService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("smsBlackService")
public class SmsBlackServiceImpl implements SmsBlackService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Override
    public boolean isBlackPhone(String phone, String versionNo) throws Exception {
        return (Integer)dao.findForObject("SmsBlackListMapper.isBlackPhone", phone)>0;
    }

}
