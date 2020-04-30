package com.franklin.multimpcore.wrapper.conditions.interfaces;

import com.franklin.multimpcore.wrapper.conditions.MultiWrapper;
import com.franklin.multimpcore.wrapper.util.Reflections;

/**
 * Description :
 * Create By: Franklin
 * Date : 2020/4/27 9:51
 */
public interface From<Children> extends SqlStatement {

    default <T> Children from(Class<T> t1Class){
        return from(t1Class, Reflections.getTableAlias(t1Class));
    }

    Children from(Children subWrapper,String alias);

    Children from(Class<?> ...tableClasses);

    <T> Children from(Class<T> t1Class,String alias);
}
