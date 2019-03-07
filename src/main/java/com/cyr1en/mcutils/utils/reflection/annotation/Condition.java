package com.cyr1en.mcutils.utils.reflection.annotation;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.util.function.Predicate;

public enum Condition {
  VERSION((server -> {
    String dirName = server.getClass().getPackage().getName();
    String versionString = dirName.substring(dirName.indexOf(46) + 1)
            .replaceAll("(_)([A-Z])\\w*", "").replaceAll("v", "")
            .replaceAll("_", ".");
    double parsed = Double.valueOf(versionString);
    return parsed >= 1.12;
  })),
  IGNORE(server -> false);

  Predicate<? super Server> predicate;

  Condition(Predicate<? super Server> predicate) {
    this.predicate = predicate;
  }

  public boolean check() {
    return predicate.test(Bukkit.getServer());
  }
}
