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
package org.beangle.commons.web.resource;

import java.io.IOException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.beangle.commons.inject.Containers;
import org.beangle.commons.lang.Strings;

public class StaticResourceServlet extends HttpServlet {

  private static final long serialVersionUID = 6086288505408491256L;

  private ResourceProcessor processor;

  @Override
  public void init(ServletConfig config) throws ServletException {
    processor = Containers.getRoot().getBean(ResourceProcessor.class).get();
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
      IOException {
    String uri = request.getRequestURI();
    String contextPath = request.getContextPath();
    if (!(contextPath.equals("") || contextPath.equals("/"))) {
      uri = Strings.substringAfter(uri, contextPath);
    }
    uri = uri.substring("/static".length());
    processor.process(uri, request, response);
  }
}
