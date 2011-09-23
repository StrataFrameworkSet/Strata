// ##########################################################################
// # File Name:		MoneyCalculatorTest.java
// # Copyright(C):	2007, Capital Group Companies, Inc. 
// #                All Rights Reserved.
// ##########################################################################

package strata1.common.money;

import static org.junit.Assert.*;

import strata1.common.money.CurrencyExchanger;
import strata1.common.money.Money;
import strata1.common.money.MoneyCalculator;
import org.junit.*;



import java.util.*;

/**
 * 
 * @author 		
 *     AFS Strategic Initiative 5 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public class MoneyCalculatorTest
{
	private static final double TOLERANCE = 0.00001;
	
	private CurrencyExchanger itsExchanger;
	private MoneyCalculator   itsTarget;
	
	/************************************************************************
	 *  
	 *
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		itsExchanger = new MockCurrencyExchanger();
		itsTarget    = new MoneyCalculator( itsExchanger );
	}

	/************************************************************************
	 *  
	 *
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		itsTarget    = null;
		itsExchanger = null;
	}

	/**
	 * Test method for {@link strata1.common.money.MoneyCalculator#MoneyCalculator(strata1.common.money.CurrencyExchanger)}.
	 */
	@Test
	public void testMoneyCalculator()
	{
		CurrencyExchanger e = new MockCurrencyExchanger();
		MoneyCalculator   c = new MoneyCalculator( e );
		
		assertSame( e,c.getExchanger() );
	}

	/**
	 * Test method for {@link threshold.api.common.MoneyCalculator#getExchanger()}.
	 */
	@Test
	public void testGetExchanger()
	{
		assertSame( itsExchanger,itsTarget.getExchanger() );
	}

	/**
	 * Test method for {@link threshold.api.common.MoneyCalculator#add(threshold.api.common.Money, threshold.api.common.Money)}.
	 */
	@Test
	public void testAdd()
	{
		Currency usd = Currency.getInstance( "USD" );
		Currency eur = Currency.getInstance( "EUR" );
		
		Money    m1 = new Money( usd,7500.50 );
		Money    m2 = new Money( usd,2500.25 );
		Money    m3 = new Money( usd,100.00 );
		Money    m4 = new Money( eur,100.00 );
		
		assertEquals( 10000.75,itsTarget.add( m1,m2 ).getAmount(),TOLERANCE );
		assertEquals( 225.00,itsTarget.add( m3,m4 ).getAmount(),TOLERANCE );
	}

	/**
	 * Test method for {@link threshold.api.common.MoneyCalculator#subtract(threshold.api.common.Money, threshold.api.common.Money)}.
	 */
	@Test
	public void testSubtract()
	{
		Currency usd = Currency.getInstance( "USD" );
		Currency eur = Currency.getInstance( "EUR" );
		
		Money    m1 = new Money( usd,7500.50 );
		Money    m2 = new Money( usd,2500.25 );
		Money    m3 = new Money( usd,50.00 );
		Money    m4 = new Money( eur,25.00 );
		
		assertEquals( 5000.25,itsTarget.subtract( m1,m2 ).getAmount(),TOLERANCE );
		assertEquals( 18.75,itsTarget.subtract( m3,m4 ).getAmount(),TOLERANCE );
	}

	/**
	 * Test method for {@link threshold.api.common.MoneyCalculator#multiply(threshold.api.common.Money, double)}.
	 */
	@Test
	public void testMultiply()
	{
		Currency usd = Currency.getInstance( "USD" );
		
		Money m1 = new Money( usd,7500.50 );
		
		assertEquals( 7500.50*2.0,itsTarget.multiply(m1,2).getAmount(),TOLERANCE );
		assertEquals( 7500.50*3.5,itsTarget.multiply(m1,3.5).getAmount(),TOLERANCE );
	}

	/**
	 * Test method for {@link threshold.api.common.MoneyCalculator#divide(threshold.api.common.Money, double)}.
	 */
	@Test
	public void testDivide()
	{
		Currency usd = Currency.getInstance( "USD" );
		
		Money m1 = new Money( usd,7500.50 );
		
		assertEquals( 7500.50/2.0,itsTarget.divide(m1,2).getAmount(),TOLERANCE );
		assertEquals( 7500.50/3.333,itsTarget.divide(m1,3.333).getAmount(),TOLERANCE );
	}

	/**
	 * Test method for {@link threshold.api.common.MoneyCalculator#isEqual(threshold.api.common.Money, threshold.api.common.Money)}.
	 */
	@Test
	public void testIsEqual()
	{
		Currency usd = Currency.getInstance( "USD" );
		Currency cad = Currency.getInstance( "CAD" );
		
		Money    m1 = new Money( usd,0.50 );
		Money    m2 = new Money( cad,1.00 );
		Money    m3 = new Money( cad,100.00 );
		
		assertTrue( "should be: m1 == m2",itsTarget.isEqual( m1,m2 ) );
		assertFalse( "should be: m1 != m3",itsTarget.isEqual( m1,m3 ) );
	}

	/**
	 * Test method for {@link threshold.api.common.MoneyCalculator#isLess(threshold.api.common.Money, threshold.api.common.Money)}.
	 */
	@Test
	public void testIsLess()
	{
		Currency eur = Currency.getInstance( "EUR" );
		Currency cad = Currency.getInstance( "CAD" );
		
		Money    m1 = new Money( eur,0.45 );
		Money    m2 = new Money( cad,2.00 );
		
		assertTrue( "should be: m1 < m2",itsTarget.isLess( m1,m2 ) );
		assertFalse( "should be: m2 > m1",itsTarget.isLess( m2,m1 ) );
	}
}


// ##########################################################################
