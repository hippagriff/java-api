package com.lumira.businessservice;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lumira.exception.TokenNotFoundException;
import com.lumira.model.ApplicationToken;
import com.lumira.model.dao.ApplicationTokenDAO;

@Component
public class ApplicationTokenBusinessServiceImpl
{
    @Autowired
    ApplicationTokenDAO applicationTokenDao;

    /**
     * Get an {@link ApplicationToken} that matches the provided token value.
     * 
     * @param token
     * @return {@link ApplicationToken}
     * @throws IllegalArgumentException if token is null
     */
    public ApplicationToken getToken(String token)
    {
        if (isEmpty(token))
        {
            throw new IllegalArgumentException("Token cannot be null");
        }
        return applicationTokenDao.findByToken(token);
    }

    /**
     * Activate a {@link ApplicationToken}, and mark it as a used token. This essentially identifies which device is
     * accessing the token, as well as setting the activation date/time.
     * 
     * @param applicationToken
     * @return the activated {@link ApplicationToken}
     * @throws IllegalArgumentException if applicationToken is null
     * @throws TokenNotFoundException
     */
    @Transactional
    public ApplicationToken activateToken(ApplicationToken applicationToken)
    {
        if (applicationToken == null)
        {
            throw new IllegalArgumentException("Existing token cannot be null");
        }

        ApplicationToken applicationTokenToUpdate = getToken(applicationToken.getConnectKey());
        if (applicationTokenToUpdate == null)
        {
            throw new TokenNotFoundException(applicationToken.getConnectKey());
        }

        applicationTokenToUpdate.setDeviceIdentity(applicationToken.getDeviceIdentity());
        applicationTokenToUpdate.setActivatedDate(new Date());

        applicationTokenDao.save(applicationTokenToUpdate);

        return applicationTokenToUpdate;
    }

}
