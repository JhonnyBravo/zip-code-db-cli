package zip_code_db_cli;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import csv_resource.CsvController;
import mysql_resource.DaoController;

/**
 * t_zip_code テーブルのレコード操作を管理する。
 */
public class TZipCodeController extends DaoController {
    private CsvController ctrlCsv = new CsvController();

    /**
     * @param path インポート対象とするファイルのパスを指定する。
     * @return List&lt;String[]&gt; CSV のデータを文字列配列のリストに変換して返す。
     */
    public List<String[]> importCsv(String path) {
        List<String[]> recordset = ctrlCsv.getRecordset(path, "MS932");

        this.setCode(ctrlCsv.getCode());
        return recordset;
    }

    /**
     * t_zip_code テーブルからレコードを削除する。
     */
    public void deleteRecord() {
        // Statement の生成
        PreparedStatement ps = this.openReadOnlyStatement("DELETE FROM t_zip_code;");

        if (this.getCode() == 1) {
            this.closeConnection();
            this.setCode(1);
            return;
        }

        // クエリの実行
        int deleteCount = 0;

        try {
            deleteCount = ps.executeUpdate();
            System.out.println(deleteCount + " 件のレコードを削除しました。");

            if (deleteCount > 0) {
                this.setCode(2);
            } else {
                this.initStatus();
            }
        } catch (SQLException e) {
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.closeStatement();
            this.closeConnection();
            this.errorTerminate();
        }
    }

    /**
     * t_zip_code.no のオートインクリメントをリセットする。
     */
    public void resetNo() {
        // Statement の生成
        PreparedStatement ps = this.openReadOnlyStatement("ALTER TABLE t_zip_code auto_increment=1;");

        if (this.getCode() == 1) {
            this.closeConnection();
            this.setCode(1);
            return;
        }

        // クエリの実行
        try {
            ps.executeUpdate();
            this.setCode(2);
        } catch (SQLException e) {
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.closeStatement();
            this.closeConnection();
            this.errorTerminate();
        }
    }

    /**
     * t_zip_code テーブルへ CSV データを一括登録する。
     * 
     * @param recordset t_zip_code テーブルへ登録する CSV データのリストを指定する。
     */
    public void insertRecord(List<String[]> recordset) {
        // Statement の生成
        PreparedStatement ps = this.openWritableStatement("SELECT * FROM t_zip_code;");

        if (this.getCode() == 1) {
            this.closeConnection();
            this.setCode(1);
            return;
        }

        // レコードを追加
        int insertCount = 0;
        ResultSet rs = null;

        try {
            rs = ps.executeQuery();

            for (String[] record : recordset) {
                rs.moveToInsertRow();

                rs.updateString("jis_code", record[0]);
                rs.updateString("zip_code", record[2]);
                rs.updateString("prefecture_phonetic", record[3]);
                rs.updateString("city_phonetic", record[4]);
                rs.updateString("area_phonetic", record[5]);
                rs.updateString("prefecture", record[6]);
                rs.updateString("city", record[7]);
                rs.updateString("area", record[8]);
                rs.updateInt("update_flag", Integer.parseInt(record[13]));
                rs.updateInt("reason_flag", Integer.parseInt(record[14]));

                rs.insertRow();
                ++insertCount;
            }

            System.out.println(insertCount + " 件のレコードを追加しました。");

            if (insertCount > 0) {
                this.setCode(2);
            } else {
                this.initStatus();
            }
        } catch (SQLException e) {
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.closeStatement();
            this.closeConnection();
            this.errorTerminate();
        }
    }
}
