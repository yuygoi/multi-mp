package com.franklin.multimpcore.wrapper.conditions;

import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.franklin.multimpcore.wrapper.conditions.constants.StringPool;
import com.franklin.multimpcore.wrapper.conditions.interfaces.SqlStatement;
import com.franklin.multimpcore.wrapper.table.Column;
import com.franklin.multimpcore.wrapper.util.Reflections;
import com.franklin.multimpcore.wrapper.util.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description :
 * Create By: Franklin
 * Date : 2020/4/25 11:05
 */
public class Select<T> implements SqlStatement {

    private List<Column> columns = new ArrayList<>();

    private List<Object> params = new ArrayList<>();

    private String tableAlias;

    public Select(){

    }

    public Select(String tableAlias){
        this.tableAlias = tableAlias;
    }

    List<Object> getParams() {
        return params;
    }

    public Select<T> column(SFunction<T,?> ...columns){
        if (StringUtils.isBlank(this.tableAlias)){
            this.tableAlias = Reflections.getTableAlias(LambdaUtils.resolve(columns[0]));
        }
        this.columns.addAll(Stream.of(columns).map(tsFunction -> {
            Column column = new Column();
            SerializedLambda lambda = LambdaUtils.resolve(tsFunction);
            column.setAlias(Reflections.getColumnAlias(lambda));
            column.setIsDefaultAlias(true);
            column.setIsSubQuery(false);
            column.setLambda(lambda);
            column.setColumnName(Reflections.guessFieldByGetMethod(lambda));
            return column;
        }).collect(Collectors.toList()));
        return this;
    }

    public Select<T> column(SFunction<T,?> column,String colAlias){
        Column column1 = new Column();
        SerializedLambda lambda = LambdaUtils.resolve(column);
        column1.setLambda(lambda);
        column1.setAlias(colAlias);
        column1.setIsDefaultAlias(false);
        column1.setIsSubQuery(false);
        column1.setColumnName(Reflections.guessFieldByGetMethod(lambda));
        this.columns.add(column1);
        return this;
    }

    public Select<T> column(MultiWrapper wrapper,String alias){
        Column column = new Column();
        column.setIsDefaultAlias(false);
        column.setColumnName(Strings.getSubQuery(wrapper,0));
        this.params.addAll(wrapper.getParams());
        column.setAlias(alias);
        column.setIsSubQuery(true);
        this.columns.add(column);
        return this;
    }

    @Override
    public String getSqlStatement(Integer subTimes) {
        return this.columns
                .stream()
                .map(column -> {
                    if (!column.getIsSubQuery()){
                        return Strings.getBlank(subTimes) + this.tableAlias + StringPool.DOT + column.getSqlStatement();
                    }
                    return Strings.getBlank(subTimes) + column.getSqlStatement(subTimes + 1);
                })
                .collect(Collectors.joining(StringPool.COMMA + StringPool.NEXT));
    }
}
