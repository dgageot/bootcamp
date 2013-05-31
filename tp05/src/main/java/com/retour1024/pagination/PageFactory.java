package com.retour1024.pagination;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.retour1024.model.LinesDao;

import java.util.List;

@Singleton
public class PageFactory {
  private static final int PER_PAGE = 10;

  private final LinesDao linesDao;

  @Inject
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
    List<Link> links = Lists.newArrayList();

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
