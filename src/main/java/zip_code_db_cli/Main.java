package zip_code_db_cli;

import java.util.List;

import csv_resource.CsvController;
import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

/**
 * t_zip_code テーブルへ郵便番号データを登録する。
 */
public class Main {
    /**
     * @param args -c, --configPath &lt;path&gt; DB 接続情報と CSV
     *             取得元ディレクトリのパスを記述した設定ファイルのパスを指定する。
     */
    public static void main(String[] args) {
        LongOpt[] longopts = new LongOpt[1];
        longopts[0] = new LongOpt("configPath", LongOpt.REQUIRED_ARGUMENT, null, 'c');

        Getopt options = new Getopt("Main", args, "c:", longopts);

        int c;
        int cFlag = 0;

        String configPath = null;
        String dirPath = null;

        while ((c = options.getopt()) != -1) {
            switch (c) {
            case 'c':
                configPath = options.getOptarg();
                cFlag = 1;
                break;
            }
        }

        if (cFlag == 1) {
            ConfigurationProperties cp = new ConfigurationProperties();
            cp.setPath(configPath);
            dirPath = cp.getDirectoryPath();

            if (cp.getCode() == 1) {
                System.exit(cp.getCode());
            }

            DirectoryController dc = new DirectoryController();
            dc.setPath(dirPath);
            List<String> pathList = dc.getPathList();

            if (dc.getCode() == 1) {
                System.exit(dc.getCode());
            }

            ZipCodeTable table = new ZipCodeTable();
            table.setPath(configPath);
            table.openConnection();

            if (table.getCode() == 1) {
                System.exit(table.getCode());
            }

            table.deleteRecord();

            if (table.getCode() == 1) {
                table.closeConnection();
                System.exit(table.getCode());
            }

            CsvController csv = new CsvController();

            for (String path : pathList) {
                csv.setPath(path);
                csv.setEncoding("MS932");
                List<String[]> recordset = csv.getContent();

                if (csv.getCode() == 1) {
                    table.closeConnection();
                    System.exit(csv.getCode());
                }

                table.insertRecord(recordset);

                if (table.getCode() == 1) {
                    table.closeConnection();
                    System.exit(table.getCode());
                }
            }

            table.closeConnection();
            System.exit(table.getCode());
        }
    }
}
