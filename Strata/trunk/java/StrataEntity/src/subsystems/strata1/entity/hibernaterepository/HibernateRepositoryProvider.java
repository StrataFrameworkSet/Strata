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

import java.io.*;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 * @param <K>
 * @param <T>
 */
public 
class HibernateRepositoryProvider<K extends Serializable,T> 
	extends AbstractRepositoryProvider<K,T>
{
	private Class<T>				   itsEntityClass;
	private HibernateRepositoryContext itsContext;
	
	/************************************************************************
	 * Creates a new HibernateRepositoryImp. 
	 *
	 */
	public 
	HibernateRepositoryProvider(
		Class<T> 				   entityClass,
		HibernateRepositoryContext context)
	{
		super();
		itsEntityClass = entityClass;
		itsContext     = context;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see RepositoryImp#getContext()
	 */
	@Override
	public HibernateRepositoryContext 
	getContext()
	{
		return itsContext;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractRepositoryProvider#doInsert(Object)
	 */
	@Override
	protected void 
	doInsert(T entity)
	{
		HibernateTemplate template = getContext().getHibernateTemplate();
		
		template.saveOrUpdate( entity );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractRepositoryProvider#doUpdate(Object)
	 */
	@Override
	protected void doUpdate(T entity)
	{
		HibernateTemplate template = getContext().getHibernateTemplate();
		
		template.saveOrUpdate( entity );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractRepositoryProvider#doRemove(Object)
	 */
	@Override
	protected void 
	doRemove(T entity)
	{
		HibernateTemplate template = getContext().getHibernateTemplate();
		
		template.delete( entity );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractRepositoryProvider#doGet(Serializable)
	 */
	@Override
	protected T 
	doGet(K key)
	{
		HibernateTemplate template = getContext().getHibernateTemplate();
		Object            entity = template.get( itsEntityClass,key );
		
		return itsEntityClass.cast( entity );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractRepositoryProvider#doHas(Serializable)
	 */
	@Override
	protected boolean 
	doHas(K key)
	{
		HibernateTemplate template = getContext().getHibernateTemplate();
		
		return template.contains( key );
	}

}


// ##########################################################################
