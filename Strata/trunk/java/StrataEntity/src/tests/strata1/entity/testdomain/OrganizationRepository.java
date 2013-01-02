// ##########################################################################
// # File Name:	OrganizationRepository.java
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

package strata1.entity.testdomain;

import strata1.entity.repository.IFinder;
import strata1.entity.repository.IRepositoryContext;
import strata1.entity.repository.IRepositoryProvider;
import strata1.entity.repository.InsertFailedException;
import strata1.entity.repository.InvalidInputException;
import strata1.entity.repository.NotUniqueException;
import strata1.entity.repository.RemoveFailedException;
import strata1.entity.repository.UpdateFailedException;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class OrganizationRepository
    implements IOrganizationRepository
{
    private IRepositoryProvider<Long,IOrganization> itsProvider;
    
    /************************************************************************
     * Creates a new {@code OrganizationRepository}. 
     *
     */
    public 
    OrganizationRepository(IRepositoryProvider<Long,IOrganization> provider)
    {
        itsProvider = provider;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IRepositoryContext 
    getContext()
    {
        return itsProvider.getContext();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IOrganization 
    insertOrganization(IOrganization organization)
        throws InsertFailedException
    {
        return itsProvider.insertNew( organization );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IOrganization 
    updateOrganization(IOrganization organization)
        throws UpdateFailedException
    {
        return itsProvider.updateExisting( organization );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    removeOrganization(IOrganization organization)
        throws RemoveFailedException
    {
        itsProvider.removeExisting( organization );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IOrganization 
    getOrganizationByPartyKey(Long partyKey)
    {
        return itsProvider.getExisting( partyKey );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IOrganization 
    getOrganizationByName(String name)
        throws InvalidInputException,NotUniqueException
    {
        IFinder<IOrganization> finder = getFinder("GetByName");
        
        finder.setInput( "name",name );
        return finder.getUnique();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IFinder<IOrganization> 
    getFinder(String finderName)
    {
        return 
            itsProvider.getFinder( 
                "strata1.entity.testdomain.IOrganization." + finderName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasOrganizationWithPartyKey(Long partyKey)
    {
        return itsProvider.hasExisting( partyKey );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasOrganizationWithName(String name) 
        throws NotUniqueException
    {
        IFinder<IOrganization> finder = getFinder( "GetByName" );
        
        try
        {
            finder.setInput( "name",name );
            return finder.hasUnique();
        }
        catch(InvalidInputException unused)
        {
            return false;
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasFinder(String finderName)
    {
        return 
            itsProvider.hasFinder( 
                "strata1.entity.testdomain.IOrganization." + finderName );
    }

}

// ##########################################################################
