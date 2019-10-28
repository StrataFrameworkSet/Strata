//  ##########################################################################
//  # File Name: PostalAddressType.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Data.Common;
using NHibernate;
using NHibernate.Engine;
using NHibernate.Type;
using NHibernate.UserTypes;
using Strata.Foundation.Core.Value;

namespace Strata.Domain.Nhibernate.Mapping
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// User defined type Nhibernate mapper for <c>PostalAddress</c> objects.
    /// </summary>
    /// 
    public
    class PostalAddressType:
        ICompositeUserType
    {
        public String[] 
        PropertyNames
        {
            get
            {
                return 
                    new String[] 
                    {
                        "StreetAddress",
                        "City",
                        "State",
                        "PostalCode",
                        "CountryCode"
                    };
            }
        }

        public IType[] 
        PropertyTypes
        {
            get
            {
                return new IType[] 
                {
                    NHibernateUtil.String,
                    NHibernateUtil.String,
                    NHibernateUtil.String,
                    NHibernateUtil.String,
                    NHibernateUtil.String
                };
            }
        }

        public Type 
        ReturnedClass
        {
            get { return typeof(PostalAddress); }
        }

        public bool
        IsMutable
        {
            get { return false; }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>PostalAddressType</c> instance.
        /// </summary>
        /// 
        public
        PostalAddressType() {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        GetPropertyValue(Object component,int property)
        {
            PostalAddress address = (PostalAddress)component;

            if (address == null)
                return null;

            switch (property)
            {
                case 0:
                    return address.StreetAddress;
                case 1:
                    return address.City;
                case 2:
                    return address.State;
                case 3:
                    return address.PostalCode;
                case 4:
                    return address.CountryCode;
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
            PostalAddress address = (PostalAddress)component;

            if (address == null)
                return;

            switch (property)
            {
                case 0:
                    address.StreetAddress = (string)value;
                    break;
                case 1:
                    address.City = (string)value;
                    break;
                case 2:
                    address.State = (string)value;
                    break;
                case 3:
                    address.PostalCode = (string)value;
                    break;
                case 4:
                    address.CountryCode = (string)value;
                    break;
                default:
                    throw new HibernateException("unknown property");
            }

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
            DbDataReader reader,
            String[] names,
            ISessionImplementor session,
            Object owner)
        {
            string streetColumn = names[0];
            string cityColumn = names[1];
            string stateColumn = names[2];
            string zipColumn = names[3];
            string countryColumn = names[4];
            object street = null;
            object city = null;
            object state = null;
            object zip = null;
            object country = null;

            if (reader == null)
                return null;

            street =
                NHibernateUtil
                    .String
                    .NullSafeGet(
                        reader,
                        streetColumn,
                        session,
                        owner);
            city =
                NHibernateUtil
                    .String
                    .NullSafeGet(
                        reader,
                        cityColumn,
                        session,
                        owner);
            state =
                NHibernateUtil
                    .String
                    .NullSafeGet(
                        reader,
                        stateColumn,
                        session,
                        owner);
            zip =
                NHibernateUtil
                    .String
                    .NullSafeGet(
                        reader,
                        zipColumn,
                        session,
                        owner);
            country =
                NHibernateUtil
                    .String
                    .NullSafeGet(
                        reader,
                        countryColumn,
                        session,
                        owner);

            return
                street == null && city == null && state == null &&
                zip == null && country == null
                    ? null
                    : new PostalAddress()
                    {
                        StreetAddress = street?.ToString(),
                        City = city?.ToString(),
                        State = state?.ToString(),
                        PostalCode = zip?.ToString(),
                        CountryCode = country?.ToString()
                    };
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
            PostalAddress name = (PostalAddress)value;

            if (value == null)
            {
                NHibernateUtil.String.NullSafeSet(st,null,index,session);
                NHibernateUtil.String.NullSafeSet(st,null,index+1,session);
                NHibernateUtil.String.NullSafeSet(st,null,index+2,session);
                NHibernateUtil.String.NullSafeSet(st,null,index+3,session);
                NHibernateUtil.String.NullSafeSet(st,null,index+4,session);
                return;
            }

            NHibernateUtil.String.NullSafeSet(st,name.StreetAddress,index,session);
            NHibernateUtil.String.NullSafeSet(st,name.City,index+1,session);
            NHibernateUtil.String.NullSafeSet(st,name.State,index+2,session);
            NHibernateUtil.String.NullSafeSet(st,name.PostalCode,index+3,session);
            NHibernateUtil.String.NullSafeSet(st,name.CountryCode,index+4,session);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        DeepCopy(Object value) 
        {
            return (PostalAddress)value;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        Disassemble(
            Object              value,
            ISessionImplementor session)
        {
            return (PostalAddress)value;
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
            return (PostalAddress)cached;
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
            return (PostalAddress)original;
        }

    }
}

//  ##########################################################################
