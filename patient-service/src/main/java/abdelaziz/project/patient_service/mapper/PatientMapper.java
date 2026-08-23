package abdelaziz.project.patient_service.mapper;

import abdelaziz.project.patient_service.dto.PatientResponseDto;
import abdelaziz.project.patient_service.model.Patient;

public class PatientMapper {
    public static PatientResponseDto mapToPatientResponseDto(Patient patient) {
        PatientResponseDto patientResponseDto = new PatientResponseDto();
        patientResponseDto.setId(patient.getId().toString());
        patientResponseDto.setName(patient.getName());
        patientResponseDto.setEmail(patient.getEmail());
        patientResponseDto.setAddress(patient.getAddress());
        patientResponseDto.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientResponseDto;
    }
}
