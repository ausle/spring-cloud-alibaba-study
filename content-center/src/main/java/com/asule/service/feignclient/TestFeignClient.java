package com.asule.service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//利用feign访问外网，服务名随便写，若不写会发生异常。
@FeignClient(name = "baidu",url = "https://www.baidu.com/?tn=47018152_dg")
public interface TestFeignClient {

    @GetMapping("")
    void accessBaidu();

}
