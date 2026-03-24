package cn.dm.ms.web.config;

import cn.ms.dm.core.utils.SpringUtils;
import io.undertow.server.DefaultByteBufferPool;
import io.undertow.server.handlers.DisallowedMethodsHandler;
import io.undertow.util.HttpString;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.task.VirtualThreadTaskExecutor;

/**
 * @author LouMT
 * @name UndertowConfig
 * @date 2026-01-20 9:38
 * @email lmtemail163@163.com
 * @description
 */
@AutoConfiguration
public class UndertowConfig implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

    /**
     * 自定义 Undertow 配置
     * <p>
     * 主要配置内容包括：
     * 1. 配置 WebSocket 部署信息
     * 2. 在虚拟线程模式下使用虚拟线程池
     * 3. 禁用不安全的 HTTP 方法，如 CONNECT、TRACE、TRACK
     * </p>
     *
     * @param factory Undertow 的 Web 服务器工厂
     */
    @Override
    public void customize(UndertowServletWebServerFactory factory) {
        factory.addDeploymentInfoCustomizers( deploymentInfo -> {
            //配置WebSocket部署信息,设置WebSocket使用的缓冲区池
            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(true, 1024));
            deploymentInfo.addServletContextAttribute("io.undertow.websockets.jsr.WebSocketDeploymentInfo", webSocketDeploymentInfo);

            //如果启用了虚拟线程,配置Undertow使用虚拟线程池
            if(SpringUtils.isVirtual()) {
                //创建虚拟线程池, 前缀为undertow-
                VirtualThreadTaskExecutor executor = new VirtualThreadTaskExecutor("undertow-");
                //设置虚拟线程池为执行器和异步执行器
                deploymentInfo.setExecutor(executor);
                deploymentInfo.setAsyncExecutor(executor);
            }

            // 防止潜在的安全风险和爬虫骚扰
            // CONNECT：避免代理隧道攻击
            // TRACE：防止跨站脚本攻击(XSS)
            // TRACK：类似 TRACE 的安全风险
            // 配置禁止某些不安全的 HTTP 方法（如 CONNECT、TRACE、TRACK）
            deploymentInfo.addInitialHandlerChainWrapper(handler -> {
                // 禁止三个方法 CONNECT/TRACE/TRACK 也是不安全的 避免爬虫骚扰
                HttpString[] disallowedHttpMethods = {
                        HttpString.tryFromString("CONNECT"),
                        HttpString.tryFromString("TRACE"),
                        HttpString.tryFromString("TRACK")
                };
                // 使用 DisallowedMethodsHandler 拦截并拒绝这些方法的请求
                return new DisallowedMethodsHandler(handler, disallowedHttpMethods);
            });
        });
    }
}
