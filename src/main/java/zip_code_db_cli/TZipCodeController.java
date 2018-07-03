package zip_code_db_cli;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import csv_resource.CsvController;
import mysql_resource.MySqlController;

/**
 * t_zip_code テーブルのレコード操作を管理する。
 */
public class TZipCodeController {
    private int code;

    /**
    * @param path インポート対象とするファイルのパスを指定する。
    * @return recordset CSV のデータをリストに変換して返す。
    */
    public List<String[]> importCsv(String path) {
        CsvController cc = new CsvController();
        List<String[]> recordset = cc.getRecordset(path, "MS932");
        this.code = cc.getCode();
        return recordset;
    }

    /**
    * t_zip_code テーブルからレコードを削除する。
    * @param path 接続情報を記述したプロパティファイルのパスを指定する。
    */
    public void deleteRecord(String path) {
        MySqlController msc = new MySqlController(path);
        //DB 接続を開始
        msc.openConnection();
        this.code = msc.getCode();

        if (this.code == 1) {
            return;
        }

        //Statement の生成
        PreparedStatement ps = msc.openStatement("DELETE FROM t_zip_code;");
        this.code = msc.getCode();

        if (this.code == 1) {
            msc.closeConnection();
            return;
        }

        //クエリの実行
        int deleteCount = 0;

        try {
            deleteCount = ps.executeUpdate();
            System.out.println(deleteCount + " 件のレコードを削除しました。");
        } catch (SQLException e) {
            this.code = 1;
            System.err.println("エラーが発生しました。 " + e.toString());
            msc.closeStatement();
            msc.closeConnection();
            return;
        }

        //DB 接続を切断
        msc.closeStatement();
        this.code = msc.getCode();

        if (this.code == 1) {
            msc.closeConnection();
            return;
        }

        msc.closeConnection();
        this.code = msc.getCode();

        if (this.code == 1) {
            return;
        }

        //終了ステータスの設定
        if (deleteCount != 0) {
            this.code = 2;
        } else {
            this.code = 0;
        }
    }

    /**
    * t_zip_code.no のオートインクリメントをリセットする。
    * @param path 接続情報を記述したプロパティファイルのパスを指定する。
    */
    public void resetNo(String path) {
        MySqlController msc = new MySqlController(path);
        //DB 接続を開始
        msc.openConnection();
        this.code = msc.getCode();

        if (this.code == 1) {
            return;
        }

        //Statement の生成
        PreparedStatement ps = msc.openStatement("ALTER TABLE t_zip_code auto_increment=1;");
        this.code = msc.getCode();

        if (this.code == 1) {
            msc.closeConnection();
            return;
        }

        //クエリの実行
        try {
            ps.executeUpdate();
            this.code = 2;
        } catch (SQLException e) {
            this.code = 1;
            System.err.println("エラーが発生しました。 " + e.toString());
            msc.closeStatement();
            msc.closeConnection();
            return;
        }

        //DB 接続を切断
        msc.closeStatement();
        this.code = msc.getCode();

        if (this.code == 1) {
            msc.closeConnection();
            return;
        }

        msc.closeConnection();
        this.code = msc.getCode();

        if (this.code == 1) {
            return;
        }
    }

    /**
    * t_zip_code テーブルへ CSV データを一括登録する。
    * @param path 接続情報を記述したプロパティファイルのパスを指定する。
    * @param recordset t_zip_code テーブルへ登録する CSV データのリストを指定する。
    */
    public void insertRecord(String path, List<String[]> recordset) {
        MySqlController msc = new MySqlController(path);
        //DB 接続を開始
        msc.openConnection();
        this.code = msc.getCode();

        if (this.code == 1) {
            return;
        }

        //Statement の生成
        msc.setResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE);
        msc.setResultSetConcurrency(ResultSet.CONCUR_UPDATABLE);
        PreparedStatement ps = msc.openStatement("SELECT * FROM t_zip_code;");
        this.code = msc.getCode();

        if (this.code == 1) {
            msc.closeConnection();
            return;
        }

        //レコードを追加
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
        } catch (SQLException e) {
            this.code = 1;
            System.err.println("エラーが発生しました。 " + e.toString());
            msc.closeStatement();
            msc.closeConnection();
            return;
        }

        //DB 接続を切断
        msc.closeStatement();
        this.code = msc.getCode();

        if (this.code == 1) {
            msc.closeConnection();
            return;
        }

        msc.closeConnection();
        this.code = msc.getCode();

        if (this.code == 1) {
            return;
        }

        //終了ステータスの設定
        if (insertCount != 0) {
            this.code = 2;
        } else {
            this.code = 0;
        }
    }

    /**
    * @return 終了ステータスを返す。
    */
    public int getCode() {
        return code;
    }
}
