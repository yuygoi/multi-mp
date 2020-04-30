package com.franklin.multimpcore.wrapper.conditions;

import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.franklin.multimpcore.wrapper.conditions.constants.Conditions;
import com.franklin.multimpcore.wrapper.conditions.constants.SqlKeywords;
import com.franklin.multimpcore.wrapper.conditions.constants.StringPool;
import com.franklin.multimpcore.wrapper.conditions.enums.InType;
import com.franklin.multimpcore.wrapper.conditions.enums.JoinType;
import com.franklin.multimpcore.wrapper.conditions.interfaces.*;
import com.franklin.multimpcore.wrapper.table.Column;
import com.franklin.multimpcore.wrapper.table.TableElement;
import com.franklin.multimpcore.wrapper.util.Reflections;
import com.franklin.multimpcore.wrapper.util.Strings;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Description :
 * Create By: Franklin
 * Date : 2020/4/27 9:55
 */
public abstract class AbstractMultiWrapper<Children extends AbstractMultiWrapper>
        implements Query<From<Children>,Children>,
        TableConnection<Join<Children>,Children>,
        Compare<Children>,
        MainConnectSub<Children>
{

    protected List<Select> selects = new ArrayList<>();
    protected List<Class> tableClasses;
    protected List<Map<String,Object>> conditions = new ArrayList<>();
    protected List<Map<String,Object>> inConditions = new ArrayList<>();
    protected List<Join> joins = new ArrayList<>();
    protected List<Object> params = new ArrayList<>();

    FromImpl<MultiWrapper> from;

    public List<Object> getParams() {
        return params;
    }

    @Override
    public Join<Children> leftJoin(Children subWrapper){
        Join<Children> join = new JoinImpl(subWrapper, JoinType.LEFT_JOIN, this);
        this.joins.add(join);
        return join;
    }

    @Override
    public Join<Children> rightJoin(Children subWrapper){
        Join<Children> join = new JoinImpl(subWrapper, JoinType.RIGHT_JOIN, this);
        this.joins.add(join);
        return join;
    }

    @Override
    public Join<Children> innerJoin(Children subWrapper){
        Join<Children> join = new JoinImpl(subWrapper, JoinType.INNER_JOIN, this);
        this.joins.add(join);
        return join;
    }

    @Override
    public Join<Children> outerJoin(Children subWrapper){
        Join<Children> join = new JoinImpl(subWrapper, JoinType.OUTER_JOIN, this);
        this.joins.add(join);
        return join;
    }

    @Override
    public <T2> Join<Children> leftJoin(Class<T2> t2Class, String alias) {
        Join<Children> join = new JoinImpl(t2Class,alias, JoinType.LEFT_JOIN, this);
        this.joins.add(join);
        return join;
    }

    @Override
    public <T2> Join<Children> rightJoin(Class<T2> t2Class, String alias) {
        Join<Children> join = new JoinImpl(t2Class, alias, JoinType.RIGHT_JOIN, this);
        this.joins.add(join);
        return join;
    }

    @Override
    public <T2> Join<Children> innerJoin(Class<T2> t2Class, String alias) {
        Join<Children> join = new JoinImpl(t2Class, alias, JoinType.INNER_JOIN, this);
        this.joins.add(join);
        return join;
    }

    @Override
    public <T2> Join<Children> outerJoin(Class<T2> t2Class, String alias) {
        Join<Children> join = new JoinImpl(t2Class, alias, JoinType.OUTER_JOIN, this);
        this.joins.add(join);
        return join;
    }

    @Override
    public String getSqlStatement(Integer subTimes) {
        //处理where条件
        String blank = Strings.getBlank(subTimes);
        Set<String> cache = new HashSet<>();
        return this.conditions.stream().map(condition -> {
            SerializedLambda lambda = (SerializedLambda) condition.get(Conditions.condition);
            String columnAlias = Reflections.getColumnAlias(lambda);
            String tableAlias = (String) condition.get(Conditions.tableAlias);
            String symbol = (String) condition.get(Conditions.symbol);
            Object valObj = condition.get(Conditions.val);
            String col = tableAlias + StringPool.DOT + columnAlias + symbol;
            StringBuilder builder = new StringBuilder();
            if (cache.size() == 0){
                cache.add(StringPool.BLANK);
                builder.append(blank);
            }
            if (this.getClass().isInstance(valObj)){
                MultiWrapper subWrapper = (MultiWrapper) valObj;
                String subQuery = Strings.getSubQuery(subWrapper, 1);
                builder.append(col + subQuery);
                this.params.addAll(subWrapper.getParams());
            }else if (valObj instanceof Collection && SqlKeywords.IN.equals(symbol)){
                Collection collection = (Collection) valObj;
                builder.append(col).append(StringPool.LEFT_BRACKET);
                Object collect = collection.stream().map(o -> {
                    this.params.add(o);
                    return StringPool.PLACEHOLDER;
                }).collect(Collectors.joining(StringPool.COMMA));
                builder.append(collect).append(StringPool.RIGHT_BRACKET);
            }else if (valObj instanceof Column){
                Column column = (Column) valObj;
                builder.append(col).append(column.getColumnName());
            } else {
                this.params.add(valObj);
                builder.append(col).append(StringPool.PLACEHOLDER);
            }
            return builder.toString();
        }).collect(Collectors.joining(StringPool.NEXT + blank + SqlKeywords.AND));
    }
}
