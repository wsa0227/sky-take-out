package com.sky.controller.admin;

import com.sky.constant.StatusConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.visitor.StackAwareMethodVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Api(tags = "店铺状态")
@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
public class ShopStatusController {

    public static final String shopStatus = "shopStatus";
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置店铺营业状态
     *
     * @param status
     * @return
     */
    @ApiOperation("修改店铺状态")
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status) {
        log.info("店铺营业状态为：{}", status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set(shopStatus, status);
        return Result.success();
    }

    /**
     * 查看店铺状态
     *
     * @return
     */
    @ApiOperation("查看店铺状态")
    @GetMapping("/status")
    public Result getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(shopStatus);
        log.info("店铺营业状态为：{}", status == 1 ? "营业中" : "打烊中");

        return Result.success(status);
    }

}
