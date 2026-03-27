package cn.ms.dm.server.operation;

import cn.ms.dm.maple.constant.packet.ReceivePacketOpcode;
import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.MethodHandle;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LouMT
 * @name MapleExecutor
 * @date 2026-02-25 16:23
 * @email lmtemail163@163.com
 * @description 包处理函数集合
 */
@Slf4j(topic = "[指令注册]")
public class PacketOperation {
    private static final Map<ReceivePacketOpcode, MethodHandle> COMMAND_MAP = new ConcurrentHashMap<>();

    public static void register(ReceivePacketOpcode opcode, MethodHandle methodHandle) {
        if (COMMAND_MAP.containsKey(opcode)) log.error("Command => ReceivePacketOpcode.{} is already registered.", opcode);
        COMMAND_MAP.put(opcode, methodHandle);
//        log.info("[Registered command executor] opcode: {}", opcode);
    }

    public static MethodHandle getExecutor(ReceivePacketOpcode opcode) {
        return COMMAND_MAP.get(opcode);
    }

    public static java.util.Set<ReceivePacketOpcode> getAllExecutor() {
        return COMMAND_MAP.keySet();
    }
}
