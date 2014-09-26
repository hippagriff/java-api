package com.hippagriff.patients.businessservices;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.hippagriff.fhir.dto.PatientSearchResultFHIRDTO;
import com.hippagriff.model.PatientSystem;
import com.hippagriff.organization.dto.OrganizationDTO;
import com.hippagriff.patients.dao.PatientDAO;
import com.hippagriff.patients.dto.PatientContactDTO;
import com.hippagriff.patients.dto.PatientDTO;
import com.hippagriff.patients.dto.PatientGenderDTO;
import com.hippagriff.patients.dto.PatientIdentifierDTO;
import com.hippagriff.patients.dto.PatientNameDTO;
import com.hippagriff.patients.dto.PatientSearchRequestDTO;
import com.hippagriff.patients.dto.PatientSearchResultDTO;
import com.hippagriff.patients.dto.PatientTelecomDTO;

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

    public List<PatientSearchResultDTO> getPatients(PatientSearchRequestDTO searchRequestDTO)
    {
        Assert.notNull(searchRequestDTO);
        List<PatientSearchResultDTO> patientDTOs = new ArrayList<PatientSearchResultDTO>();

        List<PatientSystem> patients = patientDAO.getPatients(searchRequestDTO);

        if (isEmpty(patients))
        {
            return patientDTOs;
        }

        patientDTOs = transformPatientSystemtoDTOs(patients);

        return patientDTOs;

    }
    
    public List<PatientSearchResultDTO> getPatientSearchResults(PatientSearchRequestDTO searchRequestDTO)
    {
        Assert.notNull(searchRequestDTO);
        List<PatientSearchResultDTO> patientDTOs = new ArrayList<PatientSearchResultDTO>();

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
    protected List<PatientSearchResultDTO> transformPatientSystemtoDTOs(List<PatientSystem> patientSystems)
    {
        List<PatientSearchResultDTO> patientDTOs = new ArrayList<PatientSearchResultDTO>();

        for (PatientSystem patient : patientSystems)
        {
            PatientSearchResultDTO patientDTO = new PatientSearchResultDTO();
            patientDTO.setFirstName(patient.getPatientFirstName());
            patientDTO.setLastName(patient.getPatientLastName());
            patientDTO.setMiddleName(patient.getPatientMiddleName());
            patientDTO.setMrn(patient.getPatientMrn());
            patientDTO.setMpi(patient.getPatient().getPatientMpi());

            patientDTOs.add(patientDTO);
        }

        return patientDTOs;
    }

    /**
     * Transform a list of {@link PatientSystem}s to {@link PatientSearchResultFHIRDTO}s
     * 
     * @param patientSystems
     * @return {@link PatientDTO}s
     */
    protected List<PatientSearchResultFHIRDTO> transformPatientSystemToDTOs(List<PatientSystem> patientSystems)
    {
        List<PatientSearchResultFHIRDTO> patientDTOs = new ArrayList<PatientSearchResultFHIRDTO>();

        for (PatientSystem patient : patientSystems)
        {
            PatientSearchResultFHIRDTO patientDTO = new PatientSearchResultFHIRDTO();
            patientDTO.setAddress(null);
            patientDTO.setBirthDate(new Date());
            patientDTO.setContact(new ArrayList<PatientContactDTO>());

            patientDTO.setDeceasedBoolean(false);
            patientDTO.setGender(new ArrayList<PatientGenderDTO>());

            patientDTO.setIdentifier(buildPatientIdentifierDTOsFromPatientSystem(patient));

            patientDTO.setManagingOrganization(new OrganizationDTO());

            patientDTO.setName(buildPatientNameDTOsFromPatientSystem(patient));
            patientDTO.setResourceType("Patient");
            patientDTO.setTelecom(new ArrayList<PatientTelecomDTO>());

            patientDTOs.add(patientDTO);
        }

        return patientDTOs;
    }

    /**
     * Extract from a {@link PatientSystem} object , a List of {@link PatientIdentifierDTO}s
     * 
     * @param patient
     * @return List<PatientIdentifierDTO>
     */
    protected List<PatientIdentifierDTO> buildPatientIdentifierDTOsFromPatientSystem(PatientSystem patient)
    {
        PatientIdentifierDTO patientIdentifierDTO = new PatientIdentifierDTO();
        patientIdentifierDTO.setLabel("mrn");
        patientIdentifierDTO.setValue(patient.getPatientMrn());
        patientIdentifierDTO.setSystem(patient.getSystemId());

        List<PatientIdentifierDTO> patientIdentifiers = new ArrayList<PatientIdentifierDTO>();
        return patientIdentifiers;

    }

    /**
     * Extract from a {@link PatientSystem} object , a List of {@link PatientNameDTO}s
     * 
     * @param patient
     * @return List<PatientNameDTO>
     */
    protected List<PatientNameDTO> buildPatientNameDTOsFromPatientSystem(PatientSystem patient)
    {
        PatientNameDTO patientName = new PatientNameDTO();
        patientName.setFamily(new ArrayList<String>());

        List<String> givenNames = new ArrayList<String>();
        givenNames.add(patient.getPatientLastName());
        givenNames.add(patient.getPatientFirstName());

        patientName.setGiven(givenNames);
        patientName.setUse(patient.getPatientFirstName());

        List<PatientNameDTO> names = new ArrayList<PatientNameDTO>();
        names.add(patientName);

        return names;

    }

}
