/* 
 * Copyright 2012-2017 qifu of copyright Chen Xin Nien
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
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package org.qifu.ui;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import ognl.Ognl;
import ognl.OgnlException;

public class UIComponentValueUtils {
	
	public static Object getObjectFromPage(PageContext pageContext, String paramName) {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		Object val = ( request.getParameter(paramName) != null ? request.getParameter(paramName) : request.getAttribute(paramName) ); // 以 getParameter 為主
		return val;
	}
	
	public static Object getObjectFromSession(PageContext pageContext, String paramName) {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		Object val = request.getSession().getAttribute(paramName);
		return val;
	}
	
	public static void setValue(PageContext pageContext, Map<String, Object> paramMap, String paramMapKey, String value, boolean escapeHtml, boolean ecmaScript, String scope) {
		if (!StringUtils.isBlank(value) && StringUtils.defaultString(value).indexOf(".") == -1) {
			Object val = null;
			if ( UIComponent.SCOPE_SESSION.equals(scope) ) {
				val = getObjectFromSession(pageContext, value);
			} else {
				val = getObjectFromPage(pageContext, value);
			}
			if (val != null) {
				paramMap.put(paramMapKey, val);
			}
		}
		if ( StringUtils.defaultString(value).indexOf(".") >= 1 ) { // 如 policy.no , policy.amount
			String kName = value.split("[.]")[0];
			Object valObj = null;
			if ( UIComponent.SCOPE_SESSION.equals(scope) ) {
				valObj = getObjectFromSession(pageContext, kName);
			} else {
				valObj = getObjectFromPage(pageContext, kName);
			}
			Object val = null;
			Map<String, Object> ognlRoot = new HashMap<String, Object>(); 
			if (valObj != null) {
				try {
					ognlRoot.put(kName, valObj);
					val = Ognl.getValue(value, ognlRoot);
				} catch (OgnlException e) {	
					//e.printStackTrace();
				}				
			}
			ognlRoot.clear();
			ognlRoot = null;
			if (val != null) {
				putValue(paramMap, paramMapKey, val, escapeHtml, ecmaScript);
			}			
		}
		if (paramMap.get(paramMapKey) == null) {
			paramMap.put(paramMapKey, StringUtils.defaultString(value));
		}
	}
	
	private static void putValue(Map<String, Object> params, String paramMapKey, Object val, boolean escapeHtml, boolean ecmaScript) {
		if (val instanceof java.lang.String) {
			params.put(paramMapKey, String.valueOf(val) );
			if (ecmaScript) {
				params.put(paramMapKey, StringEscapeUtils.escapeEcmaScript( (String)val ) );
			}
			if (escapeHtml) {
				params.put(paramMapKey, StringEscapeUtils.escapeHtml4( (String)val ) );
			}
			if (ecmaScript && escapeHtml) {
				params.put(paramMapKey, StringEscapeUtils.escapeHtml4( StringEscapeUtils.escapeEcmaScript( (String)val ) ) );
			}
			return;
		}		
		if (val instanceof java.lang.Integer) {
			params.put(paramMapKey, String.valueOf( (Integer)val ) );
			return;			
		}
		if (val instanceof java.lang.Long) {
			params.put(paramMapKey, String.valueOf( (Long)val ) );
			return;						
		}
		if (val instanceof java.math.BigDecimal) {
			params.put(paramMapKey, ((java.math.BigDecimal)val).toString() );
			return;					
		}
		if (val instanceof java.math.BigInteger) {
			params.put(paramMapKey, ((java.math.BigInteger)val).toString() );
			return;							
		}
		if (val instanceof java.lang.Float) {
			params.put(paramMapKey, String.valueOf( (Float)val ) );
			return;						
		}
		if (val instanceof java.lang.Double) {
			params.put(paramMapKey, String.valueOf( (Double)val ) );
			return;						
		}	
		params.put(paramMapKey, String.valueOf(val));
	}	
	
}
