package com.franklin.multimpcore.wrapper.util;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.franklin.multimpcore.wrapper.conditions.constants.StringPool;

/**
 * Description :
 * Create By: Franklin
 * Date : 2020/4/25 15:23
 */
public class Reflections {

    public static String guessFieldByGetMethod(String methodName) {
        if (StringUtils.isBlank(methodName) || !methodName.contains("get")){
            return null;
        }
        String name = methodName.substring("get".length());
        return StringUtils.camelToUnderline(name);
    }

    public static String guessFieldByGetMethod(SerializedLambda lambda) {
        return guessFieldByGetMethod(lambda.getImplMethodName());
    }

    public static String getTableName(Class tableClass){
        if (tableClass.isAnnotationPresent(TableName.class)){
            TableName tableName = (TableName) tableClass.getDeclaredAnnotation(TableName.class);
            return tableName.value();
        }
        String simpleName = tableClass.getSimpleName();
        return StringUtils.camelToUnderline(simpleName);
    }

    public static String getCamelTableName(SerializedLambda lambda){
        return getCamelTableName(lambda.getImplClass());
    }

    public static String getCamelTableName(Class tableClass){
        if (tableClass.isAnnotationPresent(TableName.class)){
            TableName tableName = (TableName) tableClass.getDeclaredAnnotation(TableName.class);
            return StringUtils.underlineToCamel(tableName.value());
        }
        return StringUtils.firstCharToLower(tableClass.getSimpleName());
    }

    public static String guessColumnNameByLambda(SerializedLambda lambda){
        Class<?> tableClass = lambda.getImplClass();
        String implMethodName = lambda.getImplMethodName();
        return getTableName(tableClass) + StringPool.DOT + guessFieldByGetMethod(implMethodName);
    }

    public static String guessColumnNameByLambda(SerializedLambda lambda,String tbAlias){
        return tbAlias + StringPool.DOT + guessFieldByGetMethod(lambda.getImplMethodName());
    }

    public static String getTableName(SerializedLambda lambda){
        Class<?> tableClass = lambda.getImplClass();
        return getTableName(tableClass);
    }

    /**
     * 获取表的别名
     * @param tableName
     * @return
     */
    public static String getTableAlias(String tableName){
        if (StringUtils.isBlank(tableName)){
            return null;
        }
        if (StringUtils.isCamel(tableName)){
            tableName = StringUtils.camelToUnderline(tableName);
        }
        String[] split = tableName.split(StringPool.UNDERSCORE);
        if (split.length < 1){
            return String.valueOf(tableName.charAt(0)).toLowerCase();
        }
        StringBuilder builder = new StringBuilder();
        for (String s : split) {
            builder.append(String.valueOf(s.charAt(0)).toLowerCase());
        }
        return builder.toString();
    }

    public static String getTableAlias(SerializedLambda lambda){
        return getTableAlias(getTableName(lambda));
    }

    public static <T> String getTableAlias(SFunction<T,?> col){
        return getTableAlias(LambdaUtils.resolve(col));
    }

    public static <T> String getTableAlias(Class<T> t1Class) {
        return getTableAlias(getTableName(t1Class));
    }

    public static String getColumnAlias(String methodName){
        if (StringUtils.isBlank(methodName) || !methodName.contains("get")){
            return null;
        }
        return StringUtils.firstCharToLower(methodName.substring("get".length()));
    }

    public static String getColumnAliasByColumnName(String column){
        return StringUtils.underlineToCamel(column);
    }

    public static String getColumnAlias(SerializedLambda lambda){
        return getColumnAlias(lambda.getImplMethodName());
    }

    public static void main(String[] args) {
        System.out.println(getTableAlias("fly_online_class_student"));
    }
}
