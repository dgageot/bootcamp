package com.retour1024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class NameVerifier {
  public void verifyName(final String name) {
    final List<RuntimeException> errors = new CopyOnWriteArrayList<RuntimeException>();

    Thread thread1 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          verifyNotAVegetable(name);
          verifyNotAThing(name);
        } catch (RuntimeException e) {
          errors.add(e);
        }
      }
    });

    Thread thread2 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          verifyInList(name);
        } catch (RuntimeException e) {
          errors.add(e);
        }
      }
    });

    thread1.start();
    thread2.start();

    try {
      thread1.join();
      thread2.join();
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }

    if (!errors.isEmpty()) {
      throw errors.get(0);
    }
  }

  private void verifyNotAThing(String name) {
    if (name.equalsIgnoreCase("Torchon") || name.equalsIgnoreCase("Serviette")) {
      throw new IllegalArgumentException("Nom invalide " + name);
    }
  }

  private void verifyNotAVegetable(String name) {
    if ((name.equalsIgnoreCase("Carotte")) || (name.equalsIgnoreCase("Chou"))) {
      throw new IllegalArgumentException("Nom invalide " + name);
    }
  }

  private void verifyInList(String name) {
    File path = new File(System.getProperty("user.home"), "names.txt");

    Scanner scanner = null;
    try {
      scanner = new Scanner(path);

      while (true) {
        String line = scanner.nextLine();
        if (line.startsWith("#")) {
          continue;
        }

        if (line.equalsIgnoreCase(name)) {
          return;
        }
      }
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Fichier des noms introuvable " + path);
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Nom introuvable " + name);
    } finally {
      if (scanner != null) {
        scanner.close();
      }
    }
  }
}