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
package org.exoplatform.bookstore.chromattic.lifecycle;

import javax.jcr.Credentials;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.chromattic.spi.jcr.SessionLifeCycle;
import org.exoplatform.bookstore.common.BookstoreConstants;
import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.services.jcr.RepositoryService;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 14, 2011  
 */
public class BookChromatticSessionLifeCycle implements SessionLifeCycle {
  
  /** Repository. */
  private static Repository repository;
  
  /**
   * Default constructor.<br/>
   * 
   * @throws RepositoryException
   */
  public BookChromatticSessionLifeCycle() throws RepositoryException {
    ExoContainer exoContainer = ExoContainerContext.getCurrentContainer();
    RepositoryService repositoryService = (RepositoryService) exoContainer.getComponentInstanceOfType(RepositoryService.class);
    repository = repositoryService.getCurrentRepository();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close(Session session) {
    session.logout();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Session login() throws RepositoryException {
    return repository.login();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Session login(String workspace) throws RepositoryException {
    return repository.login(workspace);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Session login(Credentials credentials) throws RepositoryException {
    
    Session session = null;
    
    try {
      session = repository.login(credentials);
    } catch (RepositoryException e) {
      Credentials socialCredentials = new SimpleCredentials(BookstoreConstants.USERNAME_ROOT, BookstoreConstants.PASSWORD_SOCIAL.toCharArray());
      session = repository.login(socialCredentials);
    }
    
    return session;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Session login(Credentials credentials, String workspace) throws RepositoryException {
    
    Session session = null;
    
    try {
      session = repository.login(credentials, workspace);
    } catch (RepositoryException e) {
      Credentials socialCredentials = new SimpleCredentials(BookstoreConstants.USERNAME_ROOT, BookstoreConstants.PASSWORD_SOCIAL.toCharArray());
      session = repository.login(socialCredentials, workspace);
    }
    
    return session;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void save(Session session) throws RepositoryException {
    session.save();
  }
}
