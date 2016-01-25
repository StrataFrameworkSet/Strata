using System;
using Strata.Common.Logging;
using Microsoft.Practices.Unity;

namespace Strata.Server
{
    public class Copyright
    {
        [Dependency]
        public IUnityContainer Container { get; set; }

        private const string COPYRIGHT = "Loading...\n\n"
    + "========================================================================\n"
    + "|                                                                      |\n"
    + "|        =======                                                       |\n"
    + "|     ===||== ||  The Capital Group Companies, Inc.                    |\n"
    + "|     || |||| ||  © Copyright 2011-2012                                |\n"
    + "|     || ==||===  All rights reserved.  This program is covered        |\n"
    + "|     =======     by US and international copyright laws.              |\n"
    + "|                                                                      |\n"
    + "|    IDS X-Wing .NET Service Platform                                  |\n"
    + "|                                                                      |\n"
    + "========================================================================\n";

        /// <summary>
        /// Displays the copyright information.
        /// </summary>
        /// <param name="includeInLog">
        /// If true, the information is posted using the Enterprise Library Logging Block.  If false, the information
        /// is displayed only on the Console.
        /// </param>
        public void Display(bool includeInLog)
        {
            if (includeInLog)
            {
                var log = Container.Resolve<ILogger>();
                log.Info(COPYRIGHT);
            }
            else
            {
                Console.WriteLine(COPYRIGHT);
            }
        }
    }

}
