package com.hippagriff.patients.dao;

import static org.apache.commons.lang.StringUtils.isBlank;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hippagriff.model.HippagriffDAOException;
import com.hippagriff.model.User;

/**
 * DAO for accessing the {@link User} table/.
 * 
 * @author jon
 * 
 */
@Repository
public class UserDAO
{
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

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
     * Looks up a {@link User} based on username (not case-specific)
     * 
     * @param userName
     * @return {@link User}
     */
    public User findByUserName(String userName)
    {
        if (isBlank(userName))
        {
            throw new IllegalArgumentException("Username cannot be null.");
        }

        StringBuilder jql = new StringBuilder();
        jql.append("SELECT user ");
        jql.append("FROM " + User.class.getName() + " user ");
        jql.append("WHERE user.active = true ");
        jql.append("AND user.userName = :userName ");

        TypedQuery<User> searchQuery = getEntityManager().createQuery(jql.toString(), User.class);
        searchQuery.setParameter("userName", userName.toUpperCase());
        User user = null;
        try
        {
            user = searchQuery.getSingleResult();
        }
        catch (NonUniqueResultException nre)
        {
            throw new HippagriffDAOException("Multiple users found that match userName: " + userName, nre);
        }
        catch (Exception e)
        {
            throw new HippagriffDAOException("An error occurred searching for patients!", e);
        }

        return user;
    }
}
