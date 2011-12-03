// ##########################################################################
// # File Name:		MindsetNavigationSuite.java
// # Copyright(C):	2007, Capital Group Companies, Inc. 
// #                All Rights Reserved.
// ##########################################################################

package strata1.common.testsuite;

import strata1.common.datetime.DateTimeTest;
import strata1.common.money.CurrencyExchangerTest;
import strata1.common.money.MoneyCalculatorTest;
import strata1.common.money.MoneyTest;
import strata1.common.utility.CopyableTest;
import strata1.common.utility.SingletonProxyTest;
import strata1.common.utility.SynchronizerTest;
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
