package top.ychen.forum.util;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

/**
 * 数据库连接池工具类
 *
 * @author moqi
 */
public class DataSourceUtil {

    private static final DataSource DATA_SOURCE;

    static {

        try {

            InputStream in = DataSourceUtil.class.getClassLoader().getResourceAsStream("database.properties");
            Properties p = new Properties();
            p.load(in);
            DATA_SOURCE = BasicDataSourceFactory.createDataSource(p);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new ExceptionInInitializerError("初始化DBPC失败");
        }
    }

    public static DataSource getDataSource() {
        return DATA_SOURCE;
    }
}
