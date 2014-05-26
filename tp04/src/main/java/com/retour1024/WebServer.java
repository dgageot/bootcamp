package com.retour1024;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import com.retour1024.model.LinesDao;
import com.retour1024.pagination.Page;
import com.retour1024.pagination.PageFactory;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer implements HttpHandler {
  private final PageFactory pageFactory;
  private final LinesDao linesDao;
  private final int port;
  private HttpServer server;

  /**
   * @param port    port tcp sur lequel le serveur est lancé
   * @param modules Permet de surcharger la configuration par défaut du serveur
   */
  public WebServer(int port, Module... modules) {
    this.port = port;

    Injector injector = createInjector(modules);
    pageFactory = injector.getInstance(PageFactory.class);
    linesDao = injector.getInstance(LinesDao.class);
  }

  private static Injector createInjector(Module... override) {
    Module overidenModule = Modules.override(new WebServerConfiguration()).with(override);
    return Guice.createInjector(overidenModule);
  }

  public static void main(String[] args) throws IOException {
    new WebServer(8080).start();
  }

  public void start() throws IOException {
    server = HttpServer.create(new InetSocketAddress(port), 0);
    server.createContext("/", this);
    server.start();
  }

  public void stop() {
    server.stop(0);
  }

  @VisibleForTesting
  public LinesDao getLinesDao() {
    return linesDao;
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String uri = exchange.getRequestURI().toString();

    if ("/".equals(uri)) {
      staticFile("index.html", "text/html", exchange);
    } else if ("/angular.min.js".equals(uri)) {
      staticFile("angular.min.js", "application/javascript", exchange);
    } else if ("/bootstrap.min.css".equals(uri)) {
      staticFile("bootstrap.min.css", "text/css", exchange);
    } else if (uri.startsWith("/page/")) {
      int page = Integer.parseInt(uri.split("/")[2]);
      page(page, exchange);
    } else {
      notFound(exchange);
    }

    exchange.close();
  }

  private void page(int pageIndex, HttpExchange exchange) throws IOException {
    Page page = pageFactory.page(pageIndex);

    byte[] response = new Gson().toJson(page).getBytes();
    ok(exchange, response, "application/json");
  }

  private void staticFile(String name, String contentType, HttpExchange exchange) throws IOException {
    byte[] response = Resources.toByteArray(Resources.getResource("web/" + name));
    ok(exchange, response, contentType);
  }

  private void ok(HttpExchange exchange, byte[] response, String contentType) throws IOException {
    exchange.getResponseHeaders().add("Content-Type", contentType);
    exchange.sendResponseHeaders(200, response.length);
    exchange.getResponseBody().write(response);
  }

  private void notFound(HttpExchange exchange) throws IOException {
    exchange.sendResponseHeaders(404, 0);
  }
}
