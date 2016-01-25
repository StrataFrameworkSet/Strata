//  ##########################################################################
//  # File Name: MoneyType.cs
//  # Copyright: 2013, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Data;
using Strata.Common.MoneyDomain;
using NHibernate;
using NHibernate.Engine;
using NHibernate.Type;
using NHibernate.UserTypes;

namespace Strata.NhibernatePersistence.NhibernateRepository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// User defined type Nhibernate mapper for <c>Money</c> objects.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class MoneyType:
        ICompositeUserType
    {
        public String[] 
        PropertyNames
        {
            get { return new String[] {"Currency","Amount"}; }
        }

        public IType[] 
        PropertyTypes
        {
            get { return new IType[] {NHibernateUtil.String,NHibernateUtil.Decimal}; }
        }

        public Type 
        ReturnedClass
        {
            get { return typeof(Money); }
        }

        public bool
        IsMutable
        {
            get { return false; }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>MoneyType</c> instance.
        /// </summary>
        /// 
        public 
        MoneyType() {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ICompositeUserType.GetPropertyValue(Object,int)"/>
        /// </summary>
        /// 
        public Object 
        GetPropertyValue(Object component,int property)
        {
            Money money = (Money)component;
        
            if ( property == 0 ) 
                return money.Currency.CurrencyCode;
        
            if ( property == 1 )
                return money.Amount;
        
            throw new HibernateException("unknown property");
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see 
        /// cref="ICompositeUserType.SetPropertyValue(Object,int,Object)"/>
        /// </summary>
        /// 
        public void 
        SetPropertyValue(Object component,int property,Object value)
        {
            throw new HibernateException("Money is immutable.");
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ICompositeUserType.Equals(Object,Object)"/>
        /// </summary>
        /// 
        public new bool
        Equals(Object x,Object y) 
        {
            if ( x == y )
                return true;
        
            if ( x == null || y == null )
                return false;
        
            return x.Equals( y );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ICompositeUserType.GetHashCode(Object)"/>
        /// </summary>
        /// 
        public int 
        GetHashCode(Object x) 
        {
            if ( x == null )
                return 0;
        
            return x.GetHashCode();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ICompositeUserType
        ///                .NullSafeGet(
        ///                    IDataReader,
        ///                    String[],
        ///                    ISessionImplementor,
        ///                    Object)"/>
        /// </summary>
        /// 
        public Object 
        NullSafeGet(
            IDataReader         reader,
            String[]            names,
            ISessionImplementor session,
            Object              owner) 
        {
            String  currencyColumn = names[0];
            String  amountColumn   = names[1];
            String  currency       = null;
            Decimal amount         = 0.0m;

            if( reader == null )
                return null;
            
            currency = 
                NHibernateUtil
                    .String
                    .NullSafeGet(
                        reader,
                        currencyColumn,
                        session,
                        owner).ToString();
            amount = 
                (Decimal)NHibernateUtil
                    .Decimal
                    .NullSafeGet(
                        reader,
                        amountColumn,
                        session,
                        owner);

            return new Money(CurrencyManager.GetInstanceByCode( currency ),amount);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ICompositeUserType
        ///                .NullSafeSet(
        ///                    IDbCommand,
        ///                    Object,
        ///                    int,
        ///                    bool[],
        ///                    ISessionImplementor)"/>
        /// </summary>
        /// 
        public void 
        NullSafeSet(
            IDbCommand          st,
            Object              value,
            int                 index,
            bool[]              settable,
            ISessionImplementor session) 
        {
            String  currency = "";
            Decimal amount = 0.0m;

            if( value == null )
                return;

            amount = ((Money) value).Amount;
            currency = ((Money) value).Currency.CurrencyCode;
 
            NHibernateUtil.String.NullSafeSet(st,currency,index,session);
            NHibernateUtil.Decimal.NullSafeSet(st,amount,index+1,session);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ICompositeUserType.DeepCopy(Object)"/>
        /// </summary>
        /// 
        public Object 
        DeepCopy(Object value) 
        {
            return (Money)value;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ICompositeUserType
        ///                .Disassemble(Object,ISessionImplementor)"/>
        /// </summary>
        /// 
        public Object 
        Disassemble(
            Object              value,
            ISessionImplementor session)
        {
            return (Money)value;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ICompositeUserType
        ///                .Assemble(Object,ISessionImplementor,Object)"/>
        /// </summary>
        /// 
        public Object 
        Assemble(
            Object              cached,
            ISessionImplementor session,
            Object              owner) 
        {
            return (Money)cached;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ICompositeUserType
        ///                .Replace(
        ///                    Object,Object,ISessionImplementor,Object)"/>
        /// </summary>
        /// 
        public Object 
        Replace(
            Object              original,
            Object              target,
            ISessionImplementor session,
            Object              owner) 
        {
            return (Money)original;
        }    
    }
}

//  ##########################################################################
