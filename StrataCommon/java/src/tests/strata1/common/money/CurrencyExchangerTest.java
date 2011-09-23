// ##########################################################################
// # File Name:		CurrencyExchangerTest.java
// # Copyright(C):	2007, Capital Group Companies, Inc. 
// #                All Rights Reserved.
// ##########################################################################

package strata1.common.money;

import static org.junit.Assert.*;

import strata1.common.datetime.DateTime;
import strata1.common.money.CurrencyExchanger;
import strata1.common.money.Money;
import org.junit.*;

import java.util.*;

/**
 * 
 * @author 		
 *     AFS Strategic Initiative 5 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CurrencyExchangerTest
{
	private CurrencyExchanger itsTarget;
		
	/************************************************************************
	 *  
	 *
	 * @throws java.lang.Exception
	 */
	@Before
	public void 
	setUp() throws Exception
	{
		itsTarget = new MockCurrencyExchanger();
	}

	/************************************************************************
	 *  
	 *
	 * @throws java.lang.Exception
	 */
	@After
	public void 
	tearDown() throws Exception
	{
		itsTarget = null;
	}

	/**
	 * Test method for {@link stata1.common.money.CurrencyExchanger#exchange(java.util.Currency, strata1.common.money.Money)}.
	 */
	@Test
	public void 
	testExchangeCurrencyMoney()
	{
		Currency usd = Currency.getInstance( "USD" );
		Money    e1  = new Money( usd,1.00 );
		Money    e2  = new Money( usd,9581961.22 );
		
		for (Currency c : itsTarget.getSupportedCurrencies())
		{
			Money a1 = itsTarget.exchange( usd,itsTarget.exchange( c,e1 ) );
			Money a2 = itsTarget.exchange( usd,itsTarget.exchange( c,e2 ) );
		
			assertEquals( e1.getAmount(),a1.getAmount(),0.00001 );
			assertEquals( e2.getAmount(),a2.getAmount(),0.00001 );
		}
		
		
	}

	/**
	 * Test method for {@link threshold.api.common.CurrencyExchanger#exchange(java.util.Currency, threshold.api.common.Money, threshold.api.common.DateTime)}.
	 */
	@Test
	public void 
	testExchangeCurrencyMoneyDateTime()
	{
		DateTime d   = new DateTime( 1995,1,1 );
		Currency usd = Currency.getInstance( "USD" );
		Money    e1  = new Money( usd,1.00 );
		Money    e2  = new Money( usd,9581961.22 );
		
		for (Currency c : itsTarget.getSupportedCurrencies())
		{
			Money a1 = itsTarget.exchange( usd,itsTarget.exchange( c,e1,d ),d );
			Money a2 = itsTarget.exchange( usd,itsTarget.exchange( c,e2,d ),d );
		
			assertEquals( e1.getAmount(),a1.getAmount(),0.00001 );
			assertEquals( e2.getAmount(),a2.getAmount(),0.00001 );
		}
	}
}


// ##########################################################################
