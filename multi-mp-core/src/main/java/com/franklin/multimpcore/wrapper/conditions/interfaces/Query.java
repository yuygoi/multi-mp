package com.franklin.multimpcore.wrapper.conditions.interfaces;
import com.franklin.multimpcore.wrapper.conditions.Select;

/**
 * Description :
 * Create By: Franklin
 * Date : 2020/4/27 9:36
 */
public interface Query<Children,Sub> extends SqlStatement {

    Children select(Select<?>...cols);

    Children select(Sub subQuery);


}
