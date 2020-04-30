package com.franklin.multimpcore.wrapper.util;

import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.franklin.multimpcore.wrapper.conditions.AbstractMultiWrapper;
import com.franklin.multimpcore.wrapper.conditions.MultiWrapper;
import com.franklin.multimpcore.wrapper.conditions.constants.StringPool;

import java.util.List;

/**
 * Description :字符串工具类
 * Create By: Franklin
 * Date : 2020/4/28 14:48
 */
public class Strings {

    public static String getBlank(int blankTimes){
        StringBuilder blank = new StringBuilder();
        for (int i = 0; i < blankTimes * 2; i++) {
            blank.append(StringPool.BLANK);
        }
        return blank.toString();
    }

    /**
     * 判断是不是圆括号
     * @param str
     * @return
     */
    public static boolean isCurves(String str){
        return StringPool.LEFT_BRACKET.equals(str) || StringPool.RIGHT_BRACKET.equals(str);
    }

    /**
     * 获取子查询SQL语句
     * @param wrapper
     * @return
     */
    public static String getSubQuery(AbstractMultiWrapper wrapper,int subTimes){
        String wrapperSqlStatement = wrapper.getSqlStatement(subTimes);
        StringBuilder builder = new StringBuilder(StringPool.LEFT_BRACKET + StringPool.NEXT)
                .append(wrapperSqlStatement);
        if (StringPool.NEXT.charAt(0) != wrapperSqlStatement.charAt(wrapperSqlStatement.length() - 1)){
            builder.append(StringPool.NEXT);
        }
        builder.append(StringPool.RIGHT_BRACKET);
        return builder.toString();
    }
}
