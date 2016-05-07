package strata1.common.authentication;

public 
interface ICredential
{
    public <T> void 
    setField(String name,T field);
    
    public <T> T
    getField(Class<T> type,String name);
}

// ##########################################################################
