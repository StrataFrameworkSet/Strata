//  ##########################################################################
//  # File Name: DateType.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Data.Common;
using NHibernate;
using NHibernate.Engine;
using NHibernate.SqlTypes;
using NHibernate.UserTypes;
using Strata.Foundation.Core.Value;

namespace Strata.Domain.Nhibernate.Mapping
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// User defined type Nhibernate mapper for <c>Date</c> objects.
    /// </summary>
    /// 
    public
    class DateType:
        IUserType
    {
        public SqlType[] 
        SqlTypes { get { return new SqlType[] {SqlTypeFactory.Date}; } }

        public Type ReturnedType { get { return typeof(Date); } }
        public bool IsMutable { get { return false; } }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        NullSafeSet(
            DbCommand           st,
            object              value,
            int                 index,
            ISessionImplementor session)
        {
            DateTime contents;

            if (value == null)
            {
                NHibernateUtil.Date.NullSafeSet(st,null,index,session);
                return;
            }

            contents = ((Date)value).ToDateTime();

            NHibernateUtil.Date.NullSafeSet(st,contents,index,session);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public object
        NullSafeGet(
            DbDataReader        reader,
            string[]            names,
            ISessionImplementor session,
            object              owner)
        {
            string dateColumn = names[0];
            object contents = null;

            if (reader == null)
                return null;

            contents =
                NHibernateUtil
                    .Date
                    .NullSafeGet(
                        reader,
                        dateColumn,
                        session,
                        owner);

            return
                contents != null
                    ? new Date((DateTime)contents)
                    : null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public object 
        Replace(object original,object target,object owner)
        {
            return (Date)original;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public object 
        Assemble(object cached,object owner)
        {
            return (Date)cached;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public object 
        Disassemble(object value)
        {
            return (Date)value;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public new bool
        Equals(object x,object y)
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
        GetHashCode(object x)
        {
            if (x == null)
                return 0;

            return x.GetHashCode();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public object
        DeepCopy(object value)
        {
            return (Date)value;
        }
    }
}

//  ##########################################################################
