package zip_code_db_cli.domain.service.csv_contents;

import jakarta.inject.Inject;
import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java_itamae_contents.domain.model.ContentsAttribute;
import java_itamae_contents.domain.repository.stream.StreamRepository;
import zip_code_db_cli.domain.model.ZipCodeCsvEntity;
import zip_code_db_cli.domain.repository.csv_contents.CsvContentsRepository;

public class CsvContentsServiceImpl implements CsvContentsService {
  @Inject
  private StreamRepository sr;
  @Inject
  private CsvContentsRepository ccr;

  private ContentsAttribute attr;

  @Override
  public void init(ContentsAttribute attr) {
    this.attr = attr;
  }

  @Override
  public List<ZipCodeCsvEntity> getContents() throws Exception {
    List<ZipCodeCsvEntity> contents = new ArrayList<>();

    try (Reader reader = sr.getReader(attr)) {
      contents = ccr.getContents(reader);
    }

    return contents;
  }

  @Override
  public boolean updateContent(ZipCodeCsvEntity content) throws Exception {
    boolean status = false;
    final File file = new File(attr.getPath());

    if (!file.isFile()) {
      file.createNewFile();
    }

    final List<ZipCodeCsvEntity> contents = new ArrayList<>();
    contents.add(content);

    try (Writer writer = sr.getWriter(attr)) {
      status = ccr.updateContents(writer, contents);
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

    try (Writer writer = sr.getWriter(attr)) {
      status = ccr.updateContents(writer, contents);
    }

    return status;
  }

  @Override
  public boolean deleteContents() throws Exception {
    boolean status = false;
    final List<ZipCodeCsvEntity> contents = getContents();

    if (contents.size() > 0) {
      try (Writer writer = sr.getWriter(attr)) {
        status = ccr.deleteContents(writer);
      }
    }

    return status;
  }
}
