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
 * $Id: Restrictions.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import it.cnr.si.opencmis.criteria.Criterion;
import it.cnr.si.opencmis.criteria.CMISParameterValue;
import it.cnr.si.opencmis.criteria.Utils;

import java.util.Collection;


/**
 * Utility class for using criterions/restrictions.
 *
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class Restrictions
{
    /**
     * Private constructor to prevent from creating instances of this utitlity
     * class.
     */
    private Restrictions()
    {
    }

    /**
     * @return conjunction (AND) criterion
     */
    public static Conjunction conjunction()
    {
        return new Conjunction();
    }

    /**
     * @return disjunction (OR) criterion
     */
    public static Disjunction disjunction()
    {
        return new Disjunction();
    }

    /**
     * Returns AND logical expression.
     *
     * @param lhs
     *            left hand operand
     * @param rhs
     *            right hand operand
     * @return AND logical expression of provided operands
     */
    public static LogicalExpression and( Criterion lhs, Criterion rhs )
    {
        return new LogicalExpression( lhs, rhs, LogicalExpressionOperator.AND );
    }

    /**
     * Returns OR logical expression.
     *
     * @param lhs
     *            left hand operand
     * @param rhs
     *            right hand operand
     * @return OR logical expression of provided operands
     */
    public static LogicalExpression or( Criterion lhs, Criterion rhs )
    {
        return new LogicalExpression( lhs, rhs, LogicalExpressionOperator.OR );
    }

    /**
     * Negates provided expression.
     *
     * @param expression
     *            expression to negate
     * @return negated provided expression
     */
    public static Criterion not( Criterion expression )
    {
        return new NotExpression( expression );
    }

    /**
     * Compares specified property to provided value in EQUAL expression.
     *
     * @param propertyName
     *            property to compare
     * @param value
     *            value compared with property
     * @return EQUAL expression
     */
    public static SimpleExpression eq( String propertyName, Object value )
    {
        return new SimpleExpression( propertyName,
                                     Utils.constructCMISParameterValue( value ),
                                     SimpleExpressionOperator.EQ );
    }

    /**
     * Compares specified property to provided value in NOT EQUAL expression.
     *
     * @param propertyName
     *            property to compare
     * @param value
     *            value compared with property
     * @return NOT EQUAL expression
     */
    public static SimpleExpression ne( String propertyName, Object value )
    {
        return new SimpleExpression( propertyName,
                                     Utils.constructCMISParameterValue( value ),
                                     SimpleExpressionOperator.NE );
    }

    /**
     * Compares specified property to provided value in GREATER THAN expression.
     *
     * @param propertyName
     *            property to compare
     * @param value
     *            value compared with property
     * @return GREATER THAN expression
     */
    public static SimpleExpression gt( String propertyName, Object value )
    {
        return new SimpleExpression( propertyName,
                                     Utils.constructCMISParameterValue( value ),
                                     SimpleExpressionOperator.GT );
    }

    /**
     * Compares specified property to provided value in LESS THAN expression.
     *
     * @param propertyName
     *            property to compare
     * @param value
     *            value compared with property
     * @return LESS THAN expression
     */
    public static SimpleExpression lt( String propertyName, Object value )
    {
        return new SimpleExpression( propertyName,
                                     Utils.constructCMISParameterValue( value ),
                                     SimpleExpressionOperator.LT );
    }

    /**
     * Compares specified property to provided value in LESS THAN OR EQUAL
     * expression.
     *
     * @param propertyName
     *            property to compare
     * @param value
     *            value compared with property
     * @return LESS THAN OR EQUAL expression
     */
    public static SimpleExpression le( String propertyName, Object value )
    {
        return new SimpleExpression( propertyName,
                                     Utils.constructCMISParameterValue( value ),
                                     SimpleExpressionOperator.LE );
    }

    /**
     * Compares specified property to provided value in GREATER THAN OR EQUAL
     * expression.
     *
     * @param propertyName
     *            property to compare
     * @param value
     *            value compared with property
     * @return GREATER THAN OR EQUAL expression
     */
    public static SimpleExpression ge( String propertyName, Object value )
    {
        return new SimpleExpression( propertyName,
                                     Utils.constructCMISParameterValue( value ),
                                     SimpleExpressionOperator.GE );
    }

    /**
     * Compares specified property to provided value in LIKE expression.
     *
     * @param propertyName
     *            property to compare
     * @param value
     *            value compared with property
     * @return LIKE expression
     */
    public static SimpleExpression like( String propertyName, Object value )
    {
        return new SimpleExpression( propertyName,
                                     Utils.constructCMISParameterValue( value ),
                                     SimpleExpressionOperator.LIKE );
    }

    /**
     * Compares specified property to provided value in LIKE expression.
     *
     * @param propertyName
     *            property to compare
     * @param value
     *            value compared with property
     * @param matchMode
     *            match mode in comparision
     * @return LIKE expression
     */
    public static SimpleExpression like( String propertyName, String value,
                                         MatchMode matchMode )
    {
        return new SimpleExpression( propertyName, value,
                                     SimpleExpressionOperator.LIKE, matchMode );
    }

    /**
     * Compares specified property to provided values in BETWEEN expression.
     *
     * @param propertyName
     *            property to compare
     * @param low
     *            low between boundary
     * @param high
     *            high between boundary
     * @return BETWEEN expression
     */
    public static Criterion between( String propertyName, Object low,
                                     Object high )
    {
        return new BetweenExpression( propertyName,
                                      Utils.constructCMISParameterValue( low ),
                                      Utils.constructCMISParameterValue( high ) );
    }
    
    public static Criterion between( String propertyName, Object low,
            Object high, boolean negate, boolean lowEq, boolean highEq)
	{
		return new BetweenExpression( propertyName,
		             Utils.constructCMISParameterValue( low ),
		             Utils.constructCMISParameterValue( high ),
		             negate,
		             lowEq,
		             highEq);
	}

    /**
     * Compares specified property to provided values in NOT BETWEEN expression.
     *
     * @param propertyName
     *            property to compare
     * @param low
     *            low between boundary
     * @param high
     *            high between boundary
     * @return NOT BETWEEN expression
     */
    public static Criterion notBetween( String propertyName, Object low,
                                     Object high )
    {
        return new BetweenExpression( propertyName,
                                      Utils.constructCMISParameterValue( low ),
                                      Utils.constructCMISParameterValue( high ),
                                      true,
                                      true,
                                      true);
    }

    /**
     * Compares specified property to provided values in IN expression.
     *
     * @param propertyName
     *            property to compare
     * @param values
     *            values compared with property
     * @return IN expression
     */
    public static Criterion in( String propertyName, Object... values )
    {
        CMISParameterValue<?>[] parametersValues =
            new CMISParameterValue[values.length];
        for ( int i = 0; i < parametersValues.length; i++ )
        {
            parametersValues[i] = Utils.constructCMISParameterValue( values[i] );
        }
        return new InExpression( propertyName, parametersValues );
    }

    /**
     * Compares specified property to provided values in IN expression.
     *
     * @param propertyName
     *            property to compare
     * @param value
     *            value compared with property
     * @return IN expression
     */
    public static Criterion any( String propertyName, Object value)
    {
        return new ANYExpression( propertyName, Utils.constructCMISParameterValue( value ));
    }
    
    /**
     * Compares specified property to provided values in IN expression.
     *
     * @param propertyName
     *            property to compare
     * @param values
     *            values compared with property
     * @return IN expression
     */
    public static Criterion in( String propertyName, Collection<Object> values )
    {
        Object[] valuesArray = values.toArray();
        CMISParameterValue<?>[] parametersValues =
            new CMISParameterValue[valuesArray.length];
        for ( int i = 0; i < parametersValues.length; i++ )
        {
            parametersValues[i] =
                Utils.constructCMISParameterValue( valuesArray[i] );
        }
        return new InExpression( propertyName, parametersValues );
    }

    /**
     * Checks specified property in IS NULL expression.
     *
     * @param propertyName
     *            property to check
     * @return IS NULL expression
     */
    public static Criterion isNull( String propertyName )
    {
        return new NullExpression( propertyName );
    }

    public static Criterion contains(Object value )
    {
    	return new ContainsExpression( Utils.constructCMISParameterValue( value ));
    }

    public static Criterion contains(String propertyName, String value )
    {
    	return new ContainsExpression(propertyName, Utils.constructContainsCMISParameterValue( value ));
    }

    public static Criterion inFolder(String value )
    {
    	return new InFolderExpression( Utils.constructCMISParameterValue( value ));
    }

    public static Criterion inTree(String value )
    {
    	return new InTreeExpression( Utils.constructCMISParameterValue( value ));
    }
    
    /**
     * Checks specified property in IS NOT NULL expression.
     *
     * @param propertyName
     *            property to check
     * @return IS NOT NULL expression
     */
    public static Criterion isNotNull( String propertyName )
    {
        return new NotNullExpression( propertyName );
    }

    public static PropertyExpression eqProperty( String propertyName,
                                                 String otherPropertyName )
    {
        return new PropertyExpression( propertyName, otherPropertyName,
                                       SimpleExpressionOperator.EQ );
    }

    public static PropertyExpression neProperty( String propertyName,
                                                 String otherPropertyName )
    {
        return new PropertyExpression( propertyName, otherPropertyName,
                                       SimpleExpressionOperator.NE );
    }

    public static PropertyExpression ltProperty( String propertyName,
                                                 String otherPropertyName )
    {
        return new PropertyExpression( propertyName, otherPropertyName,
                                       SimpleExpressionOperator.LT );
    }

    public static PropertyExpression leProperty( String propertyName,
                                                 String otherPropertyName )
    {
        return new PropertyExpression( propertyName, otherPropertyName,
                                       SimpleExpressionOperator.LE );
    }

    public static PropertyExpression gtProperty( String propertyName,
                                                 String otherPropertyName )
    {
        return new PropertyExpression( propertyName, otherPropertyName,
                                       SimpleExpressionOperator.GT );
    }

    public static PropertyExpression geProperty( String propertyName,
                                                 String otherPropertyName )
    {
        return new PropertyExpression( propertyName, otherPropertyName,
                                       SimpleExpressionOperator.GE );
    }

}
