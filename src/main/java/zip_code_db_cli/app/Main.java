package zip_code_db_cli.app;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java_itamae.app.properties.ContentsModelValidator;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;
import java_itamae_connection.domain.model.ConnectionInfo;
import java_itamae_connection.domain.model.ConnectionInfoConverter;
import java_itamae_connection.domain.model.ConnectionInfoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zip_code_db_cli.domain.model.ZipCode;
import zip_code_db_cli.domain.service.zip_code.ZipCodeService;
import zip_code_db_cli.domain.service.zip_code.ZipCodeServiceImpl;

/** 郵便番号データを MySQL へ一括登録する。 */
public class Main {
  /**
   * 郵便番号データを MySQL へ一括登録する。
   *
   * @param args
   *     <ul>
   *       <li>--import, -i &lt;config_path&gt;: config_path に指定した設定ファイルの内容に従って CSV を読込み、 MySQL
   *           へ一括登録する。
   *     </ul>
   */
  @SuppressWarnings("unused")
  public static void main(final String[] args) {
    final LongOpt[] longopts = new LongOpt[1];
    longopts[0] = new LongOpt("import", LongOpt.REQUIRED_ARGUMENT, null, 'i');

    final Getopt options = new Getopt("Main", args, "i:", longopts);
    final Logger logger = LoggerFactory.getLogger(Main.class);

    final ContentsModel config = new ContentsModel();

    int option;
    int status = 0;
    final Usage usage = new Usage();
    int importFlag = 0;

    if (args.length == 0) {
      usage.run();
      status = 1;
    }

    while ((option = options.getopt()) != -1) {
      if (option == 'i') {
        config.setPath(options.getOptarg());
        importFlag = 1;
      } else {
        usage.run();
        status = 1;
      }
    }

    final PropertiesService propertiesService = new PropertiesServiceImpl();
    final GetFiles getFiles = new GetFiles();
    final GetCsvContents getCsvContents = new GetCsvContents();

    List<File> fileList = new ArrayList<>();
    final List<ZipCode> zipCodeList = new ArrayList<>();

    // CSV の取得処理
    if (status != 1 && new ContentsModelValidator().test(config)) {
      propertiesService.init(config);

      try {
        fileList = getFiles.apply(propertiesService.getProperty("csvPath"));

        for (final File file : fileList) {
          final ContentsModel csv = new ContentsModel();
          csv.setPath(file.getCanonicalPath());
          zipCodeList.addAll(getCsvContents.apply(csv));
        }
      } catch (final Exception e) {
        final String message = e.toString();
        logger.warn("{}", message);
        status = 1;
      }
    } else {
      status = 1;
    }

    if (importFlag == 1 && !zipCodeList.isEmpty()) {
      try {
        // ConnectionInfo の取得
        final ConnectionInfoConverter converter = new ConnectionInfoConverter();
        final ConnectionInfo cnInfo = converter.apply(propertiesService.getProperties());

        if (new ConnectionInfoValidator().test(cnInfo)) {
          final ZipCodeService zipCodeService = new ZipCodeServiceImpl();
          zipCodeService.init(cnInfo);

          // DB の初期化処理
          if (status != 1) {
            logger.info("DB を初期化しています......");
            status = zipCodeService.deleteAll();
          }

          // DB への登録処理
          if (status != 1) {
            logger.info("レコードを登録しています......");
            status = zipCodeService.create(zipCodeList);
          }
        } else {
          status = 1;
        }
      } catch (final Exception e) {
        final String message = e.toString();
        logger.warn("{}", message);
        status = 1;
      }
    }

    logger.info("完了しました。");
    System.exit(status);
  }
}
