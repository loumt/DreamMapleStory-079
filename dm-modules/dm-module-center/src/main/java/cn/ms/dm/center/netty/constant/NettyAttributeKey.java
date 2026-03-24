package cn.ms.dm.center.netty.constant;

import cn.ms.dm.center.client.MapleClient;
import cn.ms.dm.core.enums.YesOrNo;
import io.netty.util.AttributeKey;

/**
 * @author LouMT
 * @name NettyAttributeKey
 * @date 2025-08-18 13:52
 * @email lmtemail163@163.com
 * @description
 */
public final class NettyAttributeKey {

    /**
     * 在Netty中保存的MapleClient对象
     */
    public static final AttributeKey<MapleClient> CLIENT_KEY = AttributeKey.valueOf("CLIENT");


    /**
     *
     */
    public static final AttributeKey<YesOrNo> DECODER_STATE_KEY = AttributeKey.valueOf("MAPLE_DECODER_STATE");
}
