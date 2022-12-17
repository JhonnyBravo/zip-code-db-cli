package zip_code_db_cli.app;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java_itamae.domain.model.contents.ContentsModel;
import org.slf4j.Logger;
import zip_code_db_cli.domain.model.ZipCode;
import zip_code_db_cli.domain.service.csv_contents.CsvContentsService;
import zip_code_db_cli.domain.service.csv_contents.CsvContentsServiceImpl;

/** CSV を読込み、 {@link ZipCode} の {@link List} に変換して返す。 */
public class GetCsvContents implements Function<ContentsModel, List<ZipCode>> {
  /**
   * CSV を読込み、 {@link ZipCode} の {@link List} に変換して返す。
   *
   * @param model 操作対象とするファイルの情報を収めた {@link ContentsModel} を指定する。
   * @return contents {@link ZipCode} の {@link List} を返す。
   */
  @Override
  public List<ZipCode> apply(final ContentsModel model) {
    final CsvContentsService service = new CsvContentsServiceImpl();
    service.init(model);
    final Logger logger = service.getLogger();

    List<ZipCode> contents = new ArrayList<>();

    try {
      if (logger.isInfoEnabled()) {
        logger.info("{} を読み込んでいます......", model.getPath());
      }

      contents = service.getContents();
    } catch (final Exception e) {
      final String message = e.toString();
      logger.warn("{}", message);
    }

    return contents;
  }
}
