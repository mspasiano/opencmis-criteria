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
 * $Id: GeneralCMISParameterValue.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */


/**
 * Plain object-type value holder for query parameter.
 *
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class GeneralCMISParameterValue
        implements CMISParameterValue<Object> {
    private static final long serialVersionUID = 1L;

    /**
     * Value of this holder.
     */
    private final Object value;

    /**
     * Constructor populating all required data.
     *
     * @param parameterValue value for this holder
     */
    GeneralCMISParameterValue(Object parameterValue) {
        this.value = parameterValue;
    }

    /*
     * (non-Javadoc)
     *
     * @see CMISParameterValue#populateToQuery(java.lang.String,
     *      java.lang.StringBuilder)
     */
    public void populateToQuery(String parameterName, StringBuilder query) {
        query.replace(query.indexOf(parameterName) - 1, query.indexOf(parameterName) + parameterName.length(),
                Utils.parseParameterValue(this.value));
    }

    /*
     * (non-Javadoc)
     *
     * @see CMISParameterValue#getValue()
     */
    public Object getValue() {
        return value;
    }

}
