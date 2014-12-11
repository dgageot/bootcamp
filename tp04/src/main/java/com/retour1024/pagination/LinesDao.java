package com.retour1024.pagination;

import java.util.ArrayList;
import java.util.List;

public class LinesDao {
  private static final int DEFAULT_LINE_COUNT = 800;

  private List<String> lines;

  public List<String> findAll() {
    if (lines == null) {
      lines = createDefaultLines(DEFAULT_LINE_COUNT);
    }
    fakeSlowDatabaseAccess();
    return lines;
  }

  public void insert(List<String> lines) {
    this.lines = lines;
    fakeSlowDatabaseAccess();
  }

  public int count() {
    return findAll().size();
  }

  private static List<String> createDefaultLines(int count) {
    List<String> lines = new ArrayList<>();
    for (int line = 1; line <= count; line++) {
      lines.add("Ligne " + line);
    }
    return lines;
  }

  private void fakeSlowDatabaseAccess() {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      // Ignore
    }
  }
}
