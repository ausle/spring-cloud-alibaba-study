package com.asule.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class UserCenterFeignConfiguration {

    //feign默认不打印任何日志，即使你使用日志框架并设置为debug级别，也是不打日志。
    //feign有自己的一套日志级别。默认为NONE，这里设置FULL
    @Bean
    public Logger.Level logger(){
        return Logger.Level.FULL;
    }

}
