package com.asule.configuration;

import com.ribbon.config.RibbonConfig;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

/*
    代码配置ribbon负载均衡的方式，访问指定服务时，会使用配置的规则。
    要注意的是，RibbonConfig这个配置类不可以与应用上下文在同一个包下。
    原因是@SpringBootApplication会扫描同包下的组件添加到spring容器，构建spring上下文。
    而Ribbon它属于ribbon上下文，如果被扫描到，它与spring上下文会产生父子上下文重叠。
    造成的后果之一是，ribbon的配置会在所有ribbon-client上生效。这也就不是细粒度的配置呢。

    基于此，还有配置文件配置的方式，它比代码配置的要好。
 */
@RibbonClient(name = "user-center",configuration = RibbonConfig.class)
@Configuration
public class ContentCenterConfiguration {
}
