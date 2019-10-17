//  ##########################################################################
//  # File Name: TestModule.cs
//  ##########################################################################

using Strata.Foundation.Core.Logging;

namespace Strata.Foundation.StructureMap.Inject
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class TestModule:
        AbstractModule
    {
        public 
        TestModule()
        {
            For<ILogger>()
                .Use<NullLogger>()
                .SetLifecycleTo(DefaultLifecycle);
        }
    }
}

//  ##########################################################################
