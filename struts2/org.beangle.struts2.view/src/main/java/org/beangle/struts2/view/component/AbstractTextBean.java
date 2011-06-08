/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @version $Id: AbstractTextBean.java May 3, 2011 12:46:06 PM chaostone $
 */
public abstract class AbstractTextBean extends UIBean {
	protected String name;
	protected String label;
	protected String title;
	protected String comment;
	protected String required;
	protected String value="";
	protected String check;
	protected String maxlength;

	public AbstractTextBean(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		if (null == this.id) generateIdIfEmpty();
		if (null != label) label = getText(label);
		if (null != title) {
			title = getText(title);
		} else {
			title = label;
		}
		Form myform = findAncestor(Form.class);
		if ("true".equals(required)) myform.addCheck(id, "require()");
		if (null != check) myform.addCheck(id, check);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}
	
}
