package com.retour1024.pagination;

public class Link {
  final String text;
  final int page;
  final boolean selected;

  public Link(String text, int page, boolean selected) {
    this.text = text;
    this.page = page;
    this.selected = selected;
  }
}
