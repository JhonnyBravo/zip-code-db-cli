package zip_code_db_cli.domain.component.csv_contents;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java_itamae.domain.model.contents.ContentsModel;
import zip_code_db_cli.domain.model.ZipCode;

@SuppressWarnings("unused")
public class CsvContentsComponentImpl implements CsvContentsComponent {
  @Override
  public List<ZipCode> getContents(final ContentsModel model) throws Exception {
    List<ZipCode> contents = new ArrayList<>();

    try (BufferedReader buffer = new BufferedReader(this.getReader(model))) {
      contents = new CsvToBeanBuilder<ZipCode>(buffer).withType(ZipCode.class).build().parse();
    }

    return contents;
  }

  @Override
  public int updateContents(final ContentsModel model, final List<ZipCode> contents) {
    int status = 0;

    try (BufferedWriter buffer = new BufferedWriter(this.getWriter(model))) {
      new StatefulBeanToCsvBuilder<ZipCode>(buffer).build().write(contents);
      status = 2;
    } catch (final Exception e) {
      final String message = e.toString();
      this.getLogger().warn("{}", message);
      status = 1;
    }

    return status;
  }

  @Override
  public int deleteContents(final ContentsModel model) {
    int status = 0;

    try {
      final List<ZipCode> curContents = this.getContents(model);
      final List<ZipCode> newContents = new ArrayList<>();

      if (!curContents.isEmpty()) {
        status = this.updateContents(model, newContents);
      }
    } catch (final Exception e) {
      final String message = e.toString();
      this.getLogger().warn("{}", message);
      status = 1;
    }

    return status;
  }
}
