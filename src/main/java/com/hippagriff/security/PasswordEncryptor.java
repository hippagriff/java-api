package com.hippagriff.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.hippagriff.model.User;

/**
 * This class is responsible encrypting incoming password values that pertain to {@link User}s. It is also responsible
 * for comparing encrypted password values when users are authenticated.
 */
@Component
public class PasswordEncryptor
{
    private static final Log LOG = LogFactory.getLog(PasswordEncryptor.class);

    public static final String ALGORITHM_SHA_1 = "SHA-1";
    public static final String ISO_859 = "iso-8859-1";

    /**
     * Encrypt a clear text password string.
     * 
     * @param password Byte array of clear text password string.
     * @param algorithm Name of algorithm used to encrypt.
     * @return encrypted password byte array or null on any failure.
     */
    public static byte[] encryptPassword(byte[] password, String algorithm)
    {
        byte[] passwordHash = null;
        if (password != null)
        {
            try
            {
                MessageDigest sha1 = MessageDigest.getInstance(algorithm);
                passwordHash = sha1.digest(password);
            }
            catch (NoSuchAlgorithmException e)
            {
                LOG.error("Failed to generate password hash: " + e.getMessage());
            }
        }
        return passwordHash;
    }

    private static String convertToHex(byte[] data)
    {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++)
        {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do
            {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            }
            while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String encryptPassword(String text, String algorithm) throws NoSuchAlgorithmException,
            UnsupportedEncodingException
    {
        MessageDigest md;
        md = MessageDigest.getInstance(ALGORITHM_SHA_1);
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes(ISO_859), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    /**
     * Encrypt password string using SHA-1 encryption algorithm.
     * 
     * @param password
     * @return encrypted password byte array or null on any failure.
     */
    public static String encryptPassword(String password)
    {
        try
        {
            return encryptPassword(password, ALGORITHM_SHA_1);
        }
        catch (NoSuchAlgorithmException e)
        {
            LOG.error("Failed to generate password hash: " + e.getMessage());
        }
        catch (UnsupportedEncodingException e)
        {
            LOG.error("Failed to generate password hash: " + e.getMessage());
        }

        return null;
    }

    /**
     * Encrypt passwordText and compare it with the stored encrypted password
     * 
     * @param userPassword - the user password
     * @param passwordText - the password text
     * @return true if passwords match
     */
    public boolean isPasswordsEqual(String userPassword, String passwordText)
    {
        String password = new String(encryptPassword(passwordText));

        return StringUtils.equals(userPassword, password);
    }
}
