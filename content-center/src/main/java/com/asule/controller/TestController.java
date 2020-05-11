package com.asule.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.asule.domain.entity.User;
import com.asule.sentinel.TestBlockHandler;
import com.asule.service.TestService;
import com.asule.service.feignclient.TestFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/shares")
public class TestController {

    @Autowired
    private TestFeignClient testFeignClient;

    @Autowired
    private TestService testService;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/baidu")
    public void getUser(){
        testFeignClient.accessBaidu();
    }

    @GetMapping("/test-a")
    public String testA(){
        testService.common();
        return "test-a";
    }

    @GetMapping("/test-b")
    public String testB(){
        testService.common();
        return "test-b";
    }

    @GetMapping("/test-sentinel-api")
    public String testSentinelAPI(
            @RequestParam(required = false) String a) {

        String resourceName="test-sentinel-api";
        ContextUtil.enter(resourceName,"asule");    //指定服务的访问来源

        Entry entry=null;
        try {
            //声明一个被sentinel保护的资源
            entry = SphU.entry(resourceName);
            if (StringUtils.isEmpty(a)){
                throw new IllegalArgumentException("参数为空");
            }
        } catch (BlockException e) {
            //当服务降级或限流，就会抛出BlockException相关异常
            return "服务降临或者限流";
        }catch (IllegalArgumentException e){
            //sentinel只计算抛出BlockException及其子类异常的次数。对于其他异常，这里进行手工添加次数。
            //测试服务降级---异常次数、异常比例
            Tracer.trace(e);
            return "非法参数";
        }
        finally {
            if (entry!=null){
                entry.exit();
            }
            ContextUtil.exit();
        }
        return null;
    }

    //通过@SentinelResource来声明一个受保护的资源，并且设置服务限流和降级的处理
    @GetMapping("/test-sentinel-resource")
    @SentinelResource(value = "test-sentinel-resource"
                ,fallback = "fallback",blockHandler = "blockHandler"
                ,blockHandlerClass = TestBlockHandler.class)
    public String testSentinelResource(@RequestParam(required = false,value = "name") String s){
        if (StringUtils.isEmpty(s)){
            throw new IllegalArgumentException("参数为空");
//            return "参数为空";
        }
        return s;
    }

    public String fallback(){
        log.info("服务降级....");
        return "服务降级";
    }

    //
    @GetMapping("/test-sentinel-template/{id}")
    public User testSentinelTemplate(@PathVariable(value = "id")Integer userId){
        return restTemplate.getForObject("http://user-center/users/{id}",User.class,userId);
    }
}
