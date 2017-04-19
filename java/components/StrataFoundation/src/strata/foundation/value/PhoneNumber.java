// ##########################################################################
// # File Name:	PhoneNumber.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import strata.foundation.utility.ICopyable;

/****************************************************************************
 * 
 */
public 
class PhoneNumber
    implements ICopyable,Serializable,Comparable<PhoneNumber>
{
    private static final long serialVersionUID = 8615708147899975958L;
    
    private static final IPhoneNumberValidator
    theirValidator = new PhoneNumberValidator();
    
    private final String itsPhone;
    
    /************************************************************************
     * Creates a new PhoneNumber. 
     *
     * @param phone
     * @throws IllegalArgumentException
     */
    public 
    PhoneNumber(String phone)
        throws IllegalArgumentException
    {
        checkPhoneNumber(phone);
        itsPhone = phone;
    }

    /************************************************************************
     * Creates a new PhoneNumber. 
     *
     * @param other
     */
    public 
    PhoneNumber(PhoneNumber other)
    {
        this( other.itsPhone );
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public PhoneNumber 
    copy()
    {
        return new PhoneNumber( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    compareTo(PhoneNumber other)
    {
        return itsPhone.compareToIgnoreCase( other.itsPhone );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    equals(Object other)
    {
        if ( other instanceof PhoneNumber )
            return itsPhone.equalsIgnoreCase( ((PhoneNumber)other).itsPhone );
        
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    hashCode()
    {
        return 31 * itsPhone.hashCode();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    toString()
    {
        return itsPhone;
    }

    /************************************************************************
     *  
     *
     * @param phoneList
     * @return
     */
    public static List<PhoneNumber>
    createList(String phoneList)
    {
        List<PhoneNumber> output    = new ArrayList<PhoneNumber>();
        StringTokenizer   tokenizer = new StringTokenizer( phoneList,"," );
        
        while ( tokenizer.hasMoreTokens() )
            output.add( new PhoneNumber( tokenizer.nextToken() ) );
        
        return output;
    }    
    /************************************************************************
     *  
     *
     * @param phoneNumber
     */
    private void
    checkPhoneNumber(String phoneNumber)
    {
        if ( !theirValidator.isValid( phoneNumber ) )
            throw new IllegalArgumentException( phoneNumber );
    }
}

// ##########################################################################
