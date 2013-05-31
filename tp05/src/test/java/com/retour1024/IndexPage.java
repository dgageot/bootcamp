package com.retour1024;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.concurrent.TimeUnit;

import static org.fluentlenium.core.filter.FilterConstructor.withText;

public class IndexPage extends FluentPage {
  FluentWebElement pagination;

  @Override
  public String getUrl() {
    return "/";
  }

  public void clickLinkForPage(int page) {
    pagination.find("a", withText("" + page)).click();

    await().atMost(1, TimeUnit.MINUTES).until("#pagination a.selected").hasText("" + page);
  }

  public FluentList<FluentWebElement> links() {
    return pagination.find("a");
  }
}
