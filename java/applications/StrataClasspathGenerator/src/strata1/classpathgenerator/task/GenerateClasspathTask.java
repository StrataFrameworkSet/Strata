// ##########################################################################
// # File Name:	GenerateClasspathTask.java
// #
// # Copyright:	2015, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataClasspathGenerator Framework.
// #
// #   			The StrataClasspathGenerator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataClasspathGenerator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataClasspathGenerator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.classpathgenerator.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;

/****************************************************************************
 * Implements an Ant task for converting an Ant classpath variable into
 * an Eclipse .classpath file.
 * 
 * <generateClasspath
 *     classpath="classpath"
 *     outputFile=".classpath"
 *     source="src"
 *     output="bin"
 *     container="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.7">
 *     <variable name="REPO_ROOT" value="C:/System/java/.repository"/>
 * </generateClasspath>
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class GenerateClasspathTask
    extends Task
{
    private Path           itsClasspath;
    private File           itsOutputFile;
    private String         itsSource;
    private String         itsOutput;
    private String         itsContainer;
    private List<Variable> itsVariables;
    private IOutputWriter  itsWriter;
    
    /************************************************************************
     * Creates a new GenerateClasspathTask. 
     *
     */
    public 
    GenerateClasspathTask()
    {
        this( new FileOutputWriter() );
    }

    /************************************************************************
     * Creates a new GenerateClasspathTask. 
     *
     */
    public 
    GenerateClasspathTask(IOutputWriter writer)
    {
        itsClasspath  = null;
        itsOutputFile = null;
        itsSource     = null;
        itsOutput     = null;
        itsContainer  = null;
        itsVariables  = new ArrayList<Variable>();
        itsWriter     = writer;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    execute() 
        throws BuildException
    {
        if ( getClasspath() == null )
            throw new BuildException( "classpath is null" );
        
        if ( getOutputFile() == null )
            throw new BuildException( "outputFile is null" );
        
        try
        {
            writeOutput( toEclipseClasspath() );
        }
        catch(IOException e)
        {
            throw new BuildException( e );
        }
    }

    /************************************************************************
     *  
     *
     * @param classpath
     */
    public void
    setClasspath(Path classpath)
    {
        itsClasspath = classpath;
    }
    
    /************************************************************************
     *  
     *
     * @param outputFile
     */
    public void
    setOutputFile(File outputFile)
    {
        itsOutputFile = outputFile;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public Variable
    createVariable()
    {
        Variable variable = new Variable();
        
        itsVariables.add( variable );
        return variable;
    }
    
    /************************************************************************
     *  
     *
     * @param source
     */
    public void
    setSource(String source)
    {
        itsSource = source;
    }
    
    /************************************************************************
     *  
     *
     * @param output
     */
    public void
    setOutput(String output)
    {
        itsOutput = output;
    }
    
    /************************************************************************
     *  
     *
     * @param container
     */
    public void
    setContainer(String container)
    {
        itsContainer = container;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public Path
    getClasspath()
    {
        return itsClasspath;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public File
    getOutputFile()
    {
        return itsOutputFile;
    }
    
    /************************************************************************
     *  
     *
     * @param contents
     * @throws IOException
     */
    protected void
    writeOutput(String contents) 
        throws IOException
    {
        itsWriter
            .setStorage( getOutputFile() )
            .writeOutput( contents );
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    protected String
    toEclipseClasspath()
    {
        StringBuilder builder = new StringBuilder();
        
        beginClasspath( builder );        
        insertDefaultElements( builder );
        
        for (String element : getClasspath().list())
            mapClasspathElement( builder,element );
                
        endClasspath( builder );
        
        return builder.toString();
    }

    /************************************************************************
     *  
     *
     * @param builder
     */
    private void 
    beginClasspath(StringBuilder builder)
    {
        builder
            .append( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" )
            .append( "<classpath>\n" );
    }

    /************************************************************************
     *  
     *
     * @param builder
     */
    private void 
    insertDefaultElements(StringBuilder builder)
    {
        builder
            .append( "    <classpathentry kind=\"src\" path=\"" )
            .append( itsSource )
            .append( "\"/>\n" )
            .append( "    <classpathentry kind=\"output\" path=\"" )
            .append( itsOutput )
            .append( "\"/>\n" )
            .append( "    <classpathentry kind=\"con\" path=\"" )
            .append( itsContainer )
            .append( "\"/>\n" );
    }

    /************************************************************************
     *  
     *
     * @param builder
     * @param element
     */
    private void
    mapClasspathElement(StringBuilder builder,String element)
    {
        builder
            .append( "    <classpathentry kind=\"" )
            .append( mapKind(element) )
            .append( "\" path=\"")
            .append( mapPath(element) )
            .append( "\"/>\n" );
    }
    
    /************************************************************************
     *  
     *
     * @param element
     * @return
     */
    private String 
    mapKind(String element)
    {
        return "lib";
    }

    /************************************************************************
     *  
     *
     * @param element
     * @return
     */
    private Object 
    mapPath(String element)
    {
        return element;
    }

    /************************************************************************
     *  
     *
     * @param builder
     */
    private void 
    endClasspath(StringBuilder builder)
    {
        builder.append( "</classpath>" );
    }
}

// ##########################################################################
