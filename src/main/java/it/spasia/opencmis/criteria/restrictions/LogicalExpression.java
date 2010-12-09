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
 * $Id: LogicalExpression.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import it.spasia.opencmis.criteria.Criterion;
import it.spasia.opencmis.criteria.CMISContext;

/**
 * Represends logical expression.
 *
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class LogicalExpression
    implements Criterion
{
    private static final long serialVersionUID = 1L;

    /**
     * Left hand operand.
     */
    private final Criterion lhs;

    /**
     * Right hand operand.
     */
    private final Criterion rhs;

    /**
     * Logical operator.
     */
    private final LogicalExpressionOperator operator;

    /**
     * Constructs expression with all required data.
     *
     * @param aLhs
     *            left hand operand
     * @param aRhs
     *            right hand operand
     * @param anOperator
     *            logical operator
     */
    protected LogicalExpression( Criterion aLhs, Criterion aRhs,
                                 LogicalExpressionOperator anOperator )
    {
        this.lhs = aLhs;
        this.rhs = aRhs;
        this.operator = anOperator;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.Criterion#toQueryFragment(it.spasia.opencmis.criteria.CMISContext)
     */
    public String toQueryFragment( CMISContext CMISContext )
    {
        StringBuilder buffer = new StringBuilder();

        buffer.append( this.lhs.toQueryFragment( CMISContext ) );
        buffer.append( ' ' );
        buffer.append( this.operator.getStringRepresentation() );
        buffer.append( ' ' );
        buffer.append( this.rhs.toQueryFragment( CMISContext ) );

        return buffer.toString();
    }
}
