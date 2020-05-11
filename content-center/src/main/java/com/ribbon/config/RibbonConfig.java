package com.ribbon.config;

import com.asule.configuration.rule.NacosSameClusterWeightRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfig {

//    @Bean
//    public RandomRule randomRule(){
//        return new RandomRule();
//    }

//    @Bean
//    public IRule nacosRule(){
//        return new NacosWeightRule();
//    }

        @Bean
        public IRule nacosRule(){
            return new NacosSameClusterWeightRule();
        }


}
