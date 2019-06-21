package it.cnr.si.opencmis.criteria;

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
 * $Id: Criteria.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import java.io.Serializable;
import java.util.List;

import it.cnr.si.opencmis.criteria.restrictions.Restrictions;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.ExtensionsData;

/**
 * Root interface for creating criteria based queries.
 *
 * @see CriteriaFactory
 * @see Restrictions
 *
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public interface Criteria extends Serializable
{
    /**
     * Add criterion element to criteria.
     *
     * @param criterion
     *            criterion to add
     * @return current criteria object
     */
    Criteria add( Criterion criterion );

    /**
     * Add ordering expression to criteria.
     *
     * @param order
     *            order to add
     * @return current criteria object
     */
    Criteria addOrder( Order order );

    /**
     * Create subcriteria which will be rooted to specified type.
     * @param type OpenCmis ObjectType
     *
     * @return subcriteria object rooted to specified type
     */
    Criteria createCriteria( String type);

    /**
     * Create subcriteria which will be rooted to specified type
     * specifying join type.
     *
     * @param type OpenCmis ObjectType
     *            
     * @param joinType
     *            what join type should be used
     * @return subcriteria object rooted to specified assocation path
     */
    Criteria createCriteria( String type, JoinType joinType);

    /**
     * Create subcriteria which will be rooted to specified type and
     * aliased to specified name.
     *
     * @param type OpenCmis ObjectType
     * @param alias OpenCmis ObjectType alias
     * @return subcriteria object rooted to specified type
     */
    Criteria createCriteria( String type, String alias);

    /**
    /**
     * Create subcriteria which will be rooted to specified type and
     * aliased to specified name and specifying join type.
     *
     * @param type OpenCmis ObjectType
     * @param alias OpenCmis ObjectType alias
     * @param joinType
     *            what join type should be used
     * @return subcriteria object rooted to specified type
     */
    Criteria createCriteria( String type, String alias,
                             JoinType joinType);
    
    /**
     * Create subcriteria which will be rooted to specified assocation path,
     * aliased to specified name and specifying join type.
     * 
     * @param joinCriterion join
     * @return Criterion
     */
    Criterion addJoinCriterion( Criterion joinCriterion );    

    /**
     * Execute query using provided cmisSession and if appropriate fill all
     * parameters in that query according to specified criterions.
     * 	
     * @param cmisSession session
     * @param searchAllVersions search all version
     * @param context context
     * @return ItemIterable
     */
    ItemIterable<QueryResult> executeQuery( Session cmisSession , boolean searchAllVersions, OperationContext context);

    /**
     * Execute query using provided cmisSession and if appropriate fill all
     * parameters in that query according to specified criterions.
     *
     * @param cmisSession session
     * @param searchAllVersions search all version
     * @param context context
     * @param extension extension
     * @return ItemIterable
     */
    ItemIterable<QueryResult> executeQuery( Session cmisSession , boolean searchAllVersions, OperationContext context, ExtensionsData extension);
    
    /**
     * Returns type name of root criteria.
     *
     * @return type name of root criteria
     */
    String getRootTypeName();

    /**
     * Returns type alias of root criteria.
     *
     * @return type alias of root criteria
     */
    String getRootTypeAlias();

    /**
     * Returns type alias of current criteria instance.
     *
     * @return type alias of current criteria instance.
     */
    String getTypeAlias();

    /**
     * Prefixes the assocation path with the current type alias
     * only if it is not already prefixed by one of the aliases.
     * @param path assocation path to prefix
     * @return assocation path prefixed if needed
     */
    String prefix( String path );
    /**
     * @return columns of processed criteria
     */
    List<String> getColumns();

    /**
     * @param propertyName
     *      add column of processed criteria
     */
    void addColumn(String propertyName);

    /**
     * @param propertyName
     *      remove column of processed criteria
     */
    void removeColumn(String propertyName);
}
