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
 */

import it.cnr.si.opencmis.criteria.CMISContext;
import it.cnr.si.opencmis.criteria.CMISParameterValue;
import it.cnr.si.opencmis.criteria.Criterion;

/**
 * Represents expression comparing two properties.
 *
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class InFolderExpression
        implements Criterion {
    private static final long serialVersionUID = 1L;

    private CMISParameterValue<?> value;

    protected InFolderExpression(CMISParameterValue<?> aValue) {
        this.value = aValue;
    }

    public String toQueryFragment(CMISContext CMISContext) {
        String parameterName = CMISContext.generateParameterName("inFolder", value);
        StringBuilder buffer = new StringBuilder();
        String alias = CMISContext.getTypeAlias();
        buffer.append(" IN_FOLDER ");
        buffer.append(" ( ");
        buffer.append(alias);
        buffer.append(" , ");
        buffer.append(parameterName);
        buffer.append(" ) ");
        return buffer.toString();
    }
}
