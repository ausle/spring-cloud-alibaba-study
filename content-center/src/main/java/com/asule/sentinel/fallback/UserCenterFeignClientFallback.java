package com.asule.sentinel.fallback;

import com.asule.domain.entity.User;
import com.asule.service.feignclient.UserCenterFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
public class UserCenterFeignClientFallback implements UserCenterFeignClient {

    @Override
    public User findById(Integer id) {

        User user = new User();
        user.setWxNickname("fallback-name");
        return user;
    }

    @Override
    public User getUser(User user) {
        User u = new User();
        user.setWxNickname("fallback-getuser-name");
        return u;
    }
}
