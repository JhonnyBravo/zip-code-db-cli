package zip_code_db_cli;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

/**
 * zip_code テーブルへ郵便番号データを登録する。
 */
@SpringBootApplication
public class Main {
    /**
     * @param args
     *             <ol>
     *             <li>-d, --dirPath &lt;path&gt; インポート対象とする CSV
     *             ファイルが格納されているディレクトリのパスを指定する。</li>
     *             </ol>
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        LongOpt[] longopts = new LongOpt[1];
        longopts[0] = new LongOpt("dirPath", LongOpt.REQUIRED_ARGUMENT, null, 'd');

        Getopt options = new Getopt("Main", args, "d:", longopts);

        int c;
        int dFlag = 0;
        String dirPath = null;

        while ((c = options.getopt()) != -1) {
            switch (c) {
            case 'd':
                dirPath = options.getOptarg();
                dFlag = 1;
                break;
            }
        }

        if (dFlag == 1) {
            // ファイルリストを取得。
            GetPathList gpl = context.getBean(GetPathList.class);
            gpl.init(dirPath, "csv");
            List<String> pathList = gpl.runCommand();

            if (gpl.getCode() != 2) {
                System.exit(gpl.getCode());
            }

            ZipCodeController tzcc = context.getBean(ZipCodeController.class);

            // 既存レコードを削除。
            tzcc.deleteRecord();

            if (tzcc.getCode() == 1) {
                System.exit(tzcc.getCode());
            }

            for (String path : pathList) {
                // CSV 読込。
                List<String[]> recordset = tzcc.importCsv(path);

                if (tzcc.getCode() == 1) {
                    System.exit(tzcc.getCode());
                }

                // 新規レコードを登録。
                tzcc.insertRecord(recordset);

                if (tzcc.getCode() == 1) {
                    System.exit(tzcc.getCode());
                }
            }

            System.exit(tzcc.getCode());
        }
    }
}
