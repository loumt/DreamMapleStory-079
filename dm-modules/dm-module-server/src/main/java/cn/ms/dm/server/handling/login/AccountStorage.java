package cn.ms.dm.server.handling.login;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.server.client.MapleClient;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author LouMT
 * @name AccountStorage
 * @date 2026-03-06 15:18
 * @email lmtemail163@163.com
 * @description 缓存账号数据
 * 需要绑定到登录服务器 LoginServer
 */
@Slf4j(topic = "【账号缓存】")
public class AccountStorage {
    private final Map<Integer, MapleClient> ACCOUNT_CACHE = Maps.newConcurrentMap();

    /**
     * 获取所有客户端
     */
    public final Collection<MapleClient> getAllClients() {
        return Collections.unmodifiableCollection(ACCOUNT_CACHE.values());
    }

    public final void register(final MapleClient client) {
        if (ObjectUtil.isNull(client)) return;
        //缓存
        ACCOUNT_CACHE.put(client.getAccountId(), client);
    }

    public final void deregister(final MapleClient client) {
        for (Map.Entry<Integer, MapleClient> entry : ACCOUNT_CACHE.entrySet()) {
            if (ObjectUtil.equal(entry.getValue(), client)) {
                ACCOUNT_CACHE.remove(entry.getKey());
            }
        }
    }
}
