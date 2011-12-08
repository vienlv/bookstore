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
package org.exoplatform.bookstore.utils;

import org.apache.commons.lang.StringUtils;
import org.exoplatform.bookstore.common.BookstoreConstants;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 18, 2011  
 */
public class BookstoreUtils {

  public static String getCategoryValue(String categoryId) {
    
    if (BookstoreConstants.CATEGORY_NOVEL_VALUE.equals(categoryId)) {
      return BookstoreConstants.CATEGORY_NOVEL;
    } else if (BookstoreConstants.CATEGORY_SCIENTIFIC_VALUE.equals(categoryId)) {
      return BookstoreConstants.CATEGORY_SCIENTIFIC;
    } else if (BookstoreConstants.CATEGORY_IT_VALUE.equals(categoryId)) {
      return BookstoreConstants.CATEGORY_IT;
    } else {
      return BookstoreConstants.CATEGORY_OTHERS;
    }
  }
  
  public static String appendPercentCharacter(String str) {
    
    if(StringUtils.isEmpty(str)) {
      return null;
    }
    
    StringBuilder sb = new StringBuilder();
    sb.append("%");
    sb.append(str);
    sb.append("%");
    
    return sb.toString();
  }
}
