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
package org.beangle.commons.dao.query.limit;

import java.util.ArrayList;
import java.util.List;

import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.SinglePage;
import org.beangle.commons.dao.query.LimitQuery;

public class MockQueryPage extends AbstractQueryPage<String> {

  public MockQueryPage() {

  }

  public MockQueryPage(LimitQuery<String> query) {
    super(query);
    next();
  }

  public Page<String> moveTo(int pageIndex) {
    SinglePage<String> page = new SinglePage<String>();
    page.setPageIndex(pageIndex);
    page.setPageSize(super.getPageSize());
    List<String> datas = new ArrayList<String>(getPageSize());
    for (int i = 0; i < getPageSize(); i++) {
      datas.add(String.valueOf(i) + " of " + pageIndex);
    }
    page.setItems(datas);
    page.setTotalItems(100);
    setPageData(page);
    return this;
  }

}
