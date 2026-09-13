package abdelaziz.billing_service.grpc;

import org.springframework.grpc.server.service.GrpcService;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService 
public class BillingGrpcServer extends BillingServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(BillingGrpcServer.class);

    //StreamObserver is basically the object you use to send or receive messages asynchronously.
    /*  
    “StreamObserver is gRPC's interface for handling asynchronous communication. It lets us send messages using onNext(),
     signal that the communication is finished with onCompleted(), and handle errors with onError().”
    */
    @Override
    public void createBillingAccount(BillingRequest request, StreamObserver<BillingResponse> responseObserver) {
        // TODO Auto-generated method stub
    //    super.createBillingAccount(request, responseObserver);
            logger.info("BillingGrpcServer.createBillingAccount() invoked with request: {}", request);

            BillingResponse response = BillingResponse.newBuilder()
            .setAccountId("123")
            .setStatus("Active")
            .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
    }
    
}
