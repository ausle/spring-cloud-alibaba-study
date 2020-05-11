package com.asule.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestBlockHandler {

    public static String fallback(){
        log.info("服务降级....");
        return "服务降级";
    }

    public static String blockHandler(BlockException exception){
        log.info("服务限流....");
        return "服务限流";
    }
}
