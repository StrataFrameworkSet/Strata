// ##########################################################################
// # File Name: CommitStageSuite.java
// #
// # Copyright: 2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:   This file is part of the StrataCommon Framework.
// #
// #            The StrataCommon Framework is free software: you 
// #            can redistribute it and/or modify it under the terms of 
// #            the GNU Lesser General Public License as published by
// #            the Free Software Foundation, either version 3 of the 
// #            License, or (at your option) any later version.
// #
// #            The StrataCommon Framework is distributed in the 
// #            hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #            without even the implied warranty of MERCHANTABILITY or 
// #            FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #            General Public License for more details.
// #
// #            You should have received a copy of the GNU Lesser 
// #            General Public License along with the StrataCommon
// #            Framework. If not, see http://www.gnu.org/licenses/.
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
class CommitStageSuite {}

// ##########################################################################
