package zip_code_db_cli;

import java.util.List;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

/**
 * t_zip_code テーブルへ郵便番号データを登録する。
 */
public class Main {
    /**
     * @param args
     *             <ol>
     *             <li>-c, --configPath &lt;path&gt; DB 接続時に使用する設定ファイルのパスを指定する。</li>
     *             <li>-d, --dirPath &lt;path&gt; インポート対象とする CSV
     *             ファイルが格納されているディレクトリのパスを指定する。</li>
     *             </ol>
     */
    public static void main(String[] args) {
        LongOpt[] longopts = new LongOpt[2];
        longopts[0] = new LongOpt("configPath", LongOpt.REQUIRED_ARGUMENT, null, 'c');
        longopts[1] = new LongOpt("dirPath", LongOpt.REQUIRED_ARGUMENT, null, 'd');

        Getopt options = new Getopt("Main", args, "c:d:", longopts);

        int c;
        int cFlag = 0;
        int dFlag = 0;

        String configPath = null;
        String dirPath = null;

        while ((c = options.getopt()) != -1) {
            switch (c) {
            case 'c':
                configPath = options.getOptarg();
                cFlag = 1;
                break;
            case 'd':
                dirPath = options.getOptarg();
                dFlag = 1;
                break;
            }
        }

        if (cFlag == 1 && dFlag == 1) {
            // ファイルリストを取得。
            GetPathList gpl = new GetPathList();
            gpl.init(dirPath, "csv");
            List<String> pathList = gpl.runCommand();

            if (gpl.getCode() != 2) {
                System.exit(gpl.getCode());
            }

            TZipCodeController tzcc = new TZipCodeController();
            tzcc.init(configPath);

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
