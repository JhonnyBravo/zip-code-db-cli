package zip_code_db_cli.domain.service.csv_contents;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java_itamae_contents.domain.model.ContentsAttribute;
import java_itamae_contents.domain.repository.stream.StreamRepository;
import java_itamae_contents.domain.repository.stream.StreamRepositoryImpl;
import zip_code_db_cli.domain.model.ZipCode;
import zip_code_db_cli.domain.repository.csv_contents.CsvContentsRepository;
import zip_code_db_cli.domain.repository.csv_contents.CsvContentsRepositoryImpl;

public class CsvContentsServiceImpl implements CsvContentsService {
  private final ContentsAttribute attr;
  private final StreamRepository sr;
  private final CsvContentsRepository ccr;

  /**
   * 初期化処理を実行する。
   *
   * @param attr 操作対象とするファイルの情報を納めた {@link ContentsAttribute} を指定する。
   */
  public CsvContentsServiceImpl(ContentsAttribute attr) {
    this.attr = attr;
    sr = new StreamRepositoryImpl();
    ccr = new CsvContentsRepositoryImpl();
  }

  @Override
  public List<ZipCode> getContents() throws Exception {
    List<ZipCode> contents = new ArrayList<>();

    try (Reader reader = sr.getReader(attr)) {
      contents = ccr.getContents(reader);
    }

    return contents;
  }

  @Override
  public boolean updateContent(ZipCode content) throws Exception {
    boolean status = false;
    final File file = new File(attr.getPath());

    if (!file.isFile()) {
      file.createNewFile();
    }

    final List<ZipCode> contents = new ArrayList<>();
    contents.add(content);

    try (Writer writer = sr.getWriter(attr)) {
      status = ccr.updateContents(writer, contents);
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

    try (Writer writer = sr.getWriter(attr)) {
      status = ccr.updateContents(writer, contents);
    }

    return status;
  }

  @Override
  public boolean deleteContents() throws Exception {
    boolean status = false;
    final List<ZipCode> contents = getContents();

    if (contents.size() > 0) {
      try (Writer writer = sr.getWriter(attr)) {
        status = ccr.deleteContents(writer);
      }
    }

    return status;
  }
}
