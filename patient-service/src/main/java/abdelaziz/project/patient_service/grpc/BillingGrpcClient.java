package abdelaziz.project.patient_service.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BillingGrpcClient {

    private final BillingServiceGrpc.BillingServiceBlockingStub billingStub;

    public BillingGrpcClient(
            @Value("${billing.grpc.host}") String host,
            @Value("${billing.grpc.port}") int port) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        billingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(
            String patientId,
            String name,
            String email) {
        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();

        return billingStub.createBillingAccount(request);
    }
}