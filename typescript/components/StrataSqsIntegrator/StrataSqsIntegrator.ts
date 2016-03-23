// ##########################################################################
// # File Name:	StrataSqsIntegrator.ts
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
/// <reference path='lib/aws-sdk.d.ts'/>

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
{
    export
    enum AcknowledgementMode
    {
        CLIENT_ACKNOWLEDGEMENT,
        AUTO_ACKNOWLEDGEMENT
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
{
    export
    enum PayloadType
    {
        STRING,
        MAP,
        OBJECT
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
{
    export
    class Holder<T>
    {
        private value: T;

        constructor(value: T)
        {
            this.value = value;
        }

        setValue(value: T): void
        {
            this.value = value;
        }

        getValue(): T
        {
            return this.value;
        }
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
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
    import Message = AWS.SQS.Message;
    import MessageAttribute = AWS.SQS.MessageAttribute;
    import SqsOptions = AWS.SQS.SqsOptions;
    import AwsCredentials = AWS.Credentials;
    import AmazonSqsClient = AWS.SQS;
    import QueueAttributes = AWS.SQS.QueueAttributes;
    import SetQueueAttributesRequest = AWS.SQS.SetQueueAttributesParams;
    import GetQueueAttributesRequest = AWS.SQS.GetQueueAttributesParams;
    import GetQueueAttributesReply = AWS.SQS.GetQueueAttributesResult;
    import SendMessageRequest = AWS.SQS.SendMessageParams;
    import SendMessageReply = AWS.SQS.SendMessageResult;

    export
    interface ISqsMessagingSession
        extends IMessagingSession
    {     
        insertQueue(queueId:string,queueUrl:string): ISqsMessagingSession;
        insertSelector(expression:string,selector:ISelector): ISqsMessagingSession;
    
        getQueueUrl(queueId:string): string;    
        getSelector(expression:string): ISelector;
        getAcknowledgementMode(): AcknowledgementMode;

        hasQueue(queueId: string): boolean;
        hasSelector(expression: string): boolean;
    
        acknowledge(message:SqsMessage): void;
      
        getImp(): AmazonSqsClient;
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
{
    import IMessage = Strata1.Integrator.Messaging.IMessage;
    import DeliveryMode = Strata1.Integrator.Messaging.DeliveryMode;
    import Message = AWS.SQS.Message;
    import MessageAttribute = AWS.SQS.MessageAttribute;

    export abstract
    class SqsMessage
        implements IMessage
    {
        public static CORRELATION_ID: string = "CorrelationId";
        public static RETURN_ADDRESS: string = "ReturnAddress";
        public static DELIVERY_MODE: string = "DeliveryMode";
        public static PAYLOAD_TYPE: string = "PayloadType";

        protected session: ISqsMessagingSession;
        protected messageImp: Message;
        protected queueUrl: string;
         
        constructor(session:ISqsMessagingSession,messageImp?:Message)
        {
            this.session = session;
            this.messageImp = messageImp ? messageImp : null;
            this.queueUrl = null;
        }

        setMessageId(messageId: string): IMessage
        {
            this.messageImp.MessageId = messageId;
            return this;
        }

        setCorrelationId(correlationId: string): IMessage
        {
            var value: MessageAttribute =
                {
                    StringValue: correlationId,
                    DataType: "String"
                };

            this.messageImp.MessageAttributes[SqsMessage.CORRELATION_ID] = value;
            return this;
        }

        setReturnAddress(returnAddress: string): IMessage
        {
            var value: MessageAttribute =
                {
                    StringValue: returnAddress,
                    DataType: "String"
                };

            this.messageImp.MessageAttributes[SqsMessage.RETURN_ADDRESS] = value;
            return this;
        }

        setDeliveryMode(mode: DeliveryMode): IMessage
        {
            var value: MessageAttribute =
                {
                    StringValue: mode.toString(),
                    DataType: "String.DeliveryMode"
                };

            this.messageImp.MessageAttributes[SqsMessage.DELIVERY_MODE] = value;
            return this;
        }

        setBooleanProperty(name: string,value: boolean): IMessage
        {
            var prop: MessageAttribute =
                {
                    StringValue: value ? "true" : "false",
                    DataType: "String.Boolean"
                };

            this.messageImp.MessageAttributes[name] = prop;
            return this;
        }

        setNumberProperty(name: string,value: number): IMessage
        {
            var prop: MessageAttribute =
                {
                    StringValue: value.toString(),
                    DataType: "Number.long"
                };

            this.messageImp.MessageAttributes[name] = prop;
            return this;
        }

        setStringProperty(name: string,value: string): IMessage
        {
            var prop: MessageAttribute =
                {
                    StringValue: value.toString(),
                    DataType: "String"
                };

            this.messageImp.MessageAttributes[name] = prop;
            return this;
        }

        getMessageId(): string
        {
            return this.messageImp.MessageId;
        }

        getCorrelationId(): string
        {
            if(!this.hasProperty(SqsMessage.CORRELATION_ID))
                return null;

            return
                this
                    .messageImp
                    .MessageAttributes[SqsMessage.CORRELATION_ID]
                    .StringValue;
        }

        getReturnAddress(): string
        {
            if(!this.hasProperty(SqsMessage.RETURN_ADDRESS))
                return null;

            return
                this
                    .messageImp
                    .MessageAttributes[SqsMessage.RETURN_ADDRESS]
                    .StringValue;
        }

        getDeliveryMode(): DeliveryMode
        {
            if(!this.hasProperty(SqsMessage.DELIVERY_MODE))
                return null;

            return
                DeliveryMode[
                    this
                        .messageImp
                        .MessageAttributes[SqsMessage.DELIVERY_MODE]
                        .StringValue];
        }

        getBooleanProperty(name: string): boolean
        {
            if(!this.hasProperty(name))
                return null;

            return
                new Boolean(
                    this
                        .messageImp
                        .MessageAttributes[name]
                        .StringValue).valueOf();
        }

        getNumberProperty(name: string): number
        {
            if(!this.hasProperty(name))
                return null;

            return
                new Number(
                    this
                        .messageImp
                        .MessageAttributes[name]
                        .StringValue).valueOf();
        }

        getStringProperty(name: string): string
        {
            if(!this.hasProperty(name))
                return null;

            return
                this
                    .messageImp
                    .MessageAttributes[name]
                    .StringValue;
        }

        hasProperty(name: string): boolean
        {
            return
                this.messageImp.MessageAttributes[name] != undefined ||
                this.messageImp.MessageAttributes[name] != null;
        }

        acknowledge(): void
        {
            this.session.acknowledge(this);
        }

        setQueueUrl(queueUrl: string): SqsMessage
        {
            this.queueUrl = queueUrl;
            return this;
        }

        getQueueUrl(): string
        {
            return this.queueUrl;
        }

        hasQueueUrl(): boolean
        {
            return this.queueUrl != undefined && this.queueUrl != null;
        }

        getMessageImp(): Message
        {
            return this.messageImp;
        }
    }

}

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
{
    import IMessage = Strata1.Integrator.Messaging.IMessage;
    import IStringMessage = Strata1.Integrator.Messaging.IStringMessage;
    import DeliveryMode = Strata1.Integrator.Messaging.DeliveryMode;
    import Message = AWS.SQS.Message;
    import MessageAttribute = AWS.SQS.MessageAttribute;

    export
    class SqsStringMessage
        extends    SqsMessage
        implements IStringMessage
    {
        constructor(session: ISqsMessagingSession,messageImp?: Message)
        {
            super(session,messageImp);
            this.messageImp.Body = null;
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
            this.messageImp.Body = payload;
            return this;
        }

        getPayload(): string
        {
            return this.messageImp.Body;
        }

    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
{
    import IMessage = Strata1.Integrator.Messaging.IMessage;
    import IMessageSender = Strata1.Integrator.Messaging.IMessageSender;
    import IMessagingSession = Strata1.Integrator.Messaging.IMessagingSession;
    import Message = AWS.SQS.Message;
    import MessageAttribute = AWS.SQS.MessageAttribute;
    import AmazonSqsClient = AWS.SQS;
    import QueueAttributes = AWS.SQS.QueueAttributes;
    import SetQueueAttributesRequest = AWS.SQS.SetQueueAttributesParams;
    import GetQueueAttributesRequest = AWS.SQS.GetQueueAttributesParams;
    import GetQueueAttributesReply = AWS.SQS.GetQueueAttributesResult;
    import SendMessageRequest = AWS.SQS.SendMessageParams;
    import SendMessageReply = AWS.SQS.SendMessageResult;

    export
    class SqsMessageSender
        implements IMessageSender
    {
        private session: ISqsMessagingSession;
        private queueUrl: string;

        constructor(session: ISqsMessagingSession,queueUrl: string)
        {
            this.session = session;
            this.queueUrl = queueUrl;
        }

        setTimeToLive(milliseconds: number): void
        {
            var seconds: number = milliseconds / 1000;
            var service: AmazonSqsClient = this.session.getImp();
            var request: SetQueueAttributesRequest = null;

            request =
            {
                QueueUrl: this.queueUrl,
                Attributes:
                    {
                        MessageRetentionPeriod: seconds < 60 ? 60 : seconds
                    }
            };

            service.setQueueAttributes(
                request,
                (err: Error,data: any) =>
                    {
                        if(err)
                            throw err;
                    } );
        }
    
        getSession(): IMessagingSession
        {
            return this.session;
        }
         
        getTimeToLive(): number
        {
            var service: AmazonSqsClient = this.session.getImp();
            var request: GetQueueAttributesRequest = null;
            var reply: GetQueueAttributesReply = null;

            request =
            {
                QueueUrl: this.queueUrl,
                AttributeNames: ["MessageRetentionPeriod"]
            };

            service.getQueueAttributes(
                request,
                (err: Error,result: GetQueueAttributesReply) =>
                {
                    if(err)
                        throw err;

                    if(result)
                        reply = result;
                });

            return new Number(reply.Attributes["MessageRetentionPeriod"]).valueOf();
        }
    
        send(message: IMessage): void
        {
            var s: SqsMessage = <SqsMessage>message;
            var m: Message = s.getMessageImp();
            var service: AmazonSqsClient = this.session.getImp();
            var request: SendMessageRequest = null;
            var reply: SendMessageReply = null;

            request =
            {
                QueueUrl: this.queueUrl,
                MessageAttributes: m.MessageAttributes,
                MessageBody: m.Body
            };

            service.sendMessage(
                request,
                (err: Error,result: SendMessageReply) =>
                {
                    if(err)
                        throw err;

                    if(result)
                        reply = result;
                });
        }
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
{
    import IMessage = Strata1.Integrator.Messaging.IMessage;

    export
    interface ISelector
    {
        evaluate(message: IMessage): boolean;
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
{
    import IMessage = Strata1.Integrator.Messaging.IMessage;

    export
    class DefaultSelector
        implements ISelector
    {
        evaluate(message: IMessage): boolean
        {
            return true;
        }
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
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
    import Message = AWS.SQS.Message;
    import MessageAttribute = AWS.SQS.MessageAttribute;
    import SqsOptions = AWS.SQS.SqsOptions;
    import AwsCredentials = AWS.Credentials;
    import AmazonSqsClient = AWS.SQS;
    import QueueAttributes = AWS.SQS.QueueAttributes;
    import ChangeMessageVisibilityRequest = AWS.SQS.ChangeMessageVisibilityParams;
    import SetQueueAttributesRequest = AWS.SQS.SetQueueAttributesParams;
    import GetQueueAttributesRequest = AWS.SQS.GetQueueAttributesParams;
    import GetQueueAttributesReply = AWS.SQS.GetQueueAttributesResult;
    import ReceiveMessageRequest = AWS.SQS.ReceiveMessageParams;
    import ReceiveMessageReply = AWS.SQS.ReceiveMessageResult;

    export abstract
    class AbstractMessageRetriever
    {
        private session: ISqsMessagingSession;
        private service: AmazonSqsClient;

        constructor(session: ISqsMessagingSession)
        {
            this.session = session;
            this.service = this.session.getImp();
        }

        getMessagesFromQueue(queueUrl:string,selector:ISelector,waitTimeSecs:number): IMessage[]
        {
            var request: ReceiveMessageRequest = null;
            var reply: ReceiveMessageReply = null;
            var messages: IMessage[] = [];

            request =
            {
                QueueUrl: queueUrl,
                AttributeNames: ["All"],
                MessageAttributeNames: ["All"],
                WaitTimeSeconds: waitTimeSecs,
                VisibilityTimeout: 30,
                MaxNumberOfMessages: 10
            };

            this
                .service
                .receiveMessage(
                    request,
                    (err: Error,result: ReceiveMessageReply) =>
                    {
                        if(err)
                            throw err;

                        if(result)
                            reply = result;
                        else
                            reply = null; 
                    } );

            for(var message in reply.Messages)
            {
                var output: IMessage = null;

                switch(this.getPayloadType(message))
                {
                    case PayloadType.STRING:
                        output = new SqsStringMessage(this.session,message);
                        break;

                }

                (<SqsMessage>output).setQueueUrl(queueUrl);

                if(selector.evaluate(output))
                {
                    messages.concat(output);

                    if(this.session.getAcknowledgementMode() == AcknowledgementMode.AUTO_ACKNOWLEDGEMENT)
                        output.acknowledge();
                }
                else
                    this.makeVisibleToOtherReceivers(this.service,queueUrl,message);

            }

            return messages;
        }

        protected
        getSession(): ISqsMessagingSession
        {
            return this.session;
        }
         
        protected
        getPayloadType(message: Message): PayloadType
        {
            if(message.MessageAttributes == null)
                return PayloadType.STRING;

            if(!message.MessageAttributes[SqsMessage.PAYLOAD_TYPE])
                return PayloadType.STRING;

            return
                PayloadType[
                    message.MessageAttributes[
                        SqsMessage.PAYLOAD_TYPE].StringValue];
        }

        private
        makeVisibleToOtherReceivers(
            service: AmazonSqsClient,
            queueUrl: string,
            message: Message):void
        {
            var request: ChangeMessageVisibilityRequest =
                {
                    QueueUrl: queueUrl,
                    ReceiptHandle: message.ReceiptHandle,
                    VisibilityTimeout: 0
                };

            this
                .service
                .changeMessageVisibility(
                    request,
                    (err: Error,data: any) => { if(err) throw err; });
        }

    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
{
    import IMessage = Strata1.Integrator.Messaging.IMessage;
    import IStringMessage = Strata1.Integrator.Messaging.IStringMessage;
    import IMapMessage = Strata1.Integrator.Messaging.IMapMessage;
    import IMessageListener = Strata1.Integrator.Messaging.IMessageListener;
    import IMessageReceiver = Strata1.Integrator.Messaging.IMessageReceiver;
    import IMessagingSession = Strata1.Integrator.Messaging.IMessagingSession;
    import MessagingException = Strata1.Integrator.Messaging.MessagingException;
    import NoListenerException = Strata1.Integrator.Messaging.NoListenerException;
    import MixedModeException = Strata1.Integrator.Messaging.MixedModeException;
    import NoMessageReceivedException = Strata1.Integrator.Messaging.NoMessageReceivedException;

    export
    class SqsMessageProcessor
        extends AbstractMessageRetriever
    {
        private queueUrl: string;
        private selector: ISelector;
        private listener: IMessageListener;
        private executor: Holder<number>;
        private listening: Holder<boolean>;

        constructor(
            session: ISqsMessagingSession,
            queueUrl: string,
            selector: ISelector,
            listener: IMessageListener,
            executor: Holder<number>,
            listening: Holder<boolean>)
        {
            super(session);
            this.queueUrl = queueUrl;
            this.selector = selector;
            this.listener = listener;
            this.executor = executor;
            this.listening = listening;
        }
        
        run() 
        {
            this.getNextMessages().forEach(
                (message: IMessage,index: number,array: Array<IMessage>) =>
                {
                    try
                    {
                        if(message != null)
                            this.listener.onMessage(<IStringMessage>message);
                    }
                    catch(e)
                    {
                    }
                });
            if(this.listening.getValue())
                this.executor.setValue(
                    setTimeout(() => {this.run();}));
        }

        getNextMessages(): Array<IMessage>
        {
            var messages: Array<IMessage> = 
                super.getMessagesFromQueue(this.queueUrl,this.selector,20);

            while(messages.length == 0 && this.listening.getValue())
            {
                messages =
                    super.getMessagesFromQueue(this.queueUrl,this.selector,20);
            }

            return messages;
        }

        close(): void
        {
        }
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
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
    interface IReceiverState
    {
        startListening(
            queueUrl:string,
            selector: ISelector,
            listener: IMessageListener,
            listening: Holder<boolean>): void;
        stopListening(listening: Holder<boolean>)
        isListening(listening: Holder<boolean>): boolean;

        receive(queueUrl: string,selector: ISelector): IMessage;
        receive(queueUrl: string,selector: ISelector,timeOutMs: number): IMessage;
        receiveNoWait(queueUrl: string,selector: ISelector): IMessage;
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
{
    import IMessage = Strata1.Integrator.Messaging.IMessage;
    import IStringMessage = Strata1.Integrator.Messaging.IStringMessage;
    import IMapMessage = Strata1.Integrator.Messaging.IMapMessage;
    import IMessageListener = Strata1.Integrator.Messaging.IMessageListener;
    import IMessageReceiver = Strata1.Integrator.Messaging.IMessageReceiver;
    import IMessagingSession = Strata1.Integrator.Messaging.IMessagingSession;
    import MessagingException = Strata1.Integrator.Messaging.MessagingException;
    import NoListenerException = Strata1.Integrator.Messaging.NoListenerException;
    import MixedModeException = Strata1.Integrator.Messaging.MixedModeException;
    import NoMessageReceivedException = Strata1.Integrator.Messaging.NoMessageReceivedException;

    export
    class SynchronousReceiverState
        extends AbstractMessageRetriever
        implements IReceiverState
    {
        private buffer: Array<IMessage>;

        constructor(session: ISqsMessagingSession)
        {
            super(session);
            this.buffer = new Array<IMessage>();
        }


        startListening(
            queueUrl: string,
            selector: ISelector,
            listener: IMessageListener,
            listening: Holder<boolean>): void
        {
            throw new MixedModeException(
                "Can not call startListening() in synchronous mode.");
        }

        stopListening(listening: Holder<boolean>): void
        {
            throw new MixedModeException(
                "Can not call stopListening() in synchronous mode.");
        }

        isListening(listening: Holder<boolean>): boolean
        {
            return false;
        }

        receive(queueUrl: string,selector: ISelector,timeOutMs?: number): IMessage
        {
            if(timeOutMs == undefined)
                return this.receiveWithoutTimeout(queueUrl,selector);
            else
                return this.receiveWithTimeout(queueUrl,selector,timeOutMs);
        }

        receiveNoWait(queueUrl: string,selector: ISelector): IMessage
        {
            var message: IMessage = this.poll();

            for(var i:number= 0;i < 5;i++)
            {
                if(message != null)
                    return message;

                this.addAll(this.getMessagesFromQueue(queueUrl,selector,2));
                message = this.poll();
            }

            throw new NoMessageReceivedException("no message received");

        }

        private
        receiveWithoutTimeout(queueUrl: string,selector: ISelector): IMessage
        {
            var message: IMessage = this.poll();

            while(message == null)
            {
                this.addAll(this.getMessagesFromQueue(queueUrl,selector,2));
                message = this.poll();
            }

            return message;
        }

        private
        receiveWithTimeout(queueUrl: string,selector: ISelector,timeOutMs?: number): IMessage
        {
            var secs:number = timeOutMs / 1000;
            var waitTimeSecs:number = secs > 0 ? secs : 2;
            var remaining:number = waitTimeSecs;
            var message:IMessage = this.poll();

            while(remaining > 0)
            {
                if(message != null)
                    return message;

                this.addAll(
                    this.getMessagesFromQueue(queueUrl,selector,2));
                message = this.poll();
                remaining -= 2;
            }

            if(message != null)
                return message;

            throw new NoMessageReceivedException("no message received");
        }

        private
        poll(): IMessage
        {
            if(this.buffer.length == 0)
                return null;

            return this.buffer.shift();
        }

        private
        addAll(messages: Array<IMessage>): void
        {
            this.buffer = this.buffer.concat(messages);
        }
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
{
    import IMessage = Strata1.Integrator.Messaging.IMessage;
    import IStringMessage = Strata1.Integrator.Messaging.IStringMessage;
    import IMapMessage = Strata1.Integrator.Messaging.IMapMessage;
    import IMessageListener = Strata1.Integrator.Messaging.IMessageListener;
    import IMessageReceiver = Strata1.Integrator.Messaging.IMessageReceiver;
    import IMessagingSession = Strata1.Integrator.Messaging.IMessagingSession;
    import MessagingException = Strata1.Integrator.Messaging.MessagingException;
    import NoListenerException = Strata1.Integrator.Messaging.NoListenerException;
    import MixedModeException = Strata1.Integrator.Messaging.MixedModeException;
    import NoMessageReceivedException = Strata1.Integrator.Messaging.NoMessageReceivedException;

    export
    class AsynchronousReceiverState
        implements IReceiverState
    {
        private session: ISqsMessagingSession;
        private executor: Holder<number>;
        private processor: SqsMessageProcessor;

        constructor(session: ISqsMessagingSession)
        {
            this.session = session;
            this.executor = new Holder<number>(0);
            this.processor = null;
        }

        startListening(
            queueUrl: string,
            selector: ISelector,
            listener: IMessageListener,
            listening: Holder<boolean>): void
        {
            if(!this.isListening(listening))
            {
                if(listener == null)
                    throw new Error("listener == null");

                listening.setValue( true );
                this.processor =
                    new SqsMessageProcessor(
                        this.session,
                        queueUrl,
                        selector,
                        listener,
                        this.executor,
                        listening);
                this.executor.setValue(
                    setInterval(
                        () => { this.processor.run();} ) );
            }

        }

        stopListening(listening: Holder<boolean>): void
        {
            if(this.isListening(listening))
            {
                listening.setValue(false);
                clearInterval(this.executor.getValue());
                this.processor.close();
                this.processor = null;
            }
        }

        isListening(listening: Holder<boolean>): boolean
        {
            return listening.getValue();
        }

        receive(queueUrl: string,selector: ISelector,timeOutMs?: number): IMessage
        {
            throw new MixedModeException(
                "Can not call receive() in asynchronous mode.");
        }

        receiveNoWait(queueUrl: string,selector: ISelector): IMessage
        {
            throw new MixedModeException(
                "Can not call receiveNoWait() in asynchronous mode."); 
        }
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
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
    import Message = AWS.SQS.Message;
    import MessageAttribute = AWS.SQS.MessageAttribute;
    import SqsOptions = AWS.SQS.SqsOptions;
    import AwsCredentials = AWS.Credentials;
    import AmazonSqsClient = AWS.SQS;
    import QueueAttributes = AWS.SQS.QueueAttributes;
    import SetQueueAttributesRequest = AWS.SQS.SetQueueAttributesParams;
    import GetQueueAttributesRequest = AWS.SQS.GetQueueAttributesParams;
    import GetQueueAttributesReply = AWS.SQS.GetQueueAttributesResult;
    import SendMessageRequest = AWS.SQS.SendMessageParams;
    import SendMessageReply = AWS.SQS.SendMessageResult;

    export
    class SqsMessageReceiver
        implements IMessageReceiver
    {
        private session: ISqsMessagingSession;
        private queueUrl: string;
        private listener: IMessageListener;
        private selector: string;
        private selectorImp: ISelector;
        private listening: Holder<boolean>;
        private state: IReceiverState;

        constructor(session: ISqsMessagingSession,queueUrl: string,selector?:string)
        {
            this.session = session;
            this.queueUrl = queueUrl;
            this.listener = null;
            this.selector = selector != undefined ? selector : null;
            this.selectorImp = selector != undefined ? this.session.getSelector(selector) : new DefaultSelector();
            this.listening = new Holder<boolean>(false);
            this.state = null;
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
            if(this.state == null)
                this.state = new AsynchronousReceiverState(this.session);

            this
                .state
                .startListening(
                    this.queueUrl,
                    this.selectorImp,
                    this.listener,
                    this.listening);
        }

        stopListening(): void
        {
            if(this.state == null)
                this.state = new AsynchronousReceiverState(this.session);

            this.state.stopListening(this.listening);
        }

        isListening(): boolean
        {
            if(this.state == null)
                this.state = new AsynchronousReceiverState(this.session);

            return this.state.isListening(this.listening);
        }

        receive(timeOutInMs?: number): IMessage
        {
            if(this.state == null)
                this.state = new AsynchronousReceiverState(this.session);

            if(timeOutInMs == undefined)
                return this.state.receive(this.queueUrl,this.selectorImp);
            else
                return this.state.receive(this.queueUrl,this.selectorImp,timeOutInMs);
        }

        receiveNoWait(): IMessage
        {
            if(this.state == null)
                this.state = new AsynchronousReceiverState(this.session);

            return this.state.receiveNoWait(this.queueUrl,this.selectorImp);
        }

        close(): void
        {
        }
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.SqsIntegrator.SqsMessaging
{
    import IMessage = Strata1.Integrator.Messaging.IMessage;
    import IStringMessage = Strata1.Integrator.Messaging.IStringMessage;
    import IMapMessage = Strata1.Integrator.Messaging.IMapMessage;
    import IMessageSender = Strata1.Integrator.Messaging.IMessageSender;
    import IMessageReceiver = Strata1.Integrator.Messaging.IMessageReceiver;
    import IMessagingSession = Strata1.Integrator.Messaging.IMessagingSession;
    import Message = AWS.SQS.Message;
    import MessageAttribute = AWS.SQS.MessageAttribute;
    import SqsOptions = AWS.SQS.SqsOptions;
    import AwsCredentials = AWS.Credentials;
    import AmazonSqsClient = AWS.SQS;
    import DeleteMessageRequest = AWS.SQS.DeleteMessageParams;

    export
    class SqsQueueMessagingSession
        implements ISqsMessagingSession
    {
        private credentials: AwsCredentials;
        private imp: AmazonSqsClient;
        private queueUrls: { [id: string]: string };
        private selectors: { [expression: string]: ISelector }
        private receivingFlag: Boolean;
        private acknowledgementMode: AcknowledgementMode;

        constructor(
            credentials: AwsCredentials,
            ackMode?: AcknowledgementMode)
        {
            this.credentials = credentials;
            this.imp = new AmazonSqsClient(this.credentials);
            this.queueUrls = {};
            this.selectors = {};
            this.receivingFlag = new Boolean(false);
            this.acknowledgementMode = ackMode == undefined ? AcknowledgementMode.AUTO_ACKNOWLEDGEMENT : ackMode;
        }

        createMessageSender(id: string): IMessageSender
        {
            return new SqsMessageSender(this,id);
        }

        createMessageReceiver(id: string,selector?: string): IMessageReceiver
        {
            return new SqsMessageReceiver(this,id,selector);
        }

        createStringMessage(): IStringMessage
        {
            return new SqsStringMessage(this);
        }

        createMapMessage(): IMapMessage
        {
            throw new Error("createMapMessage(): unsupported method.");
        }
    
        startReceiving(): void
        {
            this.receivingFlag = true;
        }

        stopReceiving(): void
        {
            this.receivingFlag = false;
        }

        close(): void
        {
            this.imp = null;
        }
    
        isReceiving(): boolean
        {
            return this.receivingFlag.valueOf();
        }

        isClosed(): boolean
        {
            return this.imp != null;
        }

        insertQueue(queueId: string,queueUrl: string): ISqsMessagingSession
        {
            this.queueUrls[queueId] = queueUrl;
            return this;
        }

        insertSelector(expression: string,selector: ISelector): ISqsMessagingSession
        {
            this.selectors[this.normalize(expression)] = selector;
            return this;
        }

        getQueueUrl(queueId: string): string
        {
            if(this.hasQueue(queueId))
                return this.queueUrls[queueId];

            return null;
        }

        getSelector(expression: string): ISelector
        {
            var exp: string = this.normalize(expression);

            if(this.hasSelector(exp))
                return this.selectors[exp];

            return null;
        }

        getAcknowledgementMode(): AcknowledgementMode
        {
            return this.acknowledgementMode;
        }

        hasQueue(queueId: string): boolean
        {
            return this.queueUrls[queueId] != undefined;
        }

        hasSelector(expression: string): boolean
        {
            return this.selectors[this.normalize(expression)] != undefined;
        }

        acknowledge(message: SqsMessage): void
        {
            if(!message.hasQueueUrl())
                throw new Error(
                    "SqsMessage must have a queueUrl to acknowledge.");

            this.removeMessageFromQueue(message); 
        }

        getImp(): AmazonSqsClient
        {
            return this.imp;
        }

        private
        removeMessageFromQueue(message: SqsMessage): void
        {
            var request: DeleteMessageRequest =
                {
                    QueueUrl: message.getQueueUrl(),
                    ReceiptHandle: message.getMessageImp().ReceiptHandle
                };

            this.imp.deleteMessage(
                request,
                (err: Error,data: any) => { if(err) throw err; });
        }

        private
        normalize(input: string): string
        {
            var matcher: RegExp = new RegExp("([^\"]\\S*|\".+?\")\\s*");
            var tokens: RegExpExecArray;
            var output: string = "";

            matcher.compile();
            tokens = matcher.exec(input);

            tokens.forEach(
                (token: string,index: number,tokens: string[]) =>
                {
                    output += token;
                } );

            return output;      
        }
    }
}

// ##########################################################################
