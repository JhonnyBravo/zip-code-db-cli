package zip_code_db_cli.domain.repository.csv_contents;

import java.io.Reader;
import java.io.Writer;
import java.util.List;

import zip_code_db_cli.domain.model.ZipCodeCsvEntity;

public interface CsvContentsRepository {
    /**
     * CSV を読込んで {@link List} に変換して返す。
     *
     * @param reader 操作対象とするファイルの {@link Reader} を指定する。
     * @return contents 変換された {@link List} を返す。
     * @throws Exception {@link Exception}
     */
    List<ZipCodeCsvEntity> getContents(Reader reader) throws Exception;

    /**
     * CSV を上書きする。
     *
     * @param writer   操作対象とするファイルの {@link Writer} を指定する。
     * @param contents 書込み対象とする {@link List} を指定する。
     * @return status
     *         <ul>
     *         <li>true: 書込みに成功したことを表す。</li>
     *         <li>false: 書込みが実行されなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link Exception}
     */
    boolean setContents(Writer writer, List<ZipCodeCsvEntity> contents) throws Exception;

    /**
     * CSV を空にする。
     *
     * @param writer 操作対象とするファイルの {@link Writer} を指定する。
     * @return status
     *         <ul>
     *         <li>true: 削除に成功したことを表す。</li>
     *         <li>false: 削除が実行されなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link Exception}
     */
    boolean deleteContents(Writer writer) throws Exception;
}
