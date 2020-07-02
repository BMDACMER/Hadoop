package com.atguigu.hbase.tools;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

/**
 * @author:guohao
 * @email 1163753605@qq.com
 * @date: 2020/7/2 21:57
 *
 * 1、 数据的增删改查，需要使用的是Table
 * 2、Put：代表对单行数据的put操作
 * 3、在hbase中，操作的数据都是以byte[]形式存在，需要把常用的数据类型转为byte[]
 *      hbase提供了Bytes工具类
 *          Bytes.toBytes(x): 基本数据类型转byte[]
 *          Bytes.toXxx(x): 从byte[]转为Xxx类型！
 * 4. Get: 代表对单行数据的Get操作！
 * 5. Result: scan或get的单行的所有的记录！
 * 6. Cell： 代表一个单元格，hbase提供了CellUtil.clonexxx(Cell)，来获取cell中的列族、列名和值属性！
 *
 */
public class DataUtil {
    // 先获取到表的table对象
    public static Table getTable(Connection conn, String tableName, String nsname) throws IOException, IOException {

        //验证表名是否合法
        TableName tn = TableUtil.checkTableName(tableName, nsname);

        if (tn == null) {
            return null;
        }

        //根据TableName获取对应的Table
        return conn.getTable(tn);

    }
}
