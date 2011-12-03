// ##########################################################################
// # File Name:		MoneyTest.java
// # Copyright(C):	2007, Capital Group Companies, Inc. 
// #                All Rights Reserved.
// ##########################################################################

package strata1.common.money;

import static org.junit.Assert.*;

import strata1.common.money.Money;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



import java.util.*;


/**
 * 
 * @author 		
 *     AFS Strategic Initiative 5 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public class MoneyTest
{
	private static final double DELTA = 0.00001;
	
	/************************************************************************
	 *  
	 *
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	/************************************************************************
	 *  
	 *
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

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
	 * Test method for {@link threshold.api.common.Money#hashCode()}.
	 */
	@Test
	public void testHashCode()
	{
		
	}

	/**
	 * Test method for {@link threshold.api.common.Money#Money()}.
	 */
	@Test
	public void testMoney()
	{
		Currency   usd  = Currency.getInstance( "USD" );
		Double     zero = new Double( "0.00" ); 
		
		Money m = new Money();
		
		assertSame( usd,m.getCurrency() );
		assertEquals( zero,m.getAmount(),DELTA );
	}

	/**
	 * Test method for {@link threshold.api.common.Money#Money(double)}.
	 */
	@Test
	public void testMoneyDouble()
	{
		Currency   usd  = Currency.getInstance( "USD" );
		double     amount = 3.57001; 
		
		Money m = new Money( amount );
		
		assertSame( usd,m.getCurrency() );
		assertEquals( amount,m.getAmount(),DELTA );
	}

	/**
	 * Test method for {@link threshold.api.common.Money#Money(java.util.Currency, double)}.
	 */
	@Test
	public void testMoneyCurrencyDouble()
	{
		Currency   jpy  = Currency.getInstance( "JPY" );
		double     amount = 3.57001; 
		
		Money m = new Money( jpy,amount );
		
		assertSame( jpy,m.getCurrency() );
		assertEquals( amount,m.getAmount(),DELTA );
	}

	/**
	 * Test method for {@link threshold.api.common.Money#Money(long)}.
	 */
	@Test
	public void testMoneyLong()
	{
		Currency usd  = Currency.getInstance( "USD" );
		long     amount = 9999999; 
		
		Money m = new Money( amount );
		
		assertSame( usd,m.getCurrency() );
		assertEquals( amount,(long)m.getAmount() );
	}

	/**
	 * Test method for {@link threshold.api.common.Money#Money(java.util.Currency, long)}.
	 */
	@Test
	public void testMoneyCurrencyLong()
	{
		Currency hkd  = Currency.getInstance( "HKD" );
		long     amount = 9999999; 
		
		Money m = new Money( hkd,amount );
		
		assertSame( hkd,m.getCurrency() );
		assertEquals( amount,(long)m.getAmount() );
	}

	/**
	 * Test method for {@link threshold.api.common.Money#getCurrency()}.
	 */
	@Test
	public void testGetCurrency()
	{
		Currency c = Currency.getInstance( "NZD" );
		Money    m = new Money( c );
		
		assertSame( c,m.getCurrency() );
	}

	/**
	 * Test method for {@link threshold.api.common.Money#getAmount()}.
	 */
	@Test
	public void testGetAmount()
	{
		Double zero = new Double( "0.00" ); 
		Double hundred = new Double( "100.00" );
		
		Money m1 = new Money();
		Money m2 = new Money( hundred );
		
		assertEquals( zero,m1.getAmount(),0.000000001 );
		assertEquals( hundred,m2.getAmount(),0.000000001 );
	}

	/**
	 * Test method for {@link threshold.api.common.Money#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject()
	{
		Object m1 = new Money( new Double( "5555.555" ) );
		Object m2 = new Money();
		Object m3 = new Money( Currency.getInstance( "AUD" ) );
		Object m4 = new Money( Currency.getInstance( "USD" ),5555.555 );
		
		assertFalse( "should be: m1 != m1",m1.equals( m2 ) );
		assertFalse( "should be: m2 != m3",m2.equals( m3 ) );
		assertTrue( "should be: m1 == m1",m1.equals( m1 ) );
		assertTrue( "should be: m1 == m4",m1.equals( m4 ) );
		assertEquals( m1,m1 );
		assertEquals( m1,m4 );
	}

	/**
	 * Test method for {@link threshold.api.common.Money#toString()}.
	 */
	@Test
	public void testToString()
	{
		Money m1 = new Money( 5555.555 );
		Money m2 = new Money();
		Money m3 = new Money( Currency.getInstance( "AUD" ) );
		Money m4 = new Money( Currency.getInstance( "EUR" ),5555.555 );
		
		assertEquals( "$5555.56",m1.toString() );
		assertEquals( "$0.00",m2.toString() );
		assertEquals( "AUD0.00",m3.toString() );
		assertEquals( "EUR5555.56",m4.toString() );
		
	}

	/**
	 * Test method for {@link threshold.api.common.Money#equals(threshold.api.common.Money)}.
	 */
	@Test
	public void testEqualsMoney()
	{
		Money m1 = new Money( new Double( "5555.555" ) );
		Money m2 = new Money();
		Money m3 = new Money( Currency.getInstance( "AUD" ) );
		Money m4 = new Money( Currency.getInstance( "USD" ),5555.555 );
		
		assertFalse( "should be: m1 != m1",m1.equals( m2 ) );
		assertFalse( "should be: m2 != m3",m2.equals( m3 ) );
		assertTrue( "should be: m1 == m1",m1.equals( m1 ) );
		assertTrue( "should be: m1 == m4",m1.equals( m4 ) );
		assertEquals( m1,m1 );
		assertEquals( m1,m4 );
	}

}


// ##########################################################################
