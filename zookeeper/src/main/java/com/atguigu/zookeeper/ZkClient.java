package com.atguigu.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ZkClient {

    private ZooKeeper zkCli;
    private static final String CONNECT_STRING = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
    private static final int SESSION_TIMEOUT = 2000;

    @Before
    public void before() throws IOException {
        zkCli = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, e -> {
            System.out.println("默认回调函数");
        });
    }

    @Test
    public void ls() throws KeeperException, InterruptedException {
        List<String> children = zkCli.getChildren("/", e -> {
            System.out.println("自定义回调函数");
        });

        System.out.println("************************");
        for (String child : children) {
            System.out.println(child);
        }
        System.out.println("**********************");

        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void create() throws KeeperException, InterruptedException {
        String s = zkCli.create("/Idea", "Idea2018".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        System.out.println(s);

        Thread.sleep(Long.MAX_VALUE);

    }

    @Test
    public void get() throws KeeperException, InterruptedException {
        byte[] data = zkCli.getData("/sanguo", true, new Stat());

        String string = new String(data);

        System.out.println(string);

    }

    @Test
    public void set() throws KeeperException, InterruptedException {

        Stat stat = zkCli.setData("/tt1240000000002", "defabc".getBytes(), 0);

        System.out.println(stat.getDataLength());

    }

    @Test
    public void stat() throws KeeperException, InterruptedException {
        Stat exists = zkCli.exists("/Idea", false);
        if (exists == null) {
            System.out.println("节点不存在");
        } else {
            System.out.println(exists.getDataLength());
        }
    }

    @Test
    public void delete() throws KeeperException, InterruptedException {
        Stat exists = zkCli.exists("/tt1240000000002", false);
        if (exists != null)
            zkCli.delete("/tt1240000000002", exists.getVersion());
    }

    public void register() throws KeeperException, InterruptedException {
        byte[] data = zkCli.getData("/a", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                try {
                    register();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, null);

        System.out.println(new String(data));
    }

    @Test
    public void testRegister() {
        try {
            register();
            Thread.sleep(Long.MAX_VALUE);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
