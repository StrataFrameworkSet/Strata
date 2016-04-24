// ##########################################################################
// # File Name:	.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
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

package strata1.persistence.inmemoryrepository;

import strata1.common.lockingsynchronizer.ReadWriteLockSynchronizer;
import strata1.common.synchronizer.ISynchronizer;
import strata1.persistence.repository.IRepositoryContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Contains the shared lock and in-memory transactions used by related
 * <code>PersonManager</code>s and <code>OrganizationManager</code>s.
 *  
 * @author 		
 *     Java Persistence Strategy Workgroup 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryRepositoryContext 
	implements IRepositoryContext
{
	private final ISynchronizer                   itsSynchronizer;
    private Map<EntityIdentifier,Object>          itsEntities;
	private InMemoryUnitOfWork                    itsUnitOfWork;
	private Map<Class<?>,List<InMemoryFinder<?>>> itsFinders;
	
	/************************************************************************
	 * Creates a new InMemoryRepositoryContext. 
	 *
	 */
	public 
	InMemoryRepositoryContext()
	{
		super();
		itsSynchronizer  = new ReadWriteLockSynchronizer();
        itsEntities      = new HashMap<EntityIdentifier,Object>();
		itsUnitOfWork    = new InMemoryUnitOfWork(itsEntities);
		itsFinders       = new HashMap<Class<?>,List<InMemoryFinder<?>>>();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
    @Override
	public ISynchronizer 
	getSynchronizer()
	{
		return itsSynchronizer;
	}

	/************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public InMemoryUnitOfWork 
    getUnitOfWork()
    {
        if ( itsUnitOfWork == null || !itsUnitOfWork.isActive() )
            itsUnitOfWork = new InMemoryUnitOfWork(itsEntities);
        
        return itsUnitOfWork;
    }

    /************************************************************************
     *  
     *
     * @param type
     * @return
     */
    public <T> List<T>
    getEntitiesByType(Class<T> type)
    {
        List<T> entities = new ArrayList<T>();
        
        for (
            Map.Entry<EntityIdentifier,Object> entry:
                itsEntities.entrySet())
        {
            if ( type.isAssignableFrom( entry.getKey().getType() ) )
                entities.add( type.cast( entry.getValue() ) );
        }
        
        return entities;
    }
    
    /************************************************************************
     *  
     *
     * @param type
     * @param finder
     */
    public <T> void
    insertFinder(Class<T> type,InMemoryFinder<T> finder)
    {
        if ( !itsFinders.containsKey( type ) )
            itsFinders.put( type,new ArrayList<InMemoryFinder<?>>() );
        
        itsFinders.get( type ).add( finder );
    }
    
    /************************************************************************
     *  
     *
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<InMemoryFinder<T>>
    getFinders(Class<T> type)
    {
        List<InMemoryFinder<T>> finders = 
            new ArrayList<InMemoryFinder<T>>();
        
        if ( itsFinders.containsKey( type ) )
            for (InMemoryFinder<?> finder : itsFinders.get( type ))
                finders.add( (InMemoryFinder<T>)finder );
        
        return finders;
    }
}


// ##########################################################################
