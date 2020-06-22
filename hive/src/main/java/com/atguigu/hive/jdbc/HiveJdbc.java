package com.atguigu.hive.jdbc;

import java.sql.*;

/**
 * 必须实现开启 hive下的 bin/hiveserver2
 */
public class HiveJdbc {
    public static void main(String[] args) throws SQLException {
//        Connection conn = DriverManager.getConnection("jdbc:hive2://hadoop102:10000","root","root");
        Connection conn = DriverManager.getConnection("jdbc:hive2://hadoop102:10000", "root", "");
        String sql = "select * from default.student";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            System.out.println("id:" + resultSet.getInt("id") + "---->name: " + resultSet.getString("name"));
        }
    }
}
