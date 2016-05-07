// ##########################################################################
// # File Name:		DateTimeTest.java
// # Copyright(C):	2007, Capital Group Companies, Inc. 
// #                All Rights Reserved.
// ##########################################################################

package strata1.common.datetime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 
 * @author 		
 *     AFS Strategic Initiative 5 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public class DateTimeTest
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
	 * Test method for {@link strata1.common.datetime.DateTime#DateTime()}.
	 */
	@Test
	public void testDateTime()
	{
		GregorianCalendar expected = new GregorianCalendar();
		DateTime          actual   = new DateTime();
		
		assertEquals( expected.get( Calendar.YEAR ),actual.getYear() );
		assertEquals( expected.get( Calendar.MONTH )+1,actual.getMonth() );
		assertEquals( expected.get( Calendar.DAY_OF_MONTH ),actual.getDay() );
		assertEquals( expected.get( Calendar.HOUR_OF_DAY ),actual.getHour() );
		assertEquals( expected.get( Calendar.MINUTE ),actual.getMinute() );
		assertEquals( expected.get( Calendar.SECOND ),actual.getSecond() );		
	}

	/**
	 * Test method for 
	 * {@link strata1.common.datetime.DateTime#DateTime(java.util.Calendar)}.
	 */
	@Test
	public void testDateTimeCalendar()
	{
		GregorianCalendar expected = new GregorianCalendar();
		DateTime          actual   = new DateTime( expected );
		
		assertEquals( expected,actual.getCalendar() );
	}

	/**
	 * Test method for 
	 * {@link strata1.common.datetime.DateTime#DateTime(java.util.TimeZone)}.
	 */
	@Test
	public void testDateTimeTimeZone()
	{
		TimeZone          eastern  = TimeZone.getTimeZone( "US/Eastern" );
		GregorianCalendar expected = new GregorianCalendar( eastern );
		DateTime          actual   = new DateTime( eastern );
		
		assertEquals( expected.get( Calendar.YEAR ),actual.getYear() );
		assertEquals( expected.get( Calendar.MONTH )+1,actual.getMonth() );
		assertEquals( expected.get( Calendar.DAY_OF_MONTH ),actual.getDay() );
		assertEquals( expected.get( Calendar.HOUR_OF_DAY ),actual.getHour() );
		assertEquals( expected.get( Calendar.MINUTE ),actual.getMinute() );
		assertEquals( expected.get( Calendar.SECOND ),actual.getSecond() );	
		assertEquals( expected.getTimeZone(),actual.getTimeZone() );	
	}

	/**
	 * Test method for 
	 * {@link strata1.common.datetime.DateTime#DateTime(int, int, int)}.
	 */
	@Test
	public void testDateTimeIntIntInt()
	{
		int year  = 1985;
		int month = 10;
		int day   = 22;
		
		DateTime actual = new DateTime( year,month,day );
		
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
	 * {@link DateTime#DateTime(int, int, int, TimeZone)}.
	 */
	@Test
	public void testDateTimeIntIntIntTimeZone()
	{
		int      year    = 1975;
		int      month   = 4;
		int      day     = 30;
		TimeZone eastern = TimeZone.getTimeZone( "US/Eastern" );
		DateTime actual  = new DateTime( year,month,day,eastern );
		
		assertEquals( year,actual.getYear() );
		assertEquals( month,actual.getMonth() );
		assertEquals( day,actual.getDay() );
		assertEquals( 0,actual.getHour() );
		assertEquals( 0,actual.getMinute() );
		assertEquals( 0,actual.getSecond() );
		assertEquals( 0,actual.getMilliSecond() );
		assertEquals( eastern,actual.getTimeZone() );
	}

	/**
	 * Test method for 
	 * {@link DateTime#DateTime(int, int, int, int, int)}.
	 */
	@Test
	public void testDateTimeIntIntIntIntInt()
	{
		int      year    = 1975;
		int      month   = 4;
		int      day     = 30;
		int      hour    = 7;
		int      minute  = 37;
		DateTime actual  = new DateTime( year,month,day,hour,minute );
		
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
	 * {@link DateTime#DateTime(int, int, int, int, int,TimeZone)}.
	 */
	@Test
	public void testDateTimeIntIntIntIntIntTimeZone()
	{
		int      year    = 1975;
		int      month   = 4;
		int      day     = 30;
		int      hour    = 7;
		int      minute  = 37;
		TimeZone eastern = TimeZone.getTimeZone( "US/Eastern" );
		DateTime actual  = new DateTime( year,month,day,hour,minute,eastern );
		
		assertEquals( year,actual.getYear() );
		assertEquals( month,actual.getMonth() );
		assertEquals( day,actual.getDay() );
		assertEquals( hour,actual.getHour() );
		assertEquals( minute,actual.getMinute() );
		assertEquals( 0,actual.getSecond() );
		assertEquals( 0,actual.getMilliSecond() );
		assertEquals( eastern,actual.getTimeZone() );
	}

	/**
	 * Test method for 
	 * {@link DateTime#DateTime(int, int, int, int, int, int)}.
	 */
	@Test
	public void testDateTimeIntIntIntIntIntInt()
	{
		int      year    = 1975;
		int      month   = 4;
		int      day     = 30;
		int      hour    = 7;
		int      minute  = 37;
		int      second  = 23;
		DateTime actual  = new DateTime( year,month,day,hour,minute,second );
		
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
	 * {@link DateTime#DateTime(int, int, int, int, int, int, TimeZone)}.
	 */
	@Test
	public void testDateTimeIntIntIntIntIntIntTimeZone()
	{
		int      year    = 1975;
		int      month   = 4;
		int      day     = 30;
		int      hour    = 7;
		int      minute  = 37;
		int      second  = 23;
		TimeZone eastern = TimeZone.getTimeZone( "US/Eastern" );
		DateTime actual  = 
		    new DateTime( year,month,day,hour,minute,second,eastern );
		
		assertEquals( year,actual.getYear() );
		assertEquals( month,actual.getMonth() );
		assertEquals( day,actual.getDay() );
		assertEquals( hour,actual.getHour() );
		assertEquals( minute,actual.getMinute() );
		assertEquals( second,actual.getSecond() );
		assertEquals( 0,actual.getMilliSecond() );
		assertEquals( eastern,actual.getTimeZone() );
	}

	/**
	 * Test method for 
	 * {@link DateTime#DateTime(int, int, int, int, int, int, int)}.
	 */
	@Test
	public void testDateTimeIntIntIntIntIntIntInt()
	{
		int      year    = 1975;
		int      month   = 4;
		int      day     = 30;
		int      hour    = 7;
		int      minute  = 37;
		int      second  = 23;
		int      ms      = 235;
		DateTime actual  = new DateTime( year,month,day,hour,minute,second,ms );
		
		assertEquals( year,actual.getYear() );
		assertEquals( month,actual.getMonth() );
		assertEquals( day,actual.getDay() );
		assertEquals( hour,actual.getHour() );
		assertEquals( minute,actual.getMinute() );
		assertEquals( second,actual.getSecond() );
		assertEquals( ms,actual.getMilliSecond() );
	}

	/**
	 * Test method for 
	 * {@link DateTime#DateTime(int, int, int, int, int, int, int, TimeZone)}.
	 */
	@Test
	public void testDateTimeIntIntIntIntIntIntIntTimeZone()
	{
		int      year    = 1975;
		int      month   = 4;
		int      day     = 30;
		int      hour    = 7;
		int      minute  = 37;
		int      second  = 23;
		int      ms      = 357;
		TimeZone eastern = TimeZone.getTimeZone( "US/Eastern" );
		DateTime actual  = 
		    new DateTime( year,month,day,hour,minute,second,ms,eastern );
		
		assertEquals( year,actual.getYear() );
		assertEquals( month,actual.getMonth() );
		assertEquals( day,actual.getDay() );
		assertEquals( hour,actual.getHour() );
		assertEquals( minute,actual.getMinute() );
		assertEquals( second,actual.getSecond() );
		assertEquals( ms,actual.getMilliSecond() );
		assertEquals( eastern,actual.getTimeZone() );
	}

	/**
	 * Test method for  {@link DateTime#DateTime(DateTime)}.
	 */
	@Test
	public void testDateTimeDateTime()
	{
		int      year    = 1975;
		int      month   = 4;
		int      day     = 30;
		int      hour    = 7;
		int      minute  = 37;
		int      second  = 23;
		int      ms      = 357;
		TimeZone eastern = TimeZone.getTimeZone( "US/Eastern" );
		DateTime expected  = 
		    new DateTime( year,month,day,hour,minute,second,ms,eastern );
		DateTime actual    = new DateTime( expected );
		
		assertEquals( expected,actual );		
	}

	/**
	 * Test method for  {@link DateTime#compareTo(DateTime)}.
	 */
	@Test
	public void testCompareTo()
	{
		TimeZone eastern = TimeZone.getTimeZone( "US/Eastern" );
		DateTime d1  = new DateTime( 2001,7,13 );
		DateTime d2  = new DateTime( 2002,1,10,7,25 );
		DateTime d3  = new DateTime();
		DateTime d4  = d2.changeTimeZone( eastern );
		
		assertTrue( "d1 == d1",d1.compareTo( d1 ) == 0 );
		assertTrue( "d1 < d2",d1.compareTo( d2 ) < 0 );
		assertTrue( "d1 < d3",d1.compareTo( d3 ) < 0 );
		assertTrue( "d3 > d2",d3.compareTo( d2 ) > 0 );
		assertTrue( "d2 == d4",d2.compareTo( d4 ) == 0 );

		assertEquals( "year:",d2.getYear(),d4.getYear() );
		assertEquals( "month:",d2.getMonth(),d4.getMonth() );
		assertEquals( "day:",d2.getDay(),d4.getDay() );
		assertEquals( 
		    "hour:",
		    d2.getHour()+getHourOffset( 
		        d4.getTimeZone(),d2.getTimeZone()),d4.getHour() );
		assertEquals( "minute:",d2.getMinute(),d4.getMinute() );
		assertEquals( "second:",d2.getSecond(),d4.getSecond() );
		assertEquals( "millisecond:",d2.getMilliSecond(),d4.getMilliSecond() );
	}

	/**
	 * Test method for {@link DateTime#isSame(DateTime)}.
	 */
	@Test
	public void testIsSame()
	{
		TimeZone eastern = TimeZone.getTimeZone( "US/Eastern" );
		DateTime d1  = new DateTime( 2001,7,13 );
		DateTime d2  = new DateTime( 2002,1,1 );
		DateTime d3  = d2.changeTimeZone( eastern );
		
		assertTrue( "d1.isSame(d1)",d1.isSame( d1 ) );
		assertFalse( "d1.isSame(d2)",d1.isSame( d2 ) );
		assertTrue( "d2.isSame(d3)",d2.isSame( d3 ) );
	}

	/**
	 * Test method for {@link DateTime#isBefore(DateTime)}.
	 */
	@Test
	public void testIsBefore()
	{
		TimeZone eastern = TimeZone.getTimeZone( "US/Eastern" );
		DateTime d1  = new DateTime( 2001,7,13 );
		DateTime d2  = new DateTime( 2002,1,1 );
		DateTime d3  = new DateTime();
		DateTime d4  = d2.changeTimeZone( eastern );
		
		assertFalse( "d1.isBefore(d1)",d1.isBefore( d1 ) );
		assertTrue( "d1.isBefore(d2)",d1.isBefore( d2 ) );
		assertTrue( "d1.isBefore(d3)",d1.isBefore( d3 ) );
		assertFalse( "d3.isBefore(d2)",d3.isBefore( d2 ) );
		assertFalse( "d2.isBefore(d4)",d2.isBefore( d4 ) );
	}

	/**
	 * Test method for {@link DateTime#isAfter(DateTime)}.
	 */
	@Test
	public void testIsAfter()
	{
		TimeZone eastern = TimeZone.getTimeZone( "US/Eastern" );
		DateTime d1  = new DateTime( 2001,7,13 );
		DateTime d2  = new DateTime( 2002,1,1 );
		DateTime d3  = new DateTime();
		DateTime d4  = d2.changeTimeZone( eastern );
		
		assertFalse( "d1.isAfter(d1)",d1.isAfter( d1 ) );
		assertFalse( "d1.isAfter(d2)",d1.isAfter( d2 ) );
		assertFalse( "d1.isAfter(d3)",d1.isAfter( d3 ) );
		assertTrue( "d3.isAfter(d2)",d3.isAfter( d2 ) );
		assertFalse( "d2.isAfter(d4)",d2.isAfter( d4 ) );
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
