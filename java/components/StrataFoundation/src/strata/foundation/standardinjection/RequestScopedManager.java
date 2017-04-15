// ##########################################################################
// # File Name:	RequestScopedManager.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.standardinjection;

import javax.servlet.ServletRequest;

/****************************************************************************
 * 
 */
public abstract 
class RequestScopedManager
{

    private static final ThreadLocal<ServletRequest> 
    theirRequests = new ThreadLocal<ServletRequest>();

    /************************************************************************
     * Creates a new RequestScopedManager. 
     *
     */
    protected 
    RequestScopedManager() {}

    /************************************************************************
     *  
     *
     * @param request
     */
    public static void 
    setRequest(ServletRequest request)
    {
        theirRequests.set( request );
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public static ServletRequest
    getRequest()
    {
        return theirRequests.get();
    }
    
    /************************************************************************
     *  
     *
     */
    public static void
    clearRequest() 
    {
        /*
        ServletRequest request = theirRequests.get();
        
        for (String key : Collections.list( request.getAttributeNames() ) )
        {
            Object attribute = request.getAttribute( key );
            
            if ( attribute instanceof AutoCloseable ) 
                try
                {
                    ((AutoCloseable)attribute).close();
                }
                catch(Exception e)
                {
                    e.printStackTrace( System.err );
                }
        }
        */
        theirRequests.remove();
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public static boolean
    hasRequest()
    {
        return theirRequests.get() != null;
    }
}

// ##########################################################################
