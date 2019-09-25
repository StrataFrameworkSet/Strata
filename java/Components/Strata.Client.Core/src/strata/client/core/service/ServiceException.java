//////////////////////////////////////////////////////////////////////////////
// ServiceException.java
//////////////////////////////////////////////////////////////////////////////

package strata.client.core.service;

public
class ServiceException
    extends RuntimeException
{
    private final String itsRequestPath;
    private final int    itsResponseCode;

    public
    ServiceException(IResponse response)
    {
        super(initializeMessage(response));
        itsRequestPath = "<request-path>";
        itsResponseCode = response.getStatusAsInt();
    }

    public
    ServiceException(IResponse response,Throwable cause)
    {
        super(initializeMessage(response),cause);
        itsRequestPath = "<request-path>";
        itsResponseCode = response.getStatusAsInt();
    }

    private static String
    initializeMessage(IResponse response)
    {
        StringBuilder builder = new StringBuilder();

        builder
            .append("Exception during response processing:\n")
            .append("\tResponse status code: ")
            .append(response.getStatusAsInt())
            .append('\n')
            .append("\tResponse status message: ")
            .append(response.getStatusReason())
            .append('\n')
            .append("\tResponse media type: ")
            .append(response.getMediaType())
            .append('\n')
            .append("\tFrom request: ")
            .append(response.getRequestPath())
            .append('\n');

        return builder.toString();
    }
}

//////////////////////////////////////////////////////////////////////////////
