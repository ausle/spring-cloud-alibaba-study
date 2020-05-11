package com.asule.configuration.rule;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;
import org.springframework.stereotype.Component;


//根据nacos的权重来选择调用服务实例
@Slf4j
public class NacosWeightRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties properties;

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        //根据loadbalancer获取要访问的服务名
        BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
        String name = loadBalancer.getName();

        //获取nacos管理微服务名的service。
        NamingService namingService = properties.namingServiceInstance();
        try {
            //传入服务名，会根据nacos的权重，为我们挑选一个具体的服务实例。
            Instance instance = namingService.selectOneHealthyInstance(name);

            //封装服务实例，返回NacosServer
            return new NacosServer(instance);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return null;
    }
}
