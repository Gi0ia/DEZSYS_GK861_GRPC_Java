import io.grpc.stub.StreamObserver;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import warehouse.*;


public class HealthCheckServiceImpl extends HealthCheckServiceGrpc.HealthCheckServiceImplBase {
    @Override
    public void ping(Warehouse.PingRequest req, StreamObserver<Warehouse.PingResponse> responseObserver) {
        // Implementation here
        Warehouse.PingResponse response = Warehouse.PingResponse.newBuilder().setMessage("healthy").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    private void logResponse(String clientId, String status) {
        try (PrintWriter out = new PrintWriter(new FileWriter("health_check_log.txt", true))) {
            out.println(clientId + ", " + LocalDateTime.now() + ", " + status);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
