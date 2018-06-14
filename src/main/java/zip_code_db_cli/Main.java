package zip_code_db_cli;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import csv_resource.CsvAction;
import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import mysql_resource.ConnectionBean;

/**
 * t_zip_code テーブルのレコードを削除 / 登録する。
 */
public class Main {
    /**
    * @param args
    * <ul>
    * <li>
    * delete &lt;configPath&gt; t_zip_code テーブルからレコードを全件削除する。
    * </li>
    * <li>
    * insert &lt;configPath&gt; &lt;dirPath&gt; dirPath 配下に存在する全 CSV データを
    * t_zip_code テーブルへ一括登録する。
    * </li>
    * </ul>
    */
    public static void main(String[] args) {
        LongOpt[] longopts = new LongOpt[2];
        longopts[0] = new LongOpt("delete", LongOpt.REQUIRED_ARGUMENT, null, 'd');
        longopts[1] = new LongOpt("insert", LongOpt.REQUIRED_ARGUMENT, null, 'i');

        Getopt options = new Getopt("Main", args, "d:i:", longopts);

        int c;
        int dFlag = 0;
        int iFlag = 0;

        String dirPath = null;
        String configPath = null;

        while ((c = options.getopt()) != -1) {
            switch (c) {
            case 'd':
                configPath = options.getOptarg();
                dFlag = 1;
                break;
            case 'i':
                configPath = args[1];
                dirPath = args[2];
                iFlag = 1;
                break;
            }
        }

        if (dFlag == 0 && iFlag == 0) {
            System.exit(0);
        }

        Properties p = new Properties();
        InputStream configInput = null;

        try {
            configInput = new FileInputStream(configPath);
            p.load(configInput);
        } catch (FileNotFoundException e) {
            System.err.println("不正な configPath です。 " + e.toString());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("不正な configPath です。 " + e.toString());
            System.exit(1);
        }

        ConnectionBean cb = new ConnectionBean();
        cb.setHostName(p.getProperty("hostName", "localhost"));
        cb.setPortNumber(Integer.parseInt(p.getProperty("portNumber", "3306")));
        cb.setDbName(p.getProperty("dbName"));
        cb.setEncoding(p.getProperty("encoding", "UTF-8"));
        cb.setTimeZone(p.getProperty("timeZone", "JST"));
        cb.setPassword(p.getProperty("password"));
        cb.setUserName(p.getProperty("userName"));

        DbAction dba = new DbAction(cb.getConnectionString(), cb.getUserName(), cb.getPassword());

        if (dFlag == 1) {
            dba.deleteRecord();
            System.exit(dba.getCode());
        } else if (iFlag == 1) {
            DirectoryAction da = new DirectoryAction(dirPath);
            List<String> pathList = da.getFilePathList("csv");

            if (da.getCode() == 1) {
                System.exit(da.getCode());
            }

            for (int i = 0; i < pathList.size(); i++) {
                CsvAction ca = new CsvAction(pathList.get(i));
                List<String[]> recordset = ca.getRecordset("MS932");

                if (ca.getCode() == 1) {
                    System.exit(ca.getCode());
                }

                dba.insertRecord(recordset);

                if (dba.getCode() == 1) {
                    System.exit(dba.getCode());
                }
            }

            System.exit(2);
        }
    }

}
