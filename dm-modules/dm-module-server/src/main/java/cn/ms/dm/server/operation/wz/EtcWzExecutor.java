package cn.ms.dm.server.operation.wz;

import cn.ms.dm.maple.annotation.WzParser;
import cn.ms.dm.maple.wz.MapleDataTool;
import cn.ms.dm.maple.wz.base.MapleData;
import com.google.common.collect.Lists;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LouMT
 * @name EtcExecutor
 * @date 2026-03-17 10:38
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "【WZ-Etc】")
@WzParser("Etc.wz")
@Component
public class EtcWzExecutor extends WzExecutor{
    //禁止使用的名称
    private final List<String> FORBIDDEN_NAMES = Lists.newArrayList();

    @Override
    public void init(){
        initForbiddenNames();
    }

    private void initForbiddenNames() {
        final MapleData nameData = provider.getData("ForbiddenName");
        for (final MapleData data : nameData.getChildren()) {
            FORBIDDEN_NAMES.add(MapleDataTool.getString(data));
        }
    }

    /**
     * 是否为禁用名字
     */
    public boolean isForbiddenName(final String name){
        return FORBIDDEN_NAMES.stream().anyMatch(name::contains);
    }


}
