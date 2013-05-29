package com.retour1024.pagination;

import com.google.common.collect.Lists;

import java.util.List;

public class Pagination {
  private final int page;
  private final int numberOfPages;

  public Pagination(int page, int numberOfPages) {
    this.page = page;
    this.numberOfPages = numberOfPages;
  }

  public boolean getHasPrevious() {
    return page > 1;
  }

  public List<Integer> getPreviousPages() {
    List<Integer> pages = Lists.newArrayList();
    if (page > 1) {
      pages.add(1);
    }
    return pages;
  }

  public boolean getHasDotsBefore() {
    return (page > 4) && (numberOfPages > 6);
  }

  public List<Integer> getPagesBefore() {
    List<Integer> pages = Lists.newArrayList();
    if ((page > 5) && (page > numberOfPages - 1)) {
      pages.add(page - 4);
    }
    if ((page > 4) && (page > numberOfPages - 2)) {
      pages.add(page - 3);
    }
    if (page > 3) {
      pages.add(page - 2);
    }
    if (page > 2) {
      pages.add(page - 1);
    }
    return pages;
  }

  public List<Integer> getPagesAfter() {
    List<Integer> pages = Lists.newArrayList();
    if (page <= numberOfPages - 1) {
      pages.add(page + 1);
    }
    if (page <= numberOfPages - 2) {
      pages.add(page + 2);
    }
    if ((page <= numberOfPages - 3) && (page < 3)) {
      pages.add(page + 3);
    }
    if ((page <= numberOfPages - 4) && (page < 2)) {
      pages.add(page + 4);
    }
    return pages;
  }

  public boolean getHasDotsAfter() {
    return (page < numberOfPages - 3) && (numberOfPages > 6);
  }

  public List<Integer> getNextPages() {
    List<Integer> pages = Lists.newArrayList();
    if ((page < numberOfPages - 2) && (numberOfPages > 5)) {
      pages.add(numberOfPages);
    }
    return pages;
  }

  public boolean getHasNext() {
    return page < numberOfPages;
  }
}