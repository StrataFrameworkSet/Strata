// ##########################################################################
// # File Name:	GenerateClasspathTaskTest.java
// #
// # Copyright:	2015, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataClasspathGeneratorTest Framework.
// #
// #   			The StrataClasspathGeneratorTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataClasspathGeneratorTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataClasspathGeneratorTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.classpathgenerator.task;

import static org.junit.Assert.*;
import java.io.File;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class GenerateClasspathTaskTest
{
    private GenerateClasspathTask itsTarget;
    private Project               itsProject;
    private StringOutputWriter    itsWriter;
    private Path                  itsClasspath;
    
    /************************************************************************
     *  
     *
     * @throws Exception
     */
    @Before
    public void 
    setUp() throws Exception
    {
        Variable variable = null;
        Path     value    = null;
        
        itsWriter = new StringOutputWriter();
        itsTarget = new GenerateClasspathTask( itsWriter );
        
        itsProject = new Project();
        itsClasspath = new Path( itsProject );
        value        = new Path( itsProject );
        itsClasspath.setPath( "C:/repository/component/x.jar;C:/library/y.jar" );
        itsTarget.setClasspath( itsClasspath );
        itsTarget.setOutputFile(  new File("C:/temp/classpath.gen" ) );
        itsTarget.setSource( "src" );
        itsTarget.setOutput( "bin" );
        itsTarget.setContainer( "org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.7" );
        variable = itsTarget.createVariable();
        variable.setName( "REPO_ROOT" );
        value.setPath( "C:/repository" );
        variable.setValue( value );
    }

    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @After
    public void 
    tearDown() throws Exception
    {
        itsTarget = null;
    }

    /**
     * Test method for {@link GenerateClasspathTask#execute()}.
     */
    @Test
    public void 
    testExecute() throws Exception
    {
        itsTarget.execute();
        System
            .out
            .println( itsWriter.getOutput() );
    }

}

// ##########################################################################
