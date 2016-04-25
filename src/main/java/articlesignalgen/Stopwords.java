package articlesignalgen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;

public class Stopwords {
  private static final HashSet<String> stopwords_ = new HashSet<>();

  static {
    InputStream in = Stopwords.class.getClassLoader()
        .getResourceAsStream("excluded_keywords.txt");
    try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
      String l;
      while ((l = br.readLine()) != null) {
        stopwords_.add(l);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static boolean hasnt(String s) {
    return !stopwords_.contains(s);
  }

  public static String[] filter(String[] ss) {
    return Arrays.stream(ss).filter(Stopwords::hasnt).toArray(String[]::new);
  }
}
