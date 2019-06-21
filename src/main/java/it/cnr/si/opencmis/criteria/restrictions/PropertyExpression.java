package it.cnr.si.opencmis.criteria.restrictions;

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

import it.cnr.si.opencmis.criteria.CMISContext;
import it.cnr.si.opencmis.criteria.Criterion;

/**
 * Represents expression comparing two properties.
 *
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class PropertyExpression
        implements Criterion {
    private static final long serialVersionUID = 1L;

    private final String propertyName;

    private final String otherPropertyName;

    private final String operator;

    public PropertyExpression(String propertyName, String otherPropertyName,
                              SimpleExpressionOperator operator) {
        this(propertyName, otherPropertyName,
                (operator != null) ? operator.getStringRepresentation()
                        : "null");
    }

    public PropertyExpression(String propertyName, String otherPropertyName,
                              String operator) {
        this.propertyName = propertyName;
        this.otherPropertyName = otherPropertyName;
        this.operator = operator;
    }

    public String toQueryFragment(CMISContext CMISContext) {
        final String qualifiedPropertyName = CMISContext.prefix(this.propertyName);
        final String qualifiedOtherPropertyName = CMISContext.prefix(this.otherPropertyName);

        StringBuilder buffer = new StringBuilder();
        buffer.append(qualifiedPropertyName);
        buffer.append(" ");
        buffer.append(this.operator);
        buffer.append(" ");
        buffer.append(qualifiedOtherPropertyName);

        return buffer.toString();
    }

}
