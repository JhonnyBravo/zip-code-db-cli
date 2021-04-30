package zip_code_db_cli.domain.repository.csv_contents;

import java.io.Reader;
import java.io.Writer;
import java.util.List;
import zip_code_db_cli.domain.model.ZipCode;

/**
 * CSV の読み書きを操作する。
 */
public interface CsvContentsRepository {
  /**
   * CSV を読み込んで {@link ZipCode} の {@link List} に変換して返す。
   *
   * @param reader 操作対象とするファイルの {@link Reader} を指定する。
   * @return contents {@link ZipCode} の {@link List} を返す。
   * @throws Exception {@link java.lang.Exception}
   */
  List<ZipCode> getContents(Reader reader) throws Exception;

  /**
   * CSV を上書きする。
   *
   * @param writer 操作対象とするファイルの {@link Writer} を指定する。
   * @param contents 書込み対象とする {@link ZipCode} の {@link List} を指定する。
   * @return status
   *         <ul>
   *         <li>true: 書込みに成功したことを表す。</li>
   *         <li>false: 書込みが実行されなかったことを表す。</li>
   *         </ul>
   * @throws Exception {@link java.lang.Exception}
   */
  boolean updateContents(Writer writer, List<ZipCode> contents) throws Exception;

  /**
   * CSV を空にする。
   *
   * @param writer 操作対象とするファイルの {@link Writer} を指定する。
   * @return status
   *         <ul>
   *         <li>true: 削除に成功したことを表す。</li>
   *         <li>true: 削除を実行しなかったことを表す。</li>
   *         </ul>
   * @throws Exception {@link java.lang.Exception}
   */
  boolean deleteContents(Writer writer) throws Exception;
}
