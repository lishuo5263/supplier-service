package com.ecochain.ledger.Task;

import com.alibaba.fastjson.JSONObject;
import com.ecochain.ledger.util.DateUtil;
import com.ecochain.ledger.util.HttpTool;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Lisandro on 2017/5/17.
 */
@Component
@EnableScheduling
public class BlockChainTask {

    private Logger logger = Logger.getLogger(BlockChainTask.class);

    @Scheduled(fixedDelay=10000)
    public void scheduler() {
        /**
         * 1.需要调用区块链接口查出当日增量的hash数据
         * 2.然后json解析出hash数据中的data字段
         * 3.取data字段中每个调用区块链接口存入的bussType 进行业务判断后，调用自身系统相对应的接口方法同步数据
         */
        logger.info(">>>>>>>>>>>>> scheduled ... ");
        String getToDayBlockInfo = HttpTool.doPost("http://192.168.101.28:8332/GetDataLastNew", DateUtil.getCurrDateTime());
        JSONObject toDayBlockInfo = JSONObject.parseObject(getToDayBlockInfo);
        for(int i =0;i<10;i++){  //需循环list
            if(true){ //这里为处理Json话后的data逻辑
                if("insertOrder".equals(true)){
                    HttpTool.doPost("http://192.168.101.28:8332/GetDataLastNew", "insertOrder"); //insertOrder 此处值应为给区块链的data值
                    continue;
                } else if ("deliverGoods".equals(true)) {
                    HttpTool.doPost("http://192.168.101.28:8332/GetDataLastNew", "deliverGoods"); //deliverGoods 此处值应为给区块链的data值
                    continue;
                } else if ("insertOrder".equals(true)) {
                    HttpTool.doPost("http://192.168.101.28:8332/GetDataLastNew", "insertOrder"); //insertOrder 此处值应为给区块链的data值
                    continue;
                }
            }
        }

    }
}
