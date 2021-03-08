package zip_code_db_cli.domain.repository.csv_stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

import org.springframework.stereotype.Service;

import java_itamae_contents.domain.model.ContentsAttribute;

@Service
public class CsvStreamRepositoryImpl implements CsvStreamRepository {

    @Override
    public Reader getReader(ContentsAttribute attr) throws Exception {
        final File file = new File(attr.getPath());

        if (!file.isFile()) {
            throw new FileNotFoundException(attr.getPath() + " が見つかりません");
        }

        final Reader reader = new FileReader(attr.getPath());
        return reader;
    }

    @Override
    public Writer getWriter(ContentsAttribute attr) throws Exception {
        final File file = new File(attr.getPath());

        if (!file.isFile()) {
            throw new FileNotFoundException(attr.getPath() + " が見つかりません。");
        }

        final Writer writer = new FileWriter(attr.getPath());
        return writer;
    }

}
