package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.core.BaseBatisImpl;
import com.javaoffers.batis.modelhelper.core.ConditionParse;
import com.javaoffers.batis.modelhelper.core.SQLInfo;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.BetweenCondition;
import com.javaoffers.batis.modelhelper.fun.condition.ExistsCondition;
import com.javaoffers.batis.modelhelper.fun.condition.InCondition;
import com.javaoffers.batis.modelhelper.fun.condition.OnConditionMark;
import com.javaoffers.batis.modelhelper.fun.condition.OrCondition;
import com.javaoffers.batis.modelhelper.fun.condition.WhereConditionMark;
import com.javaoffers.batis.modelhelper.fun.condition.WhereOnCondition;
import com.javaoffers.batis.modelhelper.fun.crud.WhereSelectFun;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;


/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:14
 */
public class WhereSelectFunImpl<M,V> implements WhereSelectFun<M,V> {


    /**静态**/
    private static JdbcTemplate jdbcTemplate;

    public WhereSelectFunImpl(JdbcTemplate jdbcTemplate){
        synchronized (WhereSelectFunImpl.class){
            if(jdbcTemplate == null){
                WhereSelectFunImpl.jdbcTemplate = jdbcTemplate;
            }
        }
    }

    private LinkedList<Condition> conditions;

    public WhereSelectFunImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
        this.conditions.add(new WhereConditionMark());
    }

    public WhereSelectFunImpl(LinkedList<Condition> conditions,boolean isAddMark) {
        this.conditions = conditions;
        if(isAddMark){
            this.conditions.add(new WhereConditionMark());
        }
    }

    public WhereSelectFunImpl() {
    }

    @Override
    public WhereSelectFunImpl<M, V> or() {
        conditions.add(new OrCondition());
        return this;
    }

    @Override
    public WhereSelectFun<M, V> eq(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.EQ));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> eq(boolean condition, GetterFun<M, V> col, V value) {
        if(condition){

        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> ueq(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.UEQ));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> ueq(boolean condition, GetterFun<M, V> col, V value) {
        if(condition){
            ueq(col,  value);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> gt(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.GT));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> gt(boolean condition, GetterFun<M, V> col, V value) {
        if(condition){
            gt(col,  value);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> lt(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.LT));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> lt(boolean condition, GetterFun<M, V> col, V value) {
        if(condition){
            lt(col,  value);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> gtEq(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.GT_EQ));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> gtEq(boolean condition, GetterFun<M, V> col, V value) {
        if(condition){
            gtEq(col,  value);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> ltEq(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.LT_EQ));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> ltEq(boolean condition, GetterFun<M, V> col, V value) {
        if(condition){
            ltEq(col,  value);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> between(GetterFun<M, V> col, V start, V end) {
        conditions.add(new BetweenCondition(col,start, end, ConditionTag.BETWEEN));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> between(boolean condition, GetterFun<M, V> col, V start, V end) {
        if(condition){
            between(col,  start, end);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> notBetween(GetterFun<M, V> col, V start, V end) {
        conditions.add(new BetweenCondition(col,start, end, ConditionTag.NOT_BETWEEN));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> notBetween(boolean condition, GetterFun<M, V> col, V start, V end) {
        return null;
    }

    @Override
    public WhereSelectFun<M, V> like(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.LIKE));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> like(boolean condition, GetterFun<M, V> col, V value) {
        if(condition){
            like(col,  value);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> likeLeft(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.LIKE_LEFT));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> likeLeft(boolean condition, GetterFun<M, V> col, V value) {
        if(condition){
            likeLeft(col,value);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> likeRight(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.LIKE_RIGHT));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> likeRight(boolean condition, GetterFun<M, V> col, V value) {
        if(condition){
            likeRight(col,value);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> in(GetterFun<M, V> col, V... values) {
        conditions.add(new InCondition(col, values,ConditionTag.IN));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> in(boolean condition, GetterFun<M, V> col, V... values) {
        if(condition){
            in(col,values);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> in(GetterFun<M, V> col, Collection... values) {
        conditions.add(new InCondition(col, values,ConditionTag.IN));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> in(boolean condition, GetterFun<M, V> col, Collection... values) {
        if(condition){
            in(col,values);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> notIn(GetterFun<M, V> col, V... values) {
        return null;
    }

    @Override
    public WhereSelectFun<M, V> notIn(boolean condition, GetterFun<M, V> col, V... values) {
        return null;
    }

    @Override
    public WhereSelectFun<M, V> notIn(GetterFun<M, V> col, Collection... values) {
        return null;
    }

    @Override
    public WhereSelectFun<M, V> notIn(boolean condition, GetterFun<M, V> col, Collection... values) {
        return null;
    }

    @Override
    public WhereSelectFunImpl<M, V> exists(String existsSql) {
         conditions.add(new ExistsCondition<V>(existsSql));
         return this;
    }

    @Override
    public WhereSelectFun<M, V> exists(boolean condition, String existsSql) {
        if(condition){
            exists(existsSql);
        }
        return this;
    }

    @Override
    public M ex() {
        //conditions.stream().forEach(condition -> System.out.println(condition.toString()));
        //解析SQL 并执行 select。
        List<M> exs = exs();
        if(exs!=null && exs.size()>0){
            return exs.get(0);
        }
        return null;
    }

    @Override
    public List<M> exs() {
        //conditions.stream().forEach(condition -> System.out.println(condition.toString()));
        //解析SQL select 并执行。
        SQLInfo sqlInfo = ConditionParse.conditionParse(conditions);
        System.out.println(sqlInfo.getSql());
        System.out.println(sqlInfo.getParams());
        List list = BaseBatisImpl.baseBatis.queryDataForT4(sqlInfo.getSql(), sqlInfo.getParams().get(0), sqlInfo.getAClass());
        return list;
    }

}
