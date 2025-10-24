package com.pm.billingservice.grpc;
//
import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//BillingGrpcService

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase{
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

//    @Override
//    public void CreateBillingAccount(BillingRequest billingRequest, StreamObserver<BillingResponse> responseObserver) {
//        log.info("first Create BillingAccount request recived {}", billingRequest.toString());
//
//        //Business logic
//
//        BillingResponse response = BillingResponse.newBuilder().setAccountId("1234").setStatus("Active").build();
//
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }

    @Override
    public  void createBillingAccount(BillingRequest billingRequest, StreamObserver<BillingResponse> responseObserver) {
        log.info("second Create BillingAccount request recived {}", billingRequest.toString());

        //Business logic

        BillingResponse response = BillingResponse.newBuilder().setAccountId("1234").setStatus("Active").build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
