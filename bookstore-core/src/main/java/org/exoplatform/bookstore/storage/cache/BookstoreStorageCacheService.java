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
package org.exoplatform.bookstore.storage.cache;

import org.exoplatform.bookstore.storage.cache.model.data.BookData;
import org.exoplatform.bookstore.storage.cache.model.data.ListBooksData;
import org.exoplatform.bookstore.storage.cache.model.key.BookKey;
import org.exoplatform.bookstore.storage.cache.model.key.BookPrimaryKey;
import org.exoplatform.bookstore.storage.cache.model.key.ListBooksKey;
import org.exoplatform.services.cache.CacheService;
import org.exoplatform.services.cache.ExoCache;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Dec 5, 2011  
 */
public class BookstoreStorageCacheService {

  /** . */
  private final ExoCache<BookKey, BookData> exoBookCache;
  
  /** . */
  private final ExoCache<ListBooksKey, ListBooksData> exoBooksCache;
  
  /** . */
  private final ExoCache<BookPrimaryKey, BookKey> exoBookIndexCache;
  
  /**
   * Constructor.<br/>
   * 
   * @param cacheService
   */
  public BookstoreStorageCacheService(CacheService cacheService) {
    
    exoBookCache = CacheType.BOOK.getFromService(cacheService);
    exoBooksCache = CacheType.BOOKS.getFromService(cacheService);
    exoBookIndexCache = CacheType.BOOK_INDEX.getFromService(cacheService);
  }

  /**
   * Getter method for exoBookCache.<br/>
   * 
   * @return
   */
  public ExoCache<BookKey, BookData> getExoBookCache() {
    return exoBookCache;
  }

  /**
   * Getter method for exoBooksCache.<br/>
   * 
   * @return
   */
  public ExoCache<ListBooksKey, ListBooksData> getExoBooksCache() {
    return exoBooksCache;
  }

  /**
   * Getter method for exoBookIndexCache.<br/>
   * 
   * @return
   */
  public ExoCache<BookPrimaryKey, BookKey> getExoBookIndexCache() {
    return exoBookIndexCache;
  }
}
