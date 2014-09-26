package com.hippagriff.security;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.hippagriff.cache.CacheInterface;

/**
 * Unit tests for {@link AuthenticationBusinessService}
 * 
 * @author jon
 * 
 */
public class AuthenticationBusinessServiceTest
{
    private CacheInterface mockCacheInterface;
    private AuthenticationBusinessService testService;

    @Before
    public void setup()
    {
        mockCacheInterface = mock(CacheInterface.class);
        testService = new AuthenticationBusinessService(mockCacheInterface);
    }

    @Test
    public void testRemoveAuthTokenFromUserList()
    {
        // given
        APIUser testAPIUser = APIUserBuilder.aBasicAPIUser().build();
        String testAuthToken = "TestToken1234123";

        List<String> userTokens = new LinkedList<String>(Arrays.asList("token1", "token2", testAuthToken));
        List<String> userTokensWithoutTestToken = new LinkedList<String>(Arrays.asList("token1", "token2"));

        when(mockCacheInterface.get(testAPIUser.getUserId())).thenReturn(userTokens);

        // when
        testService.removeAuthenticationTokenFromUsersList(testAPIUser, testAuthToken);

        // then
        verify(mockCacheInterface).replace(testAPIUser.getUserId(), userTokensWithoutTestToken);
    }

    @Test
    public void testAddValueToCachedList_WhenValueIsNew()
    {
        // given
        String key = "key123";
        String value = "val3";
        List<String> listOfValues = new LinkedList<String>(Arrays.asList("val1", "val2"));
        List<String> listOfValuesWithNewValue = new LinkedList<String>(Arrays.asList("val1", "val2", value));

        when(mockCacheInterface.get(key)).thenReturn(listOfValues);

        // when
        testService.addToCachedList(key, value);

        // then
        verify(mockCacheInterface).replace(key, listOfValuesWithNewValue);
    }

    @Test
    public void testAddValueToCachedList_WhenValueAlreadyExistsInCollection()
    {
        // given
        String key = "key123";
        String value = "val3";
        List<String> listOfValues = new LinkedList<String>(Arrays.asList("val1", "val2"));
        List<String> listOfValuesWithNewValue = new LinkedList<String>(Arrays.asList("val1", "val2", value));

        when(mockCacheInterface.get(key)).thenReturn(listOfValues);

        // when
        testService.addToCachedList(key, value);

        // then
        verify(mockCacheInterface, never()).replace(value, listOfValuesWithNewValue);
    }
}
