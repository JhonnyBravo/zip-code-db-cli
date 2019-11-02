package zip_code_db_cli.domain.repository.zip_code;

import java.sql.Connection;
import java.util.List;

import zip_code_db_cli.domain.model.ZipCode;

public interface ZipCodeRepository {
    /**
     * @param connection DB 接続に使用する Connection を指定する。
     * @return recordset テーブルからレコードを全件取得する。
     * @throws Exception {@link java.lang.Exception}
     */
    public List<ZipCode> findAll(Connection connection) throws Exception;

    /**
     * テーブルへレコードを一括登録する。
     *
     * @param connection DB 接続に使用する Connection を指定する。
     * @param contents   登録対象とするレコードセットを指定する。
     * @return status
     *         <ul>
     *         <li>true: レコードの登録に成功したことを表す。</li>
     *         <li>false: レコードの登録が失敗したことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean create(Connection connection, List<ZipCode> contents) throws Exception;

    /**
     * テーブルからレコードを全件削除する。
     *
     * @param connection DB 接続に使用する Connection を指定する。
     * @return status
     *         <ul>
     *         <li>true: レコードの削除に成功したことを表す。</li>
     *         <li>false: レコードの削除が失敗したことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean deleteAll(Connection connection) throws Exception;
}
