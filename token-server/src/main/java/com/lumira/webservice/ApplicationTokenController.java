package com.lumira.webservice;

import javax.validation.Valid;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lumira.businessservice.ApplicationTokenBusinessServiceImpl;
import com.lumira.exception.TokenNotFoundException;
import com.lumira.model.ApplicationToken;
import com.lumira.model.validator.ApplicationTokenValidator;

@Api(name = "API Token Services", description = "Methods for managing API Tokens")
@RestController
@RequestMapping(value = "/tokens")
public class ApplicationTokenController
{
    @Autowired
    ApplicationTokenBusinessServiceImpl tokenBusinessService;

    /**
     * Get an instance of an {@link ApplicationToken} that matches the given token value.
     * 
     * @param token
     * @return {@link ApplicationToken}
     */
    @ApiMethod(description = "Fetch an existing Application Token by token/key.")
    @RequestMapping(value = "/{token}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    ApplicationToken getApplicationToken(@PathVariable String token)
    {
        ApplicationToken appToken = tokenBusinessService.getToken(token);

        if (appToken == null)
        {
            throw new TokenNotFoundException(token);
        }
        else
        {
            return appToken;
        }
    }

    /**
     * Activate an existing {@link ApplicationToken}. <br/>
     * This call would generally be used when an application attempts to use a new token. This will update the device
     * identity and activated date.
     * 
     * @param applicationToken
     * @return {@link ApplicationToken}
     */
    @ApiMethod(description = "Activate an existing ApplicationToken by specifying the key and device that is activating it.")
    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    ApplicationToken activateApplicationToken(@RequestBody @Valid @ApiBodyObject ApplicationToken applicationToken)
    {
        return tokenBusinessService.activateToken(applicationToken);
    }

    /**
     * Activate an existing {@link ApplicationToken}. <br/>
     * This call would generally be used when an application attempts to use a new token. This will update the device
     * identity and activated date.
     * 
     * @param applicationToken
     * @return {@link ApplicationToken}
     */
    @ApiMethod(description = "Create a brand new ApplicationToken for a new environment.")
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    ApplicationToken createNewApplicationToken(@RequestBody @Valid @ApiBodyObject ApplicationToken applicationToken)
    {
        return tokenBusinessService.activateToken(applicationToken);
    }

    /**
     * Add {@link ApplicationTokenValidator} to controller validation. This allows us to validate incoming
     * {@link ApplicationToken}s on our endpoints.
     * 
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder)
    {
        binder.setValidator(new ApplicationTokenValidator());
    }

}
