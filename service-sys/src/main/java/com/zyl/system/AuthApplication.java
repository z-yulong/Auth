package com.zyl.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: zyl
 * @date 2023/4/26 15:25
 */
@SpringBootApplication
@MapperScan(basePackages = "com.zyl.system.mapper")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);

        System.out.println(" ________     ___                _    _ _______ _    _");
        System.out.println("|___  /\\ \\   / / |          /\\  | |  | |__   __| |  | |");
        System.out.println("   / /  \\ \\_/ /| |         /  \\ | |  | |  | |  | |__| |");
        System.out.println("  / /    \\   / | |        / /\\ \\| |  | |  | |  |  __  |");
        System.out.println(" / /__    | |  | |____   / ____ \\ |__| |  | |  | |  | |");
        System.out.println("/_____|   |_|  |______| /_/    \\_\\____/   |_|  |_|  |_|");
        System.out.println("           (♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
