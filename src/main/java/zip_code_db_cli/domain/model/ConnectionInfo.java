package zip_code_db_cli.domain.model;

import java.io.Serializable;

public class ConnectionInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String hostName;
    private String portNumber;
    private String encoding;
    private String timeZone;
    private String dbName;
    private String userName;
    private String password;

    /**
     * @return hostName DB サーバのホスト名を返す。
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * @param hostName DB サーバのホスト名を指定する。
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * @return portNumber DB 接続時に使用するポート番号を返す。
     */
    public String getPortNumber() {
        return portNumber;
    }

    /**
     * @param portNumber DB 接続時に使用するポート番号を指定する。
     */
    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    /**
     * @return encoding DB サーバの文字エンコーディングを返す。
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * @param encoding DB サーバの文字エンコーディングを指定する。
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * @return timeZone DB サーバのタイムゾーンを返す。
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * @param timeZone DB サーバのタイムゾーンを指定する。
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * @return dbName DB 接続時に使用する DB の名前を返す。
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName DB 接続時に使用する DB の名前を指定する。
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * @return userName DB 接続時に使用するユーザ名を返す。
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName DB 接続時に使用するユーザ名を指定する。
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return password DB 接続時に使用するパスワードを返す。
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password DB 接続時に使用するパスワードを指定する。
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
