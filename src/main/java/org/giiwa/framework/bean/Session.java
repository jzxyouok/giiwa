/*
 * Copyright 2015 JIHU, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.giiwa.framework.bean;

import java.util.*;

import org.apache.commons.logging.*;
import org.giiwa.core.bean.X;
import org.giiwa.core.cache.*;
import org.giiwa.framework.web.Module;

// TODO: Auto-generated Javadoc
/**
 * Session of http request
 * 
 * @author yjiang
 * 
 */
public class Session extends DefaultCachable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Log log = LogFactory.getLog(Session.class);

	String sid;

	Map<String, Object> a = new TreeMap<String, Object>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new StringBuilder("Session@{sid=").append(sid).append(",data=").append(a).append("}").toString();
	}

	/**
	 * Exists.
	 * 
	 * @param sid
	 *            the sid
	 * @return true, if successful
	 */
	public static boolean exists(String sid) {
		Session o = null;
		try {
			o = (Session) Cache.get("session-" + sid);
		} catch (Exception e) {
		}
		return o != null;
	}

	/**
	 * Delete.
	 * 
	 * @param sid
	 *            the sid
	 */
	public static void delete(String sid) {
		Cache.remove("session-" + sid);
	}

	/**
	 * Load.
	 * 
	 * @param sid
	 *            the sid
	 * @return the session
	 */
	public static Session load(String sid) {
		Session o = null;
		try {
			o = (Session) Cache.get("session-" + sid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		if (o == null) {
			o = new Session();

			/**
			 * set the session expired time
			 */
			o.sid = sid;

		}

		return o;
	}

	/**
	 * Checks for.
	 * 
	 * @param key
	 *            the key
	 * @return true, if successful
	 */
	public boolean has(String key) {
		return a.containsKey(key);
	}

	/**
	 * Removes the.
	 * 
	 * @param key
	 *            the key
	 * @return the session
	 */
	public Session remove(String key) {
		a.remove(key);
		return this;
	}

	/**
	 * Store.
	 * 
	 * @return the session
	 */
	public Session store() {
		int expired = Module._conf.getInt("session.expired", (int) (X.AYEAR / 1000));
		if (expired < 600) {
			log.warn("the session.expired is too short, expired=" + expired + "sec, must exceed 600sec");
			expired = 600;
		}
		this.setExpired(expired);

		if (!Cache.set("session-" + sid, this)) {
			log.error("set session failed !", new Exception("store session failed"));
		}

		return this;
	}

	/**
	 * Sets the.
	 * 
	 * @param key
	 *            the key
	 * @param o
	 *            the o
	 * @return the session
	 */
	public Session set(String key, Object o) {
		a.put(key, o);
		return this;
	}

	/**
	 * Sid.
	 * 
	 * @return the string
	 */
	public String sid() {
		return sid;
	}

	/**
	 * Gets the.
	 * 
	 * @param key
	 *            the key
	 * @return the object
	 */
	public Object get(String key) {
		return (Object) a.get(key);
	}

	/**
	 * Gets the int.
	 * 
	 * @param key
	 *            the key
	 * @return the int
	 */
	public int getInt(String key) {
		Integer i = (Integer) a.get(key);
		if (i != null) {
			return i;
		}
		return 0;
	}

	/**
   * Clear.
   */
	public void clear() {
		a.clear();
	}

}
