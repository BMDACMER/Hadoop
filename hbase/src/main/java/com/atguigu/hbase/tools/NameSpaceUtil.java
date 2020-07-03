package com.atguigu.hbase.tools;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author:guohao
 * @email 1163753605@qq.com
 * @date: 2020/7/2 21:57
 */
/*
 * 1. 创建/删除/查询/判断是否存在   名称空间
 *
 * 		hbase shell :  开启一个客户端对象
 * 		hbase shell : create_namespace 库名
 *
 * 2. Admin :  提供对hbase管理的一些api!
 * 			   例如创建，删除，查询表等！
 * 				可以使用Connection.getAdmin()来获取Admin的一个实例，使用完成后，调用
 * 				close关闭！
 *
 * 3. Connection：  Connection代表客户端和集群的一个连接！这个连接包含对master的连接，和zk的连接！
 * 				  Connection可以使用ConnectionFactory来创建！
 * 				  Connection的创建是重量级的，因此建议一个应用只创建一个Connection对象！Connection是线程安全的，
 * 				  可以在多个线程中共享同一个Connection实例！
 * 					Connection的生命周期也是用户自己控制！
 *
 * 				从Connection中获取Table和Admin对象的实例！Table和Admin对象的创建是轻量级，且不是线程安全的！
 * 				因此不建议池化或缓存Table和Admin对象的实例，每个线程有自己的Table和Admin对象的实例！
 *
 * 4. NamespaceDescriptor: 用来代表和定义名称空间
 */
public class NameSpaceUtil {
    private static Logger logger = LoggerFactory.getLogger(NameSpaceUtil.class);

    // 查询所有的名称空间
    public static List<String> listNameSpace(Connection conn) throws IOException {
        List<String> nss = new ArrayList<>();
        // 提供一个Admin
        Admin admin = conn.getAdmin();

        // 查询所有的库
        NamespaceDescriptor[] namespaceDescriptors = admin.listNamespaceDescriptors();

        for (NamespaceDescriptor namespaceDescriptor : namespaceDescriptors) {
            //取出每个库描述中库的名称
            nss.add(namespaceDescriptor.getName());
        }

        // 关闭admin
        admin.close();
        return nss;
    }

    // 判断是否存在库
    public static boolean ifNSExists(Connection conn, String nsname) throws IOException {
        // 库名校验
        if (StringUtils.isBlank(nsname)) {
            logger.error("请输入正确的库名");
            // 在后台提示，库名非法
            return false;
        }

        // 提供一个Admin
        Admin admin = conn.getAdmin();

        // 根据库名查询对应的NS，如果找不到就抛异常
        try {
            admin.getNamespaceDescriptor(nsname);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // 关闭admin
            admin.close();
        }
    }

    // 创建库
    public static boolean createNS(Connection conn, String nsname) throws IOException {
        // 库名校验
        if (StringUtils.isBlank(nsname)) {
            logger.error("请输入正确的库名");
            return false;
        }

        // 提供一个Admin
        Admin admin = conn.getAdmin();

        // 新建库
        try {
            NamespaceDescriptor descriptor = NamespaceDescriptor.create(nsname).build();
            admin.createNamespace(descriptor);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            admin.close();
        }
    }

    // 删除库
    public static boolean deleteNS(Connection conn, String nsname) throws IOException {
        if (StringUtils.isBlank(nsname)) {
            logger.error("请输入正常的库名");
            return false;
        }

        Admin admin = conn.getAdmin();

        List<String> tables = getTablesInNameSpace(conn, nsname);

        if (tables.size() == 0) {
            admin.deleteNamespace(nsname);
            admin.close();
            return true;
        }else {
            admin.close();
            logger.error(nsname+"库非空！无法删除！");
            return false;
        }
    }
    //查询库下有哪些表
    public static List<String> getTablesInNameSpace(Connection conn, String nsname) throws IOException {
        if (StringUtils.isBlank(nsname)) {
            logger.error("请输入正常的库名");
            return null;
        }

        List<String> tables = new ArrayList<>();

        Admin admin = conn.getAdmin();

        HTableDescriptor[] tableDescriptors = admin.listTableDescriptorsByNamespace(nsname);

        for (HTableDescriptor tableDescriptor : tableDescriptors) {
            tables.add(tableDescriptor.getNameAsString());
        }

        admin.close();
        return tables;
    }
}
