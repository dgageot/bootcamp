package com.retour1024.pagination;

import java.util.List;

public class Page {
  final List<String> lines;
  final List<Link> links;

  public Page(List<String> lines, List<Link> links) {
    this.lines = lines;
    this.links = links;
  }
}
