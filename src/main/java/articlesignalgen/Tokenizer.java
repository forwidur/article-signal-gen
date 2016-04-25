package articlesignalgen;

import opennlp.tools.util.Span;
import opennlp.tools.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Tokenizer implements opennlp.tools.tokenize.Tokenizer {

  public static final Tokenizer INSTANCE;

  static {
    INSTANCE = new Tokenizer();
  }


  public Tokenizer() {
  }

  enum ChrType {
    WHITESPACE, ALPHANUMERICDASH, NUMERIC, OTHER
  }

  public String[] tokenize(String s) {
    return Span.spansToStrings(tokenizePos(s), s);
  }

  int cnt;

  public Span[] tokenizePos(String s) {
    ChrType charType = ChrType.WHITESPACE;
    ChrType state = charType;

    List<Span> tokens = new ArrayList<Span>();
    int sl = s.length();
    int start = -1;
    char pc = 0;
    for (int i = 0; i < sl; i++) {
      char c = s.charAt(i);
      if (StringUtil.isWhitespace(c)) {
        charType = ChrType.WHITESPACE;
      } else if (Character.isLetter(c) || c == '-') {
        // Allow digits in words that are not only digits.
        if (state == ChrType.NUMERIC) {
          state = ChrType.ALPHANUMERICDASH;
        }
        charType = ChrType.ALPHANUMERICDASH;
      } else if (Character.isDigit(c)) {
        // Allow digits in words that are not only digits.
        charType = state == ChrType.ALPHANUMERICDASH ?
            ChrType.ALPHANUMERICDASH : ChrType.NUMERIC;
      } else {
        charType = ChrType.WHITESPACE;
      }
      if (state == ChrType.WHITESPACE) {
        if (charType != ChrType.WHITESPACE) {
          start = i;
        }
      } else {
        if (charType != state) {
          // We only want alphanum with dash, longer than 2 characters.
          if (state == ChrType.ALPHANUMERICDASH && i - start > 2) {
            tokens.add(new Span(start, i));
            if(tokens.size() > 10000) {
              break;
            }
          }
          start = i;
        }
      }
      state = charType;
      pc = c;
    }
    if (charType == ChrType.ALPHANUMERICDASH) {
      tokens.add(new Span(start, sl));
    }

    /*
    System.out.println(s);
    System.out.println("===============================");
    System.out.println(String.join("|", Span.spansToStrings(tokens.toArray(new Span[tokens.size()]), s)));
    System.out.println("===============================");

    if (cnt++ > 10) {
      System.exit(1);
    }
    */

    return tokens.toArray(new Span[tokens.size()]);
  }
}
