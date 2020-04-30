package com.franklin.multimpsample.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 在线陪练-上课小组表
 * </p>
 *
 * @author Franklin
 * @since 2020-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("fly_online_class")
public class OnlineClass implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 网易云房间号
     */
    private String neteaseRoomId;

    /**
     * 网易云房间名称
     */
    private String neteaseRoomName;

    /**
     * 老师ID
     */
    private Long teacherId;

    /**
     * 老师昵称
     */
    private String teacherNickName;

    /**
     * 老师姓名
     */
    private String teacherName;

    /**
     * 老师头像
     */
    private String teacherLogo;

    /**
     * 第几期
     */
    private Long courseLevelStage;

    /**
     * 第几讲
     */
    private Long sn;

    /**
     * 课程大纲
     */
    private String classContent;

    /**
     * 计划上课开始时间
     */
    private Long planStartTime;

    /**
     * 计划上课时间结束
     */
    private Long planEndTime;

    /**
     * 实际上课开始时间
     */
    private Long actualStartTime;

    /**
     * 实际上课结束时间
     */
    private Long actualEndTime;

    /**
     * 状态（1.待上课 2.已上课 3.已取消）
     */
    private Integer state;

    /**
     * 是否删除（0.不删除 1.删除）
     */
    @TableLogic
    private Integer isDel;


}
