package com.franklin.multimpcore.wrapper.conditions.interfaces;

import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.franklin.multimpcore.wrapper.conditions.MultiWrapper;
import com.franklin.multimpcore.wrapper.conditions.On;
import com.franklin.multimpcore.wrapper.conditions.enums.JoinType;
import com.franklin.multimpcore.wrapper.util.Reflections;

import java.util.HashMap;

/**
 * Description :
 * Create By: Franklin
 * Date : 2020/4/27 10:41
 */
public interface Join<Children> extends SqlStatement {

    default <T1,T2> Children on(SFunction<T1,?> t1Condition, SFunction<T2,?> t2Condition){
        SerializedLambda lambda1 = LambdaUtils.resolve(t1Condition);
        SerializedLambda lambda2 = LambdaUtils.resolve(t2Condition);
        return on(t1Condition, Reflections.getTableAlias(lambda1),t2Condition,Reflections.getTableAlias(lambda2));
    }

    default  <T1,T2> On on(SFunction<T1,?> t1Condition, SFunction<T2,?> t2Condition, boolean isMore){
        SerializedLambda lambda1 = LambdaUtils.resolve(t1Condition);
        SerializedLambda lambda2 = LambdaUtils.resolve(t2Condition);
        return on(t1Condition, Reflections.getTableAlias(lambda1),t2Condition,Reflections.getTableAlias(lambda2),isMore);
    }

    <T1,T2> Children on(SFunction<T1,?> t1Condition,String alias1, SFunction<T2,?> t2Condition,String alias2);

    <T1,T2> On on(SFunction<T1,?> t1Condition,String alias1, SFunction<T2,?> t2Condition,String alias2,boolean isMore);
}
