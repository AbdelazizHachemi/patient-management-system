package abdelaziz.project.analytics_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.protobuf.InvalidProtocolBufferException;
import patient.event.PatientEvent;

@Service 
public class KafkaConsumer {
 
    //this is how we consume events from topic all the work is done behind the scenes by spring boot and kafka listener
    @KafkaListener (topics = "patient", groupId = "analytics-group")
    public void consumeEvent(byte[] event) {

        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            // Process the patient event (e.g., log it, store it in a database, etc.)
            System.out.println("Received patient event: " + patientEvent);
        } catch (InvalidProtocolBufferException e) {
            throw new IllegalArgumentException("Invalid patient event", e);
        }
    }
}
