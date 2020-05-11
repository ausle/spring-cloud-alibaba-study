package com.asule.service.feignclient;


import com.asule.configuration.UserCenterFeignConfiguration;
import com.asule.domain.entity.User;
import com.asule.sentinel.fallback.UserCenterFeignClientFallback;
import com.asule.sentinel.fallback.UserCenterFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
    openfeign对http请求在restTemplate基础之上，又做了封装。
    通过@FeignClient指定要访问的服务名，定义与服务接口相同的路径、方法参数和返回值。
    这样就可以发起服务接口的调用。
 */
@Component
@FeignClient(name = "user-center"
//        ,fallback = UserCenterFeignClientFallback.class     //出现限流或降级的兜底方案，而不是报出异常
        ,fallbackFactory = UserCenterFeignClientFallbackFactory.class   //可以获取异常信息
)
public interface UserCenterFeignClient {

    @GetMapping("/users/{id}")
    User findById(@PathVariable(value = "id") Integer id);

    //get请求，指定多个参数时，若不加@SpringQueryMap，会报以下异常：
    //feign.FeignException$MethodNotAllowed: status 405 reading UserCenterFeignClient#getUser(User)
    @GetMapping("/users/test")
    User getUser(@SpringQueryMap User user);
}
