package it.spasia.opencmis.criteria.restrictions;

/*
 * Copyright 2010 Marco Spasiano (marco.spasiano@gmail.com)
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
 * $Id: BetweenExpression.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import it.spasia.opencmis.criteria.Criterion;
import it.spasia.opencmis.criteria.CMISContext;
import it.spasia.opencmis.criteria.CMISParameterValue;

/**
 * Between expression implementation.
 *
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class BetweenExpression
    implements Criterion
{
    private static final long serialVersionUID = 1L;

    /**
     * Property which value is being compared.
     */
    private final String propertyName;

    /**
     * Low boundary for between expression.
     */
    private final CMISParameterValue<?> low;

    /**
     * High boundary for between expression.
     */
    private final CMISParameterValue<?> high;

    /**
     * Should it be NOT BETWEEN expression.
     */
    private final boolean negate;

    /**
     * Low boundary for eq.
     */
    private final boolean lowEq;

    /**
     * High boundary for eq.
     */
    private final boolean highEq;
    
    /**
     * Construct expression for specified expression.
     *
     * @param aPropertyName
     *            property to compare
     * @param lowValue
     *            between low boundary value
     * @param highValue
     *            between high boundary value
     */
    protected BetweenExpression( String aPropertyName,
            CMISParameterValue<?> lowValue,
            CMISParameterValue<?> highValue )
	{
		this( aPropertyName, lowValue, highValue, false, true, true);
	}


    /**
     * Construct expression for specified expression.
     *
     * @param aPropertyName
     *            property to compare
     * @param lowValue
     *            between low boundary value
     * @param highValue
     *            between high boundary value
     * @param negate
     *            should it be NOT BETWEEN expression
     */
    protected BetweenExpression( String aPropertyName,
                                 CMISParameterValue<?> lowValue,
                                 CMISParameterValue<?> highValue, 
                                 boolean negate,
                                 boolean lowEq,
                                 boolean highEq)
    {
        this.propertyName = aPropertyName;
        this.low = lowValue;
        this.high = highValue;
        this.negate = negate;
        this.lowEq = lowEq;
        this.highEq = highEq;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.Criterion#toQueryFragment(it.spasia.opencmis.criteria.CMISContext)
     */
    public String toQueryFragment( CMISContext CMISContext )
    {
        String lowParameterName =
            CMISContext.generateParameterName( propertyName, low );
        String highParameterName =
            CMISContext.generateParameterName( propertyName, high );

        StringBuilder buffer = new StringBuilder();
        buffer.append( this.propertyName );
        buffer.append( " " );
        if ( negate )
        	buffer.append( lowEq?SimpleExpressionOperator.LE: SimpleExpressionOperator.LT);
    	else	
        	buffer.append( lowEq?SimpleExpressionOperator.GE: SimpleExpressionOperator.GT);
        buffer.append( " " );
        buffer.append( lowParameterName );
        buffer.append( " AND " );
        buffer.append( this.propertyName );
        buffer.append( " " );
        if ( negate )
        	buffer.append( highEq?SimpleExpressionOperator.GE: SimpleExpressionOperator.GT);
        else
        	buffer.append( highEq?SimpleExpressionOperator.LE: SimpleExpressionOperator.LT);
        buffer.append( " " );
        buffer.append( highParameterName );

        return buffer.toString();
    }

}
