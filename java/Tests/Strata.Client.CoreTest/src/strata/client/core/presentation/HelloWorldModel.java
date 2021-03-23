//////////////////////////////////////////////////////////////////////////////
// HelloWorldModel.java
//////////////////////////////////////////////////////////////////////////////

package strata.client.core.presentation;

public
class HelloWorldModel
    implements IHelloWorldModel
{
    private final String itsName;
    private final String itsGreeting;

    public
    HelloWorldModel(String name,String greeting)
    {
        itsName = name;
        itsGreeting = greeting;
    }

    @Override
    public String
    getName()
    {
        return itsName;
    }

    @Override
    public String
    getGreeting()
    {
        return itsGreeting;
    }

    @Override
    public String
    getPersonalizedGreeting()
    {
        return getGreeting() + ' ' + getName();
    }
}

//////////////////////////////////////////////////////////////////////////////
