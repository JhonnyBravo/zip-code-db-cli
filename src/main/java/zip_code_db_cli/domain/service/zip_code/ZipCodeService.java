package zip_code_db_cli.domain.service.zip_code;

import java.util.List;
import zip_code_db_cli.domain.model.ZipCodeEntity;

/**
 * zip_code テーブルへのレコードの読み書きを操作する。
 */
public interface ZipCodeService {
  /**
   * テーブルに登録されている全レコードを取得し、 ID 昇順に並べ替えて返す。
   *
   * @return recordset {@link ZipCodeEntity} の {@link List}
   * @throws Exception {@link Exception}
   */
  List<ZipCodeEntity> findAllByOrderById() throws Exception;

  /**
   * テーブルへレコードを一括登録する。
   *
   * @param recordset 登録対象とする {@link ZipCodeEntity} の {@link List} を指定する。
   * @return status
   *         <ul>
   *         <li>true: 登録に成功したことを表す。</li>
   *         <li>false: 登録に失敗したことを表す。</li>
   *         </ul>
   * @throws Exception {@link Exception}
   */
  boolean saveAll(List<ZipCodeEntity> recordset) throws Exception;

  /**
   * テーブルからレコードを全件削除する。
   *
   * @return status
   *         <ul>
   *         <li>true: 削除に成功したことを表す。</li>
   *         <li>false: 削除に失敗したことを表す。</li>
   *         </ul>
   * @throws Exception {@link Exception}
   */
  boolean deleteAll() throws Exception;
}
