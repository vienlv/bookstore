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
package org.exoplatform.bookstore.service.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.exoplatform.bookstore.service.rest.api.BookRestService;
import org.exoplatform.bookstore.service.rest.impl.BookRestServiceImpl;
import org.exoplatform.bookstore.service.test.SocialMockHttpServletRequest;
import org.exoplatform.bookstore.storage.BookStorageTest;
import org.exoplatform.container.StandaloneContainer;
import org.exoplatform.services.rest.ContainerResponseWriter;
import org.exoplatform.services.rest.RequestHandler;
import org.exoplatform.services.rest.impl.ContainerRequest;
import org.exoplatform.services.rest.impl.ContainerResponse;
import org.exoplatform.services.rest.impl.EnvironmentContext;
import org.exoplatform.services.rest.impl.InputHeadersMap;
import org.exoplatform.services.rest.impl.MultivaluedMapImpl;
import org.exoplatform.services.rest.impl.RequestHandlerImpl;
import org.exoplatform.services.rest.tools.DummyContainerResponseWriter;
import org.exoplatform.ws.frameworks.json.impl.JsonDefaultHandler;
import org.exoplatform.ws.frameworks.json.impl.JsonException;
import org.exoplatform.ws.frameworks.json.impl.JsonGeneratorImpl;
import org.exoplatform.ws.frameworks.json.impl.JsonParserImpl;
import org.exoplatform.ws.frameworks.json.value.JsonValue;

import junit.framework.TestCase;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 25, 2011  
 */
public class BookRestServiceTest extends TestCase {
  
  protected static BookRestService bookRestService;
  
  protected static StandaloneContainer standaloneContainer;
  
  protected static RequestHandler requestHandler;
  
  private static final String TEST_CONFIGURATION = "/conf/portal/test-configuration.xml";
  
  private static final String LOGIN_CONFIGURATION = "/conf/portal/login.conf";
  
  private final String RESOURCE_URL = "/bookstore/";
  
  static {
    initContainer();
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testBookRestService() throws Exception {
    bookRestService.insertBook("123", "asdgasd", "sad12dsa", "Novel");
    testStatusCodeOfResource("root", "GET", "/bookstore/get/", null, null, Response.Status.OK.getStatusCode());
  }
  
  private static void initContainer() {
    try {
      String jcrConfiguration = BookStorageTest.class.getResource(TEST_CONFIGURATION).toString();
      StandaloneContainer.addConfigurationURL(jcrConfiguration);

      String loginConfiguration = BookStorageTest.class.getResource(LOGIN_CONFIGURATION).toString();
      System.setProperty("java.security.auth.login.config", loginConfiguration);

      standaloneContainer = StandaloneContainer.getInstance();
      
      bookRestService = (BookRestService) standaloneContainer.getComponentInstanceOfType(BookRestServiceImpl.class);
      
      requestHandler = (RequestHandlerImpl) standaloneContainer.getComponentInstanceOfType(RequestHandlerImpl.class);
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize standalone container: " + e.getMessage(), e);
    }
  }
  
  /**
   * gets response with provided writer
   * @param method
   * @param requestURI
   * @param baseURI
   * @param headers
   * @param data
   * @param writer
   * @return
   * @throws Exception
   */
  private ContainerResponse service(String method,
                                   String requestURI,
                                   String baseURI,
                                   Map<String, List<String>> headers,
                                   byte[] data,
                                   ContainerResponseWriter writer) throws Exception {

    if (headers == null) {
      headers = new MultivaluedMapImpl();
    }

    ByteArrayInputStream in = null;
    if (data != null) {
      in = new ByteArrayInputStream(data);
    }

    EnvironmentContext envctx = new EnvironmentContext();
    HttpServletRequest httpRequest = new SocialMockHttpServletRequest("",
                                                                      in,
                                                                      in != null ? in.available() : 0,
                                                                      method,
                                                                      headers);
    envctx.put(HttpServletRequest.class, httpRequest);
    EnvironmentContext.setCurrent(envctx);
    ContainerRequest request = new ContainerRequest(method,
                                                    new URI(requestURI),
                                                    new URI(baseURI),
                                                    in,
                                                    new InputHeadersMap(headers));
    ContainerResponse response = new ContainerResponse(writer);
    requestHandler.handleRequest(request, response);
    return response;
  }
  
  /**
   * gets response without provided writer
   * @param method
   * @param requestURI
   * @param baseURI
   * @param headers
   * @param data
   * @return
   * @throws Exception
   */
  private ContainerResponse service(String method,
                                   String requestURI,
                                   String baseURI,
                                   MultivaluedMap<String, String> headers,
                                   byte[] data) throws Exception {
    return service(method, requestURI, baseURI, headers, data, new DummyContainerResponseWriter());
  }
  
  /**
   * Asserts if the provided jsonString is equal to an entity object's string.
   *
   * @param jsonString the provided json string
   * @param entity the provided entity object
   */
  private void assertJsonStringEqualsEntity(String jsonString, Object entity) throws JsonException {
    JsonParserImpl jsonParser = new JsonParserImpl();
    JsonDefaultHandler jsonDefaultHandler = new JsonDefaultHandler();
    jsonParser.parse(new InputStreamReader(new ByteArrayInputStream(jsonString.getBytes())), jsonDefaultHandler);

    JsonValue firstJsonValue = jsonDefaultHandler.getJsonObject();
    assertNotNull("firstJsonValue must not be null", firstJsonValue);

    JsonValue secondJsonValue = new JsonGeneratorImpl().createJsonObject(entity);
    assertNotNull("secondJsonValue must not be null", secondJsonValue);

    assertEquals(firstJsonValue.toString(), secondJsonValue.toString());
  }
  
  /**
   * Tests: an anonymous user that accesses a resource requires authentication.
   *
   * @param method the http method string
   * @param resourceUrl the resource url to access
   * @param data the data if any
   * @throws Exception
   */
  private void testAccessResourceAsAnonymous(String method, String resourceUrl, MultivaluedMap<String,String> h, byte[] data) throws Exception {
    testStatusCodeOfResource(null, method, resourceUrl, h, data, 401);

  }


  /**
   * Tests: an authenticated user that accesses a resource that is forbidden, has no permission.
   *
   * @param username the portal user name
   * @param method the http method string
   * @param resourceUrl the resource url to access
   * @param data the data if any
   * @throws Exception
   */
  private void testAccessResourceWithoutPermission(String username, String method, String resourceUrl, byte[] data)
                                                  throws Exception {
    testStatusCodeOfResource(username, method, resourceUrl, null, data, 403);
  }


  /**
   * Tests: an authenticated user that accesses a resource that is not found.
   *
   * @param username the portal user name
   * @param method the http method string
   * @param resourceUrl the resource url to access
   * @param data the data if any
   * @throws Exception
   */
  private void testAccessNotFoundResourceWithAuthentication(String username, String method, String resourceUrl,
                                                              byte[] data) throws Exception {
    testStatusCodeOfResource(username, method, resourceUrl, null, data, 404);
  }
  
  /**
   * Tests if the return status code matches the response of the request with provided username, method, resourceUrl, headers, and data.
   *
   * @param username the portal user name if userName == null mean not authenticated.
   * @param method the http method string
   * @param resourceUrl the resource url to access
   * @param h the header MultivalueMap
   * @param data the data if any
   * @param statusCode the expected status code of response
   * @throws Exception
   */
  private void testStatusCodeOfResource(String username, String method, String resourceUrl, 
                                                              MultivaluedMap<String,String> h,
                                                              byte[] data, int statusCode) throws Exception {
    ContainerResponse containerResponse = service(method, resourceUrl, "", h, data);
    assertEquals("The response code of resource("+resourceUrl+") is not expected.)",statusCode, containerResponse.getStatus());
  }
}
