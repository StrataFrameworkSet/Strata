//  ##########################################################################
//  # File Name: MoneyType.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using NHibernate;
using NHibernate.Engine;
using NHibernate.Type;
using NHibernate.UserTypes;
using Strata.Foundation.Value;
using System;
using System.Data.Common;

namespace Strata.Nhibernate.Mapping
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// User defined type Nhibernate mapper for <c>Money</c> objects.
    /// </summary>
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

            if (money == null)
                return null;

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
            //throw new HibernateException("Money is immutable.");
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ICompositeUserType.Equals(Object,Object)"/>
        /// </summary>
        /// 
        public new bool
        Equals(Object x,Object y) 
        {
            if (ReferenceEquals(x,y))
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
        /// <inheritDoc/>
        /// 
        public Object 
        NullSafeGet(
            DbDataReader        reader,
            string[]            names,
            ISessionImplementor session,
            object              owner) 
        {
            string  currencyColumn = names[0];
            string  amountColumn   = names[1];
            object  currency       = null;
            object  amount         = 0.0m;

            if( reader == null )
                return null;
            
            currency = 
                NHibernateUtil
                    .String
                    .NullSafeGet(
                        reader,
                        currencyColumn,
                        session,
                        owner);
            amount = 
                NHibernateUtil
                    .Decimal
                    .NullSafeGet(
                        reader,
                        amountColumn,
                        session,
                        owner);

            if (currency == null || amount == null)
                return null;

            return 
                new Money(
                    CurrencyManager.GetInstanceByCode( currency.ToString() ),
                    (decimal)amount);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        NullSafeSet(
            DbCommand           st,
            Object              value,
            int                 index,
            bool[]              settable,
            ISessionImplementor session) 
        {
            String  currency = "";
            Decimal amount = 0.0m;

            if (value == null)
            {
                NHibernateUtil.String.NullSafeSet(st,null,index,session);
                NHibernateUtil.Decimal.NullSafeSet(st,null,index + 1,session);
                return;
            }

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
