// ##########################################################################
// # File Name:		TimePointTest.java
// # Copyright(C):	2007, Capital Group Companies, Inc. 
// #                All Rights Reserved.
// ##########################################################################

package strata.foundation.value;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author 		
 *     AFS Strategic Initiative 5 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public class TimePointTest
{

	/************************************************************************
	 *  
	 *
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
	}

	/************************************************************************
	 *  
	 *
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}

	/**
	 * Test method for {@link strata1.common.datetime.TimePoint#TimePoint()}.
	 */
	@Test
	public void testTimePoint()
	{
		GregorianCalendar expected = new GregorianCalendar();
		TimePoint         actual   = new TimePoint();
		
		assertEquals( expected.get( Calendar.YEAR ),actual.getYear() );
		assertEquals( expected.get( Calendar.MONTH )+1,actual.getMonth() );
		assertEquals( expected.get( Calendar.DAY_OF_MONTH ),actual.getDay() );
		assertEquals( expected.get( Calendar.HOUR_OF_DAY ),actual.getHour() );
		assertEquals( expected.get( Calendar.MINUTE ),actual.getMinute() );
		assertEquals( expected.get( Calendar.SECOND ),actual.getSecond() );		
	}


	/**
	 * Test method for 
	 * {@link strata1.common.datetime.TimePoint#TimePoint(int, int, int)}.
	 */
	@Test
	public void testTimePointIntIntInt()
	{
		int year  = 1985;
		int month = 10;
		int day   = 22;
		
		TimePoint actual = new TimePoint( year,month,day );
		
		assertEquals( year,actual.getYear() );
		assertEquals( month,actual.getMonth() );
		assertEquals( day,actual.getDay() );
		assertEquals( 0,actual.getHour() );
		assertEquals( 0,actual.getMinute() );
		assertEquals( 0,actual.getSecond() );
		assertEquals( 0,actual.getMilliSecond() );
	}

	/**
	 * Test method for 
	 * {@link TimePoint#TimePoint(int, int, int, int, int)}.
	 */
	@Test
	public void testTimePointIntIntIntIntInt()
	{
		int      year    = 1975;
		int      month   = 4;
		int      day     = 30;
		int      hour    = 7;
		int      minute  = 37;
		TimePoint actual  = new TimePoint( year,month,day,hour,minute );
		
		assertEquals( year,actual.getYear() );
		assertEquals( month,actual.getMonth() );
		assertEquals( day,actual.getDay() );
		assertEquals( hour,actual.getHour() );
		assertEquals( minute,actual.getMinute() );
		assertEquals( 0,actual.getSecond() );
		assertEquals( 0,actual.getMilliSecond() );
	}

	/**
	 * Test method for 
	 * {@link TimePoint#TimePoint(int, int, int, int, int, int)}.
	 */
	@Test
	public void testTimePointIntIntIntIntIntInt()
	{
		int      year    = 1975;
		int      month   = 4;
		int      day     = 30;
		int      hour    = 7;
		int      minute  = 37;
		int      second  = 23;
		TimePoint actual  = new TimePoint( year,month,day,hour,minute,second );
		
		assertEquals( year,actual.getYear() );
		assertEquals( month,actual.getMonth() );
		assertEquals( day,actual.getDay() );
		assertEquals( hour,actual.getHour() );
		assertEquals( minute,actual.getMinute() );
		assertEquals( second,actual.getSecond() );
		assertEquals( 0,actual.getMilliSecond() );
	}

	/**
	 * Test method for 
	 * {@link TimePoint#TimePoint(int, int, int, int, int, int, int)}.
	 */
	@Test
	public void testTimePointIntIntIntIntIntIntInt()
	{
		int      year    = 1975;
		int      month   = 4;
		int      day     = 30;
		int      hour    = 7;
		int      minute  = 37;
		int      second  = 23;
		int      ms      = 235;
		TimePoint actual  = new TimePoint( year,month,day,hour,minute,second,ms );
		
		assertEquals( year,actual.getYear() );
		assertEquals( month,actual.getMonth() );
		assertEquals( day,actual.getDay() );
		assertEquals( hour,actual.getHour() );
		assertEquals( minute,actual.getMinute() );
		assertEquals( second,actual.getSecond() );
		assertEquals( ms,actual.getMilliSecond() );
	}


	/**
	 * Test method for  {@link TimePoint#TimePoint(TimePoint)}.
	 */
	@Test
	public void testTimePointTimePoint()
	{
		int      year    = 1975;
		int      month   = 4;
		int      day     = 30;
		int      hour    = 7;
		int      minute  = 37;
		int      second  = 23;
		int      ms      = 357;
		TimePoint expected  = 
		    new TimePoint( year,month,day,hour,minute,second,ms );
		TimePoint actual    = new TimePoint( expected );
		
		assertEquals( expected,actual );		
	}

	/**
	 * Test method for  {@link TimePoint#compareTo(TimePoint)}.
	 */
	@Test
	public void testCompareTo()
	{
		TimePoint d1  = new TimePoint( 2001,7,13 );
		TimePoint d2  = new TimePoint( 2002,1,10,7,25 );
		TimePoint d3  = new TimePoint();
		
		assertTrue( "d1 == d1",d1.compareTo( d1 ) == 0 );
		assertTrue( "d1 < d2",d1.compareTo( d2 ) < 0 );
		assertTrue( "d1 < d3",d1.compareTo( d3 ) < 0 );
		assertTrue( "d3 > d2",d3.compareTo( d2 ) > 0 );

	}

	/**
	 * Test method for {@link TimePoint#isSame(TimePoint)}.
	 */
	@Test
	public void testIsSame()
	{
		TimePoint d1  = new TimePoint( 2001,7,13 );
		TimePoint d2  = new TimePoint( 2002,1,1 );
		
		assertTrue( "d1.isSame(d1)",d1.isSame( d1 ) );
		assertFalse( "d1.isSame(d2)",d1.isSame( d2 ) );
	}

	/**
	 * Test method for {@link TimePoint#isBefore(TimePoint)}.
	 */
	@Test
	public void testIsBefore()
	{
		TimePoint d1  = new TimePoint( 2001,7,13 );
		TimePoint d2  = new TimePoint( 2002,1,1 );
		TimePoint d3  = new TimePoint();
		
		assertFalse( "d1.isBefore(d1)",d1.isBefore( d1 ) );
		assertTrue( "d1.isBefore(d2)",d1.isBefore( d2 ) );
		assertTrue( "d1.isBefore(d3)",d1.isBefore( d3 ) );
		assertFalse( "d3.isBefore(d2)",d3.isBefore( d2 ) );
	}

	/**
	 * Test method for {@link TimePoint#isAfter(TimePoint)}.
	 */
	@Test
	public void testIsAfter()
	{
		TimePoint d1  = new TimePoint( 2001,7,13 );
		TimePoint d2  = new TimePoint( 2002,1,1 );
		TimePoint d3  = new TimePoint();
		
		assertFalse( "d1.isAfter(d1)",d1.isAfter( d1 ) );
		assertFalse( "d1.isAfter(d2)",d1.isAfter( d2 ) );
		assertFalse( "d1.isAfter(d3)",d1.isAfter( d3 ) );
		assertTrue( "d3.isAfter(d2)",d3.isAfter( d2 ) );
	}

	/************************************************************************
	 *  
	 *
	 * @param from
	 * @param to
	 * @return
	 */
	private int
	getHourOffset(TimeZone from,TimeZone to)
	{
		final int MS_PER_HR = 1000 * 60 * 60;
		int       ms = from.getRawOffset() - to.getRawOffset();
		
		return ms / MS_PER_HR;
	}
}


// ##########################################################################
