/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.dao.query;

public enum Lang {
	SQL("sql"), HQL("hql");
	private final String lang;

	private Lang(String name) {
		this.lang = name;
	}

	public String getLang() {
		return lang;
	}

}