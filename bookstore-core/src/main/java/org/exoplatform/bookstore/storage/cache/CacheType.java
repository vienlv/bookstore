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

import java.io.Serializable;

import org.exoplatform.bookstore.storage.cache.loader.CacheLoader;
import org.exoplatform.bookstore.storage.cache.loader.ServiceContext;
import org.exoplatform.bookstore.storage.cache.model.key.CacheKey;
import org.exoplatform.services.cache.CacheService;
import org.exoplatform.services.cache.ExoCache;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Dec 5, 2011  
 */
public enum CacheType {

  BOOK("BookCache"),
  BOOKS("BooksCache"),
  BOOK_INDEX("BookIndexCache");
  
  private final String name;

  private CacheType(final String name) {
    this.name = name;
  }

  public <K extends CacheKey, V extends Serializable> ExoCache<K, V> getFromService(CacheService service) {
    return service.getCacheInstance(name);
  }

  public <K extends CacheKey, V extends Serializable> FutureExoCache<K, V, ServiceContext<V>> createFutureCache(
      ExoCache<K, V> cache) {

    return new FutureExoCache<K, V, ServiceContext<V>>(new CacheLoader<K, V>(), cache);
  }
}
