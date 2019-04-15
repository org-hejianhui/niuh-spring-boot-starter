package com.zc.smartcity.redis.enums;

/**
 * <p>
 *      监控的数据发送方式枚举
 * </p>
 *
 * @author: hejianhui
 * @create: 2019-04-03 17:14
 * @see MonitorPushTypeEnum
 * @since JDK1.8
 */
public enum MonitorPushTypeEnum {

    HTTP_SYNC(MonitorTransferTypeEnum.HTTP),

    HTTP_ASYN(MonitorTransferTypeEnum.HTTP),

    HTTP_SERVLET(MonitorTransferTypeEnum.HTTP),

    SOCKET(MonitorTransferTypeEnum.SOCKET);

    private MonitorTransferTypeEnum type;

    MonitorPushTypeEnum(MonitorTransferTypeEnum type){
        this.type = type;
    }

    public MonitorTransferTypeEnum getType() {
        return type;
    }

    public void setType(MonitorTransferTypeEnum type) {
        this.type = type;
    }
}
