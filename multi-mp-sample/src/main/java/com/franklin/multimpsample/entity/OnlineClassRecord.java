package com.franklin.multimpsample.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 在线陪练-学生上课表
 * </p>
 *
 * @author Franklin
 * @since 2020-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("fly_online_class_record")
public class OnlineClassRecord implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 在线陪练班级ID
     */
    private Long onlineClassId;

    /**
     * 成员类型，0.老师 1.学生
     */
    private Integer userType;

    /**
     * 老师ID，user_type=0
     */
    private Long teacherId;

    /**
     * 学生UUID，user_type=1
     */
    private String studentUuid;

    /**
     * 学生陪练课表ID，user_type=1
     */
    private Long studentCourseTableId;

    /**
     * 对老师的评分，默认5分，1-5分
     */
    private Integer ratingStar;

    /**
     * 评价内容
     */
    private String ratingContent;

    /**
     * 状态（1.已上课 2.未上课 3.已取消）
     */
    private Integer state;

    /**
     * 学生是否评价老师 0.未评价 1.已评价
     */
    private Integer type;

    /**
     * 当state=1时，学生记录消耗的哪个订单
     */
    private Long orderId;


}
