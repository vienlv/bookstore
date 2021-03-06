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
public class DataNotFoundException extends Exception {

  /** serialVersionUID. */
  private static final long serialVersionUID = -4461375392656187344L;

  /**
   * Default constructor.<br/>
   */
  public DataNotFoundException() {
    super();
  }
  
  /**
   * 
   * @param message
   */
  public DataNotFoundException(String message) {
    super(message);
  }
  
  /**
   * 
   * @param cause
   */
  public DataNotFoundException(Throwable cause) {
    super(cause);
  }
  
  /**
   * 
   * @param message
   * @param cause
   */
  public DataNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
