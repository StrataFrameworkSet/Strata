// ##########################################################################
// # File Name:	DateTime.java
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import strata.foundation.utility.ICopyable;

/**
 * Provides a simplified abstraction for representing date/time values
 * using {@code Calendar} as its internal implementation. 
 * {@code DateTime} is a <a href="">Value Object</a> and is 
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
class DateTime
	implements  
	    ICopyable,
	    Serializable,
	    Comparable<DateTime>
{
	private static final long serialVersionUID = 0;
	
	private final Calendar itsImp;
	
	/************************************************************************
	 * Creates a new DateTime. 
	 *
	 */
	public 
	DateTime()
	{
		super();
		itsImp = new GregorianCalendar();
	}
	
	/************************************************************************
	 * Creates a new DateTime. 
	 *
	 * @param imp	calendar providing the {@code DateTime} information
	 */
	public 
	DateTime(Calendar imp)
	{
		super();
		itsImp = (Calendar)imp.clone();
	}

	/************************************************************************
	 * Creates a new DateTime. 
	 *
	 * @param zone	a time zone
	 */
	public 
	DateTime(TimeZone zone)
	{
		super();
		itsImp = new GregorianCalendar( zone );
	}

	/************************************************************************
	 * Creates a new {@code DateTime}. 
	 *
	 * @param value
	 * @throws ParseException
	 */
	public 
	DateTime(String value) 
	    throws ParseException
	{
	    DateFormat formatter = new SimpleDateFormat( "M/d/y");
	    
        itsImp = new GregorianCalendar();
        itsImp.setTime(formatter.parse( value ));
	}
	
	/************************************************************************
	 * Creates a new DateTime. 
	 *
	 * @param year		the year
	 * @param month		the month (1-12)
	 * @param day		the day (1-31)
	 */
	public
	DateTime(int year,int month,int day)
	{
		itsImp = new GregorianCalendar( year,month-1,day );
	}
	
	/************************************************************************
	 * Creates a new DateTime. 
	 *
	 * @param year		the year
	 * @param month		the month (1-12)
	 * @param day		the day (1-31)
	 * @param zone		a time zone
	 */
	public
	DateTime(int year,int month,int day,TimeZone zone)
	{
		itsImp = new GregorianCalendar( year,month-1,day );
		itsImp.setTimeZone( zone );
	}
	
	/************************************************************************
	 * Creates a new DateTime. 
	 *
	 * @param year		the year
	 * @param month		the month (1-12)
	 * @param day		the day (1-31)
	 * @param hour		the hour (0-23)
	 * @param minute	the minute (0-59)
	 */
	public
	DateTime(int year,int month,int day,int hour,int minute)
	{
		itsImp = 
			new GregorianCalendar( 
					year,
					month-1,
					day,
					hour,
					minute );
	}
	
	/************************************************************************
	 * Creates a new DateTime. 
	 *
	 * @param year		the year
	 * @param month		the month (1-12)
	 * @param day		the day (1-31)
	 * @param hour		the hour (0-23)
	 * @param minute	the minute (0-59)
	 * @param zone		a time zone
	 */
	public
	DateTime(int year,int month,int day,int hour,int minute,TimeZone zone)
	{
		itsImp = 
			new GregorianCalendar( 
					year,
					month-1,
					day,
					hour,
					minute );
		itsImp.setTimeZone( zone );
	}
	
	/************************************************************************
	 * Creates a new DateTime. 
	 *
	 * @param year		the year
	 * @param mon		the month (1-12)
	 * @param day		the day (1-31)
	 * @param hour		the hour (0-23)
	 * @param min		the minute (0-59)
	 * @param sec		the second (0-59)
	 */
	public
	DateTime(int year,int mon,int day,int hour,int min,int sec)
	{
		itsImp = 
			new GregorianCalendar( 
					year,
					mon-1,
					day,
					hour,
					min,
					sec );
	}
	
	/************************************************************************
	 * Creates a new DateTime. 
	 *
	 * @param year		the year
	 * @param mon		the month (1-12)
	 * @param day		the day (1-31)
	 * @param hour		the hour (0-23)
	 * @param min		the minute (0-59)
	 * @param sec		the second (0-59)
	 * @param zone		a time zone
	 */
	public
	DateTime(int year,int mon,int day,int hour,int min,int sec,TimeZone zone)
	{
		itsImp = 
			new GregorianCalendar( 
					year,
					mon-1,
					day,
					hour,
					min,
					sec );
		itsImp.setTimeZone( zone );
	}
	
	/************************************************************************
	 * Creates a new DateTime. 
	 *
	 * @param year		the year
	 * @param mon		the month (1-12)
	 * @param day		the day (1-31)
	 * @param hour		the hour (0-23)
	 * @param min		the minute (0-59)
	 * @param sec		the second (0-59)
	 * @param ms		the millisecond (0-999)
	 */
	public
	DateTime(int year,int mon,int day,int hour,int min,int sec,int ms)
	{
		itsImp = 
			new GregorianCalendar( 
					year,
					mon-1,
					day,
					hour,
					min,
					sec );
		itsImp.set( Calendar.MILLISECOND,ms );
	}
	
	/************************************************************************
	 * Creates a new DateTime. 
	 *
	 * @param year		the year
	 * @param mon		the month (1-12)
	 * @param day		the day (1-31)
	 * @param hour		the hour (0-23)
	 * @param min		the minute (0-59)
	 * @param sec		the second (0-59)
	 * @param ms		the millisecond (0-999)
	 * @param zone		a time zone
	 */
	public
	DateTime(
		int year,int mon,int day,
		int hour,int min,int sec,int ms,
		TimeZone zone)
	{
		itsImp = 
			new GregorianCalendar( 
					year,
					mon-1,
					day,
					hour,
					min,
					sec );
		itsImp.set( Calendar.MILLISECOND,ms );		
		itsImp.setTimeZone( zone );	
	}
	
	public
	DateTime(Timestamp timestamp)
	{
	    itsImp = new GregorianCalendar();
	    itsImp.setTimeInMillis( timestamp.getTime() );
	}
	
	/************************************************************************
	 * Creates a new DateTime. 
	 *
	 * @param other the {@code DateTime} being copied
	 */
	public 
	DateTime(DateTime other)
	{
		super();
		itsImp = other.itsImp;
	}

	/************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public DateTime 
    copy()
    {
        return new DateTime( this );
    }

    /************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public int 
	compareTo(DateTime other)
	{
		return itsImp.compareTo( other.itsImp );
	}
	
	/************************************************************************
	 * Creates a copy of the {@code DateTime} shifted to the 
	 * specified time zone. 
	 *
	 * @param 	zone	new time zone
	 * @return	copy of {@code DateTime} shifted to zone
	 */
	public DateTime
	changeTimeZone(TimeZone zone)
	{
		Calendar imp       = getCalendar();
		long     timestamp = imp.getTimeInMillis();
		
		imp.setTimeZone( zone );
		imp.setTimeInMillis( timestamp );
		return new DateTime( imp );
	}
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public Timestamp
	getTimestamp()
	{
	    return new Timestamp(itsImp.getTimeInMillis());
	}
	
	/************************************************************************
	 * @return	the {@code DateTime}'s year component
	 */
	public int
	getYear()
	{
		return itsImp.get( Calendar.YEAR );
	}
	
	/************************************************************************
	 * @return	the {@code DateTime}'s month component (1-12)
	 */
	public int
	getMonth()
	{
		return itsImp.get( Calendar.MONTH )+1;
	}
	
	/************************************************************************
	 * @return	the {@code DateTime}'s day component (1-31)
	 */
	public int
	getDay()
	{
		return itsImp.get( Calendar.DAY_OF_MONTH );
	}
	
	/************************************************************************
	 * @return	the {@code DateTime}'s hour component (0-23)
	 */
	public int
	getHour()
	{
		return itsImp.get( Calendar.HOUR_OF_DAY );
	}
	
	/************************************************************************
	 * @return	the {@code DateTime}'s minute component (0-59)
	 */
	public int
	getMinute()
	{
		return itsImp.get( Calendar.MINUTE );
	}
	
	/************************************************************************
	 * @return	the {@code DateTime}'s second component (0-59)
	 */
	public int
	getSecond()
	{
		return itsImp.get( Calendar.SECOND );
	}
	
	/************************************************************************
	 * @return	the {@code DateTime}'s millisecond component (0-999)
	 */
	public int
	getMilliSecond()
	{
		return itsImp.get( Calendar.MILLISECOND );
	}
	
	/************************************************************************
	 * @return	the {@code DateTime}'s timezone component
	 */
	public TimeZone
	getTimeZone()
	{
		return itsImp.getTimeZone();
	}
	
	/************************************************************************
	 * @return a copy of the {@code DateTime}'s implementation calendar
	 */
	public Calendar
	getCalendar()
	{
		return (Calendar)itsImp.clone();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public boolean 
	equals(Object other)
	{
		return 
			(other instanceof DateTime) && 
			itsImp.equals( ((DateTime)other).itsImp );
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
	@Override
	public String 
	toString()
	{
		StringBuilder output = new StringBuilder();
		Formatter     formatter = new Formatter( output );
		
		formatter.format( "%1$tY/%1$tm/%1$td-%1$tT:%1$tL-%1$tZ",itsImp );
		formatter.close();
		return output.toString();
	}

	/************************************************************************
	 * Determines if {@code DateTime} is chronologically  
	 * the same as another {@code DateTime}.
	 *
	 * @param 	other	{@code DateTime} being compared
	 * @return	true if {@code DateTime} is same as other
	 */
	public boolean
	isSame(DateTime other)
	{
		return itsImp.getTimeInMillis() == other.itsImp.getTimeInMillis();
	}
	
	/************************************************************************
	 * Determines if {@code DateTime} is chronologically before 
	 * another {@code DateTime}.
	 *
	 * @param 	other	{@code DateTime} being compared
	 * @return	true if {@code DateTime} is before other
	 */
	public boolean
	isBefore(DateTime other)
	{
		return itsImp.before(  other.itsImp );
	}
	
	/************************************************************************
	 * Determines if {@code DateTime} is chronologically after 
	 * another {@code DateTime}.
	 *
	 * @param 	other	{@code DateTime} being compared
	 * @return	true if {@code DateTime} is after other
	 */
	public boolean
	isAfter(DateTime other)
	{
		return itsImp.after(  other.itsImp );
	}
	

}


// ##########################################################################
