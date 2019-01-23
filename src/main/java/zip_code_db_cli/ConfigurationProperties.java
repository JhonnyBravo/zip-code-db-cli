package zip_code_db_cli;

import java.util.Properties;

import mysql_resource.ConnectionProperties;

/**
 * CSV 取り込み時に適用する設定値を管理する。
 */
public class ConfigurationProperties extends ConnectionProperties {
    /**
     * @return directoryPath 取り込み対象とする CSV が格納されているディレクトリのパスを返す。
     */
    public String getDirectoryPath() {
        this.initStatus();

        String path = null;
        Properties p = this.getProperties();

        if (this.getCode() == 1) {
            return path;
        }

        if (!p.containsKey("directoryPath")) {
            this.errorTerminate("directoryPath が定義されていません。");
            return path;
        }

        path = p.getProperty("directoryPath");

        this.setCode(2);
        return path;
    }
}
