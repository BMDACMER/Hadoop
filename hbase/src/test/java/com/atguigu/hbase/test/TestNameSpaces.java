package com.atguigu.hbase.test;

import com.atguigu.hbase.tools.ConnectionUtil;
import com.atguigu.hbase.tools.NameSpaceUtil;
import org.apache.hadoop.hbase.client.Connection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author:guohao
 * @email 1163753605@qq.com
 * @date: 2020/7/3 10:56
 */
public class TestNameSpaces {
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
    public void testListNSs() throws IOException {
        System.out.println(NameSpaceUtil.listNameSpace(conn));
    }

    @Test
    public void testIfExistsNSs() throws IOException {
        System.out.println(NameSpaceUtil.ifNSExists(conn,"default"));
    }

    @Test
    public void testCreateNSs() throws IOException {
        System.out.println(NameSpaceUtil.createNS(conn,"guohao"));
    }

    @Test
    public void testTablesInNS() throws IOException {

        System.out.println(NameSpaceUtil.getTablesInNameSpace(conn,"default"));

    }

    @Test
    public void testDeleteNS() throws IOException {

        System.out.println(NameSpaceUtil.deleteNS(conn,"guohao"));

    }
}
