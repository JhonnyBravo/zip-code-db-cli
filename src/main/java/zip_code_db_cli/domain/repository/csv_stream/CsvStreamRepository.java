package zip_code_db_cli.domain.repository.csv_stream;

import java.io.Reader;
import java.io.Writer;

import java_itamae_contents.domain.model.ContentsAttribute;

public interface CsvStreamRepository {
    /**
     * CSV を読込みモードで開き、 Reader を取得して返す。
     *
     * @param attr 操作対象とするファイルの情報を納めた ContentsAttribute を指定する。
     * @return reader Reader を返す。
     * @throws Exception {@link java.lang.Exception}
     */
    public Reader getReader(ContentsAttribute attr) throws Exception;

    /**
     * CSV を書込みモードで開き、 Writer を取得して返す。
     *
     * @param attr 操作対象とするファイルの情報を納めた ContentsAttribute を指定する。
     * @return writer Writer を返す。
     * @throws Exception {@link java.lang.Exception}
     */
    public Writer getWriter(ContentsAttribute attr) throws Exception;
}
