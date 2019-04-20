package zk;

import org.apache.zookeeper.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
@PropertySource("classpath:zookeeper.properties")
public class ZKClient implements Watcher {

    @Value("${zookeeper.host}")
    private String host;

    @Value("${zookeeper.port")
    private String port;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String connectString = "192.168.100.185:2181";
        int sessionTimeOut = 5000;
        ZooKeeper zooKeeper = new ZooKeeper(connectString, sessionTimeOut, new ZKClient());
        System.out.println("zookeeper state:" + zooKeeper.getState());
        String path = "/";
        List<String> children = zooKeeper.getChildren(path, false);
        for (String child : children) {
            System.out.println("child:" + child);
        }

//        System.out.println("zookeeper ");
//        Long sessionId = zooKeeper.getSessionId();
//        byte[] password = zooKeeper.getSessionPasswd();
//        zooKeeper = new ZooKeeper("192.168.100.185:2181",5000,new ZKClient(),1L,"test".getBytes());
//        zooKeeper = new ZooKeeper("192.168.100.185:2181",5000,new ZKClient(),sessionId,password);
        //Thread.sleep(Integer.MAX_VALUE);
        //zooKeeper.create("/testData","testData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,new IStringCallback(),"i am context");
//        zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,new IStringCallback(),"i am context");
//        zooKeeper.delete("/testData",0);

        System.out.println(zooKeeper.exists("/testData", true));
    }

    public void process(WatchedEvent event) {
        System.out.println("监听到了：" + event.getType());
    }
}

class IStringCallback implements AsyncCallback.StringCallback {
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("Create path result: [" + rc + "," + path + "," + ctx + ", real path name " + name);
    }
}
