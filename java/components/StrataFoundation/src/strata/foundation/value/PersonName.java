// ##########################################################################
// # File Name:	PersonName.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.value;

import java.io.Serializable;
import strata.foundation.utility.ICopyable;

/****************************************************************************
 * 
 */
public 
class PersonName
    implements ICopyable,Serializable,Comparable<PersonName>
{
    private static final long serialVersionUID = 5784164278230827162L;
    
    private final String itsFirstName;
    private final String itsMiddleName;
    private final String itsLastName;
    
    /************************************************************************
     * Creates a new PersonName. 
     *
     * @param first
     * @param middle
     * @param last
     */
    public 
    PersonName(String first,String middle,String last)
    {
        itsFirstName  = first != null ? first : "";
        itsMiddleName = middle != null ? middle : "";
        itsLastName   = last != null ? last : "";
    }

    /************************************************************************
     * Creates a new PersonName. 
     *
     * @param first
     * @param last
     */
    public 
    PersonName(String first,String last)
    {
        this( first,"",last );
    }
    
    /************************************************************************
     * Creates a new PersonName. 
     *
     * @param other
     */
    public 
    PersonName(PersonName other)
    {
        this( 
            other.getFirstName(),
            other.getMiddleName(),
            other.getLastName() );
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public PersonName 
    copy()
    {
        return new PersonName( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    compareTo(PersonName other)
    {
        int result = 
            getLastName().compareToIgnoreCase( other.getLastName() );
        
        if ( result != 0 )
            return result;
        
        result = 
            getFirstName().compareToIgnoreCase( other.getFirstName() );
        
        if ( result != 0 )
            return result;
        
        result = 
            getMiddleName().compareToIgnoreCase( other.getMiddleName() );
        
        return result;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    equals(Object other)
    {
        if ( other == null ) 
            return false;
        
        if ( other instanceof PersonName)
        {
            PersonName p = (PersonName)other;
            
            return 
                getFirstName().equalsIgnoreCase( p.getFirstName() ) &&
                getMiddleName().equalsIgnoreCase( p.getMiddleName() ) &&
                getLastName().equalsIgnoreCase( p.getLastName() );
        }
        
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    hashCode()
    {
        int hash = 31;
        
        hash = 31 * hash + itsFirstName.hashCode();
        hash = 31 * hash + itsMiddleName.hashCode();
        hash = 31 * hash + itsLastName.hashCode();
        
        return hash; 
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    toString()
    {
        StringBuilder output = new StringBuilder();
        
        output.append( getFirstName() );
        
        if ( hasMiddleName() )
            output.append( " " ).append( getMiddleName() );
        
        if ( hasLastName() )
            output.append( " " ).append( getLastName() );
        
        
        return output.toString().trim();
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public String 
    getFirstName()
    {
        return itsFirstName;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getMiddleName()
    {
        return itsMiddleName;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getLastName()
    {
        return itsLastName;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public boolean
    hasFirstName()
    {
        return 
            itsFirstName != null &&
            !itsFirstName.isEmpty();
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public boolean
    hasMiddleName()
    {
        return 
            itsMiddleName != null &&
            !itsMiddleName.isEmpty();
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public boolean
    hasLastName()
    {
        return 
            itsLastName != null &&
            !itsLastName.isEmpty();
    }
}

// ##########################################################################
