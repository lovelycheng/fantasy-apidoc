package com.treefinance.hound.zookeeper;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;


/**
 * @author chengtong
 * @date 2019-09-08 14:14
 */
public enum ZookeeperClient {

    /**
     *
     */
    CLIENT;

    private CuratorFramework client;

    private Boolean inited = false;

    public synchronized void init(String address){
        if (inited){
            return;
        }
        inited = true;
        try {
            int timeout = 50000;
            CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                    .connectString(address)
                    .retryPolicy(new RetryNTimes(1, 1000))
                    .connectionTimeoutMs(timeout);

            client = builder.build();
            client.start();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public void create(String path,String content) throws Exception {
       Object node = client.checkExists().forPath(path);
       if(node == null){
           client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path,content.getBytes());
       }else {
           client.delete().forPath(path);
           client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path,content.getBytes());
       }
    }

    public void destory(){
        client.close();
    }

}
