//  ##########################################################################
//  # File Name: MappableSetType.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using NHibernate;
using NHibernate.Engine;
using NHibernate.Type;
using NHibernate.UserTypes;
using Strata.Foundation.Utility;
using System;
using System.Data.Common;

namespace Strata.Nhibernate.Mapping
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// User defined type Nhibernate mapper 
    /// for <c>MappableSetType{T}</c> objects.
    /// </summary>
    /// 
    public
    class MappableSetType<T,S>:
        ICompositeUserType
        where S: MappableSet<T>, new()
    {
        public String[] 
        PropertyNames
        {
            get { return new String[] {"Contents"}; }
        }

        public IType[] 
        PropertyTypes
        {
            get { return new IType[] {NHibernateUtil.String}; }
        }

        public Type 
        ReturnedClass
        {
            get { return typeof(S); }
        }

        public bool
        IsMutable
        {
            get { return false; }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>MappableSetType</c> instance.
        /// </summary>
        /// 
        public
        MappableSetType() {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        GetPropertyValue(Object component,int property)
        {
            S mappable = (S)component;

            if (mappable == null)
                return null;

            if ( property == 0 ) 
                return mappable.Contents;
        
            throw new HibernateException("unknown property");
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        SetPropertyValue(Object component,int property,Object value)
        {
            S mappable = (S)component;

            if (mappable == null)
                return;

            if (property == 0)
                mappable.Contents = (string)value;

            throw new HibernateException("unknown property");
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public new bool
        Equals(Object x,Object y) 
        {
            if (ReferenceEquals(x,y))
                return true;

            if (x == null || y == null)
                return false;

            return x.Equals(y);
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
            string contentsColumn = names[0];
            string contents       = null;

            if( reader == null )
                return null;
            
            contents = 
                NHibernateUtil
                    .String
                    .NullSafeGet(
                        reader,
                        contentsColumn,
                        session,
                        owner).ToString();
    
            return new S() { Contents = contents };
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
            string contents = string.Empty;

            if (value == null)
            {
                NHibernateUtil.String.NullSafeSet(st,null,index,session);
                return;
            }

            contents = ((S)value).Contents;
 
            NHibernateUtil.String.NullSafeSet(st,contents,index,session);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        DeepCopy(Object value) 
        {
            return (S)value;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        Disassemble(
            Object              value,
            ISessionImplementor session)
        {
            return (S)value;
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
            return (S)cached;
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
            return (S)original;
        }

    }
}

//  ##########################################################################
