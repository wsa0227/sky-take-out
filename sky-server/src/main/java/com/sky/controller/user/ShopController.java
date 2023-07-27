package com.sky.controller.user;

import com.sky.controller.admin.ShopStatusController;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "用户端查看店铺营业状态")
@Slf4j
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 用户端查看店铺营业状态
     *
     * @return
     */
    @ApiOperation("查看营业状态")
    @GetMapping("/status")
    public Result getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(ShopStatusController.shopStatus);
        log.info("店铺营业状态为：{}", status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
