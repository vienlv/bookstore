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
package org.exoplatform.bookstore;

import org.exoplatform.bookstore.storage.api.BookStorage;
import org.exoplatform.bookstore.storage.impl.BookStorageImpl;
import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.portal.application.PortalRequestContext;
import org.exoplatform.portal.webui.util.Util;
import org.exoplatform.portal.webui.workspace.UIPortalApplication;
import org.exoplatform.portal.webui.workspace.UIWorkingWorkspace;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 22, 2011  
 */
public class BookUtils {

  /**
   * Method used to get Book service from eXo Container.<br/>
   * 
   * @return
   */
  public static BookStorage getBookService() {
    ExoContainer eXoContainer = ExoContainerContext.getCurrentContainer();
    return (BookStorageImpl) eXoContainer.getComponentInstanceOfType(BookStorageImpl.class);
  }
  
  public static void updateWorkingSpace() {
    UIWorkingWorkspace uiWorkingWS = Util.getUIPortalApplication().getChildById(UIPortalApplication.UI_WORKING_WS_ID);
    PortalRequestContext pContext = Util.getPortalRequestContext();
    pContext.addUIComponentToUpdateByAjax(uiWorkingWS);
    pContext.ignoreAJAXUpdateOnPortlets(true);
  }
}
