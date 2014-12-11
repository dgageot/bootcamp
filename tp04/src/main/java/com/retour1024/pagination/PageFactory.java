package com.retour1024.pagination;

import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.List;

public class PageFactory {
  private static final int PER_PAGE = 10;

  private final LinesDao linesDao;

  public PageFactory(LinesDao linesDao) {
    this.linesDao = linesDao;
  }

  public Page page(int pageIndex) {
    return new Page(lines(pageIndex), links(pageIndex));
  }

  private List<String> lines(int pageIndex) {
    Iterable<List<String>> pages = Iterables.partition(linesDao.findAll(), PER_PAGE);
    return Iterables.get(pages, pageIndex - 1);
  }

  private List<Link> links(int pageIndex) {
    List<Link> links = new ArrayList<>();

    Pagination pagination = new Pagination(pageIndex, (linesDao.count() + PER_PAGE - 1) / PER_PAGE);

    if (pagination.getHasPrevious()) {
      links.add(new Link("Previous", pageIndex - 1, false));
    }
    for (int page : pagination.getPreviousPages()) {
      links.add(new Link(Integer.toString(page), page, false));
    }
    if (pagination.getHasDotsBefore()) {
      links.add(new Link("...", 0, false));
    }
    for (int page : pagination.getPagesBefore()) {
      links.add(new Link(Integer.toString(page), page, false));
    }
    links.add(new Link("" + pageIndex, pageIndex, true));
    for (int page : pagination.getPagesAfter()) {
      links.add(new Link(Integer.toString(page), page, false));
    }
    if (pagination.getHasDotsAfter()) {
      links.add(new Link("...", 0, false));
    }
    for (int page : pagination.getNextPages()) {
      links.add(new Link(Integer.toString(page), page, false));
    }
    if (pagination.getHasNext()) {
      links.add(new Link("Next", pageIndex + 1, false));
    }

    return links;
  }
}
