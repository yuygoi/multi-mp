package com.franklin.multimpcore.wrapper.table;

import com.franklin.multimpcore.wrapper.conditions.constants.SqlKeywords;
import com.franklin.multimpcore.wrapper.conditions.interfaces.SqlStatement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description :描述表名信息
 * Create By: Franklin
 * Date : 2020/4/27 15:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableName implements SqlStatement,TableElement{

    private String tableName;
    private String alias;

    @Override
    public String getSqlStatement(Integer subTimes) {
        return this.tableName + SqlKeywords.AS + this.alias;
    }
}
