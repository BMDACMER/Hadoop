package com.atguigu.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HdfsClient {
    @Test
    public void testMkdir() throws IOException, URISyntaxException, InterruptedException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        // 配置在集群上运行
        // configuration.set("fs.defaultFS", "hdfs://hadoop102:9000");
        // FileSystem fs = FileSystem.get(configuration);

        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:9000"), configuration, "root");

        // 2 创建目录
        fs.mkdirs(new Path("/20200617"));

        // 3 关闭资源
        fs.close();

    }

    private FileSystem fs;

    @Before
    public void before() throws IOException, InterruptedException {
        fs = FileSystem.get(URI.create("hdfs://hadoop102:900"), new Configuration(), "root");
        System.out.println("Before! 获取连接");
    }

    @Test
    public void put() throws IOException, InterruptedException {
        // 设置配置文件
        Configuration configuration = new Configuration();
        fs = FileSystem.get(URI.create("hdfs://hadoop102:9000"),configuration,"root");
        fs.copyFromLocalFile(new Path("d:/1.txt"), new Path("/20200617/3.txt"));
    }

    @Test
    public void get() throws IOException, InterruptedException {
        // 设置配置文件
        Configuration configuration = new Configuration();
        fs = FileSystem.get(URI.create("hdfs://hadoop102:9000"), configuration, "root");

        // 用这个对象操作文件系统
        fs.copyToLocalFile(new Path("/sanguo"), new Path("d:\\"));
    }

    @Test
    public void rename() throws IOException, InterruptedException {
        // 获取文件系统
        fs = FileSystem.get(URI.create("hdfs://hadoop102:9000"),new Configuration(), "root");
        fs.rename(new Path("/sanguo"),new Path("/sanguo2"));
    }

    @Test
    public void delete() throws IOException, InterruptedException {
        // 获取文件系统
        fs = FileSystem.get(URI.create("hdfs://hadoop102:9000"),new Configuration(),"root");
        boolean delete = fs.delete(new Path("/3.txt"), true);
        if (delete) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }

    @Test
    public void du() throws IOException, InterruptedException {
        fs = FileSystem.get(URI.create("hdfs://hadoop102:9000"),new Configuration(),"root");
        FSDataOutputStream append = fs.append(new Path("/20200617/3.txt"),1024);
        FileInputStream open = new FileInputStream("d:\\1.txt");
        IOUtils.copyBytes(open,append,1024,true);
    }

    @Test
    public void ls() throws IOException, InterruptedException {
        fs = FileSystem.get(URI.create("hdfs://hadoop102:9000"),new Configuration(), "root");
        FileStatus[] fileStatuses = fs.listStatus(new Path("/"));

        for (FileStatus fileStatus : fileStatuses) {
            if (fileStatus.isFile()) {
                System.out.println("以下信息是一个文件的信息");
                System.out.println(fileStatus.getPath());
                System.out.println(fileStatus.getLen());
            } else {
                System.out.println("这是一个文件夹");
                System.out.println(fileStatus.getPath());
            }
        }
    }

    @Test
    public void listFiles() throws IOException, InterruptedException {
        fs = FileSystem.get(URI.create("hdfs://hadoop102:9000"),new Configuration(), "root");
        RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path("/"),true);

        while (files.hasNext()) {
            LocatedFileStatus file = files.next();

            System.out.println("=========================");
            System.out.println(file.getPath());

            System.out.println("块信息：");
            BlockLocation[] blockLocations = file.getBlockLocations();
            for (BlockLocation blockLocation : blockLocations) {
                String[] hosts = blockLocation.getHosts();
                System.out.print("块在");
                for(String host : hosts) {
                    System.out.print(host + "\t");
                }
            }
        }
    }

    @After
    public void after() throws IOException {
        System.out.println("After! 关闭连接");
        fs.close();
    }
}
