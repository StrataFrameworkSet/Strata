// ##########################################################################
// # File Name:	StrataIntegratorTest.ts
// #
// # Copyright:	2016, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataIntegrator Framework.
// #
// #   			The StrataIntegrator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataIntegrator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataIntegrator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

/// <reference path='lib/Oscar.ts'/>
/// <reference path='lib/StrataIntegrator.ts'/>

/////////////////////////////////////////////////////////////////////////////

module Strata1.Integrator.Messaging
{
    export abstract
    class MessagingSessionTest
        extends UnitTestClass
    {
        private target: IMessagingSession;

        setUp(): void
        {
            this.target = this.createSession();
            this.target.startReceiving();
        }

        tearDown(): void
        {
            this.target.stopReceiving();
            this.target.close();
            this.target = null;
        }

        testCreateMessageSender():void
        {
            var sender: IMessageSender = this.target.createMessageSender("channel://foo.test");

            Assert.isNotNull(sender);
            Assert.areEqual(this.target,sender.getSession());
        }

        abstract createSession(): IMessagingSession;
    }

}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Integrator.Messaging
{
    export abstract
        class MessageSenderTest
        extends UnitTestClass
    {
        private session: IMessagingSession;
        private target: IMessageSender;

        setUp(): void
        {
            this.session = this.createSession();
            this.target = this.session.createMessageSender("channel://foo.test");
        }

        tearDown(): void
        {
            this.target = null;
            this.session.close();
            this.session = null;
        }

        testSend1(): void
        {
            var message: IStringMessage =
                this.session.createStringMessage();

            message.setPayload("<message type='test'>Test Message 1</message>");
            this.target.send(message);
        }

        abstract createSession(): IMessagingSession;
    }
}

/////////////////////////////////////////////////////////////////////////////

/*
module Strata1.Integrator.Suite
{
    import MockChangeEventTest = Strata1.Client.Event.MockChangeEventTest;
    import MockModelTest = Strata1.Client.Model.MockModelTest;
    import MockControllerTest = Strata1.Client.Controller.MockControllerTest;

    export
        class CommitSuite
        extends TestSuite
    {
        constructor()
        {
            super();
            this.add(new MockChangeEventTest());
            this.add(new MockModelTest());
            this.add(new MockControllerTest());
        }
    }
}
*/

// ##########################################################################
