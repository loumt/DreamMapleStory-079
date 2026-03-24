package cn.ms.dm.maple.constant.netty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChannelTypeEnum {
    LOGIN_SERVER(0, "登录服务器，频道号为0"),
    CASH_SHOP_SERVER(-10, "商城服务器，频道号为-10"),
    CHANNEL_SERVER(1, "频道服务器,频道号从1起始");
    //频道号
    private final int channelNo;
    //描述
    private final String desc;
}
