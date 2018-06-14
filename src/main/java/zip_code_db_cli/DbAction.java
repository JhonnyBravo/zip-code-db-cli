package zip_code_db_cli;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import common_resource.StatusBean;
import mysql_resource.MySqlAction;

/**
 * t_zip_code テーブルのレコードを削除 / 登録する。
 */
public class DbAction extends StatusBean {
    MySqlAction msa = null;

    /**
     * @param connectionString DB 接続に使用する接続文字列を指定する。
     * @param userName DB 接続に使用するユーザ名を指定する。
     * @param password DB 接続に使用するパスワードを指定する。
     */
    public DbAction(String connectionString, String userName, String password) {
        msa = new MySqlAction(connectionString, userName, password);
    }

    /**
    * t_zip_code テーブルからレコードを全件削除する。
    */
    public void deleteRecord() {
        this.setCode(0);
        this.setMessage(null);
        this.setResourceName(null);

        msa.openConnection();

        if (msa.getCode() == 1) {
            this.setCode(msa.getCode());
            return;
        }

        msa.invokeQuery("DELETE FROM t_zip_code;");
        msa.invokeQuery("ALTER TABLE t_zip_code auto_increment=1;");
        this.setCode(msa.getCode());
        msa.closeConnection();
    }

    /**
    * t_zip_code テーブルへレコードセットを一括登録する。
    * @param recordset テーブルのレコードセットとして登録する文字列配列を格納したリストを指定する。
    */
    public void insertRecord(List<String[]> recordset) {
        this.setCode(0);
        this.setMessage(null);
        this.setResourceName(null);

        msa.openConnection();

        if (msa.getCode() == 1) {
            this.setCode(msa.getCode());
            return;
        }

        ResultSet rs = msa.getRecordset("SELECT * FROM t_zip_code;",
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        for (String[] record : recordset) {
            try {
                rs.moveToInsertRow();

                rs.updateString("jis_code", record[0]);
                rs.updateString("zip_code_old", record[1]);
                rs.updateString("zip_code", record[2]);
                rs.updateString("prefecture_phonetic", record[3]);
                rs.updateString("city_phonetic", record[4]);
                rs.updateString("area_phonetic", record[5]);
                rs.updateString("prefecture", record[6]);
                rs.updateString("city", record[7]);
                rs.updateString("area", record[8]);
                rs.updateInt("flag1", Integer.parseInt(record[9]));
                rs.updateInt("flag2", Integer.parseInt(record[10]));
                rs.updateInt("flag3", Integer.parseInt(record[11]));
                rs.updateInt("flag4", Integer.parseInt(record[12]));
                rs.updateInt("update_flag", Integer.parseInt(record[13]));
                rs.updateInt("reason_flag", Integer.parseInt(record[14]));

                rs.insertRow();
                rs.moveToCurrentRow();
            } catch (SQLException e) {
                this.setCode(1);
                this.setMessage("エラーが発生しました。 " + e.toString());
                this.setResourceName("zip_code_db_cli");

                System.err.println(this.getStatus());
                msa.closeConnection();
                return;
            }
        }

        this.setCode(2);
        msa.closeConnection();
    }

}
