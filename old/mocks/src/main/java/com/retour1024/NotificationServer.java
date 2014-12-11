package com.retour1024;

public class NotificationServer {
  private final SmtpServer smtpServer;
  private final EmailFactory emailFactory;

  public NotificationServer(SmtpServer smtpServer, EmailFactory emailFactory) {
    this.smtpServer = smtpServer;
    this.emailFactory = emailFactory;
  }

  public boolean notify(String message, User... users) {
    try {
      for (User user : users) {
        String email = user.getEmail();
        String body = emailFactory.createBody(email, message);

        smtpServer.send(email, body);
      }
    } catch (Exception e) {
      return false;
    }

    return true;
  }
}
