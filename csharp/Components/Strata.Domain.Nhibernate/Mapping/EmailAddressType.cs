//  ##########################################################################
//  # File Name: EmailAddressType.cs
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
    /// User defined type Nhibernate mapper for <c>EmailAddress</c> objects.
    /// </summary>
    /// 
    public
    class EmailAddressType:
        ICompositeUserType
    {
        public String[] 
        PropertyNames
        {
            get { return new String[] {"EmailAddress"}; }
        }

        public IType[] 
        PropertyTypes
        {
            get { return new IType[] {NHibernateUtil.String}; }
        }

        public Type 
        ReturnedClass
        {
            get { return typeof(EmailAddress); }
        }

        public bool
        IsMutable
        {
            get { return false; }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>EmailAddressType</c> instance.
        /// </summary>
        /// 
        public
        EmailAddressType() {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        GetPropertyValue(Object component,int property)
        {
            EmailAddress email = (EmailAddress)component;

            if (email == null)
                return null;

            if ( property == 0 ) 
                return email.ToString();
        
            throw new HibernateException("unknown property");
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        SetPropertyValue(Object component,int property,Object value)
        {
            //throw new HibernateException("EmailAddress is immutable");
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
            string  emailColumn = names[0];
            object  contents    = null;

            if( reader == null )
                return null;
            
            contents = 
                NHibernateUtil
                    .String
                    .NullSafeGet(
                        reader,
                        emailColumn,
                        session,
                        owner);
    
            return 
                contents != null 
                    ? new EmailAddress(contents.ToString())
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

            contents = ((EmailAddress)value).ToString();
 
            NHibernateUtil.String.NullSafeSet(st,contents,index,session);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        DeepCopy(Object value) 
        {
            return (EmailAddress)value;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        Disassemble(
            Object              value,
            ISessionImplementor session)
        {
            return (EmailAddress)value;
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
            return (EmailAddress)cached;
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
            return (EmailAddress)original;
        }

    }
}

//  ##########################################################################
