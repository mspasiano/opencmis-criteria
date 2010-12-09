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
 * $Id: NotNullExpression.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import it.spasia.opencmis.criteria.Criterion;
import it.spasia.opencmis.criteria.CMISContext;

/**
 * Represents <code>IS NOT NULL</code> expression.
 *
 * @author <a href="mailto:maro.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class NotNullExpression
    implements Criterion
{
    private static final long serialVersionUID = 1L;

    /**
     * Property which value is being compared.
     */
    private final String propertyName;

    /**
     * Construct <code>IS NOT NULL</code> expression for specified property.
     *
     * @param aPropertyName
     *            property to check
     */
    protected NotNullExpression( String aPropertyName )
    {
        this.propertyName = aPropertyName;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.Criterion#toQueryFragment(it.spasia.opencmis.criteria.CMISContext)
     */
    public String toQueryFragment( CMISContext CMISContext )
    {
        final String qualifiedPropertyName =CMISContext.prefix( this.propertyName );
        return qualifiedPropertyName + " IS NOT NULL";
    }

}
