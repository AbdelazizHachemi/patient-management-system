package abdelaziz.project.patient_service.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import abdelaziz.project.patient_service.dto.PatientRequestDto;
import abdelaziz.project.patient_service.dto.PatientResponseDto;
import abdelaziz.project.patient_service.exception.EmailAlreadyExistsException;
import abdelaziz.project.patient_service.exception.PatientNotFoundException;
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

        if (patientRepository.existsByEmail(patientRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Patient patient = PatientMapper.toModel(patientRequestDto);
        Patient savedPatient = patientRepository.save(patient);
        return PatientMapper.mapToPatientResponseDto(savedPatient);
    }

    public PatientResponseDto updatePatient(
            UUID id,
            PatientRequestDto patientRequestDto) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("This patient does not exist"));

        //Find out whether there is a patient with this email whose ID is NOT the ID I'm currently updating
        if (patientRepository.existsByEmailAndIdNot(
                patientRequestDto.getEmail(), id)) {

            throw new EmailAlreadyExistsException("Email already exists");
        }

        patient.setName(patientRequestDto.getName());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setDateOfBirth(
                LocalDate.parse(patientRequestDto.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);

        return PatientMapper.mapToPatientResponseDto(updatedPatient);
    }

}
