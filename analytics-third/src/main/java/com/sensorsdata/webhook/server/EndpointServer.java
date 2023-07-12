/*
 * Copyright 2019 Sensors Data Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sensorsdata.webhook.server;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import javax.servlet.http.HttpServlet;

@Slf4j
public class EndpointServer {

  private Server jettyServer;

  public void startServer(int localPort, HttpServlet servlet, int threadPoolMaxSize) throws Exception {
    QueuedThreadPool serverPool = new QueuedThreadPool(threadPoolMaxSize);
    serverPool.setName("server");
    jettyServer = new Server(serverPool);
    ServerConnector serverConnector = new ServerConnector(jettyServer);
    serverConnector.setPort(localPort);
    jettyServer.addConnector(serverConnector);

    ServletContextHandler appCtx = new ServletContextHandler(jettyServer, "/", true, false);
    appCtx.addServlet(new ServletHolder(servlet), "/");
    jettyServer.start();
  }

  public void stop() throws Exception {
    if (jettyServer != null) {
      jettyServer.stop();
    }
  }

  public void join() throws InterruptedException {
    if (jettyServer != null) {
      jettyServer.join();
    }
  }
}
