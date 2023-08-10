package de.kontux.icepractice.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LangUtil {
  public static String capitalize(String string) {
    return Character.toUpperCase(string.charAt(0)) + string.substring(1);
  }
  
  public static String formatSeconds(int total) {
    int minutes = total / 60;
    int seconds = total % 60;
    return String.format("%d:%02d", new Object[] { Integer.valueOf(minutes), Integer.valueOf(seconds) });
  }
  
  public static <T> T getNext(List<T> list, T target) {
    int index = list.indexOf(target);
    if (index < 0 || index + 1 == list.size())
      return list.get(0); 
    return list.get(index + 1);
  }
  
  @SafeVarargs
  public static <T> List<T> combineLists(List<T>... lists) {
    return (List<T>)Stream.<List<T>>of(lists).flatMap(Collection::stream).collect(Collectors.toList());
  }
}
