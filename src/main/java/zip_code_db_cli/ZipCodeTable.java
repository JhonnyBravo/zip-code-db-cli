package zip_code_db_cli;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import mysql_resource.ConnectionController;
import mysql_resource.StatementController;
import status_resource.Status;

/**
 * @see zip_code_db_cli.TableResource
 */
public class ZipCodeTable extends Status implements TableResource {
    private ConfigurationProperties cp = new ConfigurationProperties();
    private ConnectionController cc = new ConnectionController();
    private StatementController sc = new StatementController();

    private Connection connection = null;

    /*
     * @see zip_code_db_cli.TableResource#setPath(java.lang.String)
     */
    @Override
    public void setPath(String path) {
        cp.setPath(path);
    }

    /*
     * @see zip_code_db_cli.TableResource#openConnection()
     */
    @Override
    public void openConnection() {
        this.initStatus();

        String userName = cp.getUserName();
        this.setCode(cp.getCode());

        if (this.getCode() == 1) {
            return;
        }

        String password = cp.getPassword();
        this.setCode(cp.getCode());

        if (this.getCode() == 1) {
            return;
        }

        String conStr = cp.getConnectionString();
        this.setCode(cp.getCode());

        if (this.getCode() == 1) {
            return;
        }

        cc.setUserName(userName);
        cc.setPassword(password);
        cc.setConnectionString(conStr);

        this.connection = cc.openConnection();
        this.setCode(cc.getCode());

        if (this.getCode() == 1) {
            return;
        }

        cc.disableAutoCommit();
        this.setCode(cc.getCode());
    }

    /*
     * @see zip_code_db_cli.TableResource#closeConnection()
     */
    @Override
    public void closeConnection() {
        sc.closeStatement();
        cc.closeConnection();
        this.connection = null;
    }

    /*
     * @see zip_code_db_cli.TableResource#deleteRecord()
     */
    @Override
    public void deleteRecord() {
        this.initStatus();

        if (this.connection == null) {
            this.errorTerminate("Connection が確立されていません。");
            return;
        }

        PreparedStatement ps = sc.openStatement(this.connection, "DELETE FROM t_zip_code;");
        this.setCode(sc.getCode());

        if (this.getCode() == 1) {
            return;
        }

        try {
            ps.addBatch();
            ps.addBatch("ALTER TABLE t_zip_code auto_increment=1;");
            int[] results = ps.executeBatch();

            System.out.println(results[0] + " 件のレコードを削除しました。");
            cc.commit();
            this.setCode(2);
        } catch (SQLException e) {
            this.errorTerminate("エラーが発生しました。 " + e.toString());
            cc.rollback();
        }
    }

    /*
     * @see zip_code_db_cli.TableResource#insertRecord(List<String[]>)
     */
    @Override
    public void insertRecord(List<String[]> csvContent) {
        this.initStatus();

        if (this.connection == null) {
            this.errorTerminate("Connection が確立されていません。");
            return;
        }

        PreparedStatement ps = sc.openStatement(this.connection, "INSERT INTO t_zip_code"
                + " (jis_code,zip_code,prefecture_phonetic,city_phonetic,area_phonetic,prefecture,city,area,update_flag,reason_flag)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?);");
        this.setCode(sc.getCode());

        if (this.getCode() == 1) {
            return;
        }

        int count = 0;

        for (String[] record : csvContent) {
            try {
                ps.setString(1, record[0]);
                ps.setString(2, record[2]);
                ps.setString(3, record[3]);
                ps.setString(4, record[4]);
                ps.setString(5, record[5]);
                ps.setString(6, record[6]);
                ps.setString(7, record[7]);
                ps.setString(8, record[8]);
                ps.setInt(9, Integer.parseInt(record[13]));
                ps.setInt(10, Integer.parseInt(record[14]));

                ps.addBatch();
                count++;

                if (count % 1000 == 0) {
                    ps.executeBatch();
                    cc.commit();
                    this.setCode(cc.getCode());

                    if (this.getCode() == 1) {
                        cc.rollback();
                        return;
                    }
                }
            } catch (SQLException e) {
                this.errorTerminate("エラーが発生しました。 " + e.toString());
            }
        }

        try {
            ps.executeBatch();
            cc.commit();

            System.out.println(count + " 件のレコードを追加しました。");
            this.setCode(cc.getCode());

            if (this.getCode() == 1) {
                cc.rollback();
                return;
            }
        } catch (SQLException e) {
            this.errorTerminate("エラーが発生しました。 " + e.toString());
            return;
        }
    }
}
