package com.atguigu.hbase.test;

import com.atguigu.hbase.tools.ConnectionUtil;
import com.atguigu.hbase.tools.TableUtil;
import org.apache.hadoop.hbase.client.Connection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author:guohao
 * @email 1163753605@qq.com
 * @date: 2020/7/2 22:48
 */
public class TestTableUtil {
    private Connection conn;

    @Before
    public void init() throws IOException {
        conn = ConnectionUtil.getConn();
    }

    @After
    public void close() throws IOException {
        ConnectionUtil.close(conn);
    }

    @Test
    public void testTableExists() throws IOException {
        System.out.println(TableUtil.ifTableExists(conn, "t2", null));
    }

    @Test
    public void testCreateTable() throws IOException {

        System.out.println(TableUtil.createTable(conn, "t2", null,"cf1","cf2"));

    }

    @Test
    public void testDropTable() throws IOException {

        System.out.println(TableUtil.dropTable(conn, "t2", null));

    }
}
