package zip_code_db_cli;

import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import content_resource.ContentResource;

/**
 * CSV の読み書きを管理する。
 */
public class CsvResource extends IOFileResource implements ContentResource<List<ZipCode>> {
    /**
     * @param path 操作対象とする CSV のパスを指定する。
     */
    public CsvResource(String path) {
        super(path);
    }

    /**
     * CSV を上書きする。
     *
     * @param model 書込み対象とするモデルオブジェクトを指定する。
     * @return status
     *         <ul>
     *         <li>true: 書込みに成功したことを表す。</li>
     *         <li>false: 書込みに失敗したことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    @Override
    public boolean setContent(List<ZipCode> model) throws Exception {
        boolean status = false;

        try (Writer writer = getWriter()) {
            new StatefulBeanToCsvBuilder<ZipCode>(writer).build().write(model);
            status = true;
        }

        return status;
    }

    /**
     * @return content CSV を読込み、 List に変換して返す。
     * @throws Exception {@link java.lang.Exception}
     */
    @Override
    public List<ZipCode> getContent() throws Exception {
        List<ZipCode> contents = new ArrayList<ZipCode>();

        try (Reader reader = getReader()) {
            contents = new CsvToBeanBuilder<ZipCode>(reader).withType(ZipCode.class).build().parse();
        }

        return contents;
    }

    /**
     * CSV を空にする。
     *
     * @return status
     *         <ul>
     *         <li>true: 削除に成功したことを表す。</li>
     *         <li>false: 削除に失敗したことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    @Override
    public boolean deleteContent() throws Exception {
        boolean status = false;

        final List<ZipCode> contents = new ArrayList<ZipCode>();
        status = setContent(contents);

        return status;
    }
}
