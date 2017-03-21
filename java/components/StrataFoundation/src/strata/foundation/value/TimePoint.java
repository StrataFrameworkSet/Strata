// ##########################################################################
// # File Name:	TimePoint.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
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

package strata.foundation.value;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import strata.foundation.utility.ICopyable;

/**
 * Provides a simplified abstraction for representing date/time values
 * using {@code Calendar} as its internal implementation. 
 * {@code TimePoint} is a <a href="">Value Object</a> and is 
 * therefore immutable.
 * 
 * @see Calendar
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class TimePoint
	implements  
	    ICopyable,
	    Serializable,
	    Comparable<TimePoint>
{	
    public static final int  MILLIS_PER_NANOS = 1000000;
    
	private static final long serialVersionUID = -2333228502300850371L;
    private final Timestamp   itsImp;
	
	/************************************************************************
	 * Creates a new TimePoint. 
	 *
	 */
	public 
	TimePoint()
	{
		itsImp = new Timestamp(new Date().getTime());
	}
	
	/************************************************************************
	 * Creates a new TimePoint. 
	 *
	 * @param imp	calendar providing the {@code TimePoint} information
	 */
	public 
	TimePoint(Timestamp imp)
	{
		itsImp = (Timestamp)imp.clone();
	}

	/************************************************************************
	 * Creates a new {@code TimePoint}. 
	 *
	 * @param value
	 * @throws IllegalArgumentException 
	 */
	public 
	TimePoint(String value) 
	    throws IllegalArgumentException
	{
        itsImp = Timestamp.valueOf( value );
	}
	
	/************************************************************************
	 * Creates a new TimePoint. 
	 *
	 * @param year		the year
	 * @param month		the month (1-12)
	 * @param day		the day (1-31)
	 */
    public
	TimePoint(int year,int month,int day)
	{
        this( year,month,day,0,0,0,0 );
	}
		
	/************************************************************************
	 * Creates a new TimePoint. 
	 *
	 * @param year		the year
	 * @param month		the month (1-12)
	 * @param day		the day (1-31)
	 * @param hour		the hour (0-23)
	 * @param minute	the minute (0-59)
	 */
	public
	TimePoint(int year,int month,int day,int hour,int minute)
	{
	    this( year,month,day,hour,minute,0,0 );
	}
	
	/************************************************************************
	 * Creates a new TimePoint. 
	 *
	 * @param year		the year
	 * @param mon		the month (1-12)
	 * @param day		the day (1-31)
	 * @param hour		the hour (0-23)
	 * @param min		the minute (0-59)
	 * @param sec		the second (0-59)
	 */
	public
	TimePoint(int year,int mon,int day,int hour,int min,int sec)
	{
	    this( year,mon,day,hour,min,sec,0 );
	}
		
	/************************************************************************
	 * Creates a new TimePoint. 
	 *
	 * @param year		the year
	 * @param mon		the month (1-12)
	 * @param day		the day (1-31)
	 * @param hour		the hour (0-23)
	 * @param min		the minute (0-59)
	 * @param sec		the second (0-59)
	 * @param ms		the millisecond (0-999)
	 */
	@SuppressWarnings("deprecation")
    public
	TimePoint(int year,int mon,int day,int hour,int min,int sec,int ms)
	{
		itsImp = 
			new Timestamp( 
					year-1900,
					mon-1,
					day,
					hour,
					min,
					sec, 
					ms*MILLIS_PER_NANOS );
	}
	
	/************************************************************************
	 * Creates a new TimePoint. 
	 *
	 * @param other the {@code TimePoint} being copied
	 */
	public 
	TimePoint(TimePoint other)
	{
		itsImp = other.itsImp;
	}

	/************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public TimePoint 
    copy()
    {
        return new TimePoint( this );
    }

    /************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public int 
	compareTo(TimePoint other)
	{
		return itsImp.compareTo( other.itsImp );
	}
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public Timestamp
	getTimestamp()
	{
	    return (Timestamp)itsImp.clone();
	}
	
	/************************************************************************
	 * @return	the {@code TimePoint}'s year component
	 */
	@SuppressWarnings("deprecation")
    public int
	getYear()
	{
		return itsImp.getYear() + 1900;
	}
	
	/************************************************************************
	 * @return	the {@code TimePoint}'s month component (1-12)
	 */
	@SuppressWarnings("deprecation")
    public int
	getMonth()
	{
		return itsImp.getMonth()+1;
	}
	
	/************************************************************************
	 * @return	the {@code TimePoint}'s day component (1-31)
	 */
	@SuppressWarnings("deprecation")
    public int
	getDay()
	{
		return itsImp.getDate();
	}
	
	/************************************************************************
	 * @return	the {@code TimePoint}'s hour component (0-23)
	 */
	@SuppressWarnings("deprecation")
    public int
	getHour()
	{
		return itsImp.getHours();
	}
	
	/************************************************************************
	 * @return	the {@code TimePoint}'s minute component (0-59)
	 */
	@SuppressWarnings("deprecation")
    public int
	getMinute()
	{
		return itsImp.getMinutes();
	}
	
	/************************************************************************
	 * @return	the {@code TimePoint}'s second component (0-59)
	 */
	@SuppressWarnings("deprecation")
    public int
	getSecond()
	{
		return itsImp.getSeconds();
	}
	
	/************************************************************************
	 * @return	the {@code TimePoint}'s millisecond component (0-999)
	 */
	public int
	getMilliSecond()
	{
		return itsImp.getNanos() / MILLIS_PER_NANOS;
	}
	
	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public boolean 
	equals(Object other)
	{
		return 
			(other instanceof TimePoint) && 
			itsImp.equals( ((TimePoint)other).itsImp );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public int 
	hashCode()
	{
		int hash = 7;
		
		hash = 31 * hash + itsImp.hashCode();
		return hash;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@SuppressWarnings("deprecation")
    @Override
	public String 
	toString()
	{
		return itsImp.toLocaleString();
	}

	/************************************************************************
	 * Determines if {@code TimePoint} is chronologically  
	 * the same as another {@code TimePoint}.
	 *
	 * @param 	other	{@code TimePoint} being compared
	 * @return	true if {@code TimePoint} is same as other
	 */
	public boolean
	isSame(TimePoint other)
	{
		return itsImp.equals( other.itsImp );
	}
	
	/************************************************************************
	 * Determines if {@code TimePoint} is chronologically before 
	 * another {@code TimePoint}.
	 *
	 * @param 	other	{@code TimePoint} being compared
	 * @return	true if {@code TimePoint} is before other
	 */
	public boolean
	isBefore(TimePoint other)
	{
		return itsImp.before(  other.itsImp );
	}
	
	/************************************************************************
	 * Determines if {@code TimePoint} is chronologically after 
	 * another {@code TimePoint}.
	 *
	 * @param 	other	{@code TimePoint} being compared
	 * @return	true if {@code TimePoint} is after other
	 */
	public boolean
	isAfter(TimePoint other)
	{
		return itsImp.after(  other.itsImp );
	}
	

}


// ##########################################################################
