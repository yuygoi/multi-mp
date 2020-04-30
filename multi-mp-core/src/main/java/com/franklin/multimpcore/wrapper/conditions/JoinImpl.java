package com.franklin.multimpcore.wrapper.conditions;

import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.franklin.multimpcore.wrapper.conditions.constants.Conditions;
import com.franklin.multimpcore.wrapper.conditions.constants.SqlKeywords;
import com.franklin.multimpcore.wrapper.conditions.constants.StringPool;
import com.franklin.multimpcore.wrapper.conditions.enums.JoinType;
import com.franklin.multimpcore.wrapper.conditions.interfaces.Join;
import com.franklin.multimpcore.wrapper.table.TableName;
import com.franklin.multimpcore.wrapper.util.Reflections;

import java.util.*;

/**
 * Description :
 * Create By: Franklin
 * Date : 2020/4/24 14:35
 */
public class JoinImpl<Children extends AbstractMultiWrapper> implements Join<Children> {

    private final static String T1 = "t1";
    private final static String T2 = "t2";

    private JoinType joinType;

    private Class t2Class;
    private Map<String,SerializedLambda> joinConditions;
    private Map<String,String> tableAlias;
    private Children multiWrapper;
    private Children subWrapper;

    Map<String, SerializedLambda> getJoinConditions() {
        return joinConditions;
    }

    Children getSubWrapper() {
        return subWrapper;
    }

    JoinType getJoinType() {
        return joinType;
    }

    <T2> JoinImpl(Class<T2> t2Class, String alias, JoinType joinType, Children multiWrapper){
        this.joinType = joinType;
        this.t2Class = t2Class;
        this.multiWrapper = multiWrapper;
        this.joinConditions = new HashMap<>(2);
        this.tableAlias = new HashMap<>(2);
        List<TableName> tableNames = this.multiWrapper.from.getTableNames();
        TableName tableName = tableNames.get(0);
        String alias1 = tableName.getAlias();
        if (alias.equals(alias1)){
            String value = alias + 1;
            this.tableAlias.put(T1, value);
            this.tableAlias.put(T2,alias + 2);
            tableName.setAlias(value);
        }else {
            this.tableAlias.put(T1, alias1);
            this.tableAlias.put(T2,alias);
        }
    }

    JoinImpl(Children subWrapper, JoinType joinType, Children multiWrapper) {
        this.joinType = joinType;
        this.subWrapper = subWrapper;
        this.multiWrapper = multiWrapper;
        this.joinConditions = new HashMap<>(2);
        this.tableAlias = new HashMap<>(2);
    }

    @Override
    public <T1, T2> Children on(SFunction<T1, ?> t1Condition, String alias1, SFunction<T2, ?> t2Condition, String alias2) {
        this.joinConditions.put(T1,LambdaUtils.resolve(t1Condition));
        this.joinConditions.put(T2,LambdaUtils.resolve(t2Condition));
        if (Objects.isNull(this.tableAlias.get(T1))){
            this.tableAlias.put(T1,alias2);
        }
        if (Objects.isNull(this.tableAlias.get(T2))){
            this.tableAlias.put(T2,alias2);
        }
        return this.multiWrapper;
    }

    @Override
    public <T1, T2> On on(SFunction<T1, ?> t1Condition, String alias1, SFunction<T2, ?> t2Condition, String alias2, boolean isMore) {
        this.joinConditions.put(T1,LambdaUtils.resolve(t1Condition));
        this.joinConditions.put(T2,LambdaUtils.resolve(t2Condition));
        if (Objects.isNull(this.tableAlias.get(T1))){
            this.tableAlias.put(T1,alias2);
        }
        if (Objects.isNull(this.tableAlias.get(T2))){
            this.tableAlias.put(T2,alias2);
        }
        return new On<Children>(this.multiWrapper);
    }

    @Override
    public String getSqlStatement(Integer subTimes) {
        StringBuilder builder = new StringBuilder();
        SerializedLambda lambda1 = this.joinConditions.get(T1);
        String alias1 = this.tableAlias.get(T1);
        String col1 = Reflections.guessColumnNameByLambda(lambda1, alias1);
        SerializedLambda lambda2 = this.joinConditions.get(T2);
        String alias2 = this.tableAlias.get(T2);
        String col2 = Reflections.guessColumnNameByLambda(lambda2, alias2);
        String tableName2 = Reflections.getTableName(lambda2);
        builder.append(this.joinType.getJoinVal()).append(StringPool.BLANK).append(tableName2).append(SqlKeywords.AS).append(alias2).append(StringPool.NEXT)
                .append(SqlKeywords.ON).append(StringPool.BLANK)
                .append(col1).append(Conditions.EQ).append(col2);
        return builder.toString();
    }
}
