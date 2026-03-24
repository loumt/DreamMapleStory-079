package cn.ms.dm.server.operation.world;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name BuddyExecutor
 * @date 2026-03-03 16:17
 * @email lmtemail163@163.com
 * @description 好友
 */
@Slf4j(topic = "【好友】")
@Component
public class BuddyExecutor {


    public void chat(Long[] recipientCharacterIds, Long sendCharacterId, String senderName, String content){
        for (Long recipientCharacterId : recipientCharacterIds) {




        }


    }

}
