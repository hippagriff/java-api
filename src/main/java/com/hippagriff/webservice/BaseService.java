package com.hippagriff.webservice;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hippagriff.dto.ErrorMessageDTO;

/**
 * Base class for shared service layer functionality.
 * 
 * @author jon
 */
public class BaseService
{
    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    /**
     * Common exception handling.
     * 
     * @param responseBuilder Web response.
     * @param ex Exception. Must not be null.
     */
    protected void handleException(ResponseBuilder responseBuilder, Exception ex)
    {
        ErrorMessageDTO errDTO = new ErrorMessageDTO(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                ex.getMessage());
        String stkTrace = ExceptionUtils.getStackTrace(ex);
        errDTO.setStackTrace(stkTrace);
        logger.warn("Error: " + ex.getMessage());
        logger.warn("\n" + stkTrace);
        responseBuilder.status(errDTO.getStatusCode()).type(MediaType.APPLICATION_JSON).entity(errDTO);
    }

}
