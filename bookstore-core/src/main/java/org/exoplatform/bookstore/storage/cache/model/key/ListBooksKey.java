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
public class ListBooksKey extends ListCacheKey {
  
  /** serialVersionUID. */
  private static final long serialVersionUID = -5690802957790974395L;
  
  /** BookFilterKey. */
  private final BookFilterKey key;

  /**
   * Constructor.<br/>
   * 
   * @param key
   * @param offset
   * @param limit
   */
  public ListBooksKey(final BookFilterKey key, final long offset, final long limit) {
    super(offset, limit);
    this.key = key;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object o) {
    
    if (this == o) {
      return true;
    }
    
    if (!(o instanceof ListBooksKey) || !super.equals(o)) {
      return false;
    }

    ListBooksKey that = (ListBooksKey) o;

    if (key != null ? !key.equals(that.key) : that.key != null) {
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
    result = 31 * result + (key != null ? key.hashCode() : 0);
    return result;
  }
}
