package zip_code_db_cli.domain.service.csv_contents;

import java.util.List;
import java_itamae.domain.model.contents.ContentsModel;
import zip_code_db_cli.domain.component.csv_contents.CsvContentsComponent;
import zip_code_db_cli.domain.component.csv_contents.CsvContentsComponentImpl;
import zip_code_db_cli.domain.model.ZipCode;

public class CsvContentsServiceImpl implements CsvContentsService {
  /** {@link ContentsModel} のインスタンス */
  private transient ContentsModel model;
  /** {@link CsvContentsComponent} のインスタンス */
  private final transient CsvContentsComponent component;

  /** 初期化処理を実行する。 */
  public CsvContentsServiceImpl() {
    this.component = new CsvContentsComponentImpl();
  }

  @Override
  public void init(final ContentsModel model) {
    this.model = model;
  }

  @Override
  public List<ZipCode> getContents() throws Exception {
    return component.getContents(model);
  }

  @Override
  public int updateContents(final List<ZipCode> contents) {
    return component.updateContents(model, contents);
  }

  @Override
  public int deleteContents() {
    return component.deleteContents(model);
  }
}
