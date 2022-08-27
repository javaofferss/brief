package com.javaoffers.batis.modelhelper.fun.crud.insert;

import com.javaoffers.batis.modelhelper.fun.GetterFun;

/**
 * 插入功能.
 *
 * @author cmj
 */
public interface InsertFun<M, C extends GetterFun<M, Object>, V> extends OneInsertCol<M,C,V>, SmartMoreInsertColAll<M,C,V> {

}
