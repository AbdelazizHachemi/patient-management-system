package abdelaziz.project.patient_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import abdelaziz.project.patient_service.dto.PatientRequestDto;
import abdelaziz.project.patient_service.dto.PatientResponseDto;
import abdelaziz.project.patient_service.dto.validators.CreatePatientValidatorGroup;
import abdelaziz.project.patient_service.service.PatientService;
import jakarta.validation.groups.Default;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@RestController
@RequestMapping("/patients")
public class PatientController {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getPatients() {
        List<PatientResponseDto> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping()
    public ResponseEntity<PatientResponseDto> createPatient(@Validated({Default.class,CreatePatientValidatorGroup.class}) @RequestBody PatientRequestDto entity) {
        PatientResponseDto createdPatient = patientService.createPatient(entity);
        return ResponseEntity.ok().body(createdPatient);
    }

    // with put we change the whole entity, with patch we change only the fields that we want to change
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable UUID id,
        @Validated({Default.class}) @RequestBody PatientRequestDto entity) {
        
        logger.info("Updating patient with ID: {}", id);
        
        PatientResponseDto updatedPatient = patientService.updatePatient(id, entity);
        return ResponseEntity.ok().body(updatedPatient);
    }

}
