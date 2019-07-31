// ##########################################################################
// # File Name:	EmailAddressValidator.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.value;

/****************************************************************************
 * 
 */
public 
class EmailAddressValidator
    implements IEmailAddressValidator
{
    private static final String EMAIL_PATTERN = 
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /************************************************************************
     * Creates a new EmailAddressValidator. 
     *
     */
    public 
    EmailAddressValidator() {}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isValid(String email)
    {
        return email.matches( EMAIL_PATTERN );
    }

}

// ##########################################################################
