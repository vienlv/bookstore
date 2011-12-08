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

import org.exoplatform.bookstore.model.Book;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Dec 5, 2011  
 */
public class BookKey extends MultitenantCacheKey {

  /** serialVersionUID. */
  private static final long serialVersionUID = -527539986644247803L;
  
  /** Id. */
  private final String id;
  
  /**
   * Constructor.<br/>
   * 
   * @param book
   */
  public BookKey(Book book) {
    this.id = book.getId();
  }
  
  /**
   * Getter method for id.<br/>
   * 
   * @return
   */
  public String getId() {
    return id;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object o) {
    
    if (this == o) {
      return true;
    }
    
    if (!(o instanceof BookKey) || !super.equals(o)) {
      return false;
    }

    BookKey that = (BookKey) o;

    if (id != null ? !id.equals(that.id) : that.id != null) {
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
    result = 31 * result + (id != null ? id.hashCode() : 0);
    return result;
  }
}
