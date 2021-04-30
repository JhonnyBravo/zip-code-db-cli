package zip_code_db_cli.domain.service.csv_contents;

import java.util.List;
import zip_code_db_cli.domain.model.ZipCode;

/**
 * CSV の読み書きを操作する。
 */
public interface CsvContentsService {
  /**
   * CSV を読み込んで {@link ZipCode} の {@link List} に変換して返す。
   *
   * @return contents {@link ZipCode} の {@link List} を返す。
   * @throws Exception {@link java.lang.Exception}
   */
  List<ZipCode> getContents() throws Exception;

  /**
   * CSV を上書きする。
   *
   * @param content 書込み対象とする情報を納めた {@link ZipCode} を指定する。
   * @return status
   *         <ul>
   *         <li>true: 書込みに成功したことを表す。</li>
   *         <li>false: 書込みを実行しなかったことを表す。</li>
   *         </ul>
   * @throws Exception {@link java.lang.Exception}
   */
  boolean updateContent(ZipCode content) throws Exception;

  /**
   * CSV を一行追記する。
   *
   * @param content 書込み対象とする情報を納めた {@link ZipCode} を指定する。
   * @return status
   *         <ul>
   *         <li>true: 追記に成功したことを表す。</li>
   *         <li>false: 追記を実行しなかったことを表す。</li>
   *         </ul>
   * @throws Exception {@link java.lang.Exception}
   */
  boolean appendContent(ZipCode content) throws Exception;

  /**
   * CSV を空にする。
   *
   * @return status
   *         <ul>
   *         <li>true: 削除に成功したことを表す。</li>
   *         <li>false: 削除を実行しなかったことを表す。</li>
   *         </ul>
   * @throws Exception {@link java.lang.Exception}
   */
  boolean deleteContents() throws Exception;
}
