package com.retour1024.pagination;

import com.retour1024.pagination.Page;
import com.retour1024.pagination.PageFactory;
import net.codestory.http.annotations.Get;

public class PageResource {
  private final PageFactory pageFactory;

  public PageResource(PageFactory pageFactory) {
    this.pageFactory = pageFactory;
  }

  @Get("/page/:pageIndex")
  public Page page(int pageIndex) {
    return pageFactory.page(pageIndex);
  }
}
