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
 * $Id: DateCMISParameterValue.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date-typed value holder for query parameter.
 *
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class DateCMISParameterValue implements CMISParameterValue<Date>
{
    private static final long serialVersionUID = 1L;
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
    /**
     * Value of this holder.
     */
    private final Date value;

    /**
     * Constructor populating all required data.
     *
     * @param parameterValue
     *            value for this holder
     */
    public DateCMISParameterValue( Date parameterValue)
    {
        this.value = parameterValue;
    }

    /*
     * (non-Javadoc)
     *
     * @see CMISParameterValue#populateToQuery(java.lang.String,
     *      javax.persistence.Query)
     */
	public void populateToQuery( String parameterName, StringBuilder query )
	{
        query.replace(query.indexOf(parameterName) -1, query.indexOf(parameterName) + parameterName.length(), "TIMESTAMP '"+formatter.format(this.value)+"'" );
	}

    /*
     * (non-Javadoc)
     *
     * @see CMISParameterValue#getValue()
     */
    public Date getValue()
    {
        return this.value;
    }
}
