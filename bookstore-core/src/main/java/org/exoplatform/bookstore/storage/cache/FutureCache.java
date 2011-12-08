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

import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.gatein.common.logging.Logger;
import org.gatein.common.logging.LoggerFactory;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Dec 5, 2011  
 */
public abstract class FutureCache<K, V, C> {

  /** Loader. */
  private final Loader<K, V, C> loader;
  
  /** ConcurrentMap. */
  private final ConcurrentMap<K, FutureTask<V>> futureEntries;
  
  /** Logger. */
  private final Logger log = LoggerFactory.getLogger(FutureCache.class);
  
  public FutureCache(Loader<K, V, C> loader) {
     this.loader = loader;
     this.futureEntries = new ConcurrentHashMap<K, FutureTask<V>>();
  }
  
  /**
   * Retrieves the cached value corresponding to the specified key from the cache, it must returns null when the
   * key does not exist. This method is intended for internal use by the future cache only.
   *
   * @param key the key
   * @return the cache value
   */
  protected abstract V get(K key);

  /**
   * Updates the cache with a new key/value pair. This method is intended for internal use by the future cache only.
   *
   * @param key the key
   * @param value the cache value
   */
  protected abstract void put(K key, V value);

  /**
   * Perform a cache lookup for the specified key within the specified context.
   * When the value cannot be loaded (because it does not exist or it failed or anything else that
   * does not come to my mind), the value null is returned.
   *
   * @param context the context in which the resource is accessed
   * @param key the key identifying the resource
   * @return the value
   */
  public final V get(final C context, final K key)
  {
     // First we try a simple cache get
     V value = get(key);

     // If it does not succeed then we go through a process that will avoid to load
     // the same resource concurrently
     if (value == null)
     {
        // Create our future
        FutureTask<V> future = new FutureTask<V>(new Callable<V>()
        {
           public V call() throws Exception
           {
              // Retrieve the value from the loader
              V value = loader.retrieve(context, key);

              //
              if (value != null)
              {
                 // Cache it, it is made available to other threads (unless someone removes it)
                 put(key, value);

                 // Return value
                 return value;
              }
              else
              {
                 return null;
              }
           }
        });

        // This boolean means we inserted in the local
        boolean inserted = true;

        //
        try
        {
           FutureTask<V> phantom = futureEntries.putIfAbsent(key, future);

           // Use the value that could have been inserted by another thread
           if (phantom != null)
           {
              future = phantom;
              inserted = false;
           }
           else
           {
              future.run();
           }

           // Returns the value
           value = future.get();
        }
        catch (ExecutionException e)
        {
           if (e.getCause() != null)
           {
              throw new UndeclaredThrowableException(e.getCause());
           }
           else
           {
              log.error("Computing of resource " + key + " threw an exception", e.getCause());
           }
        }
        catch (Exception e)
        {
           log.error("Retrieval of resource " + key + " threw an exception", e);
        }
        finally
        {
           // Clean up the per key map but only if our insertion succeeded and with our future
           if (inserted)
           {
              futureEntries.remove(key, future);
           }
        }
     }

     //
     return value;
  }
}
