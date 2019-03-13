package zip_code_db_cli;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;

import context_resource.CsvResource;

/**
 * CSV から Iterator を取得する。
 */
public class CsvIterator extends CsvResource {

    /**
     * @param path 操作対象とする CSV のパスを指定する。
     */
    public CsvIterator(String path) {
        super(path);
    }

    /**
     * @return Iterator&lt;String[]&gt; CSV を全行読込み Iterator に変換して返す。
     */
    public Iterator<String[]> iterator() {
        Iterator<String[]> result = null;
        CSVReader reader = (CSVReader) this.getContext();

        if (this.getCode() == 1) {
            return result;
        }

        List<String[]> recordset = null;

        try {
            recordset = reader.readAll();
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e);
            return result;
        }

        if (recordset.size() == 0) {
            this.initStatus();
        } else {
            this.setCode(2);
        }

        result = recordset.iterator();
        return result;
    }
}
