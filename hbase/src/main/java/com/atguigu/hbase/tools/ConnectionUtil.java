package com.atguigu.hbase.tools;


import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * @author:guohao
 * @email 1163753605@qq.com
 * @date: 2020/7/2 21:57
 *
 * 1 创建和关闭Connection对象
 *
 * 2、 如何在HBase中创建一个Configuration对象
 *     可以使用HBaseConfiguration.create(),返回的Configuration，即可包含hadoop
 *     8个配置文件的参数，又把贫寒hbase-default.xml和hbase-site.xnl中所有的参数配置！
 */
public class ConnectionUtil {
    // 创建一个Connection对象
    public static Connection getConn() throws IOException {
        return ConnectionFactory.createConnection();
    }

    public static void close(Connection conn) throws IOException {
        if (conn != null) {
            conn.close();
        }
    }
}
