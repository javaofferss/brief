package com.javaoffers.batis.modelhelper.anno.fun.params.time;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: time_format(colName,format)
 * @author: create by cmj on 2022/10/15 16:19
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TimeFormat {
    public static final String TAG = "TIME_FORMAT";

    /**
     * format
     */
    String value();
}
