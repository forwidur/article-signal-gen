package articlesignalgen;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class SignalGen {
  public static void main(String[] args) throws Exception {
    DataSource s = new DataSource();

    final List<Article> as = s.getArticles("WHERE specialty_ids IS NOT NULL AND abstract IS NOT NULL");
    System.out.println(String.format("Read: %d", as.size()));

    final Gson gson = new Gson();

    final File f = new File("articles.json");
    f.createNewFile();
    FileOutputStream fOut = new FileOutputStream(f);
    OutputStreamWriter w = new OutputStreamWriter(fOut);
    w.append(gson.toJson(as));
    w.close();
    fOut.close();
  }
}
