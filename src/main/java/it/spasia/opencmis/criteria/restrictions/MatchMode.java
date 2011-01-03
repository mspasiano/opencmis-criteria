package it.spasia.opencmis.criteria.restrictions;

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
 * $Id: MatchMode.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

/**
 * Represents string comparision match mode.
 *
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public enum MatchMode
{
    /**
     * Exact match mode.
     */
    EXACT
    {
        public String toMatchString( String pattern )
        {
            return pattern;
        }
    },

    /**
     * Match from start.
     */
    START
    {
        public String toMatchString( String pattern )
        {
            return pattern + "\\%";
        }
    },

    /**
     * Match in the end.
     */
    END
    {
        public String toMatchString( String pattern )
        {
            return "\\%" + pattern;
        }
    },

    /**
     * Match anywhere.
     */
    ANYWHERE
    {
        public String toMatchString( String pattern )
        {
            return "\\%" + pattern + "\\%";
        }
    };

    /**
     * Returns appropriate pattern with "%" metacharacters.
     *
     * @param pattern
     *            pattern to convert to given match pattern
     * @return pattern with "%" metacharacters applied
     */
    public abstract String toMatchString( String pattern );
}
