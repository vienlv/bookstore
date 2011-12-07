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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.exoplatform.bookstore.model.Book;
import org.exoplatform.bookstore.model.BookFilter;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Dec 7, 2011  
 */
public class BookFilterKey {

  private final String isbn;
  private final String title;
  private final String publisher;
  private final List<BookKey> excluded;
  
  public BookFilterKey(final String title, final BookFilter bookFilter) {
    
    this.isbn = bookFilter.getIsbn();
    this.title = title;
    this.publisher = bookFilter.getPublisher();
    
    List<BookKey> keys = new ArrayList<BookKey>();
    for(Book book : bookFilter.getExcludedBookList()) {
      keys.add(new BookKey(book));
    }
    
    this.excluded = Collections.unmodifiableList(keys);
  }

  public String getIsbn() {
    return isbn;
  }

  public String getTitle() {
    return title;
  }

  public String getPublisher() {
    return publisher;
  }

  public List<BookKey> getExcluded() {
    return excluded;
  }
  
  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BookFilterKey)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    BookFilterKey that = (BookFilterKey) o;
    
    if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) {
      return false;
    }
    if (title != null ? !title.equals(that.title) : that.title != null) {
      return false;
    }
    if (publisher != null ? !publisher.equals(that.publisher) : that.publisher != null) {
      return false;
    }
    if (excluded != null ? !excluded.equals(that.excluded) : that.excluded != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
    result = 31 * result + (excluded != null ? excluded.hashCode() : 0);
    
    return result;
  }
}
