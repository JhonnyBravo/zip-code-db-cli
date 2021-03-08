package zip_code_db_cli.domain.service.csv_contents;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java_itamae_contents.domain.model.ContentsAttribute;
import zip_code_db_cli.domain.model.ZipCodeCsvEntity;
import zip_code_db_cli.domain.repository.csv_contents.CsvContentsRepository;
import zip_code_db_cli.domain.repository.csv_stream.CsvStreamRepository;

@Service
public class CsvContentsServiceImpl implements CsvContentsService {
    private ContentsAttribute attr;
    @Autowired
    private CsvStreamRepository csr;
    @Autowired
    private CsvContentsRepository ccr;

    @Override
    public void init(ContentsAttribute attr) {
        this.attr = attr;
    }

    @Override
    public List<ZipCodeCsvEntity> getContents() throws Exception {
        List<ZipCodeCsvEntity> contents = new ArrayList<>();

        try (Reader reader = csr.getReader(attr)) {
            contents = ccr.getContents(reader);
        }

        return contents;
    }

    @Override
    public boolean setContent(ZipCodeCsvEntity content) throws Exception {
        boolean status = false;
        final File file = new File(attr.getPath());

        if (!file.isFile()) {
            file.createNewFile();
        }

        final List<ZipCodeCsvEntity> contents = new ArrayList<>();
        contents.add(content);

        try (Writer writer = csr.getWriter(attr)) {
            status = ccr.setContents(writer, contents);
        }

        return status;
    }

    @Override
    public boolean appendContent(ZipCodeCsvEntity content) throws Exception {
        boolean status = false;
        final File file = new File(attr.getPath());

        if (!file.isFile()) {
            file.createNewFile();
        }

        final List<ZipCodeCsvEntity> contents = getContents();
        contents.add(content);

        try (Writer writer = csr.getWriter(attr)) {
            status = ccr.setContents(writer, contents);
        }

        return status;
    }

    @Override
    public boolean deleteContents() throws Exception {
        boolean status = false;
        final List<ZipCodeCsvEntity> contents = getContents();

        if (contents.size() > 0) {
            try (Writer writer = csr.getWriter(attr)) {
                status = ccr.deleteContents(writer);
            }
        }

        return status;
    }

}
