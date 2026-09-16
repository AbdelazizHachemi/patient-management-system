package abdelaziz.project.patient_service.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BillingGrpcClient {

    /*
     * Production-readiness checklist:
     *
     * Client-side improvements:
     * - Add a configurable deadline to every blocking gRPC call.
     * - Keep the ManagedChannel as a field and shut it down with @PreDestroy.
     * - Replace usePlaintext() with TLS in production; keep plaintext only for local development.
     * - Read host, port, timeout, and TLS settings from environment-specific configuration.
     * - Catch StatusRuntimeException and translate gRPC status codes into application exceptions.
     * - Add retries only for safe, transient failures such as UNAVAILABLE.
     * - Add idempotency before retrying account-creation requests to prevent duplicate accounts.
     * - Propagate correlation/tracing metadata through gRPC calls.
     * - Add metrics for call duration, failures, timeouts, and gRPC status codes.
     * - Add integration tests for success, invalid input, timeout, unavailable service, and duplicates.
     *
     * Billing-service improvements:
     * - Replace the hardcoded billing response with real database persistence.
     * - Validate patient ID, name, and email on the server.
     * - Generate unique account IDs and enforce account uniqueness.
     * - Support idempotency keys and return the original result for duplicate requests.
     * - Return appropriate gRPC status codes such as INVALID_ARGUMENT and ALREADY_EXISTS.
     * - Add gRPC health checks and readiness/liveness monitoring.
     * - Avoid logging complete requests because they may contain personal information.
     * - Configure container-to-container communication with the billing service hostname, not localhost.
     */

    private static final Logger logger = LoggerFactory.getLogger(BillingGrpcClient.class);
    private final BillingServiceGrpc.BillingServiceBlockingStub billingStub;

    //these values are coming from application.properties file, and they are injected into the constructor using Spring's @Value annotation.
    public BillingGrpcClient(
            @Value("${billing.grpc.host}") String host,
            @Value("${billing.grpc.port}") int port) {
        
                logger.info("Initializing BillingGrpcClient with host: {} and port: {}", host, port);
                
                //managedChannel is a gRPC channel that abstracts the underlying network connection to the gRPC server.
                //It handles connection pooling, load balancing, and other networking concerns.
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

        BillingResponse response = billingStub.createBillingAccount(request);
        
        logger.info(
        "Returning billing response: accountId={}, status={}",
        response.getAccountId(),
        response.getStatus()
);
        
        return response;
    }
}