// ##########################################################################
// # File Name:	PostalAddress.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.value;

import java.io.Serializable;
import strata.foundation.utility.ICopyable;

/****************************************************************************
 * 
 */
public 
class PostalAddress
    implements ICopyable,Serializable,Comparable<PostalAddress>
{
    private static final long serialVersionUID = 8284898731222924307L;
    
    private final String itsAddress;
    private final String itsStreet;
    private final String itsCity;
    private final String itsState;
    private final String itsCountryCode;
    private final String itsPostalCode;
    
    /************************************************************************
     * Creates a new PostalAddress. 
     *
     */
    public 
    PostalAddress(
        final String address,
        final String street,
        final String city,
        final String state,
        final String countryCode,
        final String postalCode)
    {
        itsAddress     = address;
        itsStreet      = street;
        itsCity        = city;
        itsState       = state;
        itsCountryCode = countryCode;
        itsPostalCode  = postalCode;
    }

    /************************************************************************
     * Creates a new PostalAddress. 
     *
     * @param other
     */
    public
    PostalAddress(PostalAddress other)
    {
        this( 
            other.itsAddress,
            other.itsStreet,
            other.itsCity,
            other.itsState,
            other.itsCountryCode,
            other.itsPostalCode );
    }
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public PostalAddress 
    copy()
    {
        return new PostalAddress( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    compareTo(PostalAddress other)
    {
        int result = 
            itsPostalCode.compareToIgnoreCase( other.itsPostalCode );
        
        if ( result != 0 )
            return result;
        
        result = 
            itsCountryCode.compareToIgnoreCase( other.itsCountryCode );
        
        if ( result != 0 )
            return result;
        
        result = 
            itsState.compareToIgnoreCase( other.itsState );
        
        if ( result != 0 )
            return result;
        
        result = 
            itsCity.compareToIgnoreCase( other.itsCity );
        
        if ( result != 0 )
            return result;
        
        result =
            itsStreet.compareToIgnoreCase( other.itsStreet );
        
        if ( result != 0 )
            return result;
        
        result = 
            itsAddress.compareToIgnoreCase( other.itsAddress );
        
        return result;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    equals(Object other)
    {
        if ( other instanceof PostalAddress )
            return compareTo( (PostalAddress)other ) == 0;
        
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    hashCode()
    {
        int hash = 31;
        
        hash = 31 * hash + itsAddress.hashCode();
        hash = 31 * hash + itsStreet.hashCode();
        hash = 31 * hash + itsCity.hashCode();
        hash = 31 * hash + itsState.hashCode();
        hash = 31 * hash + itsCountryCode.hashCode();
        hash = 31 * hash + itsPostalCode.hashCode();
        
        return hash;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    toString()
    {
        StringBuilder builder = new StringBuilder();
        
        builder
            .append(  itsAddress )
            .append( ' ' )
            .append( itsStreet )
            .append(  '\n' )
            .append(  itsCity )
            .append( ' ' )
            .append( itsState )
            .append( ' ' )
            .append( itsCountryCode )
            .append(  ' ' )
            .append( itsPostalCode );
        
        return builder.toString();
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getAddress()
    {
        return itsAddress;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getStreet()
    {
        return itsStreet;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getCity()
    {
        return itsCity;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getState()
    {
        return itsState;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getCountryCode()
    {
        return itsCountryCode;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getPostalCode()
    {
        return itsPostalCode;
    }
}

// ##########################################################################
