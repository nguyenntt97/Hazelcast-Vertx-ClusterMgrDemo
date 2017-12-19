import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;

public class DemoVerticle extends AbstractVerticle {
    private int request_count = 0;
    private int worker_busy = 0;

    @Override
    public void start() throws Exception {
        DeploymentOptions options = new DeploymentOptions()
                .setInstances(10)
                .setWorker(true);

        vertx.deployVerticle(DemoWorker.class, options, res -> {
            if (res.succeeded()) {
                System.out.println("Deploy Worker successfully");
            } else {
                System.out.println("[ERROR] " + res.cause());
            }
        });
        HttpServer server = vertx.createHttpServer();


        server.requestHandler(req -> {
            req.response().end();
            worker_busy++;
            System.out.println("WORKER_BUSY = " + worker_busy);

            EventBus eb = Launcher.clusterEB;
            eb.send("worker.count", request_count, res -> {
                if (res.succeeded()) {
                    System.out.println("Received reply No." + res.result().body());
                    worker_busy--;
                    System.out.println("WORKER_BUSY = " + worker_busy);
                }
            });
        });

        server.listen(8089, res -> {
            if (res.succeeded()) {
                System.out.println("Server deployed successfully");
            } else {
                System.out.println("[ERROR] " + res.cause());
            }
        });
    }
}
