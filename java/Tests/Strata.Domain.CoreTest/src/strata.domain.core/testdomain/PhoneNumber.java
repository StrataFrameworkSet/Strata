// ##########################################################################
// # File Name:	PhoneNumber.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataEntity Framework.
// #
// #   			The StrataEntity Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataEntity Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataEntity
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.domain.core.testdomain;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PhoneNumber
    extends    AbstractContactInformation
    implements IContactInformation
{
    private String itsCountryCode;
    private String itsAreaCode;
    private String itsNumber;
    
    /************************************************************************
     * Creates a new {@code PhoneNumber}. 
     *
     */
    public 
    PhoneNumber()
    {
        super();
        itsCountryCode = "";
        itsAreaCode = "";
        itsNumber = "";
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    toString()
    {
        return itsCountryCode + "-" + itsAreaCode + "-" + itsNumber;
    }

    /************************************************************************
     *  
     *
     * @param countryCode
     */
    public void
    setCountryCode(String countryCode)
    {
        itsCountryCode = countryCode;
    }
    
    /************************************************************************
     *  
     *
     * @param areaCode
     */
    public void
    setAreaCode(String areaCode)
    {
        itsAreaCode = areaCode;
    }
    
    /************************************************************************
     *  
     *
     * @param number
     */
    public void
    setNumber(String number)
    {
        itsNumber = number;
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
    getAreaCode()
    {
        return itsAreaCode;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getNumber()
    {
        return itsNumber;
    }
}

// ##########################################################################
