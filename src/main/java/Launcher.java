import com.hazelcast.config.Config;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class Launcher {
    public static EventBus clusterEB = null;
    public static void main(String[] args) {
//        Config hazelcastConfig = new Config();
//        hazelcastConfig.getNetworkConfig().getJoin().getTcpIpConfig().addMember("192.168.110.21").addMember("192.168.150.193").setEnabled(true);
//        hazelcastConfig.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
//
//        ClusterManager mgr = new HazelcastClusterManager(hazelcastConfig);
        ClusterManager mgr = new HazelcastClusterManager();

        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                clusterEB = vertx.eventBus();
                vertx.deployVerticle(new DemoVerticle());
            }
        });
    }
}
