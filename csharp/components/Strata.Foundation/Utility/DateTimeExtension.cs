using System;

namespace Strata.Foundation.Utility
{
    public static class DateTimeExtension
    {
        /// <summary>
        /// Truncates a DateTime to the specified time level; useful for removing milliseconds or seconds.
        /// </summary>
        /// <param name="dateTime"></param>
        /// <param name="timeSpan"></param>
        /// <returns></returns>
        public static DateTime Truncate(this DateTime dateTime, TimeSpan timeSpan)
        {
            return dateTime.AddTicks(-(dateTime.Ticks % timeSpan.Ticks));
        }
    }
}
