package org.example.liuhengfei.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MakeCodeMain {

    public static void main(String[] args) throws SQLException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext" +
                "-makeCode.xml");
        DruidDataSource datasource = context.getBean(DruidDataSource.class);
        DruidPooledConnection conn = datasource.getConnection();
        String[] beanNames = {"manager_Web_tableSet"};
        for (String str : beanNames) {
            //从spring配置文件获取该模块需要生成的表集合
            Set<String> tableSet = (Set<String>) context.getBean(str);
            //循环全部表
            creatTables(conn, tableSet);
        }
        conn.close();
    }

    private static void creatTables(DruidPooledConnection conn, Set<String> tableSet) throws SQLException {
        for (String tableName : tableSet) {
            //获取指定表的全部字段和说明
            List<String> listcolumn = getColumnCommentByTableName(tableName, conn);
            //删除第一列，主键列
            String primaryKey = listcolumn.remove(0);
            String tableComment = getTableComment(tableName, conn);
            System.out.println("【表名称:" + tableName + " 】");
            //去除表名称的前缀
            //tableName = tableName.substring(3);
            //生成代码
            GeneratorSource.makeService(tableName, tableComment, primaryKey);
            GeneratorSource.makeServiceImpl(tableName, tableComment, primaryKey);
            GeneratorSource.makeController(tableName, tableComment, primaryKey);
            GeneratorSource.makeJsController(tableName, tableComment, primaryKey);
            GeneratorSource.makeJsService(tableName, tableComment, primaryKey);
            GeneratorSource.makeHtml(tableName, tableComment, listcolumn, primaryKey);
        }
    }

    //获取全部数据表名称
    public static List<String> getAllTables(DruidPooledConnection conn) throws SQLException {
        List tables = new ArrayList();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW TABLES ");
        while (rs.next()) {
            String tableName = rs.getString(1);
            tables.add(tableName);
        }
        rs.close();
        stmt.close();
        return tables;
    }

    //获取制定数据表的注释
    public static String getTableComment(String tableName, DruidPooledConnection conn) throws SQLException {
        String comment = null;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + tableName);
        if (rs != null && rs.next()) {
            String createDDL = rs.getString(2);
            comment = parse(createDDL);
        }
        rs.close();
        stmt.close();
        return comment;
    }

    //获取制定数据表的列名、类型、注释
    public static List<String> getColumnCommentByTableName(String tableName, DruidPooledConnection conn) throws SQLException {
        List<String> list = new ArrayList<String>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("show full columns from " + tableName);
        while (rs.next()) {
            //System.out.println("字段名称：" + rs.getString("Field") + "\t"+ "字段注释：" + rs.getString("Comment")+ "\t"+
            // "字段类型：" + rs.getString("Type") );
            list.add(rs.getString("Field") + "~" + rs.getString("Type") + "~" + rs.getString("Comment"));
        }
        rs.close();
        stmt.close();
        return list;
    }

    //解析注释
    public static String parse(String all) {
        String comment;
        int index = all.indexOf("COMMENT='");
        if (index < 0) {
            return "";
        }
        comment = all.substring(index + 9);
        comment = comment.substring(0, comment.length() - 1);
        return comment;
    }

}
