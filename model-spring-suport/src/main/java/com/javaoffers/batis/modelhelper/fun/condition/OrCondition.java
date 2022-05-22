package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;

import java.util.Collections;
import java.util.Map;

/**
 * @Description: or 关键字
 * @Auther: create by cmj on 2022/5/4 20:01
 */
public class OrCondition extends WhereOnCondition implements Condition {
    private String or = " or ";
    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.OR;
    }

    @Override
    public String getSql() {
        return ConditionTag.OR.getTag();
    }

    @Override
    public Map<String, Object> getParams() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public String toString() {
        return "OrCondition{" +
                "or='" + or + '\'' +
                '}';
    }
}
