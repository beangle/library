/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict.service;

import java.util.Collection;
import java.util.List;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.profile.UserProfile;
import org.beangle.security.blueprint.restrict.Restriction;

/**
 * 资源访问约束服务
 * 
 * @author chaostone
 */
public interface RestrictionService {

  /**
   * 获得该权限范围适用的数据权限
   * 
   * @param profile
   * @param resource
   * @return
   */
  public List<Restriction> getRestrictions(UserProfile profile, Resource resource);

  /**
   * Get field enumerated values.
   * 
   * @param propertyName
   * @param profile
   * @return
   */
  public Object getPropertyValue(String propertyName, UserProfile profile);

  /**
   * @param builder
   * @param restrictions
   * @param profile
   */
  public void apply(OqlBuilder<?> builder, Collection<? extends Restriction> restrictions, UserProfile profile);

}
