/**
 * 
 */
package com.giants.decorator.html;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;

/**
 * @author vencent.lu
 *
 */
public class HtmlHelper {
	
	public static final void addRequestToGlobalVarMap(HttpServletRequest request,
			Map<String, Object> globalVarMap) {
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("servletPath", request.getServletPath());
		requestMap.put("contextPath", request.getContextPath());
		
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("referer", request.getHeader("Referer"));
		requestMap.put("header", headerMap);
		
		Map<String,String[]> map = request.getParameterMap();
		if (MapUtils.isNotEmpty(map)) {
			Iterator<String> keyIt = map.keySet().iterator();
			Map<String,Object> paramMap = new HashMap<String,Object>();
			while (keyIt.hasNext()) {
				String key = keyIt.next();
				String[] values = map.get(key);
				if (values.length == 1) {
					paramMap.put(key, values[0]);
				} else {
					paramMap.put(key, values);
				}
			}
			requestMap.put("parameter", paramMap);
		}
		
		if (request.getAttributeNames().hasMoreElements()) {
			Map<String, Object> attributeMap = new HashMap<String, Object>();
			Enumeration<String> attributeNames = request.getAttributeNames();
			while (attributeNames.hasMoreElements()) {
				String attributeName = attributeNames.nextElement();
				attributeMap.put(attributeName, request.getAttribute(attributeName));
			}
			requestMap.put("attribute", attributeMap);
		}
		
		globalVarMap.put("request", requestMap);
		
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttributeNames().hasMoreElements()) {
			Map<String, Object> sessionMap = new HashMap<String, Object>();
			Enumeration<String> attributeNames = session.getAttributeNames();
			while (attributeNames.hasMoreElements()) {
				String sessionKey = attributeNames.nextElement();
				sessionMap.put(sessionKey, session.getAttribute(sessionKey));
			}
			globalVarMap.put("session", sessionMap);
		}
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			Map<String, String> cookiesMap = new HashMap<String, String>();
			for (Cookie cookie : cookies) {
				cookiesMap.put(cookie.getName(), cookie.getValue());
			}
			globalVarMap.put("cookie", cookiesMap);
		}
	}

}
