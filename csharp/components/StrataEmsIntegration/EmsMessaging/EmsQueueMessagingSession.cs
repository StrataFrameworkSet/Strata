//  ##########################################################################
//  # File Name: EmsMessagingSession.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Threading;
using Strata.Integration.Messaging;
using TIBCO.EMS;

namespace Strata.EmsIntegration.EmsMessaging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class EmsQueueMessagingSession:
        IMessagingSession
    {
        public int                       RetryAttempts {get;set;}
        public int                       RetryDelay {get;set;}
        public int                       RetryTimeout {get;set;}

        private QueueConnectionFactory   factory;
        private QueueConnection          connection;
        private Session                  session;
        private int                      acknowledgmentMode;
        private bool                     receiving;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        public 
        EmsQueueMessagingSession(
            String svrUrl,
            String usrNm,
            String pw,
            int    ackMode)
        {
            factory    = new QueueConnectionFactory(svrUrl);

            factory.SetUserName( usrNm );
            factory.SetUserPassword( pw );

            acknowledgmentMode = ackMode;

            Connect();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        public 
        EmsQueueMessagingSession(
            String svrUrl,
            String usrNm,
            String pw):
            this(svrUrl,usrNm,pw,Session.DUPS_OK_ACKNOWLEDGE) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        public 
        EmsQueueMessagingSession(QueueConnectionFactory f,int ackMode)
        {
            factory    = f;
            acknowledgmentMode = ackMode;

            Connect();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        public
        EmsQueueMessagingSession(QueueConnectionFactory f):
            this( f,Session.DUPS_OK_ACKNOWLEDGE ) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// <param name="id"></param>
        /// 
        public IMessageSender 
        CreateMessageSender(String id)
        {
            try
            {
                return
                    new EmsMessageSender(
                        session.CreateProducer(session.CreateQueue(id)));   
            }
            catch (IllegalStateException)
            {
                Reconnect();

                return
                    new EmsMessageSender(
                        session.CreateProducer(session.CreateQueue(id)));   
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// <param name="id"></param>
        /// 
        public IMessageReceiver 
        CreateMessageReceiver(String id)
        {
            try
            {
                return
                    new EmsMessageReceiver(
                        session.CreateConsumer(session.CreateQueue(id)));
            }
            catch (IllegalStateException)
            {
                Reconnect();

                return
                    new EmsMessageReceiver(
                        session.CreateConsumer(session.CreateQueue(id)));
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// <param name="id"></param>
        /// <param name="selector"></param>
        /// 
        public IMessageReceiver 
        CreateMessageReceiver(String id,String selector)
        {
            return
                new EmsMessageReceiver(
                    session.CreateConsumer(session.CreateQueue(id),selector));
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        public IStringMessage 
        CreateStringMessage()
        {
            return new EmsStringMessage(session.CreateTextMessage());
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        public IMapMessage 
        CreateMapMessage()
        {
            return new EmsMapMessage(session.CreateMapMessage());
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        public IObjectMessage 
        CreateObjectMessage()
        {
            return new EmsObjectMessage(session.CreateObjectMessage());
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        public void 
        StartReceiving()
        {
            connection.Start();
            receiving = true;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        public void 
        StopReceiving()
        {
            connection.Stop();
            receiving = false;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        public bool 
        IsReceiving()
        {
            return receiving;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        public void 
        Close()
        {
            session.Close();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        public void 
        Connect()
        {
            connection = factory.CreateQueueConnection();
            session = connection.CreateQueueSession(
                false,
                acknowledgmentMode);
            receiving = false;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        public void 
        Reconnect()
        {
            try
            {
                if ( !session.IsClosed )
                    session.Close();
            }
            catch (IllegalStateException) {}

            try
            {
                if ( !connection.IsClosed )                
                    connection.Close();
            }
            catch (IllegalStateException) {}

            for (int i=0;i<RetryAttempts;i++)
            {     
                try
                {
                    connection = factory.CreateQueueConnection();
                    session = connection.CreateQueueSession(
                        false,
                        acknowledgmentMode);
                    receiving = false;
                    return;
                }
                catch (IllegalStateException)
                {
                    Thread.Sleep( RetryDelay );
                }
            }

            throw new IllegalStateException( "Reconnect failed." );
        }

    }
}

//  ##########################################################################
