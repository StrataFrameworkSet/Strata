// ##########################################################################
// # File Name:	SecureString.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.value;

import java.io.Serializable;
import strata.foundation.utility.ICopyable;

/****************************************************************************
 * 
 */
public final 
class SecureString
    implements ICopyable,Serializable,Comparable<SecureString>
{
    private static final long serialVersionUID = 8494513623907754563L;
    
    private final char[] itsValue;
    
    /************************************************************************
     * Creates a new SecureString. 
     *
     * @param value
     */
    public 
    SecureString(char[] value)
    {
        itsValue = value;
    }

    public 
    SecureString(SecureString other)
    {
        itsValue = other.itsValue;
    }
    
    @Override
    public int 
    compareTo(SecureString other)
    {
        return 0;
    }

    @Override
    public ICopyable 
    copy()
    {
        return null;
    }

    
}

// ##########################################################################
