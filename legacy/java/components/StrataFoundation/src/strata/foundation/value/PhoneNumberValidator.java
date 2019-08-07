// ##########################################################################
// # File Name:	PhoneNumberValidator.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.value;


/****************************************************************************
 * 
 */
public 
class PhoneNumberValidator
    implements IPhoneNumberValidator
{
    private static final String NANP_PATTERN = 
        "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
    
    private static final String ITU_T_PATTERN = 
        "^\\+(?:[0-9] ?){6,14}[0-9]$";
    
    private static final String EPP_PATTERN = 
        "^\\+[0-9]{1,3}\\.[0-9]{4,14}(?:x.+)?$";

    /************************************************************************
     * Creates a new PhoneNumberValidator. 
     *
     */
    public 
    PhoneNumberValidator() {}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isValid(String phoneNumber)
    {
        if ( phoneNumber.matches( NANP_PATTERN ) )
            return true;
        
        if ( phoneNumber.matches( ITU_T_PATTERN ) )
            return true;
        
        if ( phoneNumber.matches( EPP_PATTERN ) )
            return true;
        
        return false;
    }

}

// ##########################################################################
