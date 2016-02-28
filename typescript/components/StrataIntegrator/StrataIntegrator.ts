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

/////////////////////////////////////////////////////////////////////////////

module Strata1.Integrator.Messaging
{
    export
    enum DeliveryMode
    {
        PERSISTENT,
        NON_PERSISTENT
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Integrator.Messaging
{
    export
    interface IMessage
    {
        setMessageId(messageId: string): IMessage;
        setCorrelationId(correlationId: string): IMessage;
        setReturnAddress(returnAddress: string): IMessage;
        setDeliveryMode(mode: DeliveryMode): IMessage;
        setBooleanProperty(name: string,value: boolean): IMessage;
        setNumberProperty(name: string,value: number): IMessage;
        setStringProperty(name: string,value: string): IMessage;

        getMessageId(): string;
        getCorrelationId(): string;
        getReturnAddress(): string;
        getDeliveryMode(): DeliveryMode;
        getBooleanProperty(name: string): boolean;
        getNumberProperty(name: string): number;
        getStringProperty(name: string): string;

        hasProperty(name: string): boolean;
    }

}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Integrator.Messaging
{
    export
    interface IStringMessage
        extends IMessage
    {
        setMessageId(messageId: string): IStringMessage;
        setCorrelationId(correlationId: string): IStringMessage;    
        setReturnAddress(returnAddress: string): IStringMessage;
        setDeliveryMode(mode: DeliveryMode): IStringMessage;
        setBooleanProperty(name: string,value: boolean): IStringMessage;
        setNumberProperty(name: string,value: number): IStringMessage;
        setStringProperty(name: string,value: string): IStringMessage;

        setPayload(payload: string): IStringMessage;
        getPayload(): string;
    }

}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Integrator.Messaging
{
    export
    interface IMapMessage
        extends IMessage
    {
        setMessageId(messageId: string): IMapMessage;
        setCorrelationId(correlationId: string): IMapMessage;
        setReturnAddress(returnAddress: string): IMapMessage;
        setDeliveryMode(mode: DeliveryMode): IMapMessage;
        setBooleanProperty(name: string,value: boolean): IMapMessage;
        setNumberProperty(name: string,value: number): IMapMessage;
        setStringProperty(name: string,value: string): IMapMessage;

        setBoolean(key: string,item: boolean): IMapMessage;
        setNumber(key: string,item: number): IMapMessage;
        setString(key: string,item: string): IMapMessage;

        getBoolean(key: string): boolean;
        getNumber(key: string): number;
        getString(key: string): string;
        getItemKeys(): string[];

        hasItem(key: string): boolean;
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Integrator.Messaging
{
    export
    interface IMessageListener
    {
        onMessage(message: IStringMessage): void;
        onMessage(message: IMapMessage): void;    
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Integrator.Messaging
{
    export
    interface IMessageSender
    {
        setTimeToLive(milliseconds: number): void;
    
        getSession(): IMessagingSession;  
        getTimeToLive(): number;
    
        send(message: IMessage): void;
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Integrator.Messaging
{
    export
        interface IMessageReceiver
    {
        setListener(listener: IMessageListener): IMessageReceiver;

        getSession(): IMessagingSession;
        getListener(): IMessageListener;
        getSelector(): string;

        startListening(): void;
        stopListening(): void;

        isListening(): boolean;

        receive(): IMessage;
        receive(timeOutInMs: number): IMessage;
        receiveNoWait(): IMessage

        close(): void;
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Integrator.Messaging
{
    export
    interface IMessagingSession
    {
        createMessageSender(id: string): IMessageSender;
        createMessageReceiver(id: string): IMessageReceiver;
        createMessageReceiver(id: string,selector: string): IMessageReceiver;
        createStringMessage(): IStringMessage;
        createMapMessage(): IMapMessage;
    
        startReceiving(): void;
        stopReceiving(): void;
        close(): void;
    
        isReceiving(): boolean;
        isClosed(): boolean;
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Integrator.Messaging
{
    export abstract
    class MessagingException
    {
        private message: string;

        constructor(message: string)
        {
            this.message = message;
        }

        getMessage(): string
        {
            return this.message;
        }
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Integrator.Messaging
{
    export
        class MixedModeException
        extends MessagingException
    {
        constructor(message: string)
        {
            super(message);
        }
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Integrator.Messaging
{
    export
    class NoMessageReceivedException
        extends MessagingException
    {
        constructor(message: string)
        {
            super(message);
        }
    }
}

// ##########################################################################
