package com.cyr1en.mcutils.initializers;

import java.util.Comparator;

public class NegativeComparator {

  public static Comparator<Integer> atEnd(final Comparator<Integer> comparator) {
    return (o1, o2) -> compare(o1, o2, comparator, true);
  }

  public static Comparator<Integer> atBeginning(final Comparator<Integer> comparator) {
    return (o1, o2) -> compare(o1, o2, comparator, false);
  }

  private static int compare(Integer o1, Integer o2, final Comparator<Integer> comparator, boolean end) {
    if (o1 < 0)
      return end ? 1 : -1;
    if (o2 < 0)
      return end ? -1 : 1;
    return comparator.compare(o1, o2);
  }
}
