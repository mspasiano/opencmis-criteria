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
 * $Id: Disjunction.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

/**
 * Represents disjunction (OR) expression.
 *
 * @author <a href="mailto:spasia@libero.it">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class Disjunction
    extends Junction
{
    private static final long serialVersionUID = 1L;

    /**
     * Creates instance of this expression.
     */
    protected Disjunction()
    {
        super( LogicalExpressionOperator.OR );
    }
}
