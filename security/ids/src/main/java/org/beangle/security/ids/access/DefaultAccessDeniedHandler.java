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
package org.beangle.security.ids.access;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.beangle.security.authz.AccessDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base implementation of {@link AccessDeniedHandler}.
 * <p>
 * This implementation sends a 403 (SC_FORBIDDEN) HTTP error code. In addition, if a
 * {@link #errorPage} is defined, the implementation will perform a request dispatcher "forward" to
 * the specified error page view. Being a "forward", the <code>ThreadLocalHolder</code> will
 * remain populated. This is of benefit if the view (or a tag library or macro) wishes to access the
 * <code>ThreadLocalHolder</code>. The request scope will also be populated with the exception
 * itself, available from the key {@link #ACCESS_DENIED_EXCEPTION_KEY}.
 * </p>
 *
 * @author chaostone
 */
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

  public static final String ACCESS_DENIED_EXCEPTION_KEY = "403_EXCEPTION";
  protected static final Logger logger = LoggerFactory.getLogger(DefaultAccessDeniedHandler.class);

  private String errorPage;

  private String location;

  public void handle(ServletRequest request, ServletResponse response, AccessDeniedException exception)
      throws IOException, ServletException {
    if (null != location) {

    }
    if (errorPage != null) {
      if (null != exception)
        ((HttpServletRequest) request).setAttribute(ACCESS_DENIED_EXCEPTION_KEY, exception);
      RequestDispatcher rd = request.getRequestDispatcher(errorPage);
      rd.forward(request, response);
    }

    String msg = "access denied";
    if (null != exception) msg = exception.getMessage();
    if (!response.isCommitted()) {
      // Send 403 (we do this after response has been written)
      ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, msg);
    }
  }

  /**
   * The error page to use. Must begin with a "/" and is interpreted relative
   * to the current context root.
   *
   * @param errorPage the dispatcher path to display
   * @throws IllegalArgumentException if the argument doesn't comply with the above limitations
   */
  public void setErrorPage(String errorPage) {
    if ((errorPage != null) && !errorPage
        .startsWith("/")) { throw new IllegalArgumentException("errorPage must begin with '/'"); }
    this.errorPage = errorPage;
  }

  public void setLocation(String location) {
    this.location = location;
  }

}
