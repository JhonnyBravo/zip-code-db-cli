package zip_code_db_cli.domain.service.zip_code;

import java.util.List;
import java_itamae_connection.domain.model.ConnectionInfo;
import zip_code_db_cli.domain.model.ZipCode;
import zip_code_db_cli.domain.repository.zip_code.ZipCodeRepository;
import zip_code_db_cli.domain.repository.zip_code.ZipCodeRepositoryImpl;

public class ZipCodeServiceImpl implements ZipCodeService {
  /** {@link ZipCodeRepository} のインスタンス */
  private final transient ZipCodeRepository repository;

  /** 初期化処理を実行する。 */
  public ZipCodeServiceImpl() {
    repository = new ZipCodeRepositoryImpl();
  }

  @Override
  public void init(final ConnectionInfo cnInfo) {
    repository.init(cnInfo);
  }

  @Override
  public List<ZipCode> findAll() throws Exception {
    return repository.findAll();
  }

  @Override
  public int create(final List<ZipCode> recordset) {
    int status = 0;

    try {
      final boolean result = repository.create(recordset);

      if (result) {
        status = 2;
      }
    } catch (final Exception e) {
      final String message = e.toString();
      this.getLogger().warn("{}", message);
      status = 1;
    }

    return status;
  }

  @Override
  public int deleteAll() {
    int status = 0;

    try {
      final List<ZipCode> recordset = repository.findAll();

      if (!recordset.isEmpty()) {
        final boolean result = repository.deleteAll();

        if (result) {
          status = 2;
        }
      }
    } catch (final Exception e) {
      final String message = e.toString();
      this.getLogger().warn("{}", message);
      status = 1;
    }

    return status;
  }
}
