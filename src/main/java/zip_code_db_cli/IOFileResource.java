package zip_code_db_cli;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * ファイルの読み書きに使用するリーダー / ライターを取得する。
 */
public class IOFileResource {
    private final String path;
    private final File file;

    /**
     * @param path 操作対象とするファイルのパスを指定する。
     */
    public IOFileResource(String path) {
        this.path = path;
        file = new File(path);
    }

    /**
     * @return reader ファイルを読取モードで開き、リーダーを取得する。
     * @throws Exception {@link java.lang.Exception}
     */
    public Reader getReader() throws Exception {
        if (!file.isFile()) {
            throw new FileNotFoundException(path + " が見つかりません。");
        }

        final Reader reader = new FileReader(path);
        return reader;
    }

    /**
     * @return writer ファイルを書込みモードで開き、ライターを取得する。
     * @throws Exception {@link java.lang.Exception}
     */
    public Writer getWriter() throws Exception {
        if (!file.isFile()) {
            throw new FileNotFoundException(path + " が見つかりません。");
        }

        final Writer writer = new FileWriter(path);
        return writer;
    }
}
