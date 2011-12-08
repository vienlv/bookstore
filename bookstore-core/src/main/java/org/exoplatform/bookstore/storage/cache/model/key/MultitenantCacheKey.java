/*
 * Copyright (C) 2003-2011 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.bookstore.storage.cache.model.key;

import javax.jcr.RepositoryException;

import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.services.jcr.RepositoryService;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Dec 5, 2011  
 */
public class MultitenantCacheKey implements CacheKey {
  
  /** serialVersionUID. */
  private static final long serialVersionUID = -4129895804982464423L;
  
  /** Repository name. */
  private final String repositoryName;
  
  /**
   * Default constructor.<br/>
   */
  public MultitenantCacheKey() {
    repositoryName = getCurrentRepositoryName();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object o) {
    
    if (this == o) {
      return true;
    }
    
    if (!(o instanceof MultitenantCacheKey)) {
      return false;
    }

    MultitenantCacheKey that = (MultitenantCacheKey) o;

    if (repositoryName != null ? !repositoryName.equals(that.repositoryName) : that.repositoryName != null) {
      return false;
    }

    return true;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return repositoryName != null ? repositoryName.hashCode() : 0;
  }
  
  /**
   * Method used to get current repository name.<br/>
   * 
   * @return
   */
  private String getCurrentRepositoryName() {
    
    RepositoryService repositoryService = (RepositoryService) ExoContainerContext.getCurrentContainer().getComponentInstanceOfType(RepositoryService.class);
    
    try {
      return repositoryService.getCurrentRepository().getConfiguration().getName();
    }
    catch (RepositoryException e) {
      throw new RuntimeException(e);
    }
  }
}
