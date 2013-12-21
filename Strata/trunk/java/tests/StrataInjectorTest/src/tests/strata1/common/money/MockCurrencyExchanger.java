// ##########################################################################
// # File Name:		MockCurrencyExchanger.java
// # Copyright(C):	2007, Capital Group Companies, Inc. 
// #                All Rights Reserved.
// ##########################################################################

package strata1.common.money;

import strata1.common.datetime.DateTime;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author 		
 *     AFS Strategic Initiative 5 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
class MockCurrencyExchanger 
	extends AbstractCurrencyExchanger
{
	private Map<Currency,Double> itsExchangeToUsdA;
	private Map<Currency,Double> itsExchangeToUsdB;

	/************************************************************************
	 * Creates a new MockCurrencyExchanger. 
	 *
	 */
	public MockCurrencyExchanger()
	{
		itsExchangeToUsdA = new HashMap<Currency,Double>();
		itsExchangeToUsdB = new HashMap<Currency,Double>();
		
		itsExchangeToUsdA.put( 
			Currency.getInstance( "USD" ),new Double( 1.00 ) );
		itsExchangeToUsdA.put( 
			Currency.getInstance( "EUR" ),new Double( 1.25 ) );
		itsExchangeToUsdA.put( 
			Currency.getInstance( "GBP" ),new Double( "1.55" ) );
		itsExchangeToUsdA.put( 
			Currency.getInstance( "CAD" ),new Double( "0.50" ) );
		itsExchangeToUsdA.put( 
			Currency.getInstance( "AUD" ),new Double( "0.750000" ) );
		itsExchangeToUsdA.put( 
			Currency.getInstance( "CNY" ),new Double( "0.100000" ) );
		itsExchangeToUsdA.put( 
			Currency.getInstance( "NZD" ),new Double( "0.550000" ) );
		itsExchangeToUsdA.put( 
			Currency.getInstance( "HKD" ),new Double( "0.350000" ) );
		itsExchangeToUsdA.put( 
			Currency.getInstance( "JPY" ),new Double( "0.015000" ) );
		itsExchangeToUsdA.put( 
			Currency.getInstance( "CHF" ),new Double( "0.750000" ) );
			
		itsExchangeToUsdB.put( 
			Currency.getInstance( "USD" ),new Double( "1.0" ) );
		itsExchangeToUsdB.put( 
			Currency.getInstance( "EUR" ),new Double( "1.083520" ) );
		itsExchangeToUsdB.put( 
			Currency.getInstance( "GBP" ),new Double( "1.648050" ) );
		itsExchangeToUsdB.put( 
			Currency.getInstance( "CAD" ),new Double( "0.653088" ) );
		itsExchangeToUsdB.put( 
			Currency.getInstance( "AUD" ),new Double( "0.586822" ) );
		itsExchangeToUsdB.put( 
			Currency.getInstance( "CNY" ),new Double( "0.120809" ) );
		itsExchangeToUsdB.put( 
			Currency.getInstance( "NZD" ),new Double( "0.543330" ) );
		itsExchangeToUsdB.put( 
			Currency.getInstance( "HKD" ),new Double( "0.128206" ) );
		itsExchangeToUsdB.put( 
			Currency.getInstance( "JPY" ),new Double( "0.010000" ) );
		itsExchangeToUsdB.put( 
			Currency.getInstance( "CHF" ),new Double( "0.731796" ) );
	}

	
	
	/************************************************************************
	 * {@inheritDoc} 
	 * @see ICurrencyExchanger#getSupportedCurrencies()
	 */
	@Override
	public Collection<Currency> 
	getSupportedCurrencies()
	{
		return itsExchangeToUsdA.keySet();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractCurrencyExchanger#getExchangeRate(
	 * 			Currency,Currency,DateTime)
	 */
	@Override
	protected double 
	getExchangeRate(Currency to,Currency from,DateTime when)
	{
		double source = 0.0;
		double dest   = 0.0;
		
		if ( when.getYear() >= new DateTime().getYear() )
		{
			source = itsExchangeToUsdA.get( from );
			dest   = itsExchangeToUsdA.get( to );
		}
		else
		{
			source = itsExchangeToUsdB.get( from );
			dest   = itsExchangeToUsdB.get( to );
		}
			
		return source / dest;
	}

}


// ##########################################################################
