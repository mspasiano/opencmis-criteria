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
 * $Id: CMISParameterValue.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import java.io.Serializable;

/**
 * Interface for query parameter value holder. It's responsibility is to call
 * proper javax.persistence.Query setParameter method.
 *
 * @author <a href="mailto:spasia@libero.it">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public interface CMISParameterValue<T extends Object> extends Serializable
{
    /**
     * @return value of this holder.
     */
    T getValue();

    /**
     * Binds query parameter value using name binding.
     *
     * @see javax.persistence.Query#setParameter(String, Object)
     * @see javax.persistence.Query#setParameter(String, java.util.Date,
     *      javax.persistence.TemporalType)
     * @see javax.persistence.Query#setParameter(String, java.util.Calendar,
     *      javax.persistence.TemporalType)
     *
     * @param parameterName
     *            bind to parameter of specified name
     * @param query
     *            populate to specified query
     */
    void populateToQuery( String parameterName, StringBuilder query );

    /**
     * Binds query parameter value using positional binding.
     *
     * @see javax.persistence.Query#setParameter(String, Object)
     * @see javax.persistence.Query#setParameter(String, java.util.Date,
     *      javax.persistence.TemporalType)
     * @see javax.persistence.Query#setParameter(String, java.util.Calendar,
     *      javax.persistence.TemporalType)
     *
     * @param parameterPosition
     *            bind to parameter on specified position
     * @param query
     *            populate to specified query
     */
//    void populateToQuery( int parameterPosition, StringBuilder query );
}
