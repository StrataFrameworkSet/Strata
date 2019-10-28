//  ##########################################################################
//  # File Name: MappableListType.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Data.Common;
using NHibernate;
using NHibernate.Engine;
using NHibernate.Type;
using NHibernate.UserTypes;
using Strata.Foundation.Core.Utility;

namespace Strata.Domain.Nhibernate.Mapping
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// User defined type Nhibernate mapper 
    /// for <c>MappableListType{T}</c> objects.
    /// </summary>
    /// 
    public
    class MappableListType<T,L>:
        ICompositeUserType
        where L: MappableList<T>,new()
    {
        public String[]
        PropertyNames
        {
            get { return new String[] { "Contents" }; }
        }

        public IType[]
        PropertyTypes
        {
            get { return new IType[] { NHibernateUtil.StringClob }; }
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
        MappableListType()
        { }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object
        GetPropertyValue(Object component,int property)
        {
            L mappable = (L)component;

            if (mappable == null)
                return null;

            if (property == 0)
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

            if (mappable == null)
                return;

            if (property == 0)
            {
                mappable.Contents = (string)value;
                return;
            }

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
            if (x == null)
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
            string contentsColumn = names[0];
            object contents = null;

            if (reader == null)
                return null;

            contents =
                NHibernateUtil
                    .StringClob
                    .NullSafeGet(
                        reader,
                        contentsColumn,
                        session,
                        owner);

            return 
                contents != null 
                    ? new L() { Contents = contents.ToString() }
                    : null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void
        NullSafeSet(
            DbCommand st,
            Object value,
            int index,
            bool[] settable,
            ISessionImplementor session)
        {
            string contents = string.Empty;

            if (value == null)
            {
                NHibernateUtil.StringClob.NullSafeSet(st,null,index,session);
                return;
            }

            contents = ((L)value).Contents;

            NHibernateUtil.StringClob.NullSafeSet(st,contents,index,session);
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
            Object value,
            ISessionImplementor session)
        {
            return (L)value;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object
        Assemble(
            Object cached,
            ISessionImplementor session,
            Object owner)
        {
            return (L)cached;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Object
        Replace(
            Object original,
            Object target,
            ISessionImplementor session,
            Object owner)
        {
            return (L)original;
        }

    }
}

//  ##########################################################################
