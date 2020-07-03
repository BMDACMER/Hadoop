package com.atguigu.hbase.tools;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * @author:guohao
 * @email 1163753605@qq.com
 * @date: 2020/7/2 21:57
 * <p>
 * 1、 数据的增删改查，需要使用的是Table
 * 2、Put：代表对单行数据的put操作
 * 3、在hbase中，操作的数据都是以byte[]形式存在，需要把常用的数据类型转为byte[]
 * hbase提供了Bytes工具类
 * Bytes.toBytes(x): 基本数据类型转byte[]
 * Bytes.toXxx(x): 从byte[]转为Xxx类型！
 * 4. Get: 代表对单行数据的Get操作！
 * 5. Result: scan或get的单行的所有的记录！
 * 6. Cell： 代表一个单元格，hbase提供了CellUtil.clonexxx(Cell)，来获取cell中的列族、列名和值属性！
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

    //put 表名,rowkey,列名(列族名:列名),value
    public static void put(Connection conn, String tableName, String nsname, String rowkey, String cf,
                           String cq, String value) throws IOException {
        Table table = getTable(conn, tableName, nsname);
        if (table == null) {
            return;
        }

        Put put = new Put(Bytes.toBytes(rowkey));

        //向put中设置cell的细节信息
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cq), Bytes.toBytes(value));
        //.addColumn(family, qualifier, value)

        table.put(put);

        table.close();
    }

    // get 表名   rowkey

    public static void get(Connection conn, String tableName, String nsname, String rowkey) throws IOException {

        //获取表对象
        Table table = getTable(conn, tableName, nsname);

        if (table == null) {
            return;
        }

        Get get = new Get(Bytes.toBytes(rowkey));

        //设置单行查询的详细信息
        //设置查哪个列
        //get.addColumn(family, qualifier)
        //设置查哪个列族
        //get.addFamily(family)
        //只查某个时间戳的数据
        //get.setTimeStamp(timestamp)
        //设置返回的versions
        //get.setMaxVersions(maxVersions)

        Result result = table.get(get);

        //System.out.println(result);

        parseResult(result);

        table.close();


    }

    //遍历result
    public static void parseResult(Result result) {

        if (result != null) {

            Cell[] cells = result.rawCells();

            for (Cell cell : cells) {

                System.out.println("行：" + Bytes.toString(CellUtil.cloneRow(cell)) +
                        "  列族：" + Bytes.toString(CellUtil.cloneFamily(cell)) + "   列名：" +
                        Bytes.toString(CellUtil.cloneQualifier(cell)) +
                        "  值:" + Bytes.toString(CellUtil.cloneValue(cell)));

            }

        }

    }

}
