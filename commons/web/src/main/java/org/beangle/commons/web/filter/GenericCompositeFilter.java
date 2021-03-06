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
package org.beangle.commons.web.filter;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
/**
 *
 * @author chaostone
 * @since 2.4
 */
public abstract class GenericCompositeFilter extends GenericHttpFilter {
  /**
   * A <code>FilterChain</code> that records whether or not
   * {@link FilterChain#doFilter(jakarta.servlet.ServletRequest, jakarta.servlet.ServletResponse)} is
   * called.
   */
  protected static class VirtualFilterChain implements FilterChain {
    private final FilterChain originalChain;
    private final List<? extends Filter> additionalFilters;
    private int currentPosition = 0;

    public VirtualFilterChain(FilterChain chain, List<? extends Filter> additionalFilters) {
      this.originalChain = chain;
      this.additionalFilters = additionalFilters;
    }

    public void doFilter(ServletRequest request, ServletResponse response) throws IOException,
        ServletException {
      if (currentPosition == additionalFilters.size()) {
        originalChain.doFilter(request, response);
      } else {
        currentPosition++;
        additionalFilters.get(currentPosition - 1).doFilter(request, response, this);
      }
    }
  }

}
