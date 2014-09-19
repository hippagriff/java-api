package com.hippagriff.webservice;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hippagriff.patients.businessservices.PatientSearchBusinessService;
import com.hippagriff.patients.dto.PatientSearchRequestDTO;
import com.hippagriff.patients.dto.PatientSearchResultDTO;

/**
 * API for exceuting different patient searches.
 * 
 * @author jon
 */
@Service
@Path("/patients")
public class PatientSearchService extends BaseService
{
    private static final Logger logger = LoggerFactory.getLogger(LDAPSearchService.class);

    @Autowired
    private PatientSearchBusinessService patientSearchBusinessService;

    /**
     * Execute a simple patient search.
     * 
     * @param firstName
     * @param lastName
     * @param mrn
     * @return
     */
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchLDAPUsers(@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName,
            @QueryParam("mrn") String mrn)
    {
        ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        try
        {
            PatientSearchRequestDTO searchRequestDTO = buildSearchRequestDTO(firstName, lastName, mrn);
           // List<PatientDTO> searchResults = patientSearchBusinessService.getPatients(searchRequestDTO);
            List<PatientSearchResultDTO> searchResults = patientSearchBusinessService.getPatientSearchResults(searchRequestDTO);
            
            rb.status(Response.Status.OK).entity(searchResults);
        }
        catch (Exception ex)
        {
            handleException(rb, ex);
        }

        return rb.build();
    }

    /**
     * Build {@link PatientSearchRequestDTO} based on the provided parameters.
     * 
     * @param firstName
     * @param lastName
     * @param mrn
     * @return
     */
    private PatientSearchRequestDTO buildSearchRequestDTO(String firstName, String lastName, String mrn)
    {
        PatientSearchRequestDTO request = new PatientSearchRequestDTO();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setMrn(mrn);

        return request;

    }

}
