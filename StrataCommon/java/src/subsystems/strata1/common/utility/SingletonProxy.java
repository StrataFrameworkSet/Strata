// ##########################################################################
// # File Name:	SingletonProxy.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataCommon Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommon Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommon
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.common.utility;

import java.lang.reflect.*;
import java.util.*;;

/**
 * A dynamic proxy class that manages singletons for any type.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SingletonProxy 
	implements InvocationHandler
{
	private static Map<Object,Object> theirInstances 
										= new HashMap<Object,Object>();
	private Object                    itsInstance;
	
	/************************************************************************
	 * Creates a new SingletonProxy. 
	 */
	protected 
	SingletonProxy(Object instance)
	{
		super();
		itsInstance = instance;
	}

	/************************************************************************
	 * Forwards method invocations to the singleton instance.
	 * <p>
	 * {@inheritDoc} 
	 */
	public Object 
	invoke(Object proxy,Method method,Object[] args)
		throws Throwable
	{
		Object result = null;
		
		try
		{
			result = method.invoke( itsInstance,args );
		}
		catch (InvocationTargetException e) 
		{
			throw e.getTargetException();
		}
		return result;
	}
	
	/************************************************************************
	 * Compares this singleton with another object. 
	 *
	 * @param 	other	object being compared to singleton
	 * @return	true if the singleton instance and the other 
	 * 			are the same object.
	 */
	public boolean 
	isSame(Object other)
	{
		return itsInstance == other;
	}
	
	/************************************************************************
	 * Sets the singleton instance for the specified class. 
	 *
	 * @param <S>	   singleton type
	 * @param c		   singleton's class object
	 * @param instance the singleton instance
	 * 
	 * @postcondition  SingletonProxy.getInstance( c ).isSame( instance )
	 */
	public 
	static <S> void 
	setInstance(Class<S> c,S instance)
	{
		synchronized (SingletonProxy.class)
		{
			theirInstances.put( c,instance );
		}
	}
	
	/************************************************************************
	 * Releases the reference to the singleton instance 
	 * for the specified class. 
	 *
	 * @param <S>	singleton type
	 * @param c		singleton class object	
	 */
	public
	static <S> void 
	clearInstance(Class<S> c)
	{
		synchronized (SingletonProxy.class)
		{
			theirInstances.remove( c );
		}
	}
	
	/************************************************************************
	 * Gets the singleton instance for the specified class. 
	 *
	 * @param <S>	singleton type
	 * @param c		singleton class object
	 * @return		singleton instance associated with class object
	 */
	public 
	static <S> S 
	getInstance(Class<S> c)
	{
		synchronized (SingletonProxy.class)
		{
			Object instance = theirInstances.get( c );
			Object proxy    = Proxy.newProxyInstance(
				    			instance.getClass().getClassLoader(),
				    			instance.getClass().getInterfaces(),
				    			new SingletonProxy( instance ) );
			
			return c.cast( proxy );
		}
	}

}


// ##########################################################################
