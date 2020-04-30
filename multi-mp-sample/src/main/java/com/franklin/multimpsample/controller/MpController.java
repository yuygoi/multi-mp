package com.franklin.multimpsample.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.franklin.multimpsample.entity.OnlineClass;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description :
 * Create By: Franklin
 * Date : 2020/4/28 11:16
 */
@RestController
@RequestMapping("/mp")
public class MpController {

    @PostMapping("/test/1")
    public String test1(){
        QueryWrapper<OnlineClass> classQueryWrapper = new QueryWrapper<>();
        classQueryWrapper
                .select("actual_start_time AS actualStartTime")
                .eq("id",1L);
        return classQueryWrapper.getSqlSelect();
    }
}
