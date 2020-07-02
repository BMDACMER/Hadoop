package com.atguigu.hbase.tools;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author:guohao
 * @email 1163753605@qq.com
 * @date: 2020/7/2 21:58
 * <p>
 * 1. 创建表和删除表
 * <p>
 * 2. TableName:  代表表名！
 * 调用valueof(String 库名，String 表名)，返回表名！
 * 如果库名为null，此时使用default作为库名
 * <p>
 * 3. HTableDescriptor: 代表表的细节（描述），包含表中列族的描述！
 */
public class TableUtil {
    private static Logger logger = LoggerFactory.getLogger(TableUtil.class);


    //验证表名是否合法并返回
    public static TableName checkTableName(String tableName, String nsname) {

        if (StringUtils.isBlank(tableName)) {

            logger.error("请输入正确的表名！");

            return null;

        }

        return TableName.valueOf(nsname, tableName);
    }

    //判断表是否存在
    public static boolean ifTableExists(Connection conn, String tableName, String nsname) throws IOException {

        //校验表名
        TableName tn = checkTableName(tableName, nsname);

        if (tn == null) {
            return false;
        }

        Admin admin = conn.getAdmin();

        //判断表是否存在,需要传入TableName对象
        boolean tableExists = admin.tableExists(tn);

        admin.close();

        return tableExists;


    }

    //创建表
    public static boolean createTable(Connection conn, String tableName, String nsname, String... cfs) throws IOException {

        //校验表名
        TableName tn = checkTableName(tableName, nsname);

        if (tn == null) {
            return false;
        }

        //至少需要传入一个列族
        if (cfs.length < 1) {

            logger.error("至少需要指定一个列族！");

            return false;

        }

        Admin admin = conn.getAdmin();

        //创建表的描述
        HTableDescriptor hTableDescriptor = new HTableDescriptor(tn);

        //讲列族的描述也添加到表的描述中
        for (String cf : cfs) {

            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(cf);

            //添加列族的设置
            hColumnDescriptor.setMinVersions(3);
            hColumnDescriptor.setMaxVersions(10);

            hTableDescriptor.addFamily(hColumnDescriptor);
        }

        //根据表的描述创建表
        admin.createTable(hTableDescriptor);

        admin.close();

        return true;


    }


    //删除表
    public static boolean dropTable(Connection conn, String tableName, String nsname) throws IOException {

        //检查表是否存在
        if (!ifTableExists(conn, tableName, nsname)) {

            return false;
        }

        //校验表名
        TableName tn = checkTableName(tableName, nsname);

        Admin admin = conn.getAdmin();

        //删除之前需要先禁用表
        admin.disableTable(tn);

        //删除表
        admin.deleteTable(tn);

        admin.close();

        return true;


    }
}
