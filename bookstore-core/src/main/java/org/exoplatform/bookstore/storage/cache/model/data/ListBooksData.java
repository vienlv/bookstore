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
package org.exoplatform.bookstore.storage.cache.model.data;

import java.util.List;

import org.exoplatform.bookstore.storage.cache.model.key.BookKey;


/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Dec 5, 2011  
 */
public class ListBooksData extends AbstractListData<BookKey> {
  
  /** serialVersionUID. */
  private static final long serialVersionUID = 7471013733696484282L;

  /**
   * 
   * @param ids
   */
  public ListBooksData(final List<BookKey> ids) {
    super(ids);
  }
}
