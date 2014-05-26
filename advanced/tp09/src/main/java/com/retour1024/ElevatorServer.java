package com.retour1024;

import java.io.*;
import java.net.*;

import org.simpleframework.http.*;
import org.simpleframework.http.core.*;
import org.simpleframework.transport.connect.*;

public class ElevatorServer implements Container {
  String doHandle(String path, Query query) {
    // TODO : ajouter les mappings d'urls
    //
    return "Hello " + path;
  }

  @Override
  public void handle(Request request, Response response) {
    System.out.println(request.getPath());

    try {
      String body = doHandle(request.getPath().getPath(), request.getQuery());
      response.getPrintStream().append(body);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        response.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  private void start(int port) throws IOException {
    new SocketConnection(new ContainerServer(this)).connect(new InetSocketAddress(port));
  }

  public static void main(String[] args) throws IOException {
    new ElevatorServer().start(9000);
  }
}