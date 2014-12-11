package com.retour1024;

import com.retour1024.pagination.PageResource;
import net.codestory.http.Configuration;
import net.codestory.http.routes.Routes;

public class WebConfiguration implements Configuration {
  @Override
  public void configure(Routes routes) {
    routes.add(PageResource.class);
  }
}
