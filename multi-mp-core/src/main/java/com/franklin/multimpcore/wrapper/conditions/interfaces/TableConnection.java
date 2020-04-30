package com.franklin.multimpcore.wrapper.conditions.interfaces;
import com.franklin.multimpcore.wrapper.util.Reflections;

/**
 * Description :表连接
 * Create By: Franklin
 * Date : 2020/4/27 10:37
 */
public interface TableConnection<Children,Wrapper> extends SqlStatement{

    default <T2> Children leftJoin(Class<T2> t2Class){
        return leftJoin(t2Class, Reflections.getTableAlias(t2Class));
    }

    default <T2> Children rightJoin(Class<T2> t2Class){
        return rightJoin(t2Class, Reflections.getTableAlias(t2Class));
    }

    default <T2> Children innerJoin(Class<T2> t2Class){
        return innerJoin(t2Class, Reflections.getTableAlias(t2Class));
    }

    default <T2> Children outerJoin(Class<T2> t2Class){
        return outerJoin(t2Class, Reflections.getTableAlias(t2Class));
    }

    Children leftJoin(Wrapper subWrapper);

    Children rightJoin(Wrapper subWrapper);

    Children innerJoin(Wrapper subWrapper);

    Children outerJoin(Wrapper subWrapper);

    <T2> Children leftJoin(Class<T2> t2Class,String alias);

    <T2> Children rightJoin(Class<T2> t2Class,String alias);

    <T2> Children innerJoin(Class<T2> t2Class,String alias);

    <T2> Children outerJoin(Class<T2> t2Class,String alias);
}
