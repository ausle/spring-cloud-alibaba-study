package com.asule.controller;


import com.asule.domain.dto.ShareDTO;
import com.asule.domain.entity.User;
import com.asule.service.ShareService;
import com.asule.service.TestService;
import com.asule.service.feignclient.TestFeignClient;
import com.asule.service.feignclient.UserCenterFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shares")
public class ShareConroller {

    @Autowired
    private ShareService shareService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private UserCenterFeignClient userCenterFeignClient;



    @GetMapping("/{id}")
    public ShareDTO findById(@PathVariable Integer id) {
        return this.shareService.findById(id);
    }

    @GetMapping("/getInfo")
    public List<ServiceInstance> getDiscoveryClientInfo(){
        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
        log.info("instance：{}",instances);
        return instances;
    }





}
