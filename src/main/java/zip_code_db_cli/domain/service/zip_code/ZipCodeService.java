package zip_code_db_cli.domain.service.zip_code;

import java.util.List;
import java_itamae_connection.domain.model.ConnectionInfo;
import java_itamae_connection.domain.service.connection.ConnectionService;
import zip_code_db_cli.domain.model.ZipCode;

/**
 * t_zip_code へのレコードの読み書きを操作する。
 */
public abstract class ZipCodeService extends ConnectionService {
  /**
   * 初期化処理を実行する。
   *
   * @param cnInfo DB の接続情報を納めた {@link ConnectionInfo} を指定する。
   * @throws Exception {@link Exception}}
   */
  public abstract void init(ConnectionInfo cnInfo) throws Exception;

  /**
   * テーブルに登録されている全レコードを取得する。
   *
   * @return {@link ZipCode} の {@link List} を返す。
   * @throws Exception {@link java.lang.Exception}
   */
  public abstract List<ZipCode> findAll() throws Exception;

  /**
   * テーブルへレコードを一括登録する。
   *
   * @param recordset 登録対象とする {@link ZipCode} の {@link List} を指定する。
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
