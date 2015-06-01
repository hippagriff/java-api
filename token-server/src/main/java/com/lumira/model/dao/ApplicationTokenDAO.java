package com.lumira.model.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lumira.model.ApplicationToken;

/**
 * DAO interface for managing {@link ApplicationToken}s
 * 
 * @author jon
 * 
 */
public interface ApplicationTokenDAO extends CrudRepository<ApplicationToken,Long>
{

    /**
     * Returns all users with the given name as first- or lastname. Makes use of the {@link Param} annotation to use
     * named parameters in queries. This makes the query to method relation much more refactoring safe as the order of
     * the method parameters is completely irrelevant.
     * 
     * @param name
     * @return
     */
    @Query("select appToken from ApplicationToken appToken where appToken.connectKey = :token")
    ApplicationToken findByToken(@Param("token") String token);
}