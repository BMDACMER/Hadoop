package com.atguigu.hbase.test;

import com.atguigu.hbase.tools.ConnectionUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * @author:guohao
 * @email 1163753605@qq.com
 * @date: 2020/7/2 22:17
 */
public class TestConn {
    @Test
    public void test() throws IOException {
        System.out.println(ConnectionUtil.getConn());
    }
}
