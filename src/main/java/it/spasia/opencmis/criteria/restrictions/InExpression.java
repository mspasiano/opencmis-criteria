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
 * $Id: InExpression.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import it.spasia.opencmis.criteria.Criterion;
import it.spasia.opencmis.criteria.CMISContext;
import it.spasia.opencmis.criteria.CMISParameterValue;
import it.spasia.opencmis.criteria.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents <code>IN (values...)</code> expression.
 *
 * @author <a href="mailto:maro.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class InExpression
    implements Criterion
{
    private static final long serialVersionUID = 1L;

    /**
     * Property which value is being compared.
     */
    private final String propertyName;

    /**
     * Values for comparision.
     */
    private final CMISParameterValue<?>[] values;

    /**
     * Construct expression for specified expression.
     *
     * @param aPropertyName
     *            property to compare
     * @param aValues
     *            values for comparision
     */
    protected InExpression( String aPropertyName,
                            CMISParameterValue<?>... aValues )
    {
        this.propertyName = aPropertyName;
        this.values = aValues;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.Criterion#toQueryFragment(it.spasia.opencmis.criteria.CMISContext)
     */
    public String toQueryFragment( CMISContext CMISContext )
    {
        List<String> parametersNames = new ArrayList<String>();

        for ( CMISParameterValue<?> v : values )
        {
            parametersNames.add( ":" + CMISContext.generateParameterName(
                                                                     this.propertyName,
                                                                     v ) );
        }

        final String property = CMISContext.prefix( propertyName );
        
        StringBuilder buffer = new StringBuilder();
        buffer.append( property );
        buffer.append( " IN (" );

        buffer.append( Utils.concatenate( ", ", parametersNames ) );

        buffer.append( ')' );

        return buffer.toString();
    }

}
