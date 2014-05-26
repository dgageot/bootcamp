package com.retour1024;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.*;

import org.junit.*;

public class ElevatorServerTest {
  @Test
  public void say_hello_bob() throws IOException {
    ElevatorServer.main(null);

    given().port(9000).expect().body(equalTo("Hello /Bob")).when().get("/Bob");
  }
}
