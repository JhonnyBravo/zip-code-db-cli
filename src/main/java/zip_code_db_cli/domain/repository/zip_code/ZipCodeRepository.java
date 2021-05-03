package zip_code_db_cli.domain.repository.zip_code;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zip_code_db_cli.domain.model.ZipCode;

/**
 * t_zip_code へのレコードの読み書きを操作する。
 */
@Mapper
public interface ZipCodeRepository {
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
   *         <ul>
   *         <li>true: レコードの登録に成功したことを表す。</li>
   *         <li>false: レコードが登録されなかったことを表す。</li>
   *         </ul>
   * @throws Exception {@link java.lang.Exception}
   */
  boolean create(@Param("list") List<ZipCode> contents) throws Exception;

  /**
   * テーブルからレコードを全件削除する。
   *
   * @return status
   *         <ul>
   *         <li>true: レコードの削除に成功したことを表す。</li>
   *         <li>false: レコードが削除されなかったことを表す。</li>
   *         </ul>
   * @throws Exception {@link java.lang.Exception}
   */
  boolean deleteAll() throws Exception;

  /**
   * ID のカウンターを 1 にリセットする。
   *
   * @return status
   * @throws Exception {@link java.lang.Exception}
   */
  boolean resetId() throws Exception;
}
