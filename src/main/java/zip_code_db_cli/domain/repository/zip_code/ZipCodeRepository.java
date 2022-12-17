package zip_code_db_cli.domain.repository.zip_code;

import java.util.List;
import java_itamae_connection.domain.model.ConnectionInfo;
import java_itamae_connection.domain.repository.common.BaseRepository;
import zip_code_db_cli.domain.model.ZipCode;

/** t_zip_code へのレコードの読み書きを操作する。 */
public interface ZipCodeRepository extends BaseRepository {
  /**
   * 初期化処理を実行する。
   *
   * @param cnInfo DB の接続情報を収めた {@link ConnectionInfo} を指定する。
   */
  void init(ConnectionInfo cnInfo);

  /**
   * テーブルに登録されている全レコードを取得する。
   *
   * @return recordset {@link ZipCode} の {@link List} を返す。
   * @throws Exception {@link java.lang.Exception}
   */
  List<ZipCode> findAll() throws Exception;

  /**
   * テーブルへレコードを一括登録する。
   *
   * @param contents 登録対象とする {@link ZipCode} の {@link List} を指定する。
   * @return status
   *     <ul>
   *       <li>true: レコードの登録に成功したことを表す。
   *       <li>false: レコードの登録が失敗したことを表す。
   *     </ul>
   *
   * @throws Exception {@link java.lang.Exception}
   */
  boolean create(List<ZipCode> contents) throws Exception;

  /**
   * テーブルからレコードを全件削除する。
   *
   * @return status
   *     <ul>
   *       <li>true: レコードの削除に成功したことを表す。
   *       <li>false: レコードの削除が失敗したことを表す。
   *     </ul>
   *
   * @throws Exception {@link java.lang.Exception}
   */
  boolean deleteAll() throws Exception;
}
