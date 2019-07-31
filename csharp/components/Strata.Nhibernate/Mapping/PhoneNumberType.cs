//  ##########################################################################
//  # File Name: PhoneNumberType.cs
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
    /// User defined type Nhibernate mapper for <c>PhoneNumber</c> objects.
    /// </summary>
    /// 
    public
    class PhoneNumberType:
        ICompositeUserType
    {
        public String[] 
        PropertyNames
        {
            get { return new String[] {"PhoneNumber"}; }
        }

        public IType[] 
        PropertyTypes
        {
            get { return new IType[] {NHibernateUtil.String}; }
        }

        public Type 
        ReturnedClass
        {
            get { return typeof(PhoneNumber); }
        }

        public bool
        IsMutable
        {
            get { return false; }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>PhoneNumberType</c> instance.
        /// </summary>
        /// 
        public
        PhoneNumberType() {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        GetPropertyValue(Object component,int property)
        {
            PhoneNumber phone = (PhoneNumber)component;

            if (phone == null)
                return null;

            if ( property == 0 ) 
                return phone.ToString();
        
            throw new HibernateException("unknown property");
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        SetPropertyValue(Object component,int property,Object value)
        {
            //throw new HibernateException("PhoneNumber is immutable");
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
            string  phoneColumn = names[0];
            object  contents    = null;

            if( reader == null )
                return null;
            
            contents = 
                NHibernateUtil
                    .String
                    .NullSafeGet(
                        reader,
                        phoneColumn,
                        session,
                        owner);
    
            return 
                contents != null 
                    ? new PhoneNumber(contents.ToString())
                    : null;
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
            string  contents = string.Empty;

            if (value == null)
            {
                NHibernateUtil.String.NullSafeSet(st,null,index,session);
                return;
            }

            contents = ((PhoneNumber)value).ToString();
 
            NHibernateUtil.String.NullSafeSet(st,contents,index,session);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        DeepCopy(Object value) 
        {
            return (PhoneNumber)value;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        Disassemble(
            Object              value,
            ISessionImplementor session)
        {
            return (PhoneNumber)value;
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
            return (PhoneNumber)cached;
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
            return (PhoneNumber)original;
        }

    }
}

//  ##########################################################################
