package com.hippagriff.webservice;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hippagriff.dto.UserDTO;
import com.hippagriff.ldap.model.dao.LDAPUserDAO;

/**
 * API for exceuting different searches against the LDAP server.
 * 
 * @author jon
 */
@Service
@Path("/search")
public class LDAPSearchService extends BaseService
{
    private static final Logger logger = LoggerFactory.getLogger(LDAPSearchService.class);

    @Autowired
    private LDAPUserDAO ldapSearchBusinessServiceImpl;

    /**
     * Execute a simple search of the LDAP server for a user that matches the given search term.
     * 
     * @param searchTerm
     * @return
     */
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchLDAPUsers(@QueryParam("q") String searchTerm)
    {
        ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        try
        {
            List<UserDTO> searchResults = ldapSearchBusinessServiceImpl.searchUsers(searchTerm);
            rb.status(Response.Status.OK).entity(searchResults);
        }
        catch (Exception ex)
        {
            handleException(rb, ex);
        }

        return rb.build();
    }

    /**
     * Fetch a user that exists in the LDAP server that matches the provided userName (uid)
     * 
     * @param userName
     * @return
     */
    @GET
    @Path("/users/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userName") String userName)
    {
        ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        try
        {
            UserDTO userDTO = ldapSearchBusinessServiceImpl.getUser(userName);
            rb.status(Response.Status.OK).entity(userDTO);
        }
        catch (Exception ex)
        {
            handleException(rb, ex);
        }

        return rb.build();
    }

}
