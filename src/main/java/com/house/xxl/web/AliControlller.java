package com.house.xxl.web;

import com.house.xxl.common.Ali;
import com.house.xxl.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Api(value = "阿里云", tags = "阿里云相关的接口")
@RestController
@RequestMapping("/ali")
public class AliControlller {

    @PostMapping("/uploadFile")
    @ApiOperation(value = "上传文件")
    public Result uploadFile(MultipartFile file) throws IOException {
        String s = Ali.uploadFile(file);
        return Result.success(s);
    }
}
