package com.asule.sentinel.fallback;

import com.asule.domain.entity.User;
import com.asule.service.feignclient.UserCenterFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserCenterFeignClientFallbackFactory implements FallbackFactory<UserCenterFeignClient> {

    @Override
    public UserCenterFeignClient create(Throwable cause) {
        log.info("异常信息：{}",cause);

        return new UserCenterFeignClient(){

            @Override
            public User findById(Integer id) {
                User user = new User();
                user.setWxNickname("fallback-name");
                return user;
            }

            @Override
            public User getUser(User user) {
                return null;
            }
        };

    }
}
