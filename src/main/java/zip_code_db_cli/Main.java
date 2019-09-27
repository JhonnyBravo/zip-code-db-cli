package zip_code_db_cli;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import basic_action_resource.DirectoryResource;
import content_resource.ContentResource;
import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import property_resource.PropertyResource;

/**
 * 郵便番号データの CSV を MySQL へ一括登録する。
 */
public class Main {
    /**
     * @param args
     *             <ul>
     *             <li>-i, --import &lt;config_path&gt; 引数に指定した設定ファイルに従って CSV を検出し、
     *             MySQL へ一括登録する。</li>
     *             </ul>
     */
    public static void main(String[] args) {
        final LongOpt[] longopts = new LongOpt[1];
        longopts[0] = new LongOpt("import", LongOpt.REQUIRED_ARGUMENT, null, 'i');

        final Getopt options = new Getopt("Main", args, "i:", longopts);
        final Logger logger = LoggerFactory.getLogger(Main.class);

        String configPath = null;

        ContentResource<Map<String, String>> pr = null;
        Map<String, String> properties = new HashMap<String, String>();

        int c;
        int importFlag = 0;

        while ((c = options.getopt()) != -1) {
            switch (c) {
            case 'i':
                configPath = options.getOptarg();
                importFlag = 1;
                break;
            }
        }

        boolean status = false;

        try {
            if (importFlag == 1) {
                pr = new PropertyResource(configPath);
                properties = pr.getContent();

                if (!properties.containsKey("csvPath")) {
                    logger.warn("csvPath の指定がありません。設定ファイルを確認してください。");
                    System.exit(1);
                }

                final DirectoryResource dr = new DirectoryResource(properties.get("csvPath"));
                dr.setFileFilter("csv");
                final File[] files = dr.getFiles();

                if (files.length == 0) {
                    System.exit(0);
                }

                logger.info("テーブルを初期化しています......");
                final ImportResource<List<ZipCode>> dbCtrl = new ZipCodeController(properties);
                status = dbCtrl.delete();

                for (final File file : files) {
                    logger.info(file.getCanonicalPath() + " をインポートしています......");
                    final CsvResource reader = new CsvResource(file.getCanonicalPath());
                    final List<ZipCode> contents = reader.getContent();
                    status = dbCtrl.create(contents);
                }

                logger.info("完了しました。");
            }

            if (status) {
                System.exit(2);
            } else {
                System.exit(0);
            }
        } catch (final Exception e) {
            logger.warn("エラーが発生しました。", e);
            System.exit(1);
        }
    }
}
