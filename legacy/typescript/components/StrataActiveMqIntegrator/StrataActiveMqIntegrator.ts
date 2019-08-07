// ##########################################################################
// # File Name:	StrataIntegrator.ts
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

/// <reference path='lib/StrataIntegrator.ts'/>
/// <reference path='lib/jquery.d.ts'/>
/// <reference path='lib/amq.d.ts'/>

/////////////////////////////////////////////////////////////////////////////

module Strata1.ActiveMqIntegrator.ActiveMqMessaging
{
    import IMessage = Strata1.Integrator.Messaging.IMessage;
    import DeliveryMode = Strata1.Integrator.Messaging.DeliveryMode;

    export abstract
    class Message
        implements IMessage
    {
        private messageId: string;
        private correlationId: string;
        private returnAddress: string;
        private deliveryMode: DeliveryMode;
        private properties: { [name: string]: any };
         
        constructor()
        {
            this.messageId = null;
            this.correlationId = null;
            this.returnAddress = null;
            this.deliveryMode = null;
            this.properties = {};
        }

        setMessageId(messageId: string): IMessage
        {
            this.messageId = messageId;
            return this;
        }

        setCorrelationId(correlationId: string): IMessage
        {
            this.correlationId = correlationId;
            return this;
        }

        setReturnAddress(returnAddress: string): IMessage
        {
            this.returnAddress = returnAddress;
            return this;
        }

        setDeliveryMode(mode: DeliveryMode): IMessage
        {
            this.deliveryMode = mode;
            return this;
        }

        setBooleanProperty(name: string,value: boolean): IMessage
        {
            this.properties[name] = value;
            return this;
        }

        setNumberProperty(name: string,value: number): IMessage
        {
            this.properties[name] = value;
            return this;
        }

        setStringProperty(name: string,value: string): IMessage
        {
            this.properties[name] = value;
            return this;
        }

        getMessageId(): string
        {
            return this.messageId;
        }

        getCorrelationId(): string
        {
            return this.correlationId;
        }

        getReturnAddress(): string
        {
            return this.returnAddress;
        }

        getDeliveryMode(): DeliveryMode
        {
            return this.deliveryMode;
        }

        getBooleanProperty(name: string): boolean
        {
            return <boolean>this.properties[name];
        }

        getNumberProperty(name: string): number
        {
            return <number>this.properties[name];
        }

        getStringProperty(name: string): string
        {
            return <string>this.properties[name];
        }

        hasProperty(name: string): boolean
        {
            return
                this.properties[name] != undefined ||
                this.properties[name] != null;
        }

        abstract toXml(): string;
    }

}

/////////////////////////////////////////////////////////////////////////////

module Strata1.ActiveMqIntegrator.ActiveMqMessaging
{
    import IMessage = Strata1.Integrator.Messaging.IMessage;
    import IStringMessage = Strata1.Integrator.Messaging.IStringMessage;
    import DeliveryMode = Strata1.Integrator.Messaging.DeliveryMode;

    export
    class StringMessage
        extends    Message
        implements IStringMessage
    {
        private payload: string;

        constructor()
        {
            super();
            this.payload = null;
        }

        setMessageId(messageId: string): IStringMessage
        {
            super.setMessageId(messageId);
            return this;
        }

        setCorrelationId(correlationId: string): IStringMessage
        {
            super.setCorrelationId(correlationId);
            return this;
        }
            
        setReturnAddress(returnAddress: string): IStringMessage
        {
            super.setReturnAddress(returnAddress);
            return this;
        }

        setDeliveryMode(mode: DeliveryMode): IStringMessage
        {
            super.setDeliveryMode(mode);
            return this;
        }

        setBooleanProperty(name: string,value: boolean): IStringMessage
        {
            super.setBooleanProperty(name,value);
            return this;
        }

        setNumberProperty(name: string,value: number): IStringMessage
        {
            super.setNumberProperty(name,value);
            return this;
        }

        setStringProperty(name: string,value: string): IStringMessage
        {
            super.setStringProperty(name,value);
            return this;
        }

        setPayload(payload: string): IStringMessage
        {
            this.payload = payload;
            return this;
        }

        getPayload(): string
        {
            return this.payload;
        }

        toXml(): string
        {
            return this.getPayload();
        }
    }

}

/////////////////////////////////////////////////////////////////////////////

module Strata1.ActiveMqIntegrator.ActiveMqMessaging
{
    import IMessage = Strata1.Integrator.Messaging.IMessage;
    import IMapMessage = Strata1.Integrator.Messaging.IMapMessage;
    import DeliveryMode = Strata1.Integrator.Messaging.DeliveryMode;

    export
    class MapMessage
        extends Message
        implements IMapMessage
    {
        private payload: { [key: string]: any }

        constructor()
        {
            super();
            this.payload = {};
        }

        setMessageId(messageId: string): IMapMessage
        {
            super.setMessageId(messageId);
            return this;
        }

        setCorrelationId(correlationId: string): IMapMessage
        {
            super.setCorrelationId(correlationId);
            return this;
        }

        setReturnAddress(returnAddress: string): IMapMessage
        {
            super.setReturnAddress(returnAddress);
            return this;
        }

        setDeliveryMode(mode: DeliveryMode): IMapMessage
        {
            super.setDeliveryMode(mode);
            return this;
        }

        setBooleanProperty(name: string,value: boolean): IMapMessage
        {
            super.setBooleanProperty(name,value);
            return this;
        }

        setNumberProperty(name: string,value: number): IMapMessage
        {
            super.setNumberProperty(name,value);
            return this;
        }

        setStringProperty(name: string,value: string): IMapMessage
        {
            super.setStringProperty(name,value);
            return this;
        }

        setBoolean(key: string,value: boolean): IMapMessage
        {
            this.payload[key] = value;
            return this;
        }

        setNumber(key: string,value: number): IMapMessage
        {
            this.payload[key] = value;
            return this;
        }

        setString(key: string,value: string): IMapMessage
        {
            this.payload[key] = value;
            return this;
        }

        getBoolean(key: string): boolean
        {
            return <boolean>this.payload[key];
        }

        getNumber(key: string): number
        {
            return <number>this.payload[key];
        }

        getString(key: string): string
        {
            return <string>this.payload[key];
        }

        getItemKeys(): string[]
        {
            return Object.keys(this.payload);
        }

        hasItem(key: string): boolean
        {
            return
                this.payload[key] != undefined ||
                this.payload[key] != null;
        }

        toXml(): string
        {
            return "";
        }
    }

}

/////////////////////////////////////////////////////////////////////////////

module Strata1.ActiveMqIntegrator.ActiveMqMessaging
{
    import IMessage = Strata1.Integrator.Messaging.IMessage;
    import IMessageSender = Strata1.Integrator.Messaging.IMessageSender;
    import IMessagingSession = Strata1.Integrator.Messaging.IMessagingSession;

    export
    class ActiveMqMessageSender
        implements IMessageSender
    {
        private session: ActiveMqMessagingSession;
        private destination: string;
        private timeToLive: number;
        private senderImp: MessagingSystem;
        
        constructor(session: IMessagingSession,destination:string)
        {
            this.session = <ActiveMqMessagingSession>session;
            this.destination = destination;
            this.timeToLive = 0;
            this.senderImp = this.session.getImp();
        }

        setTimeToLive(milliseconds: number): void
        {
            this.timeToLive = milliseconds;
        }
    
        getSession(): IMessagingSession
        {
            return this.session;
        }
         
        getTimeToLive(): number
        {
            return this.timeToLive;
        }
    
        send(message: IMessage): void
        {
            var m: Message = <Message>message;

            this.senderImp.sendMessage(this.destination,m.toXml());
         }
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.ActiveMqIntegrator.ActiveMqMessaging
{
    import IMessage = Strata1.Integrator.Messaging.IMessage;
    import IStringMessage = Strata1.Integrator.Messaging.IStringMessage;
    import IMapMessage = Strata1.Integrator.Messaging.IMapMessage;
    import IMessageListener = Strata1.Integrator.Messaging.IMessageListener;
    import IMessageReceiver = Strata1.Integrator.Messaging.IMessageReceiver;
    import IMessagingSession = Strata1.Integrator.Messaging.IMessagingSession;
    import MessagingException = Strata1.Integrator.Messaging.MessagingException;
    import NoListenerException = Strata1.Integrator.Messaging.NoListenerException;
    import NoMessageReceivedException = Strata1.Integrator.Messaging.NoMessageReceivedException;

    export
    class ActiveMqMessageReceiver
        implements IMessageReceiver
    {
        private session: ActiveMqMessagingSession;
        private destination: string;
        private listener: IMessageListener;
        private selector: string;
        private listenerId: string;
        private listening: boolean;
        private receiverImp: MessagingSystem;

        constructor(session: IMessagingSession,destination: string,selector?:string)
        {
            this.session = <ActiveMqMessagingSession>session;
            this.destination = destination;
            this.listener = null;
            this.selector = selector == undefined ? null : selector;
            this.listenerId = Math.random().toString();
            this.listening = false;
            this.receiverImp = this.session.getImp();
        }

        setListener(listener: IMessageListener): IMessageReceiver
        {
            this.listener = listener;
            return this;
        }

        getSession(): IMessagingSession
        {
            return this.session;
        }

        getListener(): IMessageListener
        {
            return this.listener;
        }

        getSelector(): string
        {
            return this.selector;
        }

        startListening(): void
        {
            this.receiverImp.addListener(
                this.listenerId,
                this.destination,
                this.handler);
            this.listening = true;
        }

        stopListening(): void
        {
            this.receiverImp.removeListener(this.listenerId,this.destination);
            this.listening = false;
        }

        isListening(): boolean
        {
            return this.listening;
        }

        receive(timeOutInMs?: number): IMessage
        {
            var message: IMessage = null;

            return message;
        }

        receiveNoWait(): IMessage
        {
            var message: IMessage = null;

            return message;
        }

        close(): void
        {
        }

        handler(message:IStringMessage|IMapMessage): void
        {
            if(this.listener == null)
                throw new NoListenerException(
                        "Message receiver: " +
                        this.listenerId +
                        " does not have a listener");

            if(typeof message === 'IStringMessage')
                this.listener.onMessage(<IStringMessage>message);
            else if(typeof message === 'IMapMessage')
                this.listener.onMessage(<IMapMessage>message);               
        }
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.ActiveMqIntegrator.ActiveMqMessaging
{
    import IMessage = Strata1.Integrator.Messaging.IMessage;
    import IStringMessage = Strata1.Integrator.Messaging.IStringMessage;
    import IMapMessage = Strata1.Integrator.Messaging.IMapMessage;
    import IMessageSender = Strata1.Integrator.Messaging.IMessageSender;
    import IMessageReceiver = Strata1.Integrator.Messaging.IMessageReceiver;
    import IMessagingSession = Strata1.Integrator.Messaging.IMessagingSession;

    export
    class ActiveMqMessagingSession
        implements IMessagingSession
    {
        private uri: string;
        private sessionImp: MessagingSystem;
        private receiving: boolean;

        constructor(
            uri?: string,
            clientId?: string,
            logging?: boolean,
            pollDelay?: number,
            timeout?: number)
        {
            var options: MessagingSystemOptionList =
                {
                    uri: uri,
                    clientId: clientId,
                    logging: logging,
                    pollDelay: pollDelay,
                    timeout: timeout
                };
       
            this.uri = uri;
            this.sessionImp = org.activemq.Amq;

            this.sessionImp.init(options);
            this.receiving = false;
        }

        createMessageSender(id: string): IMessageSender
        {
            return new ActiveMqMessageSender(this,id);
        }

        createMessageReceiver(id: string,selector?: string): IMessageReceiver
        {
            return new ActiveMqMessageReceiver(this,id,selector);
        }

        createStringMessage(): IStringMessage
        {
            return new StringMessage();
        }

        createMapMessage(): IMapMessage
        {
            return new MapMessage();
        }
    
        startReceiving(): void
        {
            this.receiving = true;
        }

        stopReceiving(): void
        {
            this.receiving = false;
        }

        close(): void
        {
        }
    
        isReceiving(): boolean
        {
            return this.receiving;
        }

        isClosed(): boolean
        {
            return false;
        }

        getImp(): MessagingSystem
        {
            return this.sessionImp;
        }
    }
}

// ##########################################################################
