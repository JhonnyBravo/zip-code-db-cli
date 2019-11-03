package zip_code_db_cli.domain.service.csv_contents;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import java_itamae_contents.domain.model.ContentsAttribute;
import zip_code_db_cli.domain.model.ZipCode;
import zip_code_db_cli.domain.repository.csv_contents.CsvContentsRepository;
import zip_code_db_cli.domain.repository.csv_contents.CsvContentsRepositoryImpl;
import zip_code_db_cli.domain.repository.csv_stream.CsvStreamRepository;
import zip_code_db_cli.domain.repository.csv_stream.CsvStreamRepositoryImpl;

public class CsvContentsServiceImpl implements CsvContentsService {
    private final ContentsAttribute attr;
    private final CsvStreamRepository csr;
    private final CsvContentsRepository ccr;

    /**
     * @param attr 操作対象とするファイルの情報を納めた ContentsAttribute を指定する。
     */
    public CsvContentsServiceImpl(ContentsAttribute attr) {
        this.attr = attr;
        csr = new CsvStreamRepositoryImpl();
        ccr = new CsvContentsRepositoryImpl();
    }

    @Override
    public List<ZipCode> getContents() throws Exception {
        List<ZipCode> contents = new ArrayList<ZipCode>();

        try (Reader reader = csr.getReader(attr)) {
            contents = ccr.getContents(reader);
        }

        return contents;
    }

    @Override
    public boolean setContent(ZipCode content) throws Exception {
        boolean status = false;
        final File file = new File(attr.getPath());

        if (!file.isFile()) {
            file.createNewFile();
        }

        final List<ZipCode> contents = new ArrayList<ZipCode>();
        contents.add(content);

        try (Writer writer = csr.getWriter(attr)) {
            status = ccr.setContents(writer, contents);
        }

        return status;
    }

    @Override
    public boolean appendContent(ZipCode content) throws Exception {
        boolean status = false;
        final File file = new File(attr.getPath());

        if (!file.isFile()) {
            file.createNewFile();
        }

        final List<ZipCode> contents = getContents();
        contents.add(content);

        try (Writer writer = csr.getWriter(attr)) {
            status = ccr.setContents(writer, contents);
        }

        return status;
    }

    @Override
    public boolean deleteContents() throws Exception {
        boolean status = false;
        final List<ZipCode> contents = getContents();

        if (contents.size() > 0) {
            try (Writer writer = csr.getWriter(attr)) {
                status = ccr.deleteContents(writer);
            }
        }

        return status;
    }
}
