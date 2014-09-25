package com.hippagriff.security;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.stereotype.Component;

import com.hippagriff.cache.CacheInterface;

/**
 * Base business service that contains all business logic required to authenticate users.
 * 
 * @author jon
 * 
 */
@Component
public class AuthenticationBusinessService
{
    // TODO move these to a constants file
    public static final String AUTH_TOKEN_LIST_KEY = "AUTH_TOKEN_LIST_KEY";

    public static final String AUTH_TOKEN_PARAM = "authToken";

    public static final Integer AUTH_TOKEN_CACHE_SECONDS = 1800;
    public static final Integer AUTH_TOKEN_CACHE_MILLISECONDS = AUTH_TOKEN_CACHE_SECONDS * 1000;

    public static final String LOGIN_DETAILS_CACHE_KEY = "login_details";

    @Autowired
    private CacheInterface cacheImpl;

    public AuthenticationBusinessService()
    {
        // no arg-constructor
    }

    /**
     * Constructor used for unit testing.
     * 
     * @param ldapAuthentication
     */
    public AuthenticationBusinessService(CacheInterface cacheImpl)
    {
        setCacheImpl(cacheImpl);
    }

    /**
     * This method removes an authToken from the cached list of authTokens representing all a provider's active
     * sessions.
     * 
     * @param apiUser
     * @param authToken
     */
    protected void removeAuthenticationTokenFromUsersList(APIUser apiUser, String authToken)
    {
        if (apiUser != null)
        {
            removeAuthTokenFromCachedList(authToken);

            // Retrieve the provider's current list of logged in sessions
            String userId = apiUser.getUserId();
            List<String> authList = cacheImpl.get(userId);

            if (CollectionUtils.isNotEmpty(authList))
            {
                String removeToken = null;
                // Iterate through the list and remove the one matching the authToken
                for (String userAuthToken : authList)
                {
                    if (StringUtils.equals(userAuthToken, authToken))
                    {
                        removeToken = userAuthToken;
                        break;
                    }
                }
                if (isNotBlank(removeToken))
                {
                    authList.remove(removeToken);
                    cacheImpl.replace(userId, authList);
                }
            }
        }
    }

    /**
     * @return the newly generated random authentication token value
     */
    public String generateTokenData()
    {
        UUID uid = UUID.randomUUID();
        return uid.toString().replaceAll("-", "");
    }

    /**
     * Add an auth token to the list we are keeping of auth tokens.
     * 
     * @param authToken to add.
     */
    public void addToCachedAuthTokenList(String authToken)
    {
        addToCachedList(AUTH_TOKEN_LIST_KEY, authToken);
    }

    /**
     * Remove an auth token from our list of cached auth tokens.
     * 
     * @param authToken to remove.
     */
    public void removeAuthTokenFromCachedList(String authToken)
    {
        removeFromCachedList(AUTH_TOKEN_LIST_KEY, authToken);
    }

    /**
     * Add an string entry to a cached list of strings. If no such collection exists for this key, then we will create
     * one and add the listItem. If a collection does exist we will check to see that this value does not already exist
     * within it, and add it.
     * 
     * @param listKey Key in cache to list
     * @param listItem Item to add to list.
     */
    public void addToCachedList(String listKey, String listItem)
    {
        List<String> listValues = getCacheImpl().get(listKey);
        if (CollectionUtils.isEmpty(listValues))
        {
            listValues = new ArrayList<String>();
            listValues.add(listItem);
            getCacheImpl().add(listKey, listValues);
            return;
        }
        if (listValues.contains(listItem))
        {
            return;
        }
        listValues.add(listItem);
        getCacheImpl().replace(listKey, listValues);
    }

    /**
     * Remove a principal from our list of logged-in users.
     * 
     * @param listKey Key in cache to list
     * @param listItem Item to remove from list.
     */
    public void removeFromCachedList(String listKey, String listItem)
    {
        List<String> loggedInUsers = getCacheImpl().get(listKey);
        if (CollectionUtils.isEmpty(loggedInUsers))
        {
            return;
        }
        loggedInUsers.remove(listItem);
        getCacheImpl().replace(listKey, loggedInUsers);
    }

    /**
     * @param request
     * @return the authToken value set in the request
     */
    public String extractAuthToken(HttpServletRequest request)
    {
        if (request == null)
        {
            return null;
        }
        String encodedAuthToken = request.getParameter(AUTH_TOKEN_PARAM);
        if (isNotBlank(encodedAuthToken))
        {
            return decodeAuthToken(encodedAuthToken);
        }
        return null;
    }

    /**
     * Inverse operation of decodeAuthToken.
     * 
     * @param authToken the authToken value to be encoded
     * @return base64 encoding of the token
     */
    public String encodeAuthToken(String authToken)
    {
        authToken = new String(Base64.encode(authToken.getBytes()));
        while (authToken.charAt(authToken.length() - 1) == '=')
        {
            authToken = authToken.substring(0, authToken.length() - 1);
        }

        return authToken;
    }

    /**
     * Decodes the authToken request parameter
     * 
     * @param authTokenEncoded the encoded authToken value
     * @return decoded authToken value
     * @throws InvalidCookieException if the authToken was not base64 encoded.
     */
    protected String decodeAuthToken(String authTokenEncoded) throws InvalidCookieException
    {
        authTokenEncoded = authTokenEncoded + "=";
        if (!Base64.isBase64(authTokenEncoded.getBytes()))
        {
            throw new InvalidCookieException("Cookie token was not Base64 encoded; value was '" + authTokenEncoded
                    + "'");
        }

        return new String(Base64.decode(authTokenEncoded.getBytes()));
    }

    /**
     * Place {@link Authentication} object into security context for future reference during this session.
     * 
     * @param authentication
     */
    public void setAuthenticationIntoContext(Authentication authentication)
    {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * For a given key, return the matching {@link Object} in the cache.
     * 
     * @param key
     */
    public Object getFromCache(String key)
    {
        if (isBlank(key))
        {
            return null;
        }
        return cacheImpl.get(key);
    }

    /**
     * For a given key, place the provided {@link Object} into the cache.
     * 
     * @param key
     * @param value
     */
    public void addToCache(String key, Object value)
    {
        cacheImpl.add(key, value);
    }

    /**
     * For a given key, replace the provided {@link Object} into the cache.
     * 
     * @param key
     * @param value
     */
    public void replaceInCache(String key, Object value)
    {
        cacheImpl.replace(key, value);
    }

    /**
     * For a given key, place the provided {@link Object} into the cache with a specified time to live (ttl) in seconds.
     * 
     * @param key
     * @param value
     * @param ttlSeconds
     */
    public void addToCacheWithTTL(String key, Object value, Integer ttlSeconds)
    {
        cacheImpl.add(key, value, ttlSeconds);
    }

    public CacheInterface getCacheImpl()
    {
        return cacheImpl;
    }

    public void setCacheImpl(CacheInterface cacheImpl)
    {
        this.cacheImpl = cacheImpl;
    }

}