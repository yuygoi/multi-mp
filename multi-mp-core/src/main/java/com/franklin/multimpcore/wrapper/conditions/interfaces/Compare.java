package com.franklin.multimpcore.wrapper.conditions.interfaces;

import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.franklin.multimpcore.wrapper.conditions.constants.Conditions;
import com.franklin.multimpcore.wrapper.conditions.constants.SqlKeywords;
import com.franklin.multimpcore.wrapper.util.Reflections;

import java.util.Collection;

/**
 * Description :比较接口
 * Create By: Franklin
 * Date : 2020/4/27 10:58
 */
public interface Compare<Children> extends SqlStatement {

    default <T> Children eq(SFunction<T,?> col,Object val){
        return eq(col,val, Reflections.getTableAlias(LambdaUtils.resolve(col)));
    }

    default <T> Children ge(SFunction<T,?> col,Object val){
        return ge(col,val, Reflections.getTableAlias(LambdaUtils.resolve(col)));
    }

    default <T> Children le(SFunction<T,?> col,Object val){
        return le(col,val, Reflections.getTableAlias(LambdaUtils.resolve(col)));
    }

    default <T> Children lt(SFunction<T,?> col,Object val){
        return lt(col,val, Reflections.getTableAlias(LambdaUtils.resolve(col)));
    }

    default <T> Children gt(SFunction<T,?> col,Object val){
        return gt(col,val, Reflections.getTableAlias(LambdaUtils.resolve(col)));
    }

    default <T> Children in(SFunction<T,?> col,Collection<?> collection){
        return in(col,collection, Reflections.getTableAlias(LambdaUtils.resolve(col)));
    }

    default <T> Children in(SFunction<T,?> col, Children subWrapper){
        return in(col,subWrapper, Reflections.getTableAlias(LambdaUtils.resolve(col)));
    }

    default <T> Children eq(SFunction<T,?> col,Object val,String tbAlias){
        return compare(col,val,tbAlias, Conditions.EQ);
    }

    default <T> Children ge(SFunction<T,?> col,Object val,String tbAlias){
        return compare(col,val,tbAlias, Conditions.GE);
    }

    default <T> Children le(SFunction<T,?> col,Object val,String tbAlias){
        return compare(col,val,tbAlias, Conditions.LE);
    }

    default <T> Children lt(SFunction<T,?> col,Object val,String tbAlias){
        return compare(col,val,tbAlias, Conditions.LT);
    }

    default <T> Children gt(SFunction<T,?> col,Object val,String tbAlias){
        return compare(col,val,tbAlias, Conditions.GT);
    }

    <T> Children in(SFunction<T,?> col,Collection<?> collection,String tbAlias);

    <T> Children in(SFunction<T,?> col, Children subWrapper,String tbAlias);

    <T> Children compare(SFunction<T,?> col,Object val,String tbAlias,String symbol);
}
