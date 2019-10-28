//  ##########################################################################
//  # File Name: Optional{T}.cs
//  ##########################################################################

using System;
using System.Collections.Generic;

namespace Strata.Foundation.Core.Utility
{
    using System.Collections;
    using Action = System.Action;

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    struct Optional<T>
        where T: class
    {
        private static readonly Optional<T> EMPTY = 
            new Optional<T>(null,true);

        private readonly T itsValue;

        private 
        Optional(T value,bool nullable)
        {
            if (value == null && !nullable)
                throw 
                    new NullReferenceException(
                        "optional value cannot be null");

            itsValue = value;
        }

        public Optional<U>
        Map<U>(Func<T,U> transformer)
            where U:class
        {
            if (IsPresent())
                return 
                    Optional<U>.OfNullable(transformer(itsValue));

            return Optional<U>.Empty();
        }

        public Optional<U>
        FlatMap<U>(Func<T,Optional<U>> transformer)
            where U : class
        {
            if (IsPresent())
                return
                    Optional<U>.OfNullable(transformer(itsValue).itsValue);

            return Optional<U>.Empty();
        }

        public Optional<T>
        IfPresent(Action<T> consumer)
        {
            if (IsPresent())
                consumer.Invoke(itsValue);

            return this;
        }

        public Optional<T>
        IfPresentOrElse(Action<T> consumer,Action action)
        {
            if (IsPresent())
                consumer.Invoke(itsValue);
            else
                action.Invoke();
            
            return this;
        }

        public U
        IfPresentOrElse<U>(Func<T,U> present,Func<U> notPresent)
        {
            if (IsPresent())
                return present.Invoke(itsValue);

            return notPresent.Invoke();
        }

        public T
        Get()
        {
            if (IsPresent())
                return itsValue;

            throw
                new NullReferenceException(
                    "optional value not present");
        }

        public T
        GetOrDefault(T defaultValue)
        {
            return
                IsPresent()
                    ? itsValue
                    : defaultValue;
        }

        public bool
        IsPresent()
        {
            return itsValue != null;
        }

        public static Optional<T>
        Empty()
        {
            return EMPTY;
        }

        public static Optional<T>
        Of(T value)
        {
            return new Optional<T>(value,false);
        }

        public static Optional<T>
        OfNullable(T value)
        {
            return 
                value != null 
                    ? new Optional<T>(value,true)
                    : EMPTY;
        }

        public static explicit 
        operator T(Optional<T> optional)
        {
            return optional.Get();
        }
    }
}

//  ##########################################################################
