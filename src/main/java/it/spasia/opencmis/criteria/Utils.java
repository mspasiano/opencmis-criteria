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
 * $Id: Utils.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Utility class with operations common for different classes.
 *
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class Utils
{
	public static String APOSTROPHE = "'";
	public static Map<String,String> REP_CHAR_IN_PARAM_VALUE = new HashMap<String, String>();
	static{
		REP_CHAR_IN_PARAM_VALUE.put(APOSTROPHE, "\\'");
	}
    /**
     * Checks if specified assocation path is qualified or not (basically if it
     * contains dot or not) and qualifies path when needed.
     *
     * @param path
     *            path to qualify
     * @param alias
     *            alias to qualify if needed
     * @return aliased (with alias parameter) path or just path if it was
     *         already qualified
     */
    /**
     * Unqualifies assocation path to plain property name.
     *
     * @param path
     *            path to unqualify
     * @return unqualified path
     */
    public static String unqualifyAssocationPath( String path )
    {
        int i = path.lastIndexOf( '.' );
        if ( i != -1 )
        {
            return path.substring( i + 1, path.length() );
        }
        else
        {
            return path;
        }
    }

    /**
     * Concatenates provided collections of objects using
     * java.lang.String.valueOf(java.lang.Object) separating elements with
     * provided separator.
     *
     * @param separator
     *            separate list with specified separator
     * @param values
     *            collection of values to concatenate into string
     * @return string concatenated elements
     */
    public static String concatenate( String separator, Collection<?> values )
    {
        StringBuilder buffer = new StringBuilder();

        for ( Iterator<?> iterator = values.iterator(); iterator.hasNext(); )
        {
            Object o = iterator.next();
            buffer.append( o );

            if ( iterator.hasNext() )
            {
                buffer.append( separator );
            }
        }

        return buffer.toString();
    }

    public static CMISParameterValue<?> constructCMISParameterValue(Object value){
    	if (value instanceof Date)
    		return new DateCMISParameterValue((Date)value);
    	return new GeneralCMISParameterValue(value);
    }

    public static CMISParameterValue<?> constructContainsCMISParameterValue(Object value){
    	if (value instanceof Date)
    		return new DateCMISParameterValue((Date)value);
    	return new ContainsCMISParameterValue(value);
    }
    
    public static String parseParameterValue(Object value){
    	if (value instanceof Collection<?>){
    		return concatenate(",", (Collection<?>)value);
    	}
    	String parameterValue = String.valueOf(value);
    	for (String key : REP_CHAR_IN_PARAM_VALUE.keySet()) {
    		parameterValue = parameterValue.replace(key, REP_CHAR_IN_PARAM_VALUE.get(key));
		}
    	return APOSTROPHE+parameterValue+APOSTROPHE;
    }

    public static String parseParameterValueWithoutAPOSTROPHE(Object value){
    	String parameterValue = String.valueOf(value);
		parameterValue = parameterValue.replace("'", " ");

//    	for (String key : REP_CHAR_IN_PARAM_VALUE.keySet()) {
//    		parameterValue = parameterValue.replace(key, REP_CHAR_IN_PARAM_VALUE.get(key));
//		}
    	return parameterValue;
    }
    
}
