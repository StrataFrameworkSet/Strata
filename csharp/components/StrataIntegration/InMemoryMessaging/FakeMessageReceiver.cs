//  ##########################################################################
//  # File Name: FakeMessageReceiver.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using Strata.Integration.Messaging;

namespace Strata.Integration.InMemoryMessaging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class FakeMessageReceiver:
        IMessageReceiver
    {

        public IMessageListener Listener { get; set; }
        public string Selector { get; protected set; }

        private Queue<IMessage> buffer;
        private bool            listening;

        public 
        FakeMessageReceiver()
        {
            buffer = new Queue<IMessage>();
            listening = false;
        }

        public void 
        StartListening()
        {
            listening = true;
        }

        public void 
        StopListening()
        {
            listening = false;
        }

        public bool 
        IsListening()
        {
            return listening && Listener != null;
        }

        public IMessage 
        Receive()
        {
            IMessage output = buffer.Dequeue();

            while (output == null)
            {
                Thread.Sleep( 10 );
                output = buffer.Dequeue();
            }

            return output;
        }

        public IMessage 
        ReceiveWithTimeout(long milliseconds)
        {
            const int INCREMENT = 5;
            IMessage  output    = buffer.Dequeue();
            long      remaining = milliseconds;

            while (output == null)
            {
                Thread.Sleep( INCREMENT );
                remaining = remaining - INCREMENT;

                if ( remaining <= 0 )
                    return null;

                output = buffer.Dequeue();
            }

            return output;
        }

        public IMessage 
        ReceiveNoWait()
        {
            return buffer.Dequeue();
        }

        public void 
        ReceiveMessage(IMessage message)
        {
            buffer.Enqueue(message);
            ProcessMessages();
        }

        public void 
        ProcessMessages()
        {
            if ( IsListening() )
                while (buffer.Count > 0)
                {
                    IMessage message = buffer.Dequeue();

                    if ( message is IStringMessage)
                        Listener.OnMessage( (IStringMessage)message );
                    else if ( message is IObjectMessage)
                        Listener.OnMessage( (IObjectMessage)message );
                    else
                        throw new InvalidOperationException("unknown message type");
                }
                    
        }
   
        public void
        Close()
        {
            
        }

    }
}

//  ##########################################################################
