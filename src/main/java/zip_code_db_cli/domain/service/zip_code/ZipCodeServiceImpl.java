package zip_code_db_cli.domain.service.zip_code;

import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import zip_code_db_cli.domain.model.ZipCodeEntity;
import zip_code_db_cli.domain.repository.zip_code.ZipCodeRepository;

public class ZipCodeServiceImpl implements ZipCodeService {
  @Inject
  private ZipCodeRepository repository;

  @Override
  public List<ZipCodeEntity> findAll() throws Exception {
    List<ZipCodeEntity> recordset = new ArrayList<>();
    recordset = repository.findAll();
    return recordset;
  }

  @Override
  public boolean create(List<ZipCodeEntity> recordset) throws Exception {
    final boolean status = repository.create(recordset);
    return status;
  }

  @Override
  public boolean deleteAll() throws Exception {
    boolean status = false;

    final List<ZipCodeEntity> recordset = repository.findAll();

    if (recordset.size() > 0) {
      status = repository.deleteAll();
    }

    return status;
  }
}
