package com.franklin.multimpcore.wrapper.conditions;

import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;

import java.util.HashMap;
import java.util.Map;

/**
 * Description :更多联表条件
 * Create By: Franklin
 * Date : 2020/4/24 14:35
 */
public class On<Children> {

    private Map<SerializedLambda,Object> conditions;
    private Children multiWrapper;

    On(Children multiWrapper){
        this.multiWrapper = multiWrapper;
        this.conditions = new HashMap<>();
    }

    public <T> Children and(SFunction<T,?> condition,Object val){
        this.conditions.put(LambdaUtils.resolve(condition),val);
        return this.multiWrapper;
    }

    public <T> On and(SFunction<T,?> condition,Object val,boolean isMore){
        this.conditions.put(LambdaUtils.resolve(condition),val);
        return this;
    }
}
