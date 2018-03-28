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
package org.beangle.commons.cache.concurrent;

import org.beangle.commons.cache.AbstractCacheManager;
import org.beangle.commons.cache.Cache;

/**
 * Concurrent Map Cache Manager.
 *
 * @author chaostone
 * @since 3.2.0
 */
public class ConcurrentMapCacheManager extends AbstractCacheManager {

  @Override
  protected <K, V> Cache<K, V> newCache(String name, Class<K> keyType, Class<V> valueType) {
    return new ConcurrentMapCache<K, V>();
  }

}
