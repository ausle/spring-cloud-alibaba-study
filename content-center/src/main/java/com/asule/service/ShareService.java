package com.asule.service;


import com.asule.dao.ShareMapper;
import com.asule.domain.dto.ShareDTO;
import com.asule.domain.entity.Share;
import com.asule.domain.entity.User;
import com.asule.service.feignclient.UserCenterFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShareService {

    @Autowired
    private ShareMapper shareMapper;

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private UserCenterFeignClient userCenterFeignClient;

    public ShareDTO findById(Integer id) {
        // 获取分享详情
        Share share = this.shareMapper.selectByPrimaryKey(id);
        // 发布人id
        Integer userId = share.getUserId();

        //ribbon
//        User user=restTemplate.getForObject("http://user-center/users/{id}", User.class,userId);

        User user=userCenterFeignClient.findById(userId);

        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(user.getWxNickname());
        return shareDTO;
    }



}

