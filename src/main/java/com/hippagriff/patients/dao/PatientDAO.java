package com.hippagriff.patients.dao;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hippagriff.model.HippagriffDAOException;
import com.hippagriff.model.Patient;
import com.hippagriff.model.PatientSystem;
import com.hippagriff.patients.dto.PatientSearchRequestDTO;

/**
 * DAO for executing search requests that relate to {@link Patient}s and {@link PatientSystem}s
 * 
 * @author jon
 * 
 */
@Repository
public class PatientDAO
{
    private static final Logger logger = LoggerFactory.getLogger(PatientDAO.class);

    private final String SEARCH_WILDCARD = "%";

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em)
    {
        this.em = em;
    }

    protected EntityManager getEntityManager()
    {
        return em;
    }

    /**
     * Search for patients based on the provided {@link PatientSearchRequestDTO}.
     * 
     * @param searchRequestDTO
     * @return
     */
    public List<PatientSystem> getPatients(PatientSearchRequestDTO searchRequestDTO)
    {
        if (searchRequestDTO == null)
        {
            throw new IllegalArgumentException("searchRequest cannot be null!");
        }

        StringBuilder jql = new StringBuilder();
        jql.append("SELECT patient ");
        jql.append("FROM " + PatientSystem.class.getName() + " patient ");
        jql.append("WHERE patient.active = true ");
        if (isNotBlank(searchRequestDTO.getFirstName()))
        {
            jql.append("AND patient.patientFirstName LIKE :firstName ");
        }
        if (isNotBlank(searchRequestDTO.getLastName()))
        {
            jql.append("AND patient.patientLastName LIKE :lastName ");
        }
        if (isNotBlank(searchRequestDTO.getMrn()))
        {
            jql.append("AND patient.patientMrn = :mrn ");
        }

        TypedQuery<PatientSystem> searchQuery = getEntityManager().createQuery(jql.toString(), PatientSystem.class);
        if (isNotBlank(searchRequestDTO.getFirstName()))
        {
            searchQuery.setParameter("firstName", applySearchWildcard(searchRequestDTO.getFirstName()));
        }
        if (isNotBlank(searchRequestDTO.getLastName()))
        {
            searchQuery.setParameter("lastName", applySearchWildcard(searchRequestDTO.getLastName()));
        }
        if (isNotBlank(searchRequestDTO.getMrn()))
        {
            searchQuery.setParameter("mrn", searchRequestDTO.getMrn());
        }
        List<PatientSystem> patients = null;
        try
        {
            patients = searchQuery.getResultList();
        }
        catch (Exception e)
        {
            throw new HippagriffDAOException("An error occurred searching for patients!", e);
        }

        return patients;
    }

    /**
     * TODO: This should be in a base DAO.
     * 
     * @param searchValue
     * @return
     */
    private String applySearchWildcard(String searchValue)
    {
        return SEARCH_WILDCARD + searchValue + SEARCH_WILDCARD;
    }
}
