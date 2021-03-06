//  ##########################################################################
//  # File Name: PersonNameType.cs
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
    /// User defined type Nhibernate mapper for <c>HashedString</c> objects.
    /// </summary>
    /// 
    public
    class HashedStringType:
        ICompositeUserType
    {
        public string[] 
        PropertyNames
        {
            get { return new string[] {"Value","Salt"}; }
        }

        public IType[] 
        PropertyTypes
        {
            get
            {
                return new IType[] 
                {
                    NHibernateUtil.BinaryBlob,
                    NHibernateUtil.BinaryBlob
                };
            }
        }

        public Type 
        ReturnedClass
        {
            get { return typeof(HashedString); }
        }

        public bool
        IsMutable
        {
            get { return false; }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>HashedStringType</c> instance.
        /// </summary>
        /// 
        public
        HashedStringType() {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        GetPropertyValue(Object component,int property)
        {
            HashedString hashed = (HashedString)component;

            if (hashed == null)
                return null;

            switch (property)
            {
                case 0:
                    return hashed.Value;
                case 1:
                    return hashed.Salt;
                default:
                    throw new HibernateException("unknown property");
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        SetPropertyValue(Object component,int property,Object value)
        {
            //throw new HibernateException("HashedString is immutable");
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
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
        /// <inheritDoc/>
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
            String[]            names,
            ISessionImplementor session,
            Object              owner) 
        {
            string valueColumn = names[0];
            string saltColumn  = names[1];
            object value       = null;
            object salt        = null;

            if( reader == null )
                return null;

            value =
                NHibernateUtil
                    .BinaryBlob
                    .NullSafeGet(
                        reader,
                        valueColumn,
                        session,
                        owner);
            salt =
                NHibernateUtil
                    .BinaryBlob
                    .NullSafeGet(
                        reader,
                        saltColumn,
                        session,
                        owner);
    
            return 
                value == null && salt == null 
                    ? null
                    : new HashedString((byte[])value,(byte[])salt,true);
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
            HashedString hashed = (HashedString)value;

            if (value == null)
            {
                NHibernateUtil
                    .BinaryBlob
                    .NullSafeSet(st,null,index,session);
                NHibernateUtil
                    .BinaryBlob
                    .NullSafeSet(st,null,index+1,session);
                return;
            }

            NHibernateUtil
                .BinaryBlob
                .NullSafeSet(st,hashed.Value,index,session);
            NHibernateUtil
                .BinaryBlob
                .NullSafeSet(st,hashed.Salt,index+1,session);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        DeepCopy(Object value) 
        {
            return (HashedString)value;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        Disassemble(
            Object              value,
            ISessionImplementor session)
        {
            return (HashedString)value;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        Assemble(
            Object              cached,
            ISessionImplementor session,
            Object              owner) 
        {
            return (HashedString)cached;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        Replace(
            Object              original,
            Object              target,
            ISessionImplementor session,
            Object              owner) 
        {
            return (HashedString)original;
        }

    }
}

//  ##########################################################################
