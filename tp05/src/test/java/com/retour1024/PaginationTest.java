package com.retour1024;

import com.retour1024.helpers.PhantomJsDownloader;
import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Arrays;

import static org.fest.assertions.fluentlenium.FluentLeniumAssertions.assertThat;

public class PaginationTest extends FluentTest {
  public static final int PORT = 9999;

  private WebServer server;

  @Page
  public PaginationPage page;

  @Override
  public WebDriver getDefaultDriver() {
    return new PhantomJsDownloader().createDriver();
  }

  @Override
  public String getDefaultBaseUrl() {
    return "http://localhost:" + PORT;
  }

  @After
  public void stopServer() {
    if (server != null) {
      server.stop();
    }
  }

  @Test
  public void should_show_links_for_page_1() throws IOException {
    server = new WebServer(PORT);
    server.start();

    page.go();

    FluentList<FluentWebElement> links = page.links();
    assertThat(links).hasSize(8);
    assertThat(links.get(0)).hasText("1");
    assertThat(links.get(1)).hasText("2");
    assertThat(links.get(2)).hasText("3");
    assertThat(links.get(3)).hasText("4");
    assertThat(links.get(4)).hasText("5");
    assertThat(links.get(5)).hasText("...");
    assertThat(links.get(6)).hasText("80");
    assertThat(links.get(7)).hasText("Next");
  }

  @Test
  public void should_show_links_for_page_2() throws IOException {
    server = new WebServer(PORT);
    server.start();

    page.go();
    page.clickLinkForPage(2);

    FluentList<FluentWebElement> links = page.links();
    assertThat(links).hasSize(9);
    assertThat(links.get(0)).hasText("Previous");
    assertThat(links.get(1)).hasText("1");
    assertThat(links.get(2)).hasText("2");
    assertThat(links.get(3)).hasText("3");
    assertThat(links.get(4)).hasText("4");
    assertThat(links.get(5)).hasText("5");
    assertThat(links.get(6)).hasText("...");
    assertThat(links.get(7)).hasText("80");
    assertThat(links.get(8)).hasText("Next");
  }

  @Test
  public void should_show_links_for_page_5() throws IOException {
    server = new WebServer(PORT);
    server.start();

    page.go();
    page.clickLinkForPage(5);

    FluentList<FluentWebElement> links = page.links();
    assertThat(links).hasSize(11);
    assertThat(links.get(0)).hasText("Previous");
    assertThat(links.get(1)).hasText("1");
    assertThat(links.get(2)).hasText("...");
    assertThat(links.get(3)).hasText("3");
    assertThat(links.get(4)).hasText("4");
    assertThat(links.get(5)).hasText("5");
    assertThat(links.get(6)).hasText("6");
    assertThat(links.get(7)).hasText("7");
    assertThat(links.get(8)).hasText("...");
    assertThat(links.get(9)).hasText("80");
    assertThat(links.get(10)).hasText("Next");
  }

  @Test
  public void should_show_links_for_page_80() throws IOException {
    server = new WebServer(PORT);
    server.start();

    page.go();
    page.clickLinkForPage(80);

    FluentList<FluentWebElement> links = page.links();
    assertThat(links).hasSize(8);
    assertThat(links.get(0)).hasText("Previous");
    assertThat(links.get(1)).hasText("1");
    assertThat(links.get(2)).hasText("...");
    assertThat(links.get(3)).hasText("76");
    assertThat(links.get(4)).hasText("77");
    assertThat(links.get(5)).hasText("78");
    assertThat(links.get(6)).hasText("79");
    assertThat(links.get(7)).hasText("80");
  }

  @Test
  public void should_show_links_for_page_1_out_of_1() throws IOException {
    server = new WebServer(PORT);
    server.getLinesRepository().insert(Arrays.asList("1"));
    server.start();

    page.go();

    FluentList<FluentWebElement> links = page.links();
    assertThat(links).hasSize(1);
    assertThat(links.get(0)).hasText("1");
  }

  @Test
  public void should_show_links_for_page_2_out_of_2() throws IOException {
    server = new WebServer(PORT);
    server.getLinesRepository().insert(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"));
    server.start();

    page.go();
    page.clickLinkForPage(2);

    FluentList<FluentWebElement> links = page.links();
    assertThat(links).hasSize(3);
    assertThat(links.get(0)).hasText("Previous");
    assertThat(links.get(1)).hasText("1");
    assertThat(links.get(2)).hasText("2");
  }
}
