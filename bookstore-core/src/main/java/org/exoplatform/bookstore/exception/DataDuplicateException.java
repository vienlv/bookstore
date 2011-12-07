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
package org.exoplatform.bookstore.exception;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 14, 2011  
 */
public class DataDuplicateException extends Exception {

  /** serialVersionUID. */
  private static final long serialVersionUID = -2160931372491234583L;

  /**
   * Default constructor.<br/>
   */
  public DataDuplicateException() {
    super();
  }
  
  /**
   * 
   * @param message
   */
  public DataDuplicateException(String message) {
    super(message);
  }
  
  /**
   * 
   * @param cause
   */
  public DataDuplicateException(Throwable cause) {
    super(cause);
  }
  
  /**
   * 
   * @param message
   * @param cause
   */
  public DataDuplicateException(String message, Throwable cause) {
    super(message, cause);
  }
}
