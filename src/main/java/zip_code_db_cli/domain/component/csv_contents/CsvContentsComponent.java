package zip_code_db_cli.domain.component.csv_contents;

import java.util.List;
import java_itamae.domain.component.common.StreamComponent;
import java_itamae.domain.model.contents.ContentsModel;
import zip_code_db_cli.domain.model.ZipCode;

/** CSV の読み書きを操作する。 */
public interface CsvContentsComponent extends StreamComponent {
  /**
   * CSV を読み込んで {@link ZipCode} の {@link List} に変換して返す。
   *
   * @param model 操作対象とするファイルの情報を収めた {@link ContentsModel} を指定する。
   * @return contents {@link ZipCode} の {@link List} を返す。
   * @throws Exception {@link Exception}
   */
  List<ZipCode> getContents(ContentsModel model) throws Exception;

  /**
   * CSV を上書きする。
   *
   * @param model 操作対象とするファイルの情報を収めた {@link ContentsModel} を指定する。
   * @param contents 書込み対象とする {@link ZipCode} の {@link List} を指定する。
   * @return status
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 書込みを実行して正常終了したことを表す。
   *     </ul>
   */
  int updateContents(ContentsModel model, List<ZipCode> contents);

  /**
   * CSV を空にする。
   *
   * @param model 操作対象とするファイルの情報を収めた {@link ContentsModel} を指定する。
   * @return status
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 削除を実行して正常終了したことを表す。
   *     </ul>
   */
  int deleteContents(ContentsModel model);
}
