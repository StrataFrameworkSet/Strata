//  ##########################################################################
//  # File Name: Date.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Runtime.Serialization;

namespace Strata.Foundation.Core.Value
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Wrapper around <c>DateTime</c> that only exposes the date part.
    /// </summary>
    ///  
    [DataContract]
    public
    class Date:
        IEquatable<Date>,
        IComparable<Date>
    {
        [DataMember]
        public int Year { get { return date.Year; } }
        [DataMember]
        public int Month { get { return date.Month; } }
        [DataMember]
        public int Day { get { return date.Day; } }

        public static Date Now { get { return new Date(DateTime.Now); } }
        public static Date MinValue { get { return new Date(DateTime.MinValue); } }
        public static Date MaxValue { get { return new Date(DateTime.MaxValue); } }

        private DateTime date;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Create new instance of <c>Date</c> using  
        /// the specified <c>DateTime</c>.
        /// </summary>
        ///  
        public
        Date(DateTime imp)
        {
            date = imp.Date;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Create new instance of <c>Date</c> using  
        /// the specified year, month, and day.
        /// </summary>
        ///  
        public
        Date(int year,int month,int day)
        {
            date = new DateTime(year,month,day).Date;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public override bool
        Equals(object other)
        {
            if (other is Date)
                return Equals((Date)other);

            return false;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public virtual bool 
        Equals(Date other)
        {
            return date.Equals(other.date);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public virtual int 
        CompareTo(Date other)
        {
            return date.CompareTo(other.date);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public override int
        GetHashCode()
        {
            return date.GetHashCode();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        public virtual bool
        IsLeapYear()
        {
            return DateTime.IsLeapYear(Year);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public virtual DateTime
        ToDateTime()
        {
            return date;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public static bool
        operator<= (Date x,Date y)
        {
            return x.date <= y.date;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public static bool
        operator>= (Date x,Date y)
        {
            return x.date >= y.date;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public static bool
        operator< (Date x,Date y)
        {
            return x.date < y.date;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public static bool
        operator> (Date x,Date y)
        {
            return x.date > y.date;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public static Date
        operator+ (Date date,TimeSpan span)
        {
            return new Date(date.date + span);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public static TimeSpan
        operator- (Date x,Date y)
        {
            return x.date - y.date;
        }

    }
}

//  ##########################################################################
