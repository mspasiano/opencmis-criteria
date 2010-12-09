package it.spasia.opencmis.criteria.restrictions;

/*
 * Copyright 2010 Marco Spasiano (spasia@libero.it)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * $Id: Junction.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import it.spasia.opencmis.criteria.Criterion;
import it.spasia.opencmis.criteria.CMISContext;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents juction type expression.
 *
 * @author <a href="mailto:spasia@libero.it">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class Junction
    implements Criterion
{
    private static final long serialVersionUID = 1L;

    /**
     * Operator used to perform junction.
     */
    private final Operator operator;

    /**
     * List of criterion.
     */
    private List<Criterion> criterions = new ArrayList<Criterion>();

    /**
     * Creates expression setting operator for junction.
     *
     * @param anOperator
     *            operator for junction
     */
    protected Junction( final LogicalExpressionOperator anOperator )
    {
        this.operator = anOperator;
    }

    /**
     * Adds criterion to junction.
     *
     * @param criterion
     *            criterion to add
     * @return the same junction instance
     */
    public Junction add( Criterion criterion )
    {
        this.criterions.add( criterion );

        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.Criterion#toQueryFragment(it.spasia.opencmis.criteria.CMISContext)
     */
    public String toQueryFragment( CMISContext CMISContext )
    {
        if ( criterions.isEmpty() )
        {
            return "";
        }
        else
        {
            StringBuilder buffer = new StringBuilder( "(" );
            boolean firstRun = true;

            for ( Criterion c : this.criterions )
            {
                if ( !firstRun )
                {
                    buffer.append( ' ' );
                    buffer.append( this.operator.getStringRepresentation() );
                    buffer.append( ' ' );
                }

                buffer.append( c.toQueryFragment( CMISContext ) );
                firstRun = false;
            }
            
            buffer.append( ")" );
            
            return buffer.toString();
        }
    }
}
