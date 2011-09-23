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

package strata1.entity.hibernaterepository;

import org.springframework.orm.hibernate3.*;

import strata1.entity.repository.*;

import java.util.*;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class HibernateFinder<T>
	extends 	AbstractFinder<T>
	implements	Finder<T>
{
	private Class<T>                   itsEntityClass;
	private HibernateRepositoryContext itsContext;
	private List<T>	                   itsResult;
	private Iterator<T>				   itsCurrent;
	
	/************************************************************************
	 * Creates a new HibernateFinder. 
	 *
	 */
	public 
	HibernateFinder(
		String 					   queryName,
		Class<T> 				   entityClass,
		HibernateRepositoryContext context)
	{
		super( queryName );
		itsEntityClass = entityClass;
		itsContext     = context;
		itsResult      = null;
		itsCurrent     = null;
	}

	/************************************************************************
	 * Creates a new HibernateFinder. 
	 *
	 * @param other
	 */
	public
	HibernateFinder(HibernateFinder<T> other)
	{
		super( other );
		itsEntityClass = other.itsEntityClass;
		itsContext     = other.itsContext;
		itsResult      = null;
		itsCurrent     = null;
	}
	
	/************************************************************************
	 * {@inheritDoc} 
	 * @see Finder#copy()
	 */
	@Override
	public HibernateFinder<T> 
	copy()
	{
		return new HibernateFinder<T>( this );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see Finder#clear()
	 */
	@Override
	public void clear()
	{
		clearInputs();
		itsResult  = null;
		itsCurrent = null;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see Finder#getAll()
	 */
	@Override
	public Collection<T> 
	getAll()
	{
		evaluateQuery();
		return itsResult;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see Finder#getUnique()
	 */
	@Override
	public T 
	getUnique()
	{
		evaluateQuery();
		return itsResult.get( 0 );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see Finder#getNext()
	 */
	@Override
	public T 
	getNext()
	{
		evaluateQuery();
		return itsCurrent.next();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see Finder#hasNext()
	 */
	@Override
	public boolean 
	hasNext()
	{
		evaluateQuery();
		return itsCurrent.hasNext();
	}
	
	/************************************************************************
	 *  
	 *
	 */
	protected void
	evaluateQuery()
	{
		if ( itsResult == null )
		{
			HibernateTemplate template = itsContext.getHibernateTemplate();
			InputKeeper       keeper   = getInputs();
			
			switch ( keeper.getMode() )
			{
			case NAMED:
				itsResult = evaluateQueryWithNamedInputs( template,keeper );
				break;
				
			case POSITIONAL:
				itsResult = evaluateQueryWithPositionalInputs( template,keeper );
				break;
				
			default:
				itsResult = evaluateQueryWithNoInputs( template );
				break;
			}
			
			itsCurrent = itsResult.iterator();
		}
	}

	/************************************************************************
	 *  
	 *
	 * @param template
	 * @param keeper
	 * @return
	 */
	private List<T> 
	evaluateQueryWithPositionalInputs(
		HibernateTemplate template,
		InputKeeper       keeper)
	{
		Collection<Object> inputs = keeper.getPositionalInputs();
		
		return
			convertToTypedList(
				template.findByNamedQuery( 
					getName(),
					inputs.toArray() ) );
	}

	/************************************************************************
	 *  
	 *
	 * @param template
	 * @param keeper
	 * @return
	 */
	private List<T> 
	evaluateQueryWithNamedInputs(
		HibernateTemplate template,
		InputKeeper       keeper)
	{
		Map<String,Object> inputs = keeper.getNamedInputs();
		
		return 
			convertToTypedList(
				template.findByNamedQueryAndNamedParam( 
					getName(),
					inputs.keySet().toArray(new String[0]),
					inputs.values().toArray() ) );
	}
	
	/************************************************************************
	 *  
	 *
	 * @param template
	 * @return
	 */
	private List<T> 
	evaluateQueryWithNoInputs(HibernateTemplate template)
	{
		return
			convertToTypedList(template.findByNamedQuery( getName() ) );
	}

	/************************************************************************
	 *  
	 *
	 * @param untyped
	 * @return
	 */
	private List<T> 
	convertToTypedList(List<?> untyped)
	{
		List<T> typed = new ArrayList<T>();
		
		for (Object entity : untyped)
			typed.add( itsEntityClass.cast( entity ) );
		
		return typed;
	}

}


// ##########################################################################
