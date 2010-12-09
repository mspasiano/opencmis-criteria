package it.spasia.opencmis.criteria;

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
 * $Id: CMISContext.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * This interface provides access to all necessary information for generating
 * fragments of CMIS query.
 *
 * @author <a href="mailto:maro.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public interface CMISContext extends Serializable
{

    /**
     * @return type alias of processed context
     */
    String getTypeAlias();

    /**
     * @return root type name of processed context
     */
    String getRootTypeName();

    /**
     * @return root type alias of processed context
     */
    String getRootTypeAlias();

    /**
     * Generate simply parameter name which is unique within given query and add
     * provided parameter value to this cmis context.
     *
     * @param propertyName
     *            name of property for which parameter name will be generated
     *
     * @return generated parameter name
     */
    String generateParameterName( String propertyName,
                                  CMISParameterValue<?> parameterValue );

    /**
     * Generate alias for subcriteria when no alias is specified for given
     * type.
     *
     * @return generated alias for anonymous subcriteria
     */
    String generateAlias();

    /**
     * @return cmis parameter names mapped to their value holders
     */
    Map<String, CMISParameterValue<?>> getParameters();

    /**
     * @return subcriteria elements for processed root criteria
     */
    List<Criteria> getSubcriteriaElements();

    /**
     * @return fetch elements of processed criteria
     */
    List<FetchEntry> getFetchElements();

    /**
     * @return criterion elements of processed criteria
     */
    List<Criterion> getCriterionElements();

    /**
     * @return join criterion elements of processed criteria
     */
    public Map<Criteria, Criterion> getCriterionJoinElements();
    
    /**
     * @return ordering elements of processed criteria
     */
    List<Order> getOrderingElements();

    /**
     * @return result transformer for processed criteria
     */
    ResultTransformer getResultTransformer();
    
    /**
     * Prefixes the assocation path with the current entity alias
     * only if it is not already prefixed by one of the aliases.
     * @param path assocation path to prefix
     * @return assocation path prefixed if needed
     */
    String prefix( String path );

}
