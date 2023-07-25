package com.sky.controller.admin;

import com.aliyun.oss.AliyunossUtils;
import com.sky.annotion.AutoFill;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Api(tags = "公共接口")
@RestController
public class CommonController {
    @Autowired
    private AliyunossUtils aliyunossUtils;
@ApiOperation("文件上传")
    @PostMapping("/admin/common/upload")
    public Result upload(MultipartFile file) {

        try {
            String upload = aliyunossUtils.upload(file);
            return Result.success(upload);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("上传失败");
        }

    }

}
