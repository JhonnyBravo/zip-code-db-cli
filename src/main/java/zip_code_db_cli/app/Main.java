package zip_code_db_cli.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import java_itamae_contents.domain.model.ContentsAttribute;
import zip_code_db_cli.domain.model.ZipCode;
import zip_code_db_cli.domain.service.csv_contents.CsvContentsService;
import zip_code_db_cli.domain.service.csv_contents.CsvContentsServiceImpl;
import zip_code_db_cli.domain.service.zip_code.ZipCodeService;
import zip_code_db_cli.domain.service.zip_code.ZipCodeServiceImpl;

/**
 * 郵便番号データを MySQL へ一括登録する。
 */
public class Main {
    /**
     * @param args
     *            <ul>
     *            <li>--import, -i &lt;path&gt;: path に指定したディレクトリ配下に存在する CSV
     *            を読込み、 MySQL へ一括登録する。</li>
     *            </ul>
     */
    public static void main(String[] args) {
        final LongOpt[] longopts = new LongOpt[1];
        longopts[0] = new LongOpt("import", LongOpt.REQUIRED_ARGUMENT, null, 'i');

        final Getopt options = new Getopt("Main", args, "i:", longopts);
        final Logger logger = LoggerFactory.getLogger(Main.class);

        final ContentsAttribute config = new ContentsAttribute();

        int c;
        int importFlag = 0;

        while ((c = options.getopt()) != -1) {
            switch (c) {
                case 'i' :
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

                final ZipCodeService zcs = new ZipCodeServiceImpl();
                zcs.deleteAll();

                final File[] files = directory.listFiles(filter);

                for (final File file : files) {
                    final ContentsAttribute csv = new ContentsAttribute();
                    csv.setPath(file.getCanonicalPath());

                    final CsvContentsService ccs = new CsvContentsServiceImpl(csv);
                    final List<ZipCode> contents = ccs.getContents();

                    logger.info(csv.getPath() + " をインポートしています......");
                    status = zcs.create(contents);
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
