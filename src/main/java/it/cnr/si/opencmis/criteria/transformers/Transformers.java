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
 * $Id: Transformers.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import it.cnr.si.opencmis.criteria.ResultTransformer;

/**
 * Utility class for generating transformers.
 *
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class Transformers {
    /**
     * Private constructor to prevent from creating instances of this utility
     * class.
     */
    private Transformers() {
    }

    /**
     * @return root entity result transformer
     */
    public static ResultTransformer rootType() {
        return new CMISPropertiesResultTransformer();
    }
}
