package com.franklin.multimpcore.wrapper.conditions.interfaces;

import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.franklin.multimpcore.wrapper.table.Column;
import com.franklin.multimpcore.wrapper.util.Reflections;

import java.util.Collection;

/**
 * Description : 主查询与子查询条件关联
 * Create By: Franklin
 * Date : 2020/4/29 16:16
 */
public interface MainConnectSub<Children> extends Compare<Children>{

    /** ======================无别名=========================
     */
    default <T1,T2> Children eq(SFunction<T1,?> col,SFunction<T2,?> mainCol){
        return this.eq(col,Reflections.getTableAlias(col),mainCol,Reflections.getTableAlias(mainCol));
    }

    default <T1,T2> Children ge(SFunction<T1,?> col,SFunction<T2,?> mainCol){
        return this.ge(col,Reflections.getTableAlias(col),mainCol,Reflections.getTableAlias(mainCol));
    }

    default <T1,T2> Children le(SFunction<T1,?> col,SFunction<T2,?> mainCol){
        return this.le(col,Reflections.getTableAlias(col),mainCol,Reflections.getTableAlias(mainCol));
    }

    default <T1,T2> Children lt(SFunction<T1,?> col,SFunction<T2,?> mainCol){
        return this.lt(col,Reflections.getTableAlias(col),mainCol,Reflections.getTableAlias(mainCol));
    }

    default <T1,T2> Children gt(SFunction<T1,?> col,SFunction<T2,?> mainCol){
        return this.gt(col,Reflections.getTableAlias(col),mainCol,Reflections.getTableAlias(mainCol));
    }

    /** ======================子查询有别名，主查询无别名=========================
     */
    default <T1,T2> Children eq(SFunction<T1,?> col,String subTbAlias,SFunction<T2,?> mainCol){
        return eq(col,subTbAlias,mainCol,Reflections.getTableAlias(mainCol));
    }

    default <T1,T2> Children ge(SFunction<T1,?> col,String subTbAlias,SFunction<T2,?> mainCol){
        return this.ge(col,subTbAlias,mainCol,Reflections.getTableAlias(mainCol));
    }

    default <T1,T2> Children le(SFunction<T1,?> col,String subTbAlias,SFunction<T2,?> mainCol){
        return this.le(col,subTbAlias,mainCol,Reflections.getTableAlias(mainCol));
    }

    default <T1,T2> Children lt(SFunction<T1,?> col,String subTbAlias,SFunction<T2,?> mainCol){
        return this.lt(col,subTbAlias,mainCol,Reflections.getTableAlias(mainCol));
    }

    default <T1,T2> Children gt(SFunction<T1,?> col,String subTbAlias,SFunction<T2,?> mainCol){
        return this.gt(col,subTbAlias,mainCol,Reflections.getTableAlias(mainCol));
    }

    /** ======================子查询无别名，主查询有别名=========================
     */
    default <T1,T2> Children eq(SFunction<T1,?> col,SFunction<T2,?> mainCol,String mainTbAlias){
        return this.eq(col,Reflections.getTableAlias(col),mainCol,mainTbAlias);
    }

    default <T1,T2> Children ge(SFunction<T1,?> col,SFunction<T2,?> mainCol,String mainTbAlias){
        return this.ge(col,Reflections.getTableAlias(col),mainCol,mainTbAlias);
    }

    default <T1,T2> Children le(SFunction<T1,?> col,SFunction<T2,?> mainCol,String mainTbAlias){
        return this.le(col,Reflections.getTableAlias(col),mainCol,mainTbAlias);
    }

    default <T1,T2> Children lt(SFunction<T1,?> col,SFunction<T2,?> mainCol,String mainTbAlias){
        return this.lt(col,Reflections.getTableAlias(col),mainCol,mainTbAlias);
    }

    default <T1,T2> Children gt(SFunction<T1,?> col,SFunction<T2,?> mainCol,String mainTbAlias){
        return this.gt(col,Reflections.getTableAlias(col),mainCol,mainTbAlias);
    }

    /** ======================都有别名=========================
     */

    default <T1,T2> Children eq(SFunction<T1,?> col,String subTbAlias,SFunction<T2,?> mainCol,String mainTbAlias){
        Column column = new Column();
        column.setColumnName(Reflections.guessColumnNameByLambda(LambdaUtils.resolve(mainCol),mainTbAlias));
        return this.eq(col,column,subTbAlias);
    }

    default <T1,T2> Children ge(SFunction<T1,?> col,String subTbAlias,SFunction<T2,?> mainCol,String mainTbAlias){
        Column column = new Column();
        column.setColumnName(Reflections.guessColumnNameByLambda(LambdaUtils.resolve(mainCol),mainTbAlias));
        return this.ge(col,column,subTbAlias);
    }

    default <T1,T2> Children le(SFunction<T1,?> col,String subTbAlias,SFunction<T2,?> mainCol,String mainTbAlias){
        Column column = new Column();
        column.setColumnName(Reflections.guessColumnNameByLambda(LambdaUtils.resolve(mainCol),mainTbAlias));
        return this.le(col,column,subTbAlias);
    }

    default <T1,T2> Children lt(SFunction<T1,?> col,String subTbAlias,SFunction<T2,?> mainCol,String mainTbAlias){
        Column column = new Column();
        column.setColumnName(Reflections.guessColumnNameByLambda(LambdaUtils.resolve(mainCol),mainTbAlias));
        return this.lt(col,column,subTbAlias);
    }

    default <T1,T2> Children gt(SFunction<T1,?> col,String subTbAlias,SFunction<T2,?> mainCol,String mainTbAlias){
        Column column = new Column();
        column.setColumnName(Reflections.guessColumnNameByLambda(LambdaUtils.resolve(mainCol),mainTbAlias));
        return this.gt(col,column,subTbAlias);
    }
}
