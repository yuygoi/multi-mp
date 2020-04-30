package com.franklin.multimpcore.wrapper.conditions;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.franklin.multimpcore.wrapper.conditions.constants.Conditions;
import com.franklin.multimpcore.wrapper.conditions.constants.SqlKeywords;
import com.franklin.multimpcore.wrapper.conditions.constants.StringPool;
import com.franklin.multimpcore.wrapper.conditions.enums.InType;
import com.franklin.multimpcore.wrapper.conditions.interfaces.From;
import com.franklin.multimpcore.wrapper.conditions.interfaces.Join;
import com.franklin.multimpcore.wrapper.util.Strings;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Description :
 * Create By: Franklin
 * Date : 2020/4/25 14:16
 */
public class MultiWrapper extends AbstractMultiWrapper<MultiWrapper>{


    @Override
    public From<MultiWrapper> select(Select<?> ...cols){
        this.selects.addAll(Arrays.asList(cols));
        FromImpl<MultiWrapper> from = new FromImpl<>(this);
        this.from = from;
        return from;
    }

    @Override
    public From<MultiWrapper> select(MultiWrapper subQuery) {
        return null;
    }

    @Override
    public <T> MultiWrapper eq(SFunction<T, ?> col, Object val, String tbAlias) {
        SerializedLambda resolve = LambdaUtils.resolve(col);
        Map<String,Object> map = new HashMap<>();
        map.put(Conditions.symbol,Conditions.EQ);
        map.put(Conditions.condition,resolve);
        map.put(Conditions.val,val);
        map.put(Conditions.tableAlias,tbAlias);
        this.conditions.add(map);
        return this;
    }

    @Override
    public <T> MultiWrapper ge(SFunction<T, ?> col, Object val, String tbAlias) {
        SerializedLambda resolve = LambdaUtils.resolve(col);
        Map<String,Object> map = new HashMap<>();
        map.put(Conditions.symbol,Conditions.GE);
        map.put(Conditions.condition,resolve);
        map.put(Conditions.val,val);
        map.put(Conditions.tableAlias,tbAlias);
        this.conditions.add(map);
        return this;
    }

    @Override
    public <T> MultiWrapper le(SFunction<T, ?> col, Object val, String tbAlias) {
        SerializedLambda resolve = LambdaUtils.resolve(col);
        Map<String,Object> map = new HashMap<>();
        map.put(Conditions.symbol,Conditions.LE);
        map.put(Conditions.condition,resolve);
        map.put(Conditions.val,val);
        map.put(Conditions.tableAlias,tbAlias);
        this.conditions.add(map);
        return this;
    }

    @Override
    public <T> MultiWrapper lt(SFunction<T, ?> col, Object val, String tbAlias) {
        SerializedLambda resolve = LambdaUtils.resolve(col);
        Map<String,Object> map = new HashMap<>();
        map.put(Conditions.symbol,Conditions.LT);
        map.put(Conditions.condition,resolve);
        map.put(Conditions.val,val);
        map.put(Conditions.tableAlias,tbAlias);
        this.conditions.add(map);
        return this;
    }

    @Override
    public <T> MultiWrapper gt(SFunction<T, ?> col, Object val, String tbAlias) {
        SerializedLambda resolve = LambdaUtils.resolve(col);
        Map<String,Object> map = new HashMap<>();
        map.put(Conditions.symbol,Conditions.GT);
        map.put(Conditions.condition,resolve);
        map.put(Conditions.val,val);
        map.put(Conditions.tableAlias,tbAlias);
        this.conditions.add(map);
        return this;
    }

    @Override
    public <T> MultiWrapper in(SFunction<T, ?> col, Collection<?> collection, String tbAlias) {
        SerializedLambda resolve = LambdaUtils.resolve(col);
        Map<String,Object> map = new HashMap<>();
        map.put(Conditions.condition,resolve);
        map.put(Conditions.val,collection);
        map.put(Conditions.symbol,SqlKeywords.IN);
        map.put(Conditions.tableAlias,tbAlias);
        this.conditions.add(map);
        return this;
    }

    @Override
    public <T> MultiWrapper in(SFunction<T, ?> col, MultiWrapper subWrapper, String tbAlias) {
        SerializedLambda resolve = LambdaUtils.resolve(col);
        Map<String,Object> map = new HashMap<>();
        map.put(Conditions.condition,resolve);
        map.put(Conditions.val,subWrapper);
        map.put(Conditions.tableAlias,tbAlias);
        map.put(Conditions.symbol,SqlKeywords.IN);
        this.conditions.add(map);
        return this;
    }

    @Override
    public String getSqlStatement(Integer subTimes) {
        String selects = this.selects.stream()
                .map(select -> {
                    this.params.addAll(select.getParams());
                    return select.getSqlStatement(subTimes + 1);
                })
                .collect(Collectors.joining(StringPool.COMMA + StringPool.NEXT));
        String joins = this.joins.stream()
                .map(join -> join.getSqlStatement(subTimes + 1))
                .collect(Collectors.joining(StringPool.NEXT));
        String blank = Strings.getBlank(subTimes);
        StringBuilder sql = new StringBuilder();
        sql.append(blank).append(SqlKeywords.SELECT)
                .append(blank).append(selects).append(StringPool.NEXT)
                .append(blank).append(SqlKeywords.FROM).append(this.from.getSqlStatement(subTimes + 1)).append(StringPool.NEXT);
        if (StringUtils.isNotBlank(joins)){
            sql.append(blank).append(joins).append(StringPool.NEXT);
        }
        String conditions = super.getSqlStatement(subTimes + 1);
        if (StringUtils.isNotBlank(conditions)){
            sql.append(blank).append(SqlKeywords.WHERE)
                    .append(blank)
                    .append(conditions);
        }
        return sql.toString();
    }
}
