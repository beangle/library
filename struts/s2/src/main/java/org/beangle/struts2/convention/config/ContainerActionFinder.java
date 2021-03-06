/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.struts2.convention.config;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.inject.Container;
import org.beangle.commons.inject.ContainerAware;

/**
 * Find actions from application context
 *
 * @author chaostone
 * @since 3.1.0
 */
public class ContainerActionFinder implements ActionFinder, ContainerAware {

  Container container;

  public Map<Class<?>, String> getActions(ActionTest actionTest) {
    Map<Class<?>, String> actions = CollectUtils.newHashMap();
    if (null != container) {
      for (Object name : container.keys()) {
        Class<?> type = container.getType(name).orNull();
        if (null != type && actionTest.apply(type.getName())) actions.put(type, name.toString());
      }
    }
    return actions;
  }

  @Override
  public void setContainer(Container container) {
    this.container = container;
  }

}
