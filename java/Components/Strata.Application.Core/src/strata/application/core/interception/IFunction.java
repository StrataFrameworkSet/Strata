//////////////////////////////////////////////////////////////////////////////
// IFunction.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

public
interface IFunction<I,O>
{
    O
    apply(I input)
        throws Throwable;
}

//////////////////////////////////////////////////////////////////////////////