package zip_code_db_cli;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import basic_action_resource.DirectoryResource;
import context_resource.PropertiesResource;

/**
 * CSV から DB へレコードを一括登録する。
 */
public class Main {
    /**
     * @param args レコード登録時に使用する設定ファイルのパスを指定する。
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            // directoryPath の取得
            String configPath = args[0];
            PropertiesResource pr = new PropertiesResource(configPath);
            pr.openContext();

            if (pr.getCode() == 1) {
                pr.closeContext();
                System.exit(1);
            }

            Properties p = (Properties) pr.getContext();

            if (!p.containsKey("directoryPath")) {
                System.err.println("directoryPath が定義されていません。 " + configPath + " を確認してください。");
                pr.closeContext();
                System.exit(1);
            }

            String directoryPath = p.getProperty("directoryPath");
            pr.closeContext();

            // 既存レコードの削除
            DBController dbc = new DBController(configPath);
            dbc.deleteAll();

            if (dbc.getCode() == 1) {
                System.exit(1);
            }

            // CSV リストの取得
            DirectoryResource dr = new DirectoryResource(directoryPath);
            dr.setFileFilter("csv");
            File[] files = dr.getFiles();

            if (dr.getCode() == 1) {
                System.exit(1);
            }

            String csvPath = null;
            CsvIterator ci = null;

            for (File f : files) {
                try {
                    // CSV から Iterator を取得
                    csvPath = f.getCanonicalPath();
                    ci = new CsvIterator(csvPath);
                    ci.setEncoding("MS932");
                    ci.openContext();

                    if (ci.getCode() == 1) {
                        System.exit(1);
                    }

                    Iterator<String[]> iterator = ci.iterator();

                    if (ci.getCode() == 1) {
                        ci.closeContext();
                        System.exit(1);
                    }

                    // CSV から DB へ一括登録
                    dbc.insertAll(iterator);
                    ci.closeContext();
                } catch (IOException e) {
                    System.err.println("エラーが発生しました。 " + e);
                    System.exit(1);
                }
            }

            System.exit(dbc.getCode());
        }
    }
}
