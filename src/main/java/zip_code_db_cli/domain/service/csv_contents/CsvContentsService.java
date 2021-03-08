package zip_code_db_cli.domain.service.csv_contents;

import java.util.List;

import java_itamae_contents.domain.model.ContentsAttribute;
import zip_code_db_cli.domain.model.ZipCodeCsvEntity;

public interface CsvContentsService {
    /**
     * @param attr 操作対象とするファイルの情報を収めた {@link ContentsAttribute} を指定する。
     */
    void init(ContentsAttribute attr);

    /**
     * CSV を読込んで {@link List} に変換して返す。
     *
     * @return contents 変換された {@link List}
     * @throws Exception {@link Exception}
     */
    List<ZipCodeCsvEntity> getContents() throws Exception;

    /**
     * CSV を上書きする。
     *
     * @param content 書込み対象とする情報を収めた {@link ZipCodeCsvEntity} を指定する。
     * @return status
     *         <ul>
     *         <li>true: 書込みに成功したことを表す。</li>
     *         <li>false: 書込みを実行しなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link Exception}
     */
    boolean setContent(ZipCodeCsvEntity content) throws Exception;

    /**
     * CSV を一行追記する。
     *
     * @param content 書込み対象とする情報を収めた {@link ZipCodeCsvEntity} を指定する。
     * @return status
     *         <ul>
     *         <li>true: 書込みに成功したことを表す。</li>
     *         <li>false: 書込みを実行しなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link Exception}
     */
    boolean appendContent(ZipCodeCsvEntity content) throws Exception;

    /**
     * CSV を空にする。
     *
     * @return status
     *         <ul>
     *         <li>true: 削除に成功したことを表す。</li>
     *         <li>false: 削除を実行しなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link Exception}
     */
    boolean deleteContents() throws Exception;
}
