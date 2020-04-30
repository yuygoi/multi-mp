package com.franklin.multimpcore.wrapper.conditions.interfaces;

import java.io.Serializable;

/**
 * Description :
 * Create By: Franklin
 * Date : 2020/4/25 14:57
 */
public interface SqlStatement extends Serializable {

    default String getSqlStatement(){
        return getSqlStatement(0);
    }

    String getSqlStatement(Integer subTimes);
}
