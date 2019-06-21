package it.cnr.si.opencmis.criteria.impl;

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
 * $Id: CMISBuilder.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import it.cnr.si.opencmis.criteria.*;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.ExtensionsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
class CMISBuilder implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(CMISBuilder.class);

    private static final long serialVersionUID = 1L;

    private final CMISContext cmisContext;

    private final Session cmisSession;

    private final List<String> joinElements = new ArrayList<String>();

    private final List<String> whereElements = new ArrayList<String>();

    CMISBuilder(CMISContext aCMISContext, Session cmisSession) {
        this.cmisContext = aCMISContext;
        this.cmisSession = cmisSession;
    }

    private static String appendSpaceIfNeeded(String string) {
        if (string != null && string.length() > 0) {
            return " " + string;
        } else {
            return "";
        }
    }

    ItemIterable<QueryResult> executeQuery(boolean searchAllVersions,
                                           OperationContext context, ExtensionsData extension) {
        this.collectJoinElements();
        this.collectWhereElements();

        StringBuilder statement = this.buildQueryString();
        Map<String, CMISParameterValue<?>> parameters = this.cmisContext.getParameters();
        for (String parameterName : parameters.keySet()) {
            CMISParameterValue<?> parameterValue = parameters.get(parameterName);
            parameterValue.populateToQuery(parameterName, statement);
        }
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(statement.toString());
        return cmisSession.query(statement.toString(), searchAllVersions, context);
    }

    private void collectJoinElements() {
        for (Criteria sc : this.cmisContext.getSubcriteriaElements()) {
            CriteriaImpl.Subcriteria subCriteria = (CriteriaImpl.Subcriteria) sc;
            StringBuilder buffer = new StringBuilder();
            buffer.append(subCriteria.getJoinType().getStringRepresentation());
            buffer.append(' ');
            buffer.append(subCriteria.getType());

            if (sc.getTypeAlias() != null) {
                buffer.append(" AS ");
                buffer.append(sc.getTypeAlias());
            }
            Criterion joinCriterion = this.cmisContext.getCriterionJoinElements().get(subCriteria);
            if (joinCriterion != null) {
                buffer.append(" ON ");
                buffer.append(joinCriterion.toQueryFragment(this.cmisContext));
            }

            this.joinElements.add(buffer.toString());


        }
    }

    private void collectWhereElements() {
        for (Criterion c : this.cmisContext.getCriterionElements()) {
            this.whereElements.add(c.toQueryFragment(this.cmisContext));
        }
    }

    private StringBuilder buildQueryString() {
        StringBuilder buffer = new StringBuilder();

        buffer.append(buildQuerySelectClause());
        buffer.append(appendSpaceIfNeeded(buildQueryFromClause()));
        buffer.append(appendSpaceIfNeeded(buildQueryJoinClause()));
        buffer.append(appendSpaceIfNeeded(buildQueryWhereClause()));
        buffer.append(appendSpaceIfNeeded(buildQueryOrderByClause()));

        return buffer;
    }

    private String buildQuerySelectClause() {
        return this.cmisContext.getResultTransformer().toQueryFragment(this.cmisContext);
    }

    private String buildQueryFromClause() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("FROM ");
        buffer.append(this.cmisContext.getRootTypeName());
        buffer.append(" AS ");
        buffer.append(this.cmisContext.getRootTypeAlias());

        return buffer.toString();
    }

    private String buildQueryJoinClause() {
        return Utils.concatenate(" ", this.joinElements);
    }

    private String buildQueryWhereClause() {
        if (!this.whereElements.isEmpty()) {
            StringBuilder buffer = new StringBuilder();
            buffer.append("WHERE ");

            buffer.append(Utils.concatenate(" AND ", this.whereElements));

            return buffer.toString();
        } else {
            return "";
        }
    }

    private String buildQueryOrderByClause() {
        List<Order> orderingElements = this.cmisContext.getOrderingElements();
        if (!orderingElements.isEmpty()) {
            StringBuilder buffer = new StringBuilder();
            buffer.append("ORDER BY ");

            for (Iterator<Order> iterator = orderingElements.iterator(); iterator.hasNext(); ) {
                Order o = iterator.next();
                String property = cmisContext.prefix(o.getPropertyName());
                buffer.append(property);
                buffer.append(" ");
                if (o.isDescending()) {
                    buffer.append("desc");
                } else {
                    buffer.append("asc");
                }

                if (iterator.hasNext()) {
                    buffer.append(", ");
                }
            }
            return buffer.toString();
        } else {
            return "";
        }
    }

}
