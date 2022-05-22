package com.javaoffers.batis.modelhelper.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 表的描述信息
 * @Auther: create by cmj on 2022/5/4 00:00
 */
public class TableInfo {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * K: 表字段
     * V: 对应的表字段信息， 由数据库查询所得
     * 懒加载存放
     */
    private Map<String,ColumnInfo> colName = new LinkedHashMap<>();

    /**
     * K: 属性名称
     * V: 对应的表字段， 由类属性生成
     * 懒加载存放
     */
    private Map<String,String> colNameOfModel = new LinkedHashMap<>();

    /**
     * K:  对应的表字段， 由类属性生成
     * V:  属性名称，作为别名在生成sql时
     * 懒加载存放
     */
    private Map<String,String> cloNameAsAlias = new LinkedHashMap<>();

    /**
     * K: lamda获取的方法名称
     * V: 对应的表字段 由lamda生成
     * 懒加载存放
     */
    private Map<String,String> colNameOfGetter = new LinkedHashMap<>();

    /**
     * 存放字段信息
     */
    private Set<ColumnInfo> columnInfos = new LinkedHashSet<>();

    public String getTableName() {
        return tableName;
    }

    public Map<String, ColumnInfo> getColName() {
        return colName;
    }

    public Set<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public TableInfo(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, String> getColNameOfModel() {
        return colNameOfModel;
    }

    public Map<String, String> getColNameOfGetter() {
        return colNameOfGetter;
    }

    public Map<String, String> getCloNameAsAlias() {
        return cloNameAsAlias;
    }
}
