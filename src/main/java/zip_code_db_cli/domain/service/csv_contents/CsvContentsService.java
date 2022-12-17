package zip_code_db_cli.domain.service.csv_contents;

import java.util.List;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.service.common.BaseService;
import zip_code_db_cli.domain.model.ZipCode;

/** CSV の読み書きを操作する。 */
public interface CsvContentsService extends BaseService {
  /**
   * 初期化処理を実行する。
   *
   * @param model 操作対象とするファイルの情報を収めた {@link ContentsModel} を指定する。
   */
  void init(ContentsModel model);

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
   * @param contents 書込み対象とする {@link ZipCode} を収めた {@link List} を指定する。
   * @return status
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 書込みを実行して正常終了したことを表す。
   *     </ul>
   */
  int updateContents(List<ZipCode> contents);

  /**
   * CSV を空にする。
   *
   * @return status
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 書込みを実行して正常終了したことを表す。
   *     </ul>
   */
  int deleteContents();
}
