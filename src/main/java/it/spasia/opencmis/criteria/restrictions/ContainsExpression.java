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
 * $Id: PropertyExpression.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import it.spasia.opencmis.criteria.Criterion;
import it.spasia.opencmis.criteria.CMISContext;
import it.spasia.opencmis.criteria.CMISParameterValue;

/**
 * Represents expression comparing two properties.
 *
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class ContainsExpression
    implements Criterion
{
    private static final long serialVersionUID = 1L;
    /**
     * Property which value is being compared.
     */
    private final String propertyName;
    private CMISParameterValue<?> value;

    protected ContainsExpression(String properyName, CMISParameterValue<?> aValue )
    {
    	this.propertyName = properyName;
    	this.value = aValue;
    }
    
    protected ContainsExpression(CMISParameterValue<?> aValue )
    {
    	this(null, aValue);
    }

    public String toQueryFragment( CMISContext CMISContext )
    {
    	String parameterName = CMISContext.generateParameterName("contains", value );    	
        StringBuilder buffer = new StringBuilder();
        String alias = CMISContext.getTypeAlias();
        buffer.append( " CONTAINS " );
        buffer.append( " ( " );
        buffer.append( alias );
        buffer.append( " , " );
        if (propertyName !=  null){
            buffer.append( "'" );
            buffer.append( propertyName );
            buffer.append( ":\\''" );
        }
        buffer.append(parameterName);
        if (propertyName !=  null){
            buffer.append( "\\''" );
        }
        buffer.append( " ) " );
        return buffer.toString();
    }

}
