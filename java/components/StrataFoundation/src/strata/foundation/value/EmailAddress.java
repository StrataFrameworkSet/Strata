// ##########################################################################
// # File Name:	EmailAddress.java
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
class EmailAddress
    implements ICopyable,Serializable,Comparable<EmailAddress>
{
    private static final long serialVersionUID = 2320238746080434459L;
    private static final IEmailAddressValidator 
    theirValidator = new EmailAddressValidator();
    
    private final String itsEmail;
    
    /************************************************************************
     * Creates a new EmailAddress. 
     *
     * @param email
     */
    public 
    EmailAddress(String email)
    {
        checkEmailAddress( email );
        itsEmail = email;
    }

    /************************************************************************
     * Creates a new EmailAddress. 
     *
     * @param other
     */
    public 
    EmailAddress(EmailAddress other)
    {
        this( other.itsEmail );
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public EmailAddress 
    copy()
    {
        return new EmailAddress( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    compareTo(EmailAddress other)
    {
        return itsEmail.compareToIgnoreCase( other.itsEmail );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    equals(Object other)
    {
        if ( other instanceof EmailAddress )
            return itsEmail.equalsIgnoreCase( ((EmailAddress)other).itsEmail );
        
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    hashCode()
    {
        return 31 * itsEmail.hashCode();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    toString()
    {
        return itsEmail;
    }

    /************************************************************************
     *  
     *
     * @param addresses
     * @return
     */
    public static List<EmailAddress>
    createList(String addresses)
    {
        List<EmailAddress> output    = new ArrayList<EmailAddress>();
        StringTokenizer    tokenizer = new StringTokenizer( addresses,"," );
        
        while ( tokenizer.hasMoreTokens() )
            output.add( new EmailAddress( tokenizer.nextToken() ) );
        
        return output;
    }
        
    /************************************************************************
     *  
     *
     * @param email
     * @throws IllegalArgumentException
     */
    private void
    checkEmailAddress(String email) 
        throws IllegalArgumentException 
    {
        if ( !theirValidator.isValid( email ))
            throw new IllegalArgumentException(email);
    }

}

// ##########################################################################
