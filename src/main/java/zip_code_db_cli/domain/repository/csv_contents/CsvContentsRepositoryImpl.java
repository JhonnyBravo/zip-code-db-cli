package zip_code_db_cli.domain.repository.csv_contents;

import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import zip_code_db_cli.domain.model.ZipCodeCsvEntity;

@Service
public class CsvContentsRepositoryImpl implements CsvContentsRepository {

    @Override
    public List<ZipCodeCsvEntity> getContents(Reader reader) throws Exception {
        List<ZipCodeCsvEntity> contents = new ArrayList<>();
        contents = new CsvToBeanBuilder<ZipCodeCsvEntity>(reader).withType(ZipCodeCsvEntity.class).build().parse();
        return contents;
    }

    @Override
    public boolean setContents(Writer writer, List<ZipCodeCsvEntity> contents) throws Exception {
        boolean status = false;

        new StatefulBeanToCsvBuilder<ZipCodeCsvEntity>(writer).build().write(contents);
        status = true;

        return status;
    }

    @Override
    public boolean deleteContents(Writer writer) throws Exception {
        boolean status = false;

        final List<ZipCodeCsvEntity> contents = new ArrayList<>();
        status = setContents(writer, contents);

        return status;
    }

}
