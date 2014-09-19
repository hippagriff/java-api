package com.hippagriff.ldap.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Common functionality for functionality within the project.
 * 
 * @author jon
 * 
 */
@Component
public class HippagriffCommonService
{
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(HippagriffCommonService.class);

    private static final String DEFAULT_HOST = "unknown host";

    /**
     * Return the name of the host running the service.
     * 
     * @return
     */
    public static String getCurrentHost()
    {
        String hostname = null;
        try
        {
            InetAddress addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        }
        catch (UnknownHostException e)
        {
            hostname = DEFAULT_HOST;
        }

        return hostname;
    }

    /**
     * Returns the current date and time in the correct timezone for our DB
     * 
     * @return
     */
    public static Date getNow()
    {
        TimeZone tz = TimeZone.getTimeZone("GMT");
        Calendar now = Calendar.getInstance(tz);

        return now.getTime();
    }

    /**
     * Get a uuid that is stripped of dashes.
     * 
     * @return
     */
    public static String getUuidNoDashes()
    {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        return uuid;
    }
}
