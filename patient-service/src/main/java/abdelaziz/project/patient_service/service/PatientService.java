package abdelaziz.project.patient_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import abdelaziz.project.patient_service.dto.PatientRequestDto;
import abdelaziz.project.patient_service.dto.PatientResponseDto;
import abdelaziz.project.patient_service.mapper.PatientMapper;
import abdelaziz.project.patient_service.model.Patient;
import abdelaziz.project.patient_service.repository.PatientRepository;

@Service
public class PatientService {
    
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDto> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        
        List<PatientResponseDto> patientResponseDtos = patients.stream()
            .map(PatientMapper::mapToPatientResponseDto)
            .toList();
        return patientResponseDtos;
    }

    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto) {
        Patient patient = PatientMapper.toModel(patientRequestDto);
        Patient savedPatient = patientRepository.save(patient);
        return PatientMapper.mapToPatientResponseDto(savedPatient);
    }

}
