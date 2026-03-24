package cn.ms.dm.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author LouMT
 * @name DMClientApplication
 * @date 2025-08-11 9:20
 * @email lmtemail163@163.com
 * @description
 */
@SpringBootApplication
public class DMClientApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DMClientApplication.class);
        // 添加异常处理
        try {
            application.run(args);
            System.out.println("启动成功   ლ(´ڡ`ლ)ﾞ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
