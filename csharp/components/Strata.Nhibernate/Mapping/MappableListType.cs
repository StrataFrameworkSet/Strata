//  ##########################################################################
//  # File Name: MappableListType.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using NHibernate;
using NHibernate.Engine;
using NHibernate.Type;
using NHibernate.UserTypes;
using Strata.Foundation.Utility;
using Strata.Foundation.Value;
using System;
using System.Data.Common;

namespace Strata.Nhibernate.Mapping
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// User defined type Nhibernate mapper 
    /// for <c>MappableList{T}</c> objects.
    /// </summary>
    /// 
    public
    class MappableListType<T,L>:
        ICompositeUserType
        where L: MappableList<T>, new()
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
            get { return typeof(L); }
        }

        public bool
        IsMutable
        {
            get { return false; }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>MappableListType</c> instance.
        /// </summary>
        /// 
        public
        MappableListType() {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        GetPropertyValue(Object component,int property)
        {
            L mappable = (L)component;
        
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
            L mappable = (L)component;

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
            if ( x == y )
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
            String  contentsColumn = names[0];
            String  contents       = null;

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
    
            return new L() { Contents = contents };
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
            String  contents = "";

            if( value == null )
                return;

            contents = ((L)value).Contents;
 
            NHibernateUtil.String.NullSafeSet(st,contents,index,session);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        DeepCopy(Object value) 
        {
            return (L)value;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object 
        Disassemble(
            Object              value,
            ISessionImplementor session)
        {
            return (L)value;
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
            return (L)cached;
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
            return (L)original;
        }

    }
}

//  ##########################################################################
