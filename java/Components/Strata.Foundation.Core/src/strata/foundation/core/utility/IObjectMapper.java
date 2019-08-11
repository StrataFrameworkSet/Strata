// ##########################################################################
// # File Name:	IObjectMapper.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.core.utility;

/****************************************************************************
 * Maps objects to and from a payload type (most commonly String). 
 */
public 
interface IObjectMapper<T,P>
{
    /************************************************************************
     * Maps object to payload type {P}. 
     *
     * @param  object object being mapped to payload
     * @return payload
     */
    <S extends T> P
    toPayload(S object);
    
    /************************************************************************
     * Maps payload to object type {S}. 
     *
     * @param  type    object type being mapped to
     * @param  payload payload being mapped to object
     * @return 
     */
    <S extends T> S
    toObject(Class<S> type,P payload);
}

// ##########################################################################
