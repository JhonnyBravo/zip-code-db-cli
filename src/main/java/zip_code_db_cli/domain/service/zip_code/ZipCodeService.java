package zip_code_db_cli.domain.service.zip_code;

import java.util.List;
import java_itamae.domain.service.common.BaseService;
import java_itamae_connection.domain.model.ConnectionInfo;
import zip_code_db_cli.domain.model.ZipCode;

/** t_zip_code へのレコードの読み書きを操作する。 */
public interface ZipCodeService extends BaseService {
  /**
   * 初期化処理を実行する。
   *
   * @param cnInfo DB の接続情報を収めた {@link ConnectionInfo} を指定する。
   */
  void init(ConnectionInfo cnInfo);

  /**
   * テーブルに登録されている全レコードを取得する。
   *
   * @return {@link ZipCode} の {@link List} を返す。
   * @throws Exception {@link Exception}
   */
  List<ZipCode> findAll() throws Exception;

  /**
   * テーブルへレコードを一括登録する。
   *
   * @param recordset 登録対象とする {@link ZipCode} の {@link List} を指定する。
   * @return status
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: レコードを登録して正常終了したことを表す。
   *     </ul>
   */
  int create(List<ZipCode> recordset);

  /**
   * テーブルからレコードを全件削除する。
   *
   * @return status
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: レコードを削除して正常終了したことを表す。
   *     </ul>
   */
  int deleteAll();
}
