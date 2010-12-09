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
 * $Id: CriteriaFactory.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import it.spasia.opencmis.criteria.impl.CriteriaImpl;

import java.io.Serializable;



/**
 * Factory of criteria objects.
 *
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class CriteriaFactory implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Private constructor to prevent from creating instances of this utility
     * class.
     */
    private CriteriaFactory()
    {
    }

    /**
     * Creates criteria for specified entity name aliased as &amp;root&amp;.
     *
     * @param typeId
     *            id of root type
     * @return criteria for specified type id
     */
    public static Criteria createCriteria( String typeId )
    {
        return new CriteriaImpl( typeId );
    }

    /**
     * Creates criteria for specified type id aliased as specified alias.
     *
     * @param typeId
     *            id of root type
     * @param alias
     *            alias for root type
     * @return criteria for specified type id
     */
    public static Criteria createCriteria( String typeId, String alias )
    {
        return new CriteriaImpl( typeId, alias );
    }

}
