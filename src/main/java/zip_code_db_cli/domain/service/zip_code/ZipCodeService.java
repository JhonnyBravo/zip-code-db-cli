package zip_code_db_cli.domain.service.zip_code;

import java.util.List;
import zip_code_db_cli.domain.model.ZipCodeEntity;

/**
 * t_zip_code へのレコードの読み書きを操作する。
 */
public interface ZipCodeService {
  /**
   * テーブルに登録されている全レコードを取得する。
   *
   * @return {@link ZipCodeEntity} の {@link List} を返す。
   * @throws Exception {@link java.lang.Exception}
   */
  List<ZipCodeEntity> findAll() throws Exception;

  /**
   * テーブルへレコードを一括登録する。
   *
   * @param recordset 登録対象とする {@link ZipCodeEntity} の {@link List} を指定する。
   * @return status
   *         <ul>
   *         <li>true: 登録に成功したことを表す。</li>
   *         <li>false: 登録に失敗したことを表す。</li>
   *         </ul>
   * @throws Exception {@link java.lang.Exception}
   */
  boolean create(List<ZipCodeEntity> recordset) throws Exception;

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
  boolean deleteAll() throws Exception;
}
