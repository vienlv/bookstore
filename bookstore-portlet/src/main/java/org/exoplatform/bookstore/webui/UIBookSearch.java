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
package org.exoplatform.bookstore.webui;

import org.apache.commons.lang.StringUtils;
import org.exoplatform.bookstore.BookUtils;
import org.exoplatform.bookstore.storage.api.BookStorage;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.UIPopupWindow;
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.Event.Phase;
import org.exoplatform.webui.event.EventListener;
import org.exoplatform.webui.form.UIFormStringInput;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 15, 2011  
 */
@ComponentConfig(
  lifecycle = UIFormLifecycle.class,
  template = "app:/groovy/bookstore/webui/UIBookSearch.gtmpl",
  events = {
    @EventConfig(listeners = UIBookSearch.AddBookActionListener.class, phase = Phase.DECODE),
    @EventConfig(listeners = UIBookSearch.SearchActionListener.class)
  }
)
public class UIBookSearch extends UIBookstoreSection {
  
  /** Name of the book search text field. */
  public static final String TXT_BOOK_SEARCH = "txtBookSearch";
  
  /** Book search text field default value. */
  public static final String TXT_BOOK_SEARCH_DEFAULT_VALUE = "Please input title to search";
  
  /** UIPopupAddBook. */
  public static final String COMPONENT_ID_UI_POPUP_ADD_BOOK = "UIPopupAddBook";

  /**
   * Default constructor.<br/>
   * 
   * @throws Exception 
   */
  public UIBookSearch() throws Exception {
    addUIFormInput(new UIFormStringInput(TXT_BOOK_SEARCH, null, TXT_BOOK_SEARCH_DEFAULT_VALUE));
    UIPopupWindow uiPopupWindow = createUIComponent(UIPopupWindow.class, null, COMPONENT_ID_UI_POPUP_ADD_BOOK);
    uiPopupWindow.setShow(false);
    uiPopupWindow.setWindowSize(400, 0);
    addChild(uiPopupWindow);
  }
  
  /**
   * Listener for search book action.<br/>
   */
  public static class SearchActionListener extends EventListener<UIBookSearch> {
    
    @Override
    public void execute(Event<UIBookSearch> event) throws Exception {
      UIBookSearch uiBookSearch = event.getSource();
      String title = uiBookSearch.getUIStringInput(UIBookSearch.TXT_BOOK_SEARCH).getValue();
      
      BookStorage bookStorage = BookUtils.getBookService();
      
      UIBookList uiBookList = ((UIBookstore) uiBookSearch.getParent()).getChildById("UIBookList");
      uiBookList.setBookList(StringUtils.isEmpty(title) ? bookStorage.findAll() : bookStorage.findByTitle(title));
      event.getRequestContext().addUIComponentToUpdateByAjax(uiBookList);
    }
  }
  
  /**
   * Listener for add book action.<br/>
   */
  public static class AddBookActionListener extends EventListener<UIBookSearch> {
    
    @Override
    public void execute(Event<UIBookSearch> event) throws Exception {
      
      UIBookSearch uiBookSearch = event.getSource();
      UIPopupWindow uiPopupWindow = uiBookSearch.getChild(UIPopupWindow.class);
      
      UIBookAddForm uiBookAddForm = uiBookSearch.createUIComponent(UIBookAddForm.class, null, null);
      uiPopupWindow.setUIComponent(uiBookAddForm);
      uiPopupWindow.setWindowSize(500, 350);
      uiPopupWindow.setShow(true);
    }
  }
}
