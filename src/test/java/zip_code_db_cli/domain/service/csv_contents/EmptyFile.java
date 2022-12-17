package zip_code_db_cli.domain.service.csv_contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java_itamae.domain.model.contents.ContentsModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import zip_code_db_cli.domain.common.GetTestContents;
import zip_code_db_cli.domain.component.csv_contents.CsvContentsComponent;
import zip_code_db_cli.domain.model.ZipCode;

/** ファイルが空である場合のテスト */
public class EmptyFile {
  private Path path;
  private ContentsModel model;
  private CsvContentsService service;

  @Before
  public void setUp() throws Exception {
    model = new ContentsModel();
    model.setPath("test.csv");

    service = new CsvContentsServiceImpl();
    service.init(model);

    path = Paths.get(model.getPath());
    Files.createFile(path);
  }

  @After
  public void tearDown() throws Exception {
    Files.delete(path);
  }

  /** {@link CsvContentsService#getContents()} 実行時に空の {@link List} が返されること。 */
  @Test
  public void ccs001() throws Exception {
    final List<ZipCode> contents = service.getContents();
    assertThat(contents.size(), is(0));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link CsvContentsComponent#updateContents(ContentsModel, List)} 実行時にファイルを上書きできること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void ccs002() throws Exception {
    final List<ZipCode> extContents = new GetTestContents().get();
    final int status = service.updateContents(extContents);
    assertThat(status, is(2));

    final List<ZipCode> actContents = service.getContents();
    assertThat(actContents.size(), is(2));

    for (int i = 0; i < actContents.size(); i++) {
      final ZipCode expModel = extContents.get(i);
      final ZipCode actModel = actContents.get(i);

      assertThat(actModel.getZipCode(), is(expModel.getZipCode()));
      assertThat(actModel.getPrefecture(), is(expModel.getPrefecture()));
      assertThat(actModel.getArea(), is(expModel.getArea()));
      assertThat(actModel.getCity(), is(expModel.getCity()));
    }
  }
}
