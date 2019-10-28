//  ##########################################################################
//  # File Name: PersonNameType.cs
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
    /// User defined type Nhibernate mapper for <c>PersonName</c> objects.
    /// </summary>
    /// 
    public
    class PersonNameType:
        ICompositeUserType
    {
        public String[] 
        PropertyNames
        {
            get { return new String[] {"FirstName","MiddleName","LastName"}; }
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
                    NHibernateUtil.String
                };
            }
        }

        public Type 
        ReturnedClass
        {
            get { return typeof(PersonName); }
        }

        public bool
        IsMutable
        {
            get { return false; }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>PersonNameType</c> instance.
        /// </summary>
        /// 
        public
        PersonNameType() {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        GetPropertyValue(Object component,int property)
        {
            PersonName name = (PersonName)component;

            if (name == null)
                return null;

            switch (property)
            {
                case 0:
                    return name.FirstName;
                case 1:
                    return name.MiddleName;
                case 2:
                    return name.LastName;
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
            //throw new HibernateException("PersonName is immutable");
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
            string firstColumn  = names[0];
            string middleColumn = names[1];
            string lastColumn   = names[2];
            object first        = null;
            object middle       = null;
            object last         = null;

            if( reader == null )
                return null;

            first =
                NHibernateUtil
                    .String
                    .NullSafeGet(
                        reader,
                        firstColumn,
                        session,
                        owner);
            middle =
                NHibernateUtil
                    .String
                    .NullSafeGet(
                        reader,
                        middleColumn,
                        session,
                        owner);
            last = 
                NHibernateUtil
                    .String
                    .NullSafeGet(
                        reader,
                        lastColumn,
                        session,
                        owner);
    
            return 
                first == null && middle == null && last == null 
                    ? null
                    : new PersonName(
                        first.ToString(),
                        middle.ToString(),
                        last.ToString());
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
            PersonName name = (PersonName)value;

            if (value == null)
            {
                NHibernateUtil.String.NullSafeSet(st,null,index,session);
                NHibernateUtil.String.NullSafeSet(st,null,index+1,session);
                NHibernateUtil.String.NullSafeSet(st,null,index+2,session);
                return;
            }

            NHibernateUtil.String.NullSafeSet(st,name.FirstName,index,session);
            NHibernateUtil.String.NullSafeSet(st,name.MiddleName,index+1,session);
            NHibernateUtil.String.NullSafeSet(st,name.LastName,index+2,session);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        DeepCopy(Object value) 
        {
            return (PersonName)value;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        Disassemble(
            Object              value,
            ISessionImplementor session)
        {
            return (PersonName)value;
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
            return (PersonName)cached;
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
            return (PersonName)original;
        }

    }
}

//  ##########################################################################
