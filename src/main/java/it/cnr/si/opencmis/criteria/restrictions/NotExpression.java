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
 * $Id: NotExpression.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import it.cnr.si.opencmis.criteria.CMISContext;
import it.cnr.si.opencmis.criteria.Criterion;

/**
 * Represents <code>NOT</code> expression.
 *
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class NotExpression
        implements Criterion {
    private static final long serialVersionUID = 1L;

    /**
     * Criterion expression to negate.
     */
    private final Criterion expression;

    /**
     * Construct negation of given expression.
     *
     * @param anExpression expression to negate
     */
    protected NotExpression(Criterion anExpression) {
        this.expression = anExpression;
    }

    /*
     * (non-Javadoc)
     *
     * @see Criterion#toQueryFragment(CMISContext)
     */
    public String toQueryFragment(CMISContext CMISContext) {
        return "NOT " + expression.toQueryFragment(CMISContext);
    }

}
