package zip_code_db_cli.domain.repository.connection;

import java.sql.Connection;

import java_itamae_contents.domain.model.ContentsAttribute;

public interface ConnectionRepository {
    /**
     * @param attr DB の接続情報を記載した設定ファイルの情報を納めた ContentsAttribute を指定する。
     * @return connection Connection を開いて返す。
     * @throws Exception {@link java.lang.Exception}
     */
    public Connection getConnection(ContentsAttribute attr) throws Exception;
}
