// ##########################################################################
// # File Name:	InMemoryNamedQuery.java
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

package strata.persistence.inmemory;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import strata.persistence.namedquery.AbstractNamedQuery;
import strata.persistence.namedquery.INamedQuery;
import strata.persistence.namedquery.NotUniqueException;

/****************************************************************************
 * 
 * @param <K> key type
 * @param <T> entity type
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryNamedQuery<T> 
    extends    AbstractNamedQuery<T>
	implements INamedQuery<T>
{
	private InMemoryUnitOfWorkProvider itsProvider;
	private Class<T>                   itsType;
	private IPredicate<T>			   itsPredicate;
	private List<T>				       itsObjects;
	private List<T>				       itsResults;
	private Iterator<T>				   itsIterator;
		
	/************************************************************************
	 * Creates a new InMemoryNamedQuery. 
	 * @param type TODO
	 *
	 */
	public 
	InMemoryNamedQuery(
	    InMemoryUnitOfWorkProvider provider,
		String                     name,
		Class<T>                   type, 
		IPredicate<T>			   predicate)
	{
		super(type.getCanonicalName() + "." + name);
		itsProvider      = provider;
		itsType          = type;
		itsPredicate	 = predicate;
		itsObjects       = itsProvider.getEntitiesByType( itsType );
		itsResults       = null;
		itsIterator      = null;
		
		itsProvider.insertNamedQuery(type,this);
	}

	/************************************************************************
	 * Creates a new InMemoryNamedQuery. 
	 *
	 */
	public 
	InMemoryNamedQuery(InMemoryNamedQuery<T> other)
	{
		super(other);
		itsProvider       = other.itsProvider;
		itsType          = other.itsType;
		itsPredicate	 = other.itsPredicate;
		itsObjects       = itsProvider.getEntitiesByType( itsType );
		itsResults       = null;
		itsIterator      = null;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public InMemoryNamedQuery<T> 
	copy()
	{
	    return new InMemoryNamedQuery<T>( this );
	}

	/************************************************************************
     * {@inheritDoc} 
	 * @return 
     */
    @Override
    public INamedQuery<T> 
    clear()
    {
        itsObjects = itsProvider.getEntitiesByType( itsType );
        itsResults = null;
        itsIterator = null;
        return this;
    }

    /************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public T 
	getUnique() 
	    throws NotUniqueException
	{
		execute();
		
		if ( itsResults.size() > 1 )
		    throw new NotUniqueException("result is not unique");
		
		if ( itsResults.isEmpty() )
		    return null;
		
		return itsResults.get( 0 );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public Collection<T> 
	getAll()
	{
		execute();
		return itsResults;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public T 
	getNext()
	{
		execute();
		return itsIterator.next();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public boolean 
	hasNext()
	{
		execute();
		return itsIterator.hasNext();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
    public boolean 
    hasUnique() 
        throws NotUniqueException
    {
	    execute();
	    return itsResults.size() == 1;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasAny()
    {
        execute();
        return !itsResults.isEmpty();
    }

    /************************************************************************
	 *  
	 *
	 */
	protected void
	execute()
	{
		if ( itsResults != null )
			return;
		
		itsObjects  = itsProvider.getEntitiesByType( itsType );
		itsResults  = getResults( getInputs().getNamedInputs(),itsObjects );
		itsIterator = itsResults.iterator();
	}
	
	/************************************************************************
	 *  
	 *
	 * @param inputs
	 * @param objects
	 * @return
	 */
	protected List<T> 
	getResults(Map<String,Object> inputs,List<T> objects)
	{
		List<T> output = new ArrayList<T>();
		
		for (T entry : objects)
			if ( itsPredicate.evaluate( entry,inputs ) )
				output.add( entry );
		
		return output;
	}
}


// ##########################################################################
