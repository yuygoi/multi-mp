package com.franklin.multimpcore.wrapper.conditions.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description :join关键字
 * Create By: Franklin
 * Date : 2020/4/24 15:04
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum JoinType {

    LEFT_JOIN("LEFT JOIN"),
    RIGHT_JOIN("RIGHT JOIN"),
    INNER_JOIN("INNER JOIN"),
    OUTER_JOIN("OUTER JOIN");

    private String joinVal;
}
