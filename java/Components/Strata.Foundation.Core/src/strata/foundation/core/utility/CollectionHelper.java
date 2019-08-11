// ##########################################################################
// # File Name:	CollectionHelper.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.core.utility;

import java.util.Collection;

/****************************************************************************
 * 
 */
public 
class CollectionHelper
{

    /************************************************************************
     *  
     *
     * @param collection
     * @param predicate
     * @return
     */
    public static <T> T
    find(Collection<T> collection,IMatcher<T> predicate)
    {
        for (T element:collection)
            if ( predicate.match( element ) )
                return element;
        
        return null;
    }
    
    /************************************************************************
     * Private no-op constructor because this is a utility class. 
     *
     */
    private 
    CollectionHelper() {}
}

// ##########################################################################
