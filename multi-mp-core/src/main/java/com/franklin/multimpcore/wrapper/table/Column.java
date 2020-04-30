package com.franklin.multimpcore.wrapper.table;

import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.franklin.multimpcore.wrapper.conditions.constants.SqlKeywords;
import com.franklin.multimpcore.wrapper.conditions.constants.StringPool;
import com.franklin.multimpcore.wrapper.conditions.interfaces.SqlStatement;
import com.franklin.multimpcore.wrapper.util.Reflections;
import com.franklin.multimpcore.wrapper.util.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description :描述字段信息
 * Create By: Franklin
 * Date : 2020/4/27 19:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Column implements SqlStatement,TableElement{

    private String columnName;

    private String alias;
    private Boolean isDefaultAlias;
    private Boolean isSubQuery;

    private SerializedLambda lambda;

    private String tableName;

    @Override
    public String getSqlStatement(Integer subTimes) {
        if (this.isDefaultAlias && SqlKeywords.ID_UPPER.equalsIgnoreCase(this.alias)){
            if (Objects.nonNull(this.lambda)){
                String camelTableName = Reflections.getCamelTableName(this.lambda);
                return this.columnName + SqlKeywords.AS + camelTableName + SqlKeywords.ID_UPPER;
            }
        }
        String blank = Strings.getBlank(subTimes);
        String leftBlank = Strings.getBlank(subTimes - 2);
        String rightBlank = Strings.getBlank(subTimes - 1);
        if (this.isSubQuery){
            String[] split = this.columnName.split(StringPool.NEXT);
            String collect = Stream.of(split)
                    .map(s -> {
                        if (StringPool.LEFT_BRACKET.equals(s)){
                            return leftBlank + s;
                        }else if (StringPool.RIGHT_BRACKET.equals(s)){
                            return rightBlank + s;
                        }
                        return blank + s;
                    })
                    .collect(Collectors.joining(StringPool.NEXT));
            return collect + SqlKeywords.AS + this.alias;
        }
        return blank + this.columnName + SqlKeywords.AS + this.alias;
    }
}
