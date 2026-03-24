package cn.ms.dm.server.config;

import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.ReceivePacketOpcode;
import cn.ms.dm.server.operation.PacketOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

/**
 * @author LouMT
 * @name OpcodeExecutorRegistrationConfig
 * @date 2026-02-26 15:12
 * @email lmtemail163@163.com
 * @description
 */
@Component
@Slf4j(topic = "opcode register")
public class OpcodeOperationRegConfig implements ApplicationRunner {
    public static final String PACKET_LOCATION = "cn.ms.dm.server.operation.packet";
    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 获取所有类型为 Object 的 Bean 名称 (或者更精确地，可以尝试获取一个共同父类或接口)
        // 但更好的方式是获取 executors 包下所有标注了 @Component 的类
        // 我们可以通过类型来获取，假设所有 Executor 都是 Component
        // 或者，我们可以先获取所有 Bean 名字，再检查它们的类是否在目标包下

        // 方式一：如果所有 Executor 都实现了某个公共接口 IExecutor
        // Map<String, IExecutor> executorBeans = applicationContext.getBeansOfType(IExecutor.class);
        // for (IExecutor executor : executorBeans.values()) {
        //     scanAndRegister(executor);
        // }

        // 方式二：获取所有 Bean，检查其类是否在指定包下
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : allBeanNames) {
            Object beanInstance = applicationContext.getBean(beanName);
            Class<?> beanClass = beanInstance.getClass();
            if (beanClass.getPackageName().equals(PACKET_LOCATION)) {
                scanAndRegister(beanInstance);
            }
        }
    }

    /**
     * 扫描单个 Bean 实例的所有方法，查找 @CommandHandler 注解并注册
     *
     * @param beanInstance Bean 实例
     */
    private void scanAndRegister(Object beanInstance) throws Exception {
        Class<?> clazz = beanInstance.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(PacketHandler.class)) {
                PacketHandler annotation = method.getAnnotation(PacketHandler.class);
                ReceivePacketOpcode opcode = annotation.value();

                MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(clazz, MethodHandles.lookup());
                MethodType methodType = MethodType.methodType(method.getReturnType(), method.getParameterTypes());
                // 关键：将 MethodHandle 绑定到具体的 Bean 实例上
                java.lang.invoke.MethodHandle methodHandle = lookup.findVirtual(clazz, method.getName(), methodType)
                        .bindTo(beanInstance);
                PacketOperation.register(opcode, methodHandle);
            }
        }
    }
}
