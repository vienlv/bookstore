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

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Dec 5, 2011  
 */
public class ListCacheKey extends MultitenantCacheKey {
  
  /** serialVersionUID. */
  private static final long serialVersionUID = -6882132842346624110L;
  
  /** offset. */
  private final long offset;
  
  /** limit. */
  private final long limit;

  /**
   * Constructor.<br/>
   * 
   * @param offset
   * @param limit
   */
  public ListCacheKey(final long offset, final long limit) {
    this.offset = offset;
    this.limit = limit;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object o) {
    
    if (this == o) {
      return true;
    }
    
    if (!(o instanceof ListCacheKey) || !super.equals(o)) {
      return false;
    }

    ListCacheKey that = (ListCacheKey) o;

    if (limit != that.limit || offset != that.offset) {
      return false;
    }

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (int) (offset ^ (offset >>> 32));
    result = 31 * result + (int) (limit ^ (limit >>> 32));
    return result;
  }
}
