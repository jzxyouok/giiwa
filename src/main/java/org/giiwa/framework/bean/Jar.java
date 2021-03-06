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

import java.util.List;

import org.giiwa.core.bean.Bean;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.bean.Table;
import org.giiwa.core.bean.UID;
import org.giiwa.core.bean.X;
import org.giiwa.framework.web.Model;

// TODO: Auto-generated Javadoc
/**
 * internal class, using for module management. <br>
 * collection="gi_jar"
 * 
 * @author wujun
 *
 */
@Table(name = "gi_jar")
public class Jar extends Bean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Update.
   *
   * @param module
   *          the module
   * @param name
   *          the name
   */
  public static void update(String module, String name) {
    String node = Model.node();
    String id = UID.id(module, name, node);

    try {
      if (!Helper.exists(W.create(X.ID, id), Jar.class)) {
        V v = V.create(X.ID, id).set("module", module).set("name", name).set("node", node).set("reset", 1);
        Helper.insert(v, Jar.class);
      } else {
        Helper.update(id, V.create("reset", 1), Jar.class);
      }
    } catch (Exception e1) {
      log.error(e1.getMessage(), e1);
    }

  }

  /**
   * Removes the.
   *
   * @param module
   *          the module
   * @param name
   *          the name
   */
  public static void remove(String module, String name) {
    String node = Model.node();

    Helper.delete(W.create("module", module).and("name", name).and("node", node), Jar.class);
  }

  /**
   * Removes the.
   *
   * @param name
   *          the name
   */
  public static void remove(String name) {
    Helper.delete(W.create("name", name), Jar.class);
  }

  /**
   * Load all.
   *
   * @param q
   *          the q
   * @return the list
   */
  public static List<Object> loadAll(W q) {
    String node = Model.node();
    return Helper.distinct("name", q.and("node", node), Jar.class);
  }

  /**
   * Load.
   *
   * @param name
   *          the name
   * @return the list
   */
  public static List<Object> load(String name) {
    String node = Model.node();

    return Helper.distinct("module", W.create("name", name).and("node", node), Jar.class);
  }

  /**
   * Reset.
   *
   * @param module
   *          the module
   */
  public static void reset(String module) {
    String node = Model.node();
    Helper.update(W.create("module", module).and("node", node), V.create("reset", 0), Jar.class);
  }

  // public static List<Object> loadNames(String module, BasicDBObject q) {
  // String node = Model.node();
  // return Bean.distinct("name", q.append("module", module).append("node",
  // node), Jar.class);
  // }

}
