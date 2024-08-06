package dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.JDBCUtilsByDruid;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote BasicDAO是其他 DAO 的父类
 */
public class BasicDAO<T> {
    private QueryRunner queryRunner = new QueryRunner();

    /**
     * DML
     * @param sql sql 语句，可以包含 ？
     * @param parameters 传入 ？ 的具体值，可以是多个
     * @return 返回受影响的行数
     */
    public int update(String sql, Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            int update = queryRunner.update(connection, sql, parameters);
            return update;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtilsByDruid.close(null, null, connection);
        }
    }

    /**
     * 多行查询
     * @param sql sql 语句，可以包含 ？
     * @param clazz 传入一个类的Class对象
     * @param parameters 传入 ？ 的具体值，可以是多个
     * @return 根据 Class 返回对应的 ArrayList 集合
     */
    public List<T> multipleQuery(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return queryRunner.query(connection, sql, new BeanListHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtilsByDruid.close(null, null, connection);
        }
    }

    /**
     * 单行查询
     * @param sql sql 语句，可以包含 ？
     * @param clazz 传入一个类的Class对象
     * @param parameters 传入 ？ 的具体值，可以是多个
     * @return 根据 Class 返回对应的 Class 对象
     */
    public T singleQuery(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return queryRunner.query(connection, sql, new BeanHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtilsByDruid.close(null, null, connection);
        }
    }

    /**
     * 查询单行单列，即返回单个值的查询
     * @param sql sql 语句，可以包含 ？
     * @param parameters 传入 ？ 的具体值，可以是多个
     * @return Object 对象
     */
    public Object scalarQuery(String sql, Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return queryRunner.query(connection, sql, new ScalarHandler(), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtilsByDruid.close(null, null, connection);
        }
    }
}
