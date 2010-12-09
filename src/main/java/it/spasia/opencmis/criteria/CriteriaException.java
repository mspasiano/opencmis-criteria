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
 * $Id: CriteriaException.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

/**
 * @author <a href="mailto:maro.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class CriteriaException extends Exception {
	private static final long serialVersionUID = 1L;
	private final String errorContent;

	public CriteriaException(String errorContent) {
		super();
		this.errorContent = errorContent;
	}

	public CriteriaException(String message, Throwable cause, String errorContent) {
		super(message, cause);
		this.errorContent = errorContent;
	}

	public CriteriaException(String message, String errorContent) {
		super(message);
		this.errorContent = errorContent;
	}

	public CriteriaException(Throwable cause, String errorContent) {
		super(cause);
		this.errorContent = errorContent;
	}

	public String getErrorContent() {
		return errorContent;
	}

}
