package zip_code_db_cli.domain.repository.csv_contents;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import zip_code_db_cli.domain.model.ZipCode;

public class CsvContentsRepositoryImpl implements CsvContentsRepository {

  @Override
  public List<ZipCode> getContents(Reader reader) throws Exception {
    List<ZipCode> contents = new ArrayList<>();
    contents = new CsvToBeanBuilder<ZipCode>(reader).withType(ZipCode.class).build().parse();
    return contents;
  }

  @Override
  public boolean updateContents(Writer writer, List<ZipCode> contents) throws Exception {
    boolean status = false;

    new StatefulBeanToCsvBuilder<ZipCode>(writer).build().write(contents);
    status = true;

    return status;
  }

  @Override
  public boolean deleteContents(Writer writer) throws Exception {
    boolean status = false;

    final List<ZipCode> contents = new ArrayList<>();
    status = updateContents(writer, contents);

    return status;
  }

}
