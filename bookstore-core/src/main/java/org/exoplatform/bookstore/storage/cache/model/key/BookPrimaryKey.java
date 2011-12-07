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
 * Dec 7, 2011  
 */
public class BookPrimaryKey extends MultitenantCacheKey {

  private final String isbn;
  
  public BookPrimaryKey(final String isbn) {
    this.isbn = isbn;
  }
  
  public String getIsbn() {
    return isbn;
  }
  
  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BookPrimaryKey)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    BookPrimaryKey that = (BookPrimaryKey) o;

    if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
    return result;
  }
}
