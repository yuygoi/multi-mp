package com.franklin.multimpcore.wrapper.conditions;

import com.franklin.multimpcore.wrapper.conditions.constants.Conditions;
import com.franklin.multimpcore.wrapper.conditions.constants.StringPool;
import com.franklin.multimpcore.wrapper.conditions.interfaces.From;
import com.franklin.multimpcore.wrapper.table.TableName;
import com.franklin.multimpcore.wrapper.util.Reflections;
import com.franklin.multimpcore.wrapper.util.Strings;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Description :
 * Create By: Franklin
 * Date : 2020/4/25 12:16
 */
public class FromImpl<Children extends AbstractMultiWrapper> implements From<Children>{

    private Children multiWrapper;
    private Children subWrapper;

    private List<Class> tableClasses = new ArrayList<>();

    private List<TableName> tableNames = new ArrayList<>();

    FromImpl(Children multiWrapper){
        this.multiWrapper = multiWrapper;
    }

    List<Class> getTableClasses() {
        return tableClasses;
    }

    List<TableName> getTableNames() {
        return tableNames;
    }

    @Override
    public Children from(Children subWrapper,String alias){
        TableName tableName = new TableName();
        tableName.setAlias(alias);
        tableName.setTableName(Strings.getSubQuery(subWrapper,1));
        this.subWrapper = subWrapper;
        this.tableNames.add(tableName);
        return this.multiWrapper;
    }

    @Override
    public Children from(Class<?>... tableClasses) {
        this.tableClasses.addAll(Arrays.asList(tableClasses));
        for (Class<?> tableClass : tableClasses) {
            this.tableNames.add(new TableName(Reflections.getTableName(tableClass),Reflections.getTableAlias(tableClass)));
        }
        return this.multiWrapper;
    }

    @Override
    public <T> Children from(Class<T> t1Class, String alias) {
        this.tableNames.add(new TableName(Reflections.getTableName(t1Class),alias));
        this.tableClasses.add(t1Class);
        return this.multiWrapper;
    }

    @Override
    public String getSqlStatement(Integer subTimes) {
        Map<String,Integer> cache = new HashMap<>(this.tableNames.size());
        if (Objects.nonNull(this.subWrapper)) {
            this.multiWrapper.getParams().addAll(this.subWrapper.getParams());
        }
        return this.tableNames
                .stream()
                .map(tableName -> {
                    int count = cache.getOrDefault(tableName.getTableName(), 0);
                    if (count != 0){
                        tableName.setAlias(tableName.getAlias() + count);
                    }
                    count++;
                    cache.put(tableName.getTableName(),count);
                    return tableName.getSqlStatement();
                })
                .collect(Collectors.joining(StringPool.COMMA));
    }

}
