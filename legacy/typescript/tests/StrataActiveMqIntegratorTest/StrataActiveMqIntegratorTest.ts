// ##########################################################################
// # File Name:	StrataActiveMqIntegratorTest.ts
// #
// # Copyright:	2016, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataIntegrator Framework.
// #
// #   			The StrataActiveMqIntegrator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataActiveMqIntegrator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataIntegrator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

/// <reference path='lib/StrataIntegratorTest.ts'/>
/// <reference path='lib/StrataActiveMqIntegrator.ts'/>
/// <reference path='lib/Oscar.ts'/>

/////////////////////////////////////////////////////////////////////////////

module Strata1.ActiveMqIntegrator.ActiveMqMessaging
{
    import MessagingSessionTest = Strata1.Integrator.Messaging.MessagingSessionTest;
    import IMessagingSession = Strata1.Integrator.Messaging.IMessagingSession;

    export
    class ActiveMqMessagingSessionTest
        extends MessagingSessionTest
    {
        createSession(): IMessagingSession
        {
            var url: string = "http://localhost:8080/StrataActiveMq/amq";

            return new ActiveMqMessagingSession(url,"test",true,0,60);
        }
    }

}

/////////////////////////////////////////////////////////////////////////////

module Strata1.ActiveMqIntegrator.ActiveMqMessaging
{
    import MessageSenderTest = Strata1.Integrator.Messaging.MessageSenderTest;
    import IMessagingSession = Strata1.Integrator.Messaging.IMessagingSession;
    import IMessageSender = Strata1.Integrator.Messaging.IMessageSender;

    export
    class ActiveMqMessageSenderTest
        extends MessageSenderTest
    {
        createSession(): IMessagingSession
        {
            var url: string = "http://localhost:8080/StrataActiveMq/amq";

            return new ActiveMqMessagingSession(url,"test",true,0,60);
        }
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.ActiveMqIntegrator.Suite
{
    import ActiveMqMessagingSessionTest = Strata1.ActiveMqIntegrator.ActiveMqMessaging.ActiveMqMessagingSessionTest;
    import ActiveMqMessageSenderTest = Strata1.ActiveMqIntegrator.ActiveMqMessaging.ActiveMqMessageSenderTest;

    export
    class CommitSuite
        extends TestSuite
    {
        constructor()
        {
            super();
            this.add(new ActiveMqMessagingSessionTest());
            this.add(new ActiveMqMessageSenderTest());
        }
    }
}

// ##########################################################################
