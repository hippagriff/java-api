package com.hippagriff.patients.businessservices;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.hippagriff.model.PatientSystem;
import com.hippagriff.patients.dao.PatientDAO;
import com.hippagriff.patients.dto.PatientDTO;
import com.hippagriff.patients.dto.PatientSearchRequestDTO;

/**
 * This class is responsible for all business logic that pertains to patient searches.
 * 
 * @author jon
 * 
 */
@Component
public class PatientSearchBusinessService
{
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(PatientSearchBusinessService.class);

    @Autowired
    private PatientDAO patientDAO;

    public List<PatientDTO> getPatients(PatientSearchRequestDTO searchRequestDTO)
    {
        Assert.notNull(searchRequestDTO);
        List<PatientDTO> patientDTOs = new ArrayList<PatientDTO>();

        List<PatientSystem> patients = patientDAO.getPatients(searchRequestDTO);

        if (isEmpty(patients))
        {
            return patientDTOs;
        }

        patientDTOs = transformPatientSystemtoDTOs(patients);

        return patientDTOs;

    }

    /**
     * Transform a list of {@link PatientSystem}s to {@link PatientDTO}s
     * 
     * @param patientSystems
     * @return {@link PatientDTO}s
     */
    protected List<PatientDTO> transformPatientSystemtoDTOs(List<PatientSystem> patientSystems)
    {
        List<PatientDTO> patientDTOs = new ArrayList<PatientDTO>();

        for (PatientSystem patient : patientSystems)
        {
            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setFirstName(patient.getPatientFirstName());
            patientDTO.setLastName(patient.getPatientLastName());
            patientDTO.setMiddleName(patient.getPatientMiddleName());
            patientDTO.setMrn(patient.getPatientMrn());
            patientDTO.setMpi(patient.getPatient().getPatientMpi());

            patientDTOs.add(patientDTO);
        }

        return patientDTOs;
    }

}
