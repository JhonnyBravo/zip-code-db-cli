package zip_code_db_cli;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

import context_resource.ConnectionResource;
import context_resource.StatementResource;
import status_resource.Status;

/**
 * DB へのデータ登録と削除を管理する。
 */
public class DBController extends Status {
    private ConnectionResource cr;
    private StatementResource sr;

    /**
     * @param configPath DB の接続情報が記述された設定ファイルのパスを指定する。
     */
    public DBController(String configPath) {
        this.cr = new ConnectionResource(configPath);
    }

    /**
     * t_zip_code に登録されているレコードを全件削除する。
     */
    public void deleteAll() {
        this.initStatus();

        cr.openContext();
        this.setCode(cr.getCode());

        if (this.getCode() == 1) {
            return;
        }

        Connection connection = (Connection) cr.getContext();
        cr.disableAutoCommit();

        this.sr = new StatementResource(connection, "DELETE FROM t_zip_code;");
        sr.openContext();
        this.setCode(sr.getCode());

        if (this.getCode() == 1) {
            cr.enableAutoCommit();
            cr.closeContext();
            return;
        }

        PreparedStatement statement = (PreparedStatement) sr.getContext();

        try {
            statement.addBatch();
            statement.addBatch("ALTER TABLE t_zip_code auto_increment=1;");
            int[] result = statement.executeBatch();

            if (result[0] > 0) {
                this.setCode(2);
                System.out.println(result[0] + " 件のレコードを削除しました。");
            } else {
                this.initStatus();
            }

            cr.commit();
        } catch (SQLException e) {
            this.errorTerminate("エラーが発生しました。 " + e);
            cr.rollback();
        } finally {
            sr.closeContext();
            cr.enableAutoCommit();
            cr.closeContext();
        }
    }

    /**
     * CSV から t_zip_code へレコードを一括登録する。
     * 
     * @param iterator 登録対象とする CSV の Iterator を指定する。
     */
    public void insertAll(Iterator<String[]> iterator) {
        this.initStatus();

        cr.openContext();
        this.setCode(cr.getCode());

        if (this.getCode() == 1) {
            return;
        }

        Connection connection = (Connection) cr.getContext();
        cr.disableAutoCommit();

        String sql = "INSERT INTO t_zip_code"
                + " (jis_code,zip_code,prefecture_phonetic,city_phonetic,area_phonetic,prefecture,city,area,update_flag,reason_flag)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?);";
        this.sr = new StatementResource(connection, sql);
        sr.openContext();
        this.setCode(sr.getCode());

        if (this.getCode() == 1) {
            cr.enableAutoCommit();
            cr.closeContext();
            return;
        }

        PreparedStatement statement = (PreparedStatement) sr.getContext();
        int count = 0;

        while (iterator.hasNext()) {
            String[] record = iterator.next();

            try {
                statement.setString(1, record[0]);
                statement.setString(2, record[2]);
                statement.setString(3, record[3]);
                statement.setString(4, record[4]);
                statement.setString(5, record[5]);
                statement.setString(6, record[6]);
                statement.setString(7, record[7]);
                statement.setString(8, record[8]);
                statement.setInt(9, Integer.parseInt(record[13]));
                statement.setInt(10, Integer.parseInt(record[14]));

                statement.addBatch();
                count++;

                if (count % 1000 == 0) {
                    statement.executeBatch();
                    cr.commit();
                    this.setCode(cr.getCode());

                    if (this.getCode() == 1) {
                        cr.rollback();
                        return;
                    }
                }
            } catch (SQLException e) {
                this.errorTerminate("エラーが発生しました。 " + e);
                cr.rollback();
            }
        }

        try {
            statement.executeBatch();
            cr.commit();

            System.out.println(count + " 件のレコードを登録しました。");
            this.setCode(cr.getCode());

            if (this.getCode() == 1) {
                cr.rollback();
            }
        } catch (SQLException e) {
            this.errorTerminate("エラーが発生しました。 " + e);
            cr.rollback();
        } finally {
            cr.enableAutoCommit();
            sr.closeContext();
            cr.closeContext();
        }
    }
}
