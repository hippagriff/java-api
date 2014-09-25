package com.hippagriff.security;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;

import com.hippagriff.dto.LoginResponseDTO;
import com.hippagriff.model.Person;
import com.hippagriff.model.User;
import com.hippagriff.model.UserBuilder;
import com.hippagriff.patients.dao.UserDAO;

/**
 * Unit tests for {@link AuthenticationPostProcessor}
 * 
 * @author jon
 * 
 */
public class AuthenticationPostProcessorTest
{

    private UserDAO mockUserDAO;
    private AuthenticationBusinessService mockAuthenticationBusinessService;
    private Authentication mockTestAuth;
    private MockHttpServletResponse mockResponse;
    private AuthenticationPostProcessor testService;

    @Before
    public void setup()
    {
        mockUserDAO = mock(UserDAO.class);
        mockTestAuth = mock(Authentication.class);
        mockResponse = new MockHttpServletResponse();
        mockAuthenticationBusinessService = mock(AuthenticationBusinessService.class);
        testService = new AuthenticationPostProcessor(mockUserDAO, mockAuthenticationBusinessService);
    }

    @Test
    public void testPostProcessForLogin()
    {
        // given
        APIUser testAPIUser = APIUserBuilder.aBasicAPIUser().withEmptyAuthToken().build();
        String newAuthToken = "token123";
        User testUser = UserBuilder.aBasicUser().build();
        when(mockTestAuth.getPrincipal()).thenReturn(testAPIUser);
        when(mockAuthenticationBusinessService.generateTokenData()).thenReturn(newAuthToken);
        when(mockUserDAO.findActive(testAPIUser.getUserId())).thenReturn(testUser);

        // when
        testService.postProcess(mockResponse, mockTestAuth, true);

        // then
        assertEquals(testAPIUser.getAuthenticationToken(), newAuthToken);
        assertNotNull(testAPIUser.getLoginDetails());
        assertNotNull(testAPIUser.getAuthenticationTokenExpiryDate());
        assertEquals(mockResponse.getStatus(), HttpServletResponse.SC_ACCEPTED);
        assertEquals(mockResponse.getContentType(), MediaType.APPLICATION_JSON);
        try
        {
            assertNotNull(mockResponse.getWriter());
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void testLoginResponseDTOBuilder()
    {
        // given
        APIUser testAPIUser = APIUserBuilder.aBasicAPIUser().build();
        User testUser = UserBuilder.aBasicUser().build();
        String newAuthToken = "token123";
        when(mockAuthenticationBusinessService.encodeAuthToken(testAPIUser.getAuthenticationToken())).thenReturn(
                newAuthToken);
        when(mockUserDAO.findActive(testAPIUser.getUserId())).thenReturn(testUser);

        // when
        LoginResponseDTO loginDTO = testService.getLoginResponseDTO(testAPIUser);

        // then
        assertEquals(loginDTO.getUserName(), testUser.getUserName());
        assertEquals(loginDTO.getUserId(), testUser.getUserId());
        assertEquals(loginDTO.getForcePasswordReset(), testUser.getForcePasswordReset());
        assertEquals(loginDTO.getAuthenticationToken(), newAuthToken);
        Person testPerson = testUser.getPrimaryPerson();
        assertEquals(loginDTO.getFirstName(), testPerson.getFirstName());
        assertEquals(loginDTO.getLastName(), testPerson.getLastName());
        assertEquals(loginDTO.getPersonId(), testPerson.getPersonId());
    }

}
