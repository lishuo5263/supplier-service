package com.ecochain.ledger.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ecochain.ledger.dao.DaoSupport;
import com.ecochain.ledger.model.Page;
import com.ecochain.ledger.model.PageData;
import com.ecochain.ledger.service.AccDetailService;
import com.ecochain.ledger.service.ShopOrderGoodsService;
import com.ecochain.ledger.service.ShopOrderInfoService;
import com.ecochain.ledger.util.DateUtil;
import com.ecochain.ledger.util.Logger;
@Component("accDetailService")
public class AccDetailServiceImpl implements AccDetailService {
    
    private final Logger logger = Logger.getLogger(AccDetailServiceImpl.class);
    
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Resource
    private ShopOrderInfoService shopOrderInfoService;
    @Resource
    private ShopOrderGoodsService shopOrderGoodsService;
    
    @Override
    public boolean insertSelective(PageData pd, String versionNo) throws Exception {
        return (Integer)dao.save("AccDetailMapper.insertSelective", pd)>0;
    }

    @Override
    public PageData selectById(Integer id, String versionNo) throws Exception {
        return (PageData)dao.findForObject("AccDetailMapper.selectById", id);
    }

    @Override
    public boolean updateByIdSelective(PageData pd, String versionNo) throws Exception {
        return (Integer)dao.update("AccDetailMapper.updateByIdSelective", pd)>0;
    }
    @Override
    public PageData listPageAcc(Page page, String versionNo) throws Exception {
        List<PageData> list = (List<PageData>)dao.findForList("AccDetailMapper.listPageAcc", page);
        PageData tpd = new PageData();
        tpd.put("list", list);
        tpd.put("page", page);
        return tpd;
    }

    @Override
    public boolean insertStoreDownReturn(PageData pageData, String s) throws Exception {
        return (Integer)dao.update("AccDetailMapper.insertStoreDownReturn", pageData)>0;
    }

    @Override
    public List<PageData> getAccList(PageData pd, String versionNo) throws Exception {
        return (List<PageData>)dao.findForList("AccDetailMapper.getAccList", pd);
    }

    @Override
    public PageData getSubTotal(PageData pd, String versionNo) throws Exception {
        return (PageData)dao.findForObject("AccDetailMapper.getSubTotal", pd);
    }

    @Override
    public List<PageData> getAccTypeList(PageData pd, String versionNo) throws Exception {
        return (List<PageData>)dao.findForList("AccDetailMapper.getAccTypeList", pd);
    }

    @Override
    public boolean accDetailSummary(PageData pd, String versionNo) throws Exception {
        return (Integer)dao.update("AccDetailMapper.accDetailSummary", pd)>0;
    }

    @Override
    public boolean updateCntflag(PageData pd, String versionNo) throws Exception {
        return (Integer)dao.update("AccDetailMapper.updateCntflag", pd)>0;
    }

    @Override
    public boolean addSupplierSalesAchievement(String shop_order_no, String versionNo) throws Exception {
        return (Integer)dao.save("AccDetailMapper.addSupplierSalesAchievement", shop_order_no)>0;
    }

    @Override
    public PageData listPageTransferSJS(Page page, String versionNo) throws Exception {
        List<PageData> list = (List<PageData>)dao.findForList("AccDetailMapper.listPageTransferSJS", page);
        PageData tpd = new PageData();
        tpd.put("list", list);
        tpd.put("page", page);
        return tpd;
    }

    @Override
    public boolean addWithDrawalAccDetail() throws Exception {
        return (Integer)dao.save("AccDetailMapper.addWithDrawalAccDetail", null)>0;
    }

    @Override
    public PageData listPageTradeData(Page page, String versionNo) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<PageData> getHashList(PageData pd, String versionNo) throws Exception {
        return (List<PageData>)dao.findForList("AccDetailMapper.getHashList", pd);
    }

    @Override
    @Transactional(propagation =Propagation.REQUIRED)
    public boolean updatePayByHash(PageData pd, String versionNo) throws Exception {
        //修改订单状态为已支付
        PageData shopOrder = new PageData();
        shopOrder.put("hashList", pd.get("hashList"));
        shopOrder.put("order_status", "2");
        shopOrder.put("pay_time", DateUtil.getCurrDateTime());
        if(shopOrderInfoService.updateShopOrderStatusByHash(shopOrder, versionNo)){
            //修改订单商品关联表状态为已支付
            PageData shopOrderGoods = new PageData();
            shopOrderGoods.put("hashList", pd.get("hashList"));
            shopOrderGoods.put("state", "2");
            if(shopOrderGoodsService.updateOrderGoodsStatusByHash(shopOrderGoods, versionNo)){
                PageData accDetail = new PageData();
                accDetail.put("caldate", DateUtil.getCurrDateTime());
                accDetail.put("cntflag", "1");
                accDetail.put("status", "6");
                accDetail.put("hashList", pd.get("hashList"));
                boolean updateCntflagByHash = updateCntflagByHash(accDetail, versionNo);
                logger.error("--------定时器商城支付-------AccDetailService.updateCntflagByHash------更新账户统计标志和状态");
            }else{
                logger.error("--------定时器商城支付-------shopOrderGoodsService.updateOrderGoodsStatus------更新商城订单商品关联表状态  失败");
            }
        }else{
            logger.error("--------定时器商城支付-------updateShopOrderStatus------更新商城订单状态 失败");
        }
        
        return true;
    }

    @Override
    public boolean updateRechargeByHash(PageData pd, String versionNo) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean updateCntflagByHash(PageData pd, String versionNo) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean accDetailHashSummary(PageData pd, String versionNo) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

}
