// ##########################################################################
// # File Name:	MockMessageListener.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataIntegratorTest Framework.
// #
// #   			The StrataIntegratorTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataIntegratorTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataIntegratorTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.integration.messaging;

import java.util.ArrayList;
import java.util.List;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class MockMessageListener
    implements IMessageListener
{
    private final List<IStringMessage> itsStringMessages;
    private final List<IMapMessage>    itsMapMessages;
    private final List<IObjectMessage> itsObjectMessages;
    private final List<IBytesMessage>  itsBytesMessages;
    
    /************************************************************************
     * Creates a new {@code MockMessageListener}. 
     *
     */
    public 
    MockMessageListener()
    {
        itsStringMessages = new ArrayList<IStringMessage>();
        itsMapMessages    = new ArrayList<IMapMessage>();
        itsObjectMessages = new ArrayList<IObjectMessage>();
        itsBytesMessages  = new ArrayList<IBytesMessage>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onMessage(IStringMessage message)
    {
        itsStringMessages.add( message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onMessage(IMapMessage message)
    {
        itsMapMessages.add( message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onMessage(IObjectMessage message)
    {
        itsObjectMessages.add( message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onMessage(IBytesMessage message)
    {
        itsBytesMessages.add( message );
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public List<IStringMessage>
    getStringMessages()
    {
        return itsStringMessages;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public List<IMapMessage>
    getMapMessages()
    {
        return itsMapMessages;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public List<IObjectMessage>
    getObjectMessages()
    {
        return itsObjectMessages;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public List<IBytesMessage>
    getBytesMessages()
    {
        return itsBytesMessages;
    }
}

// ##########################################################################
