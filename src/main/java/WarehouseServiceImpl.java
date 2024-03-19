import com.google.common.collect.ImmutableList;
import io.grpc.stub.StreamObserver;
import warehouse.*;

import java.time.LocalDateTime;


public class WarehouseServiceImpl extends WarehouseServiceGrpc.WarehouseServiceImplBase {
    @Override
    public void getWarehouseData(Warehouse.WarehouseRequest request, StreamObserver<Warehouse.WarehouseResponse> responseObserver) {
        System.out.println("Handling warehouse endpoint" + request.toString());

        String warehouseUUID = request.getUuid();

        System.out.println("Getting data of warehouse with uuid=" + warehouseUUID + "...");

        // create a few dummy product objects

        Warehouse.Product product1 = Warehouse.Product.newBuilder()
                .setProductId("798ou9hvn98mj98")
                .setProductName("Ananas")
                .setProductCategory("Obst")
                .setProductQuantity(103)
                .setProductUnit("2 Stk.")
                .build();
        Warehouse.Product product2 = Warehouse.Product.newBuilder()
                .setProductId("joi094090")
                .setProductName("PINARELLO F9 SRAM RED AXS")
                .setProductCategory("Rennrad")
                .setProductQuantity(12)
                .setProductUnit("1 Stk.")
                .build();
        Warehouse.Product product3 = Warehouse.Product.newBuilder()
                .setProductId("rg442567b")
                .setProductName("Aeroad CFR Di2 ")
                .setProductCategory("Rennrad")
                .setProductQuantity(43)
                .setProductUnit("1 Stk.")
                .build();


        // now create the warehouse response object
        Warehouse.WarehouseResponse response = Warehouse.WarehouseResponse.newBuilder()
                .setWarehouseId(warehouseUUID)
                .setWarehouseName("Klosternueburg Lager")
                .setWarehouseAddress("Inkusstraße 5")
                .setWarehousePostalCode(3400)
                .setWarehouseCity("Klosternueburg")
                .setWarehouseCountry("AUT")
                .setTimestamp(LocalDateTime.now().toString())
                .addAllProductData(ImmutableList.of(product1, product2, product3))
                .build();

        // send the response to the client
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}