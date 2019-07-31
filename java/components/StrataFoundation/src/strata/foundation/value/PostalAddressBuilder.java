// ##########################################################################
// # File Name:	PostalAddressBuilder.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.value;

/****************************************************************************
 * 
 */
public 
class PostalAddressBuilder
{
    private String itsAddress;
    private String itsStreet;
    private String itsCity;
    private String itsState;
    private String itsCountryCode;
    private String itsPostalCode;

    /************************************************************************
     * Creates a new PostalAddressBuilder. 
     *
     */
    public 
    PostalAddressBuilder() 
    {
        itsAddress     = null;
        itsStreet      = null;
        itsCity        = null;
        itsState       = null;
        itsCountryCode = null;
        itsPostalCode  = null;
    }
    
    /************************************************************************
     *  
     *
     * @param address
     * @return
     */
    public PostalAddressBuilder
    setAddress(String address)
    {
        itsAddress = address;
        return this;
    }
    
    /************************************************************************
     *  
     *
     * @param street
     * @return
     */
    public PostalAddressBuilder
    setStreet(String street)
    {
        itsStreet = street;
        return this;
    }
    
    /************************************************************************
     *  
     *
     * @param city
     * @return
     */
    public PostalAddressBuilder
    setCity(String city)
    {
        itsCity = city;
        return this;
    }
    
    /************************************************************************
     *  
     *
     * @param state
     * @return
     */
    public PostalAddressBuilder
    setState(String state)
    {
        itsState = state;
        return this;
    }
    
    /************************************************************************
     *  
     *
     * @param countryCode
     * @return
     */
    public PostalAddressBuilder
    setCountryCode(String countryCode)
    {
        itsCountryCode = countryCode;
        return this;
    }
    
    /************************************************************************
     *  
     *
     * @param postalCode
     * @return
     */
    public PostalAddressBuilder
    setPostalCode(String postalCode)
    {
        itsPostalCode = postalCode;
        return this;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public PostalAddress
    toPostalAddress()
    {
        return
            new PostalAddress(
                itsAddress,
                itsStreet,
                itsCity,
                itsState,
                itsCountryCode,
                itsPostalCode );
    }
}

// ##########################################################################
