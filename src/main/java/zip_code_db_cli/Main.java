package zip_code_db_cli;

import java.util.List;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

/**
 * t_zip_code テーブルへ郵便番号データを登録する。
 */
public class Main {
    /**
     * @param args -i, --initialize &lt;configPath&gt; &lt;directoryPath&gt;
     */
    public static void main(String[] args) {
        LongOpt[] longopts = new LongOpt[1];
        longopts[0] = new LongOpt("initialize", LongOpt.REQUIRED_ARGUMENT, null, 'i');

        Getopt options = new Getopt("Main", args, "i:", longopts);

        int c;
        int iFlag = 0;

        String dirPath = null;
        String configPath = null;

        while ((c = options.getopt()) != -1) {
            switch (c) {
            case 'i':
                configPath = args[1];
                dirPath = args[2];
                iFlag = 1;
                break;
            }
        }

        if (iFlag == 1) {
            // ファイルリストを取得。
            GetPathList gpl = new GetPathList(dirPath, "csv");
            List<String> pathList = gpl.runCommand();

            if (gpl.getCode() != 2) {
                System.exit(gpl.getCode());
            }

            // 既存レコードを削除。
            TZipCodeController tzcc = new TZipCodeController();
            tzcc.deleteRecord(configPath);

            if (tzcc.getCode() == 1) {
                System.exit(tzcc.getCode());
            }

            tzcc.resetNo(configPath);

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
                tzcc.insertRecord(configPath, recordset);
                System.exit(tzcc.getCode());
            }
        }
    }
}
