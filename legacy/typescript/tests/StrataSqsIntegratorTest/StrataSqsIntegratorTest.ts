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
/// <reference path='lib/StrataIntegrator.ts'/>
/// <reference path='lib/StrataSqsIntegrator.ts'/>
/// <reference path='lib/aws-sdk.d.ts'/>
/// <reference path='lib/Oscar.ts'/>

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
{
    import MessagingSessionTest = Strata1.Integrator.Messaging.MessagingSessionTest;
    import IMessagingSession = Strata1.Integrator.Messaging.IMessagingSession;
    import AwsCredentials = AWS.Credentials;

    export
    class SqsMessagingSessionTest
        extends MessagingSessionTest
    {
        createSession(): IMessagingSession
        {
            var credentials: AwsCredentials = null;

            AWS.config.loadFromPath("C:\Users\John\.aws\credentials.json");
            AWS.config.getCredentials((err?: any) =>
            {
                if(err) throw err;
            });

            credentials = AWS.config.credentials;

            return new SqsQueueMessagingSession(credentials);
        }
    }

}

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
{
    import MessageSenderTest = Strata1.Integrator.Messaging.MessageSenderTest;
    import IMessagingSession = Strata1.Integrator.Messaging.IMessagingSession;
    import IMessageSender = Strata1.Integrator.Messaging.IMessageSender;
    import AwsCredentials = AWS.Credentials;

    export
    class SqsMessageSenderTest
        extends MessageSenderTest
    {
        createSession(): IMessagingSession
        {
            var credentials: AwsCredentials = null;

            AWS.config.loadFromPath("~/.aws/credentials");
            AWS.config.getCredentials((err?: any) =>
            {
                if(err) throw err;
            });

            credentials = AWS.config.credentials;

            return new SqsQueueMessagingSession(credentials);
        }
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.Suite
{
    import SqsMessagingSessionTest = Strata1.SqsIntegrator.SqsMessaging.SqsMessagingSessionTest;
    import SqsMessageSenderTest = Strata1.SqsIntegrator.SqsMessaging.SqsMessageSenderTest;

    export
    class CommitSuite
        extends TestSuite
    {
        constructor()
        {
            super();
            this.add(new SqsMessagingSessionTest());
            this.add(new SqsMessageSenderTest());
        }
    }
}

// ##########################################################################
