package zip_code_db_cli;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import csv_resource.CsvController;
import mysql_resource.ConnectionController;
import mysql_resource.StatementController;
import status_resource.StatusController;

/**
 * t_zip_code テーブルのレコード操作を管理する。
 */
public class TZipCodeController extends StatusController {
    private CsvController ctrlCsv = new CsvController();
    private ConnectionController ctrlConnection = new ConnectionController();
    private StatementController ctrlStatement = new StatementController();

    public void init(String path) {
        ctrlConnection.init(path);
    }

    /**
     * @param path インポート対象とするファイルのパスを指定する。
     * @return List&lt;String[]&gt; CSV のデータを文字列配列のリストに変換して返す。
     */
    public List<String[]> importCsv(String path) {
        this.initStatus();

        List<String[]> recordset = ctrlCsv.getRecordset(path, "MS932");
        this.setCode(ctrlCsv.getCode());

        return recordset;
    }

    /**
     * t_zip_code テーブルからレコードを削除する。
     */
    public void deleteRecord() {
        this.initStatus();

        // Connection Open
        ctrlConnection.openConnection(false);
        this.setCode(ctrlConnection.getCode());

        if (this.getCode() == 1) {
            return;
        }

        Connection connection = ctrlConnection.getConnection();
        this.setCode(ctrlConnection.getCode());

        if (this.getCode() == 1) {
            ctrlConnection.closeConnection();
            return;
        }

        // PreparedStatement Open
        ctrlStatement.init(connection);

        ctrlStatement.openStatement("DELETE FROM t_zip_code;");
        this.setCode(ctrlStatement.getCode());

        if (this.getCode() == 1) {
            ctrlConnection.closeConnection();
            return;
        }

        PreparedStatement ps = ctrlStatement.getStatement();
        this.setCode(ctrlStatement.getCode());

        if (this.getCode() == 1) {
            ctrlStatement.closeStatement();
            ctrlConnection.closeConnection();
        }

        try {
            ps.addBatch();
            ps.addBatch("ALTER TABLE t_zip_code auto_increment=1;");

            // SQL 実行
            int[] results = ps.executeBatch();
            System.out.println(results[0] + " 件のレコードを削除しました。");

            ctrlConnection.commit();
            this.setCode(ctrlConnection.getCode());

            if (this.getCode() == 1) {
                ctrlConnection.rollback();
            }
        } catch (SQLException e) {
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.errorTerminate();
        } finally {
            // 終了処理
            ctrlStatement.closeStatement();
            this.setCode(ctrlStatement.getCode());
            ctrlConnection.closeConnection();
            this.setCode(ctrlConnection.getCode());
        }
    }

    /**
     * t_zip_code テーブルへ CSV データを一括登録する。
     * 
     * @param recordset t_zip_code テーブルへ登録する CSV データのリストを指定する。
     */
    public void insertRecord(List<String[]> recordset) {
        this.initStatus();

        ctrlConnection.openConnection(false);
        this.setCode(ctrlConnection.getCode());

        if (this.getCode() == 1) {
            return;
        }

        Connection connection = ctrlConnection.getConnection();
        this.setCode(ctrlConnection.getCode());

        if (this.getCode() == 1) {
            ctrlConnection.closeConnection();
            return;
        }

        ctrlStatement.init(connection);

        ctrlStatement.openStatement("INSERT INTO t_zip_code"
                + " (jis_code,zip_code,prefecture_phonetic,city_phonetic,area_phonetic,prefecture,city,area,update_flag,reason_flag)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?);");
        this.setCode(ctrlStatement.getCode());

        if (this.getCode() == 1) {
            ctrlConnection.closeConnection();
            return;
        }

        PreparedStatement ps = ctrlStatement.getStatement();
        this.setCode(ctrlStatement.getCode());

        if (this.getCode() == 1) {
            ctrlStatement.closeStatement();
            ctrlConnection.closeConnection();
            return;
        }

        int count = 0;

        for (String[] record : recordset) {
            try {
                ps.setString(1, record[0]);// jis_code
                ps.setString(2, record[2]);// zip_code
                ps.setString(3, record[3]);// prefecture_phonetic
                ps.setString(4, record[4]);// city_phonetic
                ps.setString(5, record[5]);// area_phonetic
                ps.setString(6, record[6]);// prefecture
                ps.setString(7, record[7]);// city
                ps.setString(8, record[8]);// area
                ps.setInt(9, Integer.parseInt(record[13]));// update_flag
                ps.setInt(10, Integer.parseInt(record[14]));// reason_flag
                ps.addBatch();

                count++;

                if (count % 1000 == 0) {
                    ps.executeBatch();
                    ctrlConnection.commit();
                    this.setCode(ctrlConnection.getCode());

                    if (this.getCode() == 1) {
                        ctrlConnection.rollback();
                        ctrlStatement.closeStatement();
                        ctrlConnection.closeConnection();
                        return;
                    }
                }
            } catch (SQLException e) {
                this.setMessage("エラーが発生しました。 " + e.toString());
                this.errorTerminate();

                ctrlStatement.closeStatement();
                ctrlConnection.closeConnection();
            }
        }

        try {
            ps.executeBatch();
            System.out.println(count + " 件のレコードを追加しました。");

            ctrlConnection.commit();
            this.setCode(ctrlConnection.getCode());

            if (this.getCode() == 1) {
                ctrlConnection.rollback();
            }
        } catch (SQLException e) {
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.errorTerminate();
        } finally {
            ctrlStatement.closeStatement();
            ctrlConnection.closeConnection();
        }
    }
}
