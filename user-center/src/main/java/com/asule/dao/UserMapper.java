package com.asule.dao;

import com.asule.domain.entity.User;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component
public interface UserMapper extends Mapper<User> {

}