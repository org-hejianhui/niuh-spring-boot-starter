package com.zc.smartcity.redis.monitor.job;

import com.google.gson.Gson;
import com.zc.smartcity.redis.common.Constants;
import com.zc.smartcity.redis.enums.MonitorPushTypeEnum;
import com.zc.smartcity.redis.monitor.protocol.AbstractProtocol;
import com.zc.smartcity.redis.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
@Slf4j
public class MonitorJob {

    public static void doJob(Map<String,Object> map){
        MonitorPushTypeEnum mPushType = (MonitorPushTypeEnum) map.get(Constants.MONITOR_PUSH_TYPE_NAME);
        AbstractProtocol protocol = (AbstractProtocol) map.get(Constants.MONITOR_PROTOCOL_NAME);
        String host = (String) map.get(Constants.MONITOR_HOST_NAME);
        Integer port = (Integer) map.get(Constants.MONITOR_PORT_NAME);
        List<Serializable> datas = null;
        if(null != protocol && null != (datas = AbstractProtocol.buildLocalData(protocol)) && !datas.isEmpty()){
            String result = null;
            try {
                Gson gson = new Gson();
                switch (mPushType){
                    case HTTP_ASYN:
                        result = HttpUtil.doPostAsyn(host,gson.toJson(datas));
                        break;
                    case HTTP_SYNC:
                        result = HttpUtil.doPostSync(host, gson.toJson(datas));
                        break;
                    default:

                }
            } catch (Exception e){
                e.printStackTrace();
                 log.error(e.getMessage());
            }

             log.info("post "+ host + port +",result is " +result);

        }

        // 清除本地内存数据
        AbstractProtocol.clearLocalData();
    }

}
