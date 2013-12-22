// ##########################################################################
// # File Name:		MindsetNavigationSuite.java
// # Copyright(C):	2007, Capital Group Companies, Inc. 
// #                All Rights Reserved.
// ##########################################################################

package strata1.injector.testsuite;

import strata1.injector.datetime.DateTimeTest;
import strata1.injector.money.CurrencyExchangerTest;
import strata1.injector.money.MoneyCalculatorTest;
import strata1.injector.money.MoneyTest;
import strata1.injector.utility.CopyableTest;
import strata1.injector.utility.SingletonProxyTest;
import strata1.injector.utility.SynchronizerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	DateTimeTest.class,
	MoneyTest.class,
	CurrencyExchangerTest.class,
	MoneyCalculatorTest.class,
	CopyableTest.class,
	SingletonProxyTest.class,
	SynchronizerTest.class})
public 
class CommonSuite {}

// ##########################################################################
