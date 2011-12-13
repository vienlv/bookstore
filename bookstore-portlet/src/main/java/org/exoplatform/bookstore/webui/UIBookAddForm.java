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

import java.io.InputStream;

import org.exoplatform.bookstore.BookUtils;
import org.exoplatform.bookstore.common.BookstoreConstants;
import org.exoplatform.bookstore.exception.DataDuplicateException;
import org.exoplatform.bookstore.model.Book;
import org.exoplatform.bookstore.storage.api.BookStorage;
import org.exoplatform.bookstore.utils.BookstoreUtils;
import org.exoplatform.upload.UploadResource;
import org.exoplatform.web.application.ApplicationMessage;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.UIApplication;
import org.exoplatform.webui.core.UIPopupWindow;
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.exoplatform.webui.form.UIFormInputSet;
import org.exoplatform.webui.form.UIFormSelectBox;
import org.exoplatform.webui.form.UIFormStringInput;
import org.exoplatform.webui.form.UIFormTabPane;
import org.exoplatform.webui.form.UIFormUploadInput;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 17, 2011  
 */
@ComponentConfig(
  lifecycle = UIFormLifecycle.class,
  template = "system:/groovy/webui/form/UIFormTabPane.gtmpl",
  events = {
    @EventConfig(listeners = UIBookAddForm.CreateActionListener.class)
  }
)
public class UIBookAddForm extends UIFormTabPane {

  /**
   * Deafult constructor.<br/>
   * 
   * @throws Exception
   */
  public UIBookAddForm() throws Exception {
    super("UIBookAddForm");
    UIFormInputSet uiBookInformation = new UIBookInformation("UIBookInformation");
    addChild(uiBookInformation);
    setActions(new String[]{"Create"});
    setSelectedTab(1);
  }
  
  /**
   * Listener for create book action.<br/>
   */
  public static class CreateActionListener extends EventListener<UIBookAddForm> {

    @Override
    public void execute(Event<UIBookAddForm> event) throws Exception {
      
      UIBookAddForm uiBookAddForm = event.getSource();
      UIFormStringInput txtIsbn =  uiBookAddForm.getUIStringInput(UIBookInformation.TXT_ISBN);
      UIFormStringInput txtTitle = uiBookAddForm.getUIStringInput(UIBookInformation.TXT_TITLE);
      UIFormStringInput txtPublisher = uiBookAddForm.getUIStringInput(UIBookInformation.TXT_PUBLISHER);
      UIFormSelectBox cmbCategories = uiBookAddForm.getUIFormSelectBox(UIBookInformation.CMB_CATEGORIES);
      UIFormUploadInput uiFormUploadInput = uiBookAddForm.getChild(UIBookInformation.class).getUIInput(UIBookInformation.UPL_IMAGE);
      
      Book book = new Book();
      book.setIsbn(txtIsbn.getValue());
      book.setTitle(txtTitle.getValue());
      book.setPublisher(txtPublisher.getValue());
      book.setCategory(BookstoreUtils.getCategoryValue(cmbCategories.getSelectedValues()[0]));
      
      UploadResource uploadResource = uiFormUploadInput.getUploadResource();
      if(uploadResource != null) {
        book.setImageMimeType(uploadResource.getMimeType());
      }
      
      InputStream inputStream = uiFormUploadInput.getUploadDataAsStream();
      if(inputStream != null) {
        book.setImageBytes(new byte[inputStream.available()]);
        inputStream.read(book.getImageBytes());
      }
      
      BookStorage bookStorage = BookUtils.getBookService();
      
      try {
        bookStorage.insert(book);
        UIBookList.bookList = bookStorage.findAll();
      } catch(DataDuplicateException e) {
        WebuiRequestContext ctx = event.getRequestContext();
        UIApplication uiApplication = ctx.getUIApplication();
        uiApplication.addMessage(new ApplicationMessage(String.format(BookstoreConstants.MSG_BOOK_ALREADY_EXIST,
                                                                      book.getTitle(), book.getIsbn()),
                                                        null, ApplicationMessage.WARNING));
      }
      
      UIPopupWindow uiPopupWindow = uiBookAddForm.getParent();
      uiPopupWindow.setShow(false);
      BookUtils.updateWorkingSpace();
    }
  }
}
