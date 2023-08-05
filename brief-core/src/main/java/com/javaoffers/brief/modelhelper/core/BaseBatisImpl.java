package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.config.BriefProperties;
import com.javaoffers.brief.modelhelper.constants.ConfigPropertiesConstants;
import com.javaoffers.brief.modelhelper.exception.BriefException;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.jdbc.BriefJdbcExecutor;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutor;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutorFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: core implementation class
 * @Auther: create by cmj on 2022/05/22 02:56
 */
public class BaseBatisImpl<T, ID> implements BaseBatis<T> {

    private static JdbcExecutorFactory jdbcExecutorFactory;
    static {
        jdbcExecutorFactory = BriefProperties.getJdbcExecutorFactory();
    }

    private JdbcExecutor<T> jdbcExecutor;

    public static <T, ID> BaseBatis getInstance(HeadCondition headCondition) {
        return getInstance(headCondition.getDataSource(), headCondition.getModelClass());
    }

    private static <T, ID> BaseBatis getInstance(DataSource dataSource, Class mClass) {
        BaseBatisImpl batis = new BaseBatisImpl(dataSource,mClass);
        return new BaseBatisImplProxy(batis, mClass);
    }

    private BaseBatisImpl(DataSource dataSource,Class modelClass) {
        this.jdbcExecutor = jdbcExecutorFactory.createJdbcExecutor(dataSource, modelClass);
    }

    /****************************crud****************************/
    public int saveData(String sql) {
        return saveData(sql, Collections.EMPTY_MAP);
    }

    @Override
    public int saveData(String sql, Map<String, Object> map) {
        SQL sql_ = SQLParse.getSQL(sql, map);
        return this.jdbcExecutor.save(sql_).toInt();
    }

    public int deleteData(String sql) {
        return deleteData(sql, Collections.EMPTY_MAP);
    }

    @Override
    public int deleteData(String sql, Map<String, Object> map) {
        SQL sql_ = SQLParse.getSQL(sql, map);
        return this.jdbcExecutor.modify(sql_);
        //return this.jdbcTemplate.update(sql_.getSql(), new ArgumentPreparedStatementSetter(sql_.getArgsParam().get(0)));
    }

    public int updateData(String sql) {
        return updateData(sql, Collections.EMPTY_MAP);
    }

    @Override
    public int updateData(String sql, Map<String, Object> map) {
        SQL sql_ = SQLParse.getSQL(sql, map);
        return this.jdbcExecutor.modify(sql_);
        //return this.jdbcTemplate.update(sql_.getSql(), new ArgumentPreparedStatementSetter(sql_.getArgsParam().get(0)));
    }

//    @Override
//    public List<Map<String, Object>> queryData(String sql, Map<String, Object> map) {
//        SQL batchSQL = SQLParse.getSQL(sql, map);
//        long start = System.currentTimeMillis();
//        List<Map<String, Object>> result = queryData(batchSQL.getSql(), batchSQL.getArgsParam().get(0));
//        long end = System.currentTimeMillis();
//        System.out.println("C耗时 : "+(end-start));
//        return result;
//    }

    /*********************************Support Model*********************************/
    public List<T> queryData(String sql) {
//        List<Map<String, Object>> list_map = queryData(sql, EMPTY);
//        List<E> list = ModelParseUtils.converterMap2Model(clazz, list_map);
//        return list;
        return this.queryData(sql, new HashMap<>());
    }

    @Override
    public List<T> queryData(String sql, Map<String, Object> paramMap) {
//        long start1 = System.nanoTime();
//        List<Map<String, Object>> maps = queryData(sql, paramMap);
//        long start = System.nanoTime();
//        System.out.println("A耗时："+ TimeUnit.NANOSECONDS.toMillis(start - start1));
//        List<Map<String, Object>> tmp =  new ArrayList<>();
//        tmp.add(maps.get(0));
//        List<E> es = ModelParseUtils.converterMap2Model(clazz, tmp);
//        long end = System.nanoTime();
//        System.out.println("B耗时："+ TimeUnit.NANOSECONDS.toMillis(end - start));
//        return es;
        List<Map<String, Object>> paramMapList = new ArrayList<>();
        paramMapList.add(paramMap);
        SQL querySql = SQLParse.parseSqlParams(sql, paramMapList);
        return this.jdbcExecutor.queryList(querySql);
    }

    /*********************************batch processing*********************************/
    public Integer batchUpdate(String sql, List<Map<String, Object>> paramMap) {
        SQL batchSQL = SQLParse.parseSqlParams(sql, paramMap);
//        int[] is = batchUpdate(batchSQL.getSql(), batchSQL);
//
//        Assert.isTrue(is != null, " batch update is null ");
//        AtomicInteger countSuccess = new AtomicInteger();
//        Arrays.stream(is).forEach(countSuccess::addAndGet);
//        return Integer.valueOf(countSuccess.get());
        return this.jdbcExecutor.batchModify(batchSQL);
    }

    @Override
    public List<Id> batchInsert(String sql, List<Map<String, Object>> paramMap) {

        SQL pss = SQLParse.parseSqlParams(sql, paramMap);
        return this.jdbcExecutor.batchSave(pss);
//        List<Serializable> ids = new ArrayList<>();
//        jdbcTemplate.execute(new InsertPreparedStatementCreator(pss.getSql()), (PreparedStatementCallback<List<Serializable>>) ps -> {
//            try {
//                int batchSize = pss.getBatchSize();
//                InterruptibleBatchPreparedStatementSetter ipss =
//                        (pss instanceof InterruptibleBatchPreparedStatementSetter ?
//                                (InterruptibleBatchPreparedStatementSetter) pss : null);
//                Connection connection = ps.getConnection();
//                if (JdbcUtils.supportsBatchUpdates(connection) && batchSize > 0) {
//                    boolean oldAutoCommit = connection.getAutoCommit();
//                    connection.setAutoCommit(false);
//                    try {
//                        for (int i = 0; i < batchSize; i++) {
//                            pss.setValues(ps, i);
//                            if (ipss != null && ipss.isBatchExhausted(i)) {
//                                break;
//                            }
//                            ps.addBatch();
//                        }
//
//                        ps.executeBatch();
//                        //Avoid transactional inconsistencies
//                        if(oldAutoCommit){
//                            connection.commit();
//                        }
//                    }catch (Exception e){
//                        throw e;
//                    }finally {
//                        connection.setAutoCommit(oldAutoCommit);
//                    }
//
//                } else {
//                    List<Integer> rowsAffected = new ArrayList<>();
//                    for (int i = 0; i < batchSize; i++) {
//                        pss.setValues(ps, i);
//                        if (ipss != null && ipss.isBatchExhausted(i)) {
//                            break;
//                        }
//                        rowsAffected.add(ps.executeUpdate());
//                    }
//                    int[] rowsAffectedArray = new int[rowsAffected.size()];
//                    for (int i = 0; i < rowsAffectedArray.length; i++) {
//                        rowsAffectedArray[i] = rowsAffected.get(i);
//                    }
//
//                }
//
//                int i = 0;
//                ResultSet rs = ps.getGeneratedKeys();
//                while (rs.next() && i < batchSize) {
//                    Object object = rs.getObject(1);
//                    ids.add(Serializable2IdConvert.newId((Serializable) object));
//                    i++;
//                }
//                return ids;
//            } finally {
//                if (pss instanceof ParameterDisposer) {
//                    ((ParameterDisposer) pss).cleanupParameters();
//                }
//            }
//        });
//
//        return ids;
    }

//    /**
//     * Basic query implementation
//     *
//     * @param nativeSql sql
//     * @param param     parameter
//     * @return
//     */
//    private List<Map<String, Object>> queryData(String nativeSql, Object[] param) {
//        RowMapperResultSetExtractorPlus<Map<String, Object>> rowMapperResultSetExtractorPlus =
//                new RowMapperResultSetExtractorPlus(this.modelClass);
//        return this.jdbcTemplate.query(nativeSql, param, rowMapperResultSetExtractorPlus);
//    }

//   private int[] batchUpdate(String sql, final BatchPreparedStatementSetter pss) throws DataAccessException {
//
//        int[] result = this.jdbcTemplate.execute(sql, (PreparedStatementCallback<int[]>) ps -> {
//            try {
//                int batchSize = pss.getBatchSize();
//                InterruptibleBatchPreparedStatementSetter ipss =
//                        (pss instanceof InterruptibleBatchPreparedStatementSetter ?
//                                (InterruptibleBatchPreparedStatementSetter) pss : null);
//                Connection connection = ps.getConnection();
//                if (JdbcUtils.supportsBatchUpdates(connection)) {
//                    boolean oldAutoCommit = connection.getAutoCommit();
//                    connection.setAutoCommit(false);
//                    try {
//                        for (int i = 0; i < batchSize; i++) {
//                            pss.setValues(ps, i);
//                            if (ipss != null && ipss.isBatchExhausted(i)) {
//                                break;
//                            }
//                            ps.addBatch();
//                        }
//                        int[] ints = ps.executeBatch();
//                        //Avoid transactional inconsistencies
//                        if(oldAutoCommit){
//                            connection.commit();
//                        }
//                        return ints;
//                    }catch (Exception e){
//                        throw e;
//                    }finally {
//                        connection.setAutoCommit(oldAutoCommit);
//                    }
//                }
//                else {
//                    List<Integer> rowsAffected = new ArrayList<>();
//                    for (int i = 0; i < batchSize; i++) {
//                        pss.setValues(ps, i);
//                        if (ipss != null && ipss.isBatchExhausted(i)) {
//                            break;
//                        }
//                        rowsAffected.add(ps.executeUpdate());
//                    }
//                    int[] rowsAffectedArray = new int[rowsAffected.size()];
//                    for (int i = 0; i < rowsAffectedArray.length; i++) {
//                        rowsAffectedArray[i] = rowsAffected.get(i);
//                    }
//                    return rowsAffectedArray;
//                }
//            }
//            finally {
//                if (pss instanceof ParameterDisposer) {
//                    ((ParameterDisposer) pss).cleanupParameters();
//                }
//            }
//        });
//
//        Assert.state(result != null, "No result array");
//        return result;
//    }

}
