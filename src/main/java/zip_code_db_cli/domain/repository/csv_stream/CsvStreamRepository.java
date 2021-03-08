package zip_code_db_cli.domain.repository.csv_stream;

import java.io.Reader;
import java.io.Writer;

import java_itamae_contents.domain.model.ContentsAttribute;

public interface CsvStreamRepository {
    /**
     * CSV を読込みモードで開き、 {@link Reader} を取得して返す。
     *
     * @param attr 操作対象とするファイルの情報を収めた {@link ContentsAttribute} を指定する。
     * @return {@link Reader} を返す。
     * @throws Exception {@link Exception}
     */
    Reader getReader(ContentsAttribute attr) throws Exception;

    /**
     * CSV を書込みモードで開き、 {@link Writer} を取得して返す。
     *
     * @param attr 操作対象とするファイルの情報を収めた {@link ContentsAttribute} を指定する。
     * @return {@link Writer} を返す。
     * @throws Exception {@link Exception}
     */
    Writer getWriter(ContentsAttribute attr) throws Exception;
}
