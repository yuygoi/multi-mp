package com.franklin.multimpsample.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 在线陪练-学生课程表
 * </p>
 *
 * @author Franklin
 * @since 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("fly_online_student_course_table")
public class OnlineStudentCourseTable implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 学生UUID
     */
    private String studentUuid;

    /**
     * 第几期
     */
    private Long courseLevelStage;

    /**
     * 第几讲
     */
    private Long sn;

    /**
     * 课程预约开始时间-时间戳，秒
     */
    private Long planStartTime;

    /**
     * 课程预约结束时间-时间戳，秒
     */
    private Long planEndTime;

    /**
     * 课程确定开始时间-时间戳，秒
     */
    private Long confirmStartTime;

    /**
     * 课程确定结束时间-时间戳，秒
     */
    private Long confirmEndTime;
    /**
     * 状态（1.待上课 2.已上课 3.已取消）
     */
    private Integer state;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
