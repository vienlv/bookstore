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

import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.exoplatform.webui.form.UIForm;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 15, 2011  
 */
@ComponentConfig(
  lifecycle = UIFormLifecycle.class
)
public abstract class UIBookstoreSection extends UIForm {

  /** The isEditMode is used for check the view mode. */
  private boolean isEditMode;
  
  /**
   * Checks the current display of title bar is can be edit.<br>
   * 
   * @return true if title bar is in edit mode.
   */
  public boolean isEditMode() {
    return this.isEditMode;
  }

  /**
   * Sets the edit mode for form.<br>
   * 
   * @param editMode
   */
  public void setEditMode(boolean editMode) {
    this.isEditMode = editMode;
  }
  
  /**
   * Checks the current user is right edit permission.<br>
   * 
   * @return true if current user has permission.
   */
  public boolean isEditable() {
    // TODO
    return true;
  }
  
  /**
   * Listens to edit event and changes the form to edit mode.<br>
   *
   */
  public static class EditActionListener extends EventListener<UIBookstoreSection> {

    @Override
    public void execute(Event<UIBookstoreSection> event) throws Exception {
      
      UIBookstoreSection sect = event.getSource();
      sect.setEditMode("true".equals(event.getRequestContext().getRequestParameter(OBJECTID)));
      event.getRequestContext().addUIComponentToUpdateByAjax(sect);
    }
  }
  
  /**
   * Listens to save event and change form to non edit mode.<br> 
   *
   */
  public static class SaveActionListener extends EventListener<UIBookstoreSection> {

    @Override
    public void execute(Event<UIBookstoreSection> event) throws Exception {
      
      UIBookstoreSection sect = event.getSource();
      sect.setEditMode(false);
      event.getRequestContext().addUIComponentToUpdateByAjax(sect);
    }
  }
  
  /**
   * Listens to cancel event and change the form to non edit mode.<br>
   *
   */
  public static class CancelActionListener extends EventListener<UIBookstoreSection> {

    @Override
    public void execute(Event<UIBookstoreSection> event) throws Exception {
      
      UIBookstoreSection sect = event.getSource();
      sect.setEditMode(false);
      event.getRequestContext().addUIComponentToUpdateByAjax(sect);
    }
  }
}
