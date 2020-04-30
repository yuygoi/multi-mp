package com.franklin.multimpsample.controller;

import com.franklin.multimpcore.wrapper.conditions.MultiWrapper;
import com.franklin.multimpcore.wrapper.conditions.Select;
import com.franklin.multimpsample.entity.OnlineClass;
import com.franklin.multimpsample.entity.OnlineClassRecord;
import com.franklin.multimpsample.entity.OnlineStudentCourseTable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * Description :
 * Create By: Franklin
 * Date : 2020/4/24 15:57
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @PostMapping("/test/1")
    public String test1(){
        MultiWrapper multiWrapper = new MultiWrapper();
        multiWrapper
                .select(
                        new Select<OnlineClass>()
                        .column(
                                OnlineClass::getId,
                                OnlineClass::getActualEndTime,
                                OnlineClass::getActualStartTime
                        ),
                        new Select<OnlineClassRecord>()
                        .column(
                                OnlineClassRecord::getId,
                                OnlineClassRecord::getOnlineClassId,
                                OnlineClassRecord::getRatingContent
                        ).column(new MultiWrapper().select(
                                new Select<OnlineStudentCourseTable>()
                                .column(OnlineStudentCourseTable::getConfirmStartTime)
                                .column(new MultiWrapper().select(
                                        new Select<OnlineClassRecord>()
                                        .column(OnlineClassRecord::getId)
                                ).from(OnlineClassRecord.class),"recordId")
                            ).from(OnlineStudentCourseTable.class)
                                .leftJoin(OnlineClassRecord.class)
                                .on(OnlineStudentCourseTable::getId,OnlineClassRecord::getOnlineClassId)
                                .eq(OnlineStudentCourseTable::getId,2L)
                                .in(OnlineClass::getId, Arrays.asList(1,2,3,4))
                        ,"courseTableTime")
                ).from(
                        new MultiWrapper().select(
                                new Select<OnlineClass>()
                                .column(
                                        OnlineClass::getId,
                                        OnlineClass::getActualEndTime
                                )
                        ).from(OnlineClass.class)
                        .eq(OnlineClass::getId,1L)
                ,"test")
                .leftJoin(OnlineClassRecord.class)
                .on(OnlineClass::getId,OnlineClassRecord::getOnlineClassId)
                .leftJoin(OnlineStudentCourseTable.class)
                .on(OnlineClassRecord::getStudentCourseTableId,OnlineStudentCourseTable::getId)
                .eq(OnlineClass::getId,new MultiWrapper().select(
                        new Select<OnlineClassRecord>()
                                .column(OnlineClassRecord::getId)
                ).from(OnlineClassRecord.class))
                .eq(OnlineClassRecord::getOrderId,1)
                .ge(OnlineStudentCourseTable::getConfirmStartTime,524534645L)
                .in(OnlineClassRecord::getId,new MultiWrapper().select(
                        new Select<OnlineClassRecord>()
                                .column(OnlineClassRecord::getId)
                ).from(OnlineClassRecord.class)
                    .eq(OnlineClassRecord::getOrderId,OnlineClass::getId)
                )
                .in(OnlineClass::getId, Arrays.asList(1,2,3,4));
        String sqlStatement = multiWrapper.getSqlStatement();
        System.out.println(multiWrapper.getParams());
        return sqlStatement;
    }

    @PostMapping("/test/2")
    public String test2(){
        MultiWrapper multiWrapper = new MultiWrapper();
        multiWrapper
                .select(
                        new Select<OnlineClass>()
                                .column(
                                        OnlineClass::getId,
                                        OnlineClass::getActualEndTime,
                                        OnlineClass::getActualStartTime
                                ),
                        new Select<OnlineClass>()
                                .column(
                                        OnlineClass::getId,
                                        OnlineClass::getActualEndTime,
                                        OnlineClass::getActualEndTime
                                )
                ).from(OnlineClass.class)
                .innerJoin(OnlineClass.class)
                .on(OnlineClass::getId,OnlineClass::getId);
        return multiWrapper.getSqlStatement();
    }

    @PostMapping("/test/3")
    public String test3(){
        MultiWrapper multiWrapper = new MultiWrapper();
        multiWrapper
                .select(
                        new Select<OnlineClass>("oc1")
                                .column(
                                        OnlineClass::getId,
                                        OnlineClass::getActualEndTime,
                                        OnlineClass::getActualStartTime
                                ),
                        new Select<OnlineClass>("oc2")
                                .column(
                                        OnlineClass::getId,
                                        OnlineClass::getActualEndTime,
                                        OnlineClass::getActualEndTime
                                ).column(OnlineClass::getId,"testId")
                ).from(OnlineClass.class,"oc1")
                .innerJoin(OnlineClass.class,"oc2")
                .on(OnlineClass::getId,OnlineClass::getId);
        return multiWrapper.getSqlStatement();
    }

    @PostMapping("/test/4")
    public String test4(){
        MultiWrapper multiWrapper = new MultiWrapper();
        multiWrapper
                .select(
                        new Select<OnlineClass>()
                                .column(
                                        OnlineClass::getId,
                                        OnlineClass::getActualEndTime,
                                        OnlineClass::getActualStartTime
                                ),
                        new Select<OnlineClass>()
                                .column(
                                        OnlineClass::getId,
                                        OnlineClass::getActualEndTime,
                                        OnlineClass::getActualEndTime
                                ).column()
                ).from(OnlineClass.class,OnlineClass.class,OnlineClass.class);
        return multiWrapper.getSqlStatement();
    }

    @PostMapping("/test/5")
    public String test5(){
        MultiWrapper multiWrapper = new MultiWrapper();
        multiWrapper
                .select(
                        new Select<OnlineClass>()
                                .column(
                                        OnlineClass::getId,
                                        OnlineClass::getActualEndTime,
                                        OnlineClass::getActualStartTime
                                ),
                        new Select<OnlineClass>()
                                .column(
                                        OnlineClass::getId,
                                        OnlineClass::getActualEndTime,
                                        OnlineClass::getActualEndTime
                                ).column()
                ).from(new MultiWrapper().select(
                new Select<OnlineClassRecord>()
                        .column(OnlineClassRecord::getId)
        ).from(OnlineClassRecord.class),"test");
        return multiWrapper.getSqlStatement();
    }
}
