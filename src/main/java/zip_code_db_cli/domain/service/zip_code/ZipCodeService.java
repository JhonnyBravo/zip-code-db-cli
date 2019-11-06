package zip_code_db_cli.domain.service.zip_code;

import java.util.List;

import java_itamae_connection.domain.service.connection.ConnectionService;
import zip_code_db_cli.domain.model.ZipCode;

public abstract class ZipCodeService extends ConnectionService {
    /**
     * @return テーブルからレコードを全件取得する。
     * @throws Exception {@link java.lang.Exception}
     */
    public abstract List<ZipCode> findAll() throws Exception;

    /**
     * テーブルへレコードを一括登録する。
     *
     * @param recordset 登録対象とするレコードセットを指定する。
     * @return status
     *         <ul>
     *         <li>true: 登録に成功したことを表す。</li>
     *         <li>false: 登録に失敗したことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public abstract boolean create(List<ZipCode> recordset) throws Exception;

    /**
     * テーブルからレコードを全件削除する。
     *
     * @return status
     *         <ul>
     *         <li>true: 削除に成功したことを表す。</li>
     *         <li>false: 削除を実行しなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public abstract boolean deleteAll() throws Exception;
}
