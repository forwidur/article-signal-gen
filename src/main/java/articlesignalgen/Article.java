package articlesignalgen;

import opennlp.tools.stemmer.PorterStemmer;
import org.jsoup.Jsoup;

import java.util.Arrays;

public class Article {
  public Integer id;
  public String title;
  public String[] titlet;
  public String[] titlets;
  public String abs;
  public String[] abst;
  public String[] absts;
  public Integer[] specs;
  public String[] kws;
  public String[] kwss;
//  public String body = null;
//  public String[] bodyt;
//  public String[] bodyts;

  public Article(Integer id, String title, String abs, String[] kws, Integer[] topic) {
    this.id = id;
    this.title = title;
    titlet = Stopwords.filter(Tokenizer.INSTANCE.tokenize(title.toLowerCase()));
    titlets = Stopwords.filter(stem(titlet));
    this.abs = stripTags(abs);
    if (this.abs != null) {
      abst = Stopwords.filter(Tokenizer.INSTANCE.tokenize(this.abs.toLowerCase()));
      absts = Stopwords.filter(stem(abst));
    } else {
      abst = absts = null;
    }
    this.kws = Stopwords.filter(
        Arrays.stream(kws).map(String::toLowerCase).toArray(String[]::new));
    this.kwss = Stopwords.filter(stem(this.kws));
    specs = topic;
//    body = text;
//    bodyt = Tokenizer.INSTANCE.tokenize(text.toLowerCase());
 //   bodyts = stem(bodyt);
  }

  private String[] stem(String[] ts) {
    PorterStemmer stm = new PorterStemmer();
    String[] res = new String[ts.length];
    int i = 0;
    for (String t: ts) {
      res[i++] = stm.stem(t);
    }
    return res;
  }

  private String stripTags(String s) {
    return Jsoup.parse(s).text();
  }
}
