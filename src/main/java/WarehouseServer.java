import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class WarehouseServer {
    private static final int PORT = 50022;
    private Server server;
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1); // Executor initialized here

    public void start() throws IOException {
        server = ServerBuilder.forPort(PORT)
                .addService(new WarehouseServiceImpl())
                .addService(new HealthCheckServiceImpl())
                .build()
                .start();

        System.out.println("Server started, listening on " + PORT);

        // Schedule periodic tasks, e.g., sending ping messages
        startPeriodicTasks();
    }

    private void startPeriodicTasks() {
        executor.scheduleAtFixedRate(() -> {
            // This is where you send Ping messages to clients
            System.out.println("Ping clients - Server up");
        }, 0, 1, TimeUnit.SECONDS); // Adjust timing as needed
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
        executor.shutdownNow(); // Ensure the executor is also shut down
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final WarehouseServer server = new WarehouseServer();
        System.out.println("Starting Warehouse Server...");
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server since JVM is shutting down.");
            server.stop();
            System.out.println("Server shut down.");
        }));

        server.blockUntilShutdown();
    }
}
