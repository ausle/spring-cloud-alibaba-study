package com.asule.configuration.rule;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;

import java.util.ArrayList;
import java.util.List;

//实现nacos同一集群下优先调用
@Slf4j
public class NacosSameClusterWeightRule extends AbstractLoadBalancerRule {


    @Autowired
    private NacosDiscoveryProperties properties;

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {

        try {
            String clusterName = properties.getClusterName();

            BaseLoadBalancer loadBalancer= (BaseLoadBalancer) getLoadBalancer();
            String name = loadBalancer.getName();

            NamingService namingService = properties.namingServiceInstance();
            List<Instance> instances = namingService.selectInstances(name, true);
            List<Instance> sampleClusterInstance=new ArrayList<>();
            for (Instance is:instances) {
                if (is.getClusterName().equals(clusterName)){
                    sampleClusterInstance.add(is);
                }
            }

            if (instances!=null&&sampleClusterInstance.size()>0){
                instances=sampleClusterInstance;
            }
            //再根据nacos权重去选择同一集群下的优先服务
            Instance instance = ClusterBalancer.getHostByRandomWeight2(instances);

            return new NacosServer(instance);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class ClusterBalancer extends Balancer {
    protected static Instance getHostByRandomWeight2(List<Instance> hosts) {

        return getHostByRandomWeight(hosts);
    }
}
