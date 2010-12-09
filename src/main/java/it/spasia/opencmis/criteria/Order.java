package it.spasia.opencmis.criteria;

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
 * $Id: Order.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import java.io.Serializable;

/**
 * Specifies order element of CMIS query.
 *
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class Order implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Property used for ordering.
     */
    private final String propertyName;

    /**
     * Flag indicating ascending or descending ordering.
     */
    private final boolean descending;

    /**
     * Constructs new ascending order element.
     *
     * @param propertyName
     *            property used for ordering
     * @return ascending order element for specified property
     */
    public static Order ascending( String propertyName )
    {
        return new Order( propertyName, false );
    }

    /**
     * Constructs new ascending order element.
     *
     * @param propertyName
     *            property used for ordering
     * @return ascending order element for specified property
     */
    public static Order asc( String propertyName )
    {
        return ascending( propertyName );
    }

    /**
     * Constructs new descending order element.
     *
     * @param propertyName
     *            property used for ordering
     * @return descending order element for specified property
     */
    public static Order descending( String propertyName )
    {
        return new Order( propertyName, true );
    }

    /**
     * Constructs new descending order element.
     *
     * @param propertyName
     *            property used for ordering
     * @return descending order element for specified property
     */
    public static Order desc( String propertyName )
    {
        return descending( propertyName );
    }

    /**
     * Constructs new order element for specified property and direction.
     *
     * @param propertyName
     *            property used for ordering
     */
    protected Order( String aPropertyName, boolean descendingFlag )
    {
        this.propertyName = aPropertyName;
        this.descending = descendingFlag;
    }

    /**
     * @return property used for ordering.
     */
    public String getPropertyName()
    {
        return this.propertyName;
    }

    /**
     * @return <code>true</code> if this is descending order,
     *         <code>false</code> otherwise
     */
    public boolean isDescending()
    {
        return this.descending;
    }

}
