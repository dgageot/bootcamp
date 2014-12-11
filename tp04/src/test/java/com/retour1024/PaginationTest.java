package com.retour1024;

import com.retour1024.pagination.LinesDao;
import net.codestory.http.Configuration;
import net.codestory.http.WebServer;
import net.codestory.http.injection.Singletons;
import net.codestory.simplelenium.SeleniumTest;
import org.junit.After;
import org.junit.Test;

import java.util.Arrays;

import static net.codestory.http.Configuration.override;

public class PaginationTest extends SeleniumTest {
  public static final int PORT = 9999;

  private WebServer server;

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

  private void startServer(Configuration configuration) {
    server = new WebServer().configure(configuration).start(PORT);
  }

  @Test
  public void should_show_links_for_page_1() {
    startServer(new WebConfiguration());

    goTo("/");

    find(".pagination a").should().haveSize(8);
    find(".pagination li:nth-child(1) a").should().contain("1");
    find(".pagination li:nth-child(2) a").should().contain("2");
    find(".pagination li:nth-child(3) a").should().contain("3");
    find(".pagination li:nth-child(4) a").should().contain("4");
    find(".pagination li:nth-child(5) a").should().contain("5");
    find(".pagination li:nth-child(6) a").should().contain("...");
    find(".pagination li:nth-child(7) a").should().contain("80");
    find(".pagination li:nth-child(8) a").should().contain("Next");
  }

  @Test
  public void should_show_links_for_page_2() {
    startServer(new WebConfiguration());

    goTo("/");
    find(".pagination a").withText("2").click();

    find(".pagination a").should().haveSize(9);
    find(".pagination li:nth-child(1) a").should().contain("Previous");
    find(".pagination li:nth-child(2) a").should().contain("1");
    find(".pagination li:nth-child(3) a").should().contain("2");
    find(".pagination li:nth-child(4) a").should().contain("3");
    find(".pagination li:nth-child(5) a").should().contain("4");
    find(".pagination li:nth-child(6) a").should().contain("5");
    find(".pagination li:nth-child(7) a").should().contain("...");
    find(".pagination li:nth-child(8) a").should().contain("80");
    find(".pagination li:nth-child(9) a").should().contain("Next");
  }

  @Test
  public void should_show_links_for_page_5() {
    startServer(new WebConfiguration());

    goTo("/");
    find(".pagination a").withText("5").click();

    find(".pagination a").should().haveSize(11);
    find(".pagination li:nth-child(1) a").should().contain("Previous");
    find(".pagination li:nth-child(2) a").should().contain("1");
    find(".pagination li:nth-child(3) a").should().contain("...");
    find(".pagination li:nth-child(4) a").should().contain("3");
    find(".pagination li:nth-child(5) a").should().contain("4");
    find(".pagination li:nth-child(6) a").should().contain("5");
    find(".pagination li:nth-child(7) a").should().contain("6");
    find(".pagination li:nth-child(8) a").should().contain("7");
    find(".pagination li:nth-child(9) a").should().contain("...");
    find(".pagination li:nth-child(10) a").should().contain("80");
    find(".pagination li:nth-child(11) a").should().contain("Next");
  }

  @Test
  public void should_show_links_for_page_80() {
    startServer(new WebConfiguration());

    goTo("/");
    find(".pagination a").withText("80").click();

    find(".pagination a").should().haveSize(8);
    find(".pagination li:nth-child(1) a").should().contain("Previous");
    find(".pagination li:nth-child(2) a").should().contain("1");
    find(".pagination li:nth-child(3) a").should().contain("...");
    find(".pagination li:nth-child(4) a").should().contain("76");
    find(".pagination li:nth-child(5) a").should().contain("77");
    find(".pagination li:nth-child(6) a").should().contain("78");
    find(".pagination li:nth-child(7) a").should().contain("79");
    find(".pagination li:nth-child(8) a").should().contain("80");
  }

  @Test
  public void should_show_links_for_page_1_out_of_1() {
    startServer(override(new WebConfiguration()).with(routes -> {
      Singletons beans = new Singletons();
      routes.setIocAdapter(beans);

      beans.get(LinesDao.class).insert(Arrays.asList("1"));
    }));

    goTo("/");

    find(".pagination a").should().haveSize(1);
    find(".pagination li:nth-child(1) a").should().contain("1");
  }

  @Test
  public void should_show_links_for_page_2_out_of_2() {
    startServer(override(new WebConfiguration()).with(routes -> {
      Singletons beans = new Singletons();
      routes.setIocAdapter(beans);

      beans.get(LinesDao.class).insert(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"));
    }));

    goTo("/");
    find(".pagination a").withText("2").click();

    find(".pagination a").should().haveSize(3);
    find(".pagination li:nth-child(1) a").should().contain("Previous");
    find(".pagination li:nth-child(2) a").should().contain("1");
    find(".pagination li:nth-child(3) a").should().contain("2");
  }
}
