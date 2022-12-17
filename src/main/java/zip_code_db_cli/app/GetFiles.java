package zip_code_db_cli.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 指定したディレクトリ配下に存在する CSV ファイルの {@link List} を取得して返す。 */
public class GetFiles implements Function<String, List<File>> {
  /**
   * 指定したディレクトリ配下に存在する CSV ファイルの {@link List} を取得して返す。
   *
   * @param path 走査対象とするディレクトリのパスを指定する。
   * @return files CSV ファイルの {@link List} を返す。
   */
  @Override
  public List<File> apply(final String path) {
    final Logger logger = LoggerFactory.getLogger(getClass());
    List<File> fileList = new ArrayList<>();
    final File directory = new File(path);

    if (directory.isDirectory()) {
      final File[] fileArray =
          directory.listFiles(
              (dir, name) -> {
                if (name.toLowerCase(Locale.getDefault()).endsWith("csv")) {
                  return true;
                } else {
                  return false;
                }
              });

      fileList = Arrays.asList(fileArray);
    } else {
      logger.warn("{} が見つかりません。", path);
    }

    return fileList;
  }
}
