package com.pm.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * BillingServiceGrpcClient is a gRPC client that communicates with the Billing Service.
 * It establishes a channel to the billing microservice and invokes remote gRPC methods
 * to create billing accounts for patients.
 */
@Service
public class BillingServiceGrpcClient {

    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);

    // gRPC blocking stub — used for making synchronous (blocking) calls to the Billing Service.
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    /**
     * Constructor that initializes a gRPC connection to the Billing Service.
     *
     * @param serverAddress Hostname or IP of the billing service (default: localhost)
     * @param serverPort    Port number where billing service’s gRPC server is running (default: 9001)
     */
    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort
    ) {
        log.info("Connecting to Billing Service gRPC at {}:{}", serverAddress, serverPort);

        // Create a channel to connect to the Billing Service server.
        // usePlaintext() disables TLS for simplicity in local/dev environments.
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(serverAddress, serverPort)
                .usePlaintext()
                .build();

        // Initialize the blocking stub using the channel.
        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Sends a CreateBillingAccount gRPC request to the Billing Service.
     *
     * @param patientId Unique ID of the patient
     * @param name      Name of the patient
     * @param email     Email address of the patient
     * @return BillingResponse containing account ID and status
     */
    public BillingResponse createBillingAccount(String patientId, String name, String email) {
        // Build the gRPC request message.
        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();

        // Make a synchronous (blocking) gRPC call to the Billing Service.
        BillingResponse response = blockingStub.createBillingAccount(request);

        // Log and return the response received from the Billing Service.
        log.info("Received response from Billing Service via gRPC: {}", response);
        return response;
    }
}
