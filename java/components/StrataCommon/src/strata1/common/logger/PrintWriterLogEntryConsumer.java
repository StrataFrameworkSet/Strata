// ##########################################################################
// # File Name:	PrintWriterLogEntryConsumer.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataCommon Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommon Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommon
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.common.logger;

import strata1.common.producerconsumer.AbstractConsumer;
import strata1.common.producerconsumer.NullSelector;
import java.io.PrintWriter;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PrintWriterLogEntryConsumer
    extends AbstractConsumer<ILogEntry>
    implements ILogEntryConsumer
{
    private PrintWriter itsWriter;
    private String      itsFormat;
   
    /************************************************************************
     * Creates a new {@code PrintWriterLogEntryConsumer}. 
     *
     */
    public
    PrintWriterLogEntryConsumer(PrintWriter writer)
    {
        super(new NullSelector<ILogEntry>());
        itsWriter = writer;
        itsFormat = "%1$-28s %2$-10s %3$s\n\n";
   }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected void 
    doConsume(ILogEntry entry) 
    {
        itsWriter
            .printf( 
                itsFormat,
                entry.getTimestamp(),
                entry.getLevel().name(),
                entry.getMessage() )
            .flush();   
    }
}

// ##########################################################################
