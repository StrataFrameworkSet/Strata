// ##########################################################################
// # File Name:	AbstractParty.java
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

import java.util.HashSet;
import java.util.Set;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractParty
    implements IParty
{
    private Long                     itsPartyKey;
    private Integer                  itsVersion;
    private Set<IContactInformation> itsContactInformation;
    
    /************************************************************************
     * Creates a new {@code AbstractParty}. 
     *
     */
    public 
    AbstractParty()
    {
        itsPartyKey = 0L;
        itsVersion = 0;
        itsContactInformation = new HashSet<IContactInformation>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setPartyKey(Long key)
    {
        itsPartyKey = key;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setVersion(Integer version)
    {
        itsVersion = version;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setContactInformation(Set<IContactInformation> info)
    {
        itsContactInformation = info;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Long 
    getPartyKey()
    {
        return itsPartyKey;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Integer 
    getVersion()
    {
        return itsVersion;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Set<IContactInformation> 
    getContactInformation()
    {
        return itsContactInformation;
    }

}

// ##########################################################################
