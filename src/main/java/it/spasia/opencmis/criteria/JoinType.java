package it.spasia.opencmis.criteria;

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
 * $Id: JoinType.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

/**
 * Enum specifying join type.
 *
 * @author <a href="mailto:spasia@libero.it">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public enum JoinType
{

    /**
     * Represents inner join.
     */
    INNER_JOIN( "INNER JOIN" ),

    /**
     * Represents left (outer) join.
     */
    LEFT_JOIN( "LEFT JOIN" );

    /**
     * Holds string representation of this join type.
     */
    private String stringRepresentation;

    /**
     * Creates enum instance.
     *
     * @param aStringRepresentation
     *            string representation of join expression
     */
    JoinType( String aStringRepresentation )
    {
        this.stringRepresentation = aStringRepresentation;
    }

    /**
     * Returns string representation of this join type.
     *
     * @return string representation of this join type
     */
    public String getStringRepresentation()
    {
        return this.stringRepresentation;
    }

    /**
     * Default join used in this criteria API.
     */
    public static final JoinType DEFAULT = INNER_JOIN;

    /**
     * Synonym for left join.
     */
    public static final JoinType LEFT_OUTER_JOIN = LEFT_JOIN;
}
