package com.hippagriff.dto;

import org.apache.commons.lang3.StringUtils;

/**
 * @author jon DTO responsible for returning any sort of error message back to presentation layer.
 */
public class ErrorMessageDTO
{

    /**
     * Constructor
     * 
     * @param statusCode HTTP Status code
     * @param errorCode Wellogic application error code.
     * @param errorMessage Readable description of error
     * @param identifier separate identifying string (e.g., a filename associated with error).
     * @see WellogicErrorCodes
     */
    public ErrorMessageDTO(int statusCode, String errorMessage)
    {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    /**
     * Constructor
     * 
     * @param errorMessage Readable description of error
     * @see WellogicErrorCodes
     */
    public ErrorMessageDTO(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    private int statusCode;

    private String errorMessage;

    private String stackTrace;

    /**
     * @return HTTP status code.
     */
    public int getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    /**
     * @return Description of error.
     */
    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    /**
     * @return Stack trace string or null if none.
     */
    public String getStackTrace()
    {
        return this.stackTrace;
    }

    public void setStackTrace(String stkTrace)
    {
        this.stackTrace = stkTrace;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.statusCode + ": ");
        if (StringUtils.isNotBlank(errorMessage))
        {
            sb.append(errorMessage + " ");
        }
        if (StringUtils.isNotBlank(stackTrace))
        {
            sb.append("\nStack Trace: " + stackTrace);
        }
        return sb.toString();
    }

}
