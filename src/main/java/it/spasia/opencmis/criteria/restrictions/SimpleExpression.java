package it.spasia.opencmis.criteria.restrictions;

/*
 * Copyright 2010 Marco Spasiano (maro.spasiano@gmail.com)
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
 * $Id: SimpleExpression.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import it.spasia.opencmis.criteria.Criterion;
import it.spasia.opencmis.criteria.CMISContext;
import it.spasia.opencmis.criteria.CMISParameterValue;
import it.spasia.opencmis.criteria.Utils;

/**
 * Represents simple expression in form of
 * <code>property operator value</code>
 *
 * @author <a href="mailto:maro.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class SimpleExpression implements Criterion
{
    private static final long serialVersionUID = 1L;

    /**
     * Property which value is being compared.
     */
    private final String propertyName;

    private final CMISParameterValue<?> value;

    private final SimpleExpressionOperator operator;

    private boolean ignoreCase = false;

    protected SimpleExpression( String aPropertyName, String value, SimpleExpressionOperator anOperator,
                                MatchMode matchMode )
    {
        this( aPropertyName, Utils.constructCMISParameterValue( matchMode.toMatchString( value ) ), anOperator );
    }

    protected SimpleExpression(
            String aPropertyName,
            CMISParameterValue<?> parameterValue,
            SimpleExpressionOperator anOperator )
    {
        this.propertyName = aPropertyName;
        this.value = parameterValue;
        this.operator = anOperator;
    }

    public SimpleExpression ignoreCase()
    {
        this.ignoreCase = true;

        return this;
    }

    public String toQueryFragment( CMISContext CMISContext )
    {
        final String property = CMISContext.prefix( propertyName );

        CMISParameterValue<?> parameterValue = value;
        if ( ignoreCase )
        {
            parameterValue = Utils.constructCMISParameterValue( this.value.getValue().toString().toLowerCase() );
        }
        String parameterName = CMISContext.generateParameterName( propertyName, parameterValue );

        StringBuilder buffer = new StringBuilder();

        if ( ignoreCase )
        {
            buffer.append( "LOWER(" );
        }
        buffer.append( property );
        if ( ignoreCase )
        {
            buffer.append( ')' );
        }

        buffer.append( ' ' );
        buffer.append( operator.getStringRepresentation() );
        buffer.append( ' ' );

        buffer.append( ":" );
        buffer.append( parameterName );

        return buffer.toString();
    }

}
