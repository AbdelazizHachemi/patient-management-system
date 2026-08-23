package abdelaziz.project.patient_service.dto;

import lombok.Data;

@Data
public class PatientResponseDto {
    private String id;
    private String name;
    private String email;
    private String address;
    private String dateOfBirth;    
}
