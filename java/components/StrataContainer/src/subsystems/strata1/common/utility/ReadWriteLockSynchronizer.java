// ##########################################################################
// # File Name:	ReadWriteLockSynchronizer.java
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

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implements the {@code ISynchronizer} interface by wrapping
 * the standard {@code ReadWriteLock}. 
 * 
 * @see ReadWriteLock
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ReadWriteLockSynchronizer 
	implements ISynchronizer
{
	private final ReadWriteLock itsLock;
	
	/************************************************************************
	 * Creates a new ReadWriteLockSynchronizer. 
	 *
	 * @param lock
	 */
	public 
	ReadWriteLockSynchronizer()
	{
		super();
		itsLock = new ReentrantReadWriteLock();
	}
	
	/************************************************************************
	 * Creates a new {@code ReadWriteLockSynchronizer}. 
	 *
	 * @param lock
	 */
	public 
	ReadWriteLockSynchronizer(ReadWriteLock lock)
	{
		super();
		itsLock = lock;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public void 
	lockForReading()
	{
		itsLock.readLock().lock();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public void 
	lockForWriting()
	{
		itsLock.writeLock().lock();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public void 
	unlockFromReading()
	{
		itsLock.readLock().unlock();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public void 
	unlockFromWriting()
	{
		itsLock.writeLock().unlock();
	}

}


// ##########################################################################
