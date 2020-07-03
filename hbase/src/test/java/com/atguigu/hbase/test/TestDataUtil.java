package com.atguigu.hbase.test;

import com.atguigu.hbase.tools.ConnectionUtil;
import com.atguigu.hbase.tools.DataUtil;
import org.apache.hadoop.hbase.client.Connection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author:guohao
 * @email 1163753605@qq.com
 * @date: 2020/7/3 11:28
 */
public class TestDataUtil {
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
    public void testPut() throws IOException {

        DataUtil.put(conn, "t1", null, "b1", "cf1", "name", "jack");

    }

    @Test
    public void testGet() throws IOException {

        DataUtil.get(conn, "t1", "ns1", "r1");

    }

}
