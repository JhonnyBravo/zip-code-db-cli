package zip_code_db_cli;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import java_itamae_contents.domain.model.ContentsAttribute;
import zip_code_db_cli.domain.model.ZipCodeEntity;
import zip_code_db_cli.domain.service.csv_contents.CsvContentsService;
import zip_code_db_cli.domain.service.csv_contents.CsvContentsServiceImpl;
import zip_code_db_cli.domain.service.zip_code.ZipCodeService;
import zip_code_db_cli.domain.service.zip_code.ZipCodeServiceImpl;

/**
 * zip_code テーブルへ郵便番号データを登録する。
 */
@SpringBootApplication(scanBasePackages = "zip_code_db_cli")
public class Main {
    /**
     * @param args
     *             <ol>
     *             <li>-d, --dirPath &lt;path&gt; インポート対象とする CSV
     *             ファイルが格納されているディレクトリのパスを指定する。</li>
     *             </ol>
     */
    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        final LongOpt[] longopts = new LongOpt[1];
        longopts[0] = new LongOpt("import", LongOpt.REQUIRED_ARGUMENT, null, 'i');

        final Getopt options = new Getopt("Main", args, "i:", longopts);
        final Logger logger = LoggerFactory.getLogger(Main.class);

        final ContentsAttribute config = new ContentsAttribute();

        int c;
        int importFlag = 0;

        while ((c = options.getopt()) != -1) {
            switch (c) {
            case 'i':
                config.setPath(options.getOptarg());
                importFlag = 1;
                break;
            }
        }

        boolean status = false;

        try {
            if (importFlag == 1) {
                final File directory = new File(config.getPath());

                if (!directory.isDirectory()) {
                    throw new FileNotFoundException(config.getPath() + " が見つかりません。");
                }

                final FilenameFilter filter = (dir, name) -> {
                    if (name.toLowerCase().endsWith("csv")) {
                        return true;
                    } else {
                        return false;
                    }
                };

                final ZipCodeService zcs = context.getBean(ZipCodeServiceImpl.class);
                final CsvContentsService ccs = context.getBean(CsvContentsServiceImpl.class);

                zcs.deleteAll();

                final File[] files = directory.listFiles(filter);

                for (final File file : files) {
                    final ContentsAttribute csv = new ContentsAttribute();
                    csv.setPath(file.getCanonicalPath());
                    ccs.init(csv);

                    final List<ZipCodeEntity> recordset = new ArrayList<>();

                    ccs.getContents().forEach(content -> {
                        final ZipCodeEntity record = new ZipCodeEntity();

                        record.setJisCode(content.getJisCode());
                        record.setZipCode(content.getZipCode());
                        record.setPrefecturePhonetic(content.getPrefecturePhonetic());
                        record.setCityPhonetic(content.getCityPhonetic());
                        record.setAreaPhonetic(content.getAreaPhonetic());
                        record.setPrefecture(content.getPrefecture());
                        record.setCity(content.getCity());
                        record.setArea(content.getArea());
                        record.setUpdateFlag(content.getUpdateFlag());
                        record.setReasonFlag(content.getReasonFlag());

                        recordset.add(record);
                    });

                    logger.info(csv.getPath() + " をインポートしています......");
                    status = zcs.saveAll(recordset);
                }

                logger.info("完了しました。");
            }
        } catch (final Exception e) {
            logger.warn(e.toString());
            System.exit(1);
        }

        if (status) {
            System.exit(2);
        } else {
            System.exit(0);
        }
    }
}
