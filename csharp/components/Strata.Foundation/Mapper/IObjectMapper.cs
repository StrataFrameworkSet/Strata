//  ##########################################################################
//  # File Name: IObjectMapper.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################


namespace Strata.Foundation.Mapper
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Maps objects to and from a payload type (most commonly String).
    /// </summary>
    ///  
    public
    interface IObjectMapper<T,P>
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Maps object to payload type. 
        /// </summary>
        /// 
        /// <param name="source">object being mapped to payload</param>
        /// <returns>mapped payload</returns>
        /// 
        P
        ToPayload(T source);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Maps payload to object type.
        /// </summary>
        ///
        /// <param name="payload">payload being mapped to object</param>
        /// <returns>mapped object</returns>
        S
        ToObject<S>(P payload)
            where S: T;
    }
}

//  ##########################################################################
