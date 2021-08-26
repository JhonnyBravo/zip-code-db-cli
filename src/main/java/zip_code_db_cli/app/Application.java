package zip_code_db_cli.app;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import jakarta.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.List;
import java_itamae_connection.domain.service.connection_info.ConnectionInfoService;
import java_itamae_contents.domain.model.ContentsAttribute;
import java_itamae_properties.domain.service.properties.PropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zip_code_db_cli.domain.model.ZipCode;
import zip_code_db_cli.domain.service.csv_contents.CsvContentsService;
import zip_code_db_cli.domain.service.zip_code.ZipCodeService;

/**
 * 郵便番号データを MySQL へ一括登録する。
 */
public class Application {
  @Inject
  private PropertiesService ps;
  @Inject
  private ConnectionInfoService cis;
  @Inject
  private ZipCodeService zcs;
  @Inject
  private CsvContentsService ccs;
  @Inject
  private Usage usage;

  /**
   * 郵便番号データを MySQL へ一括登録する。
   *
   * @param args
   *        <ul>
   *        <li>--import, -i &lt;config_path&gt;: config_path に指定した設定ファイルの内容に従って CSV を読込み、 MySQL
   *        へ一括登録する。</li>
   *        </ul>
   */
  public int main(String[] args) {
    final LongOpt[] longopts = new LongOpt[1];
    longopts[0] = new LongOpt("import", LongOpt.REQUIRED_ARGUMENT, null, 'i');

    final Getopt options = new Getopt("Main", args, "i:", longopts);
    final Logger logger = LoggerFactory.getLogger(Application.class);

    final ContentsAttribute config = new ContentsAttribute();

    int c;
    int importFlag = 0;

    while ((c = options.getopt()) != -1) {
      switch (c) {
        case 'i':
          config.setPath(options.getOptarg());
          importFlag = 1;
          break;
        default:
          usage.run();
      }
    }

    boolean status = false;

    try {
      if (importFlag == 1) {
        ps.init(config);
        final String csvPath = ps.getProperty("csvPath");
        final File directory = new File(csvPath);

        if (!directory.isDirectory()) {
          throw new FileNotFoundException(csvPath + " が見つかりません。");
        }

        final FilenameFilter filter = (dir, name) -> {
          if (name.toLowerCase().endsWith("csv")) {
            return true;
          } else {
            return false;
          }
        };

        zcs.init(cis.getConnectionInfo(config));
        zcs.deleteAll();

        final File[] files = directory.listFiles(filter);

        for (final File file : files) {
          final ContentsAttribute csv = new ContentsAttribute();
          csv.setPath(file.getCanonicalPath());
          ccs.init(csv);
          final List<ZipCode> contents = ccs.getContents();

          logger.info(csv.getPath() + " をインポートしています......");
          status = zcs.create(contents);
        }

        logger.info("完了しました。");
      }
    } catch (final Exception e) {
      logger.warn(e.toString());
      return 1;
    }

    if (status) {
      return 2;
    } else {
      return 0;
    }
  }
}
