package it.cnr.si.opencmis.criteria.transformers;

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
 * $Id: CMISAllPropertiesResultTransformer.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import it.cnr.si.opencmis.criteria.Criteria;
import it.cnr.si.opencmis.criteria.CMISContext;
import it.cnr.si.opencmis.criteria.ResultTransformer;
import it.cnr.si.opencmis.criteria.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates select query fragment in form of
 * <code>SELECT typeAlias</code>.
 *
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class CMISPropertiesResultTransformer
    implements ResultTransformer
{
    private static final long serialVersionUID = 1L;
    /*
     * (non-Javadoc)
     *
     * @see ResultTransformer#toQueryFragment(CMISContext)
     */
    public String toQueryFragment( CMISContext cmisContext )
    {
    	List<String> columns = new ArrayList<String>();
    	StringBuilder result = new StringBuilder("SELECT ");
    	if (cmisContext.getColumns().isEmpty()){
        	columns.add(cmisContext.getTypeAlias()+".*");
            for ( Criteria sc : cmisContext.getSubcriteriaElements() )
            {
                columns.add(sc.getTypeAlias()+".*");
            }
    	}else{
    		for (String column : cmisContext.getColumns()) {
            	columns.add(cmisContext.getTypeAlias()+"."+column);
			}
            for ( Criteria sc : cmisContext.getSubcriteriaElements() )
            {
            	if (sc.getColumns().isEmpty())
                    columns.add(sc.getTypeAlias()+".*");
            	else
	            	for (String subColumn : sc.getColumns()) {
	            		columns.add(sc.getTypeAlias()+"."+subColumn);
					}
            }
    	}
        result.append(Utils.concatenate(", ", columns));
        return result.toString();
    }

}
