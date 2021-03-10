package zip_code_db_cli.domain.service.zip_code;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zip_code_db_cli.domain.model.ZipCodeEntity;
import zip_code_db_cli.domain.repository.zip_code.ZipCodeRepository;

@Service
@Transactional
public class ZipCodeServiceImpl implements ZipCodeService {
    @Autowired
    private ZipCodeRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ZipCodeEntity> findAllByOrderById() throws Exception {
        final List<ZipCodeEntity> recordset = repository.findAllByOrderById();
        return recordset;
    }

    @Override
    public boolean saveAll(List<ZipCodeEntity> recordset) throws Exception {
        if (recordset.size() > 0) {
            repository.saveAll(recordset);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteAll() throws Exception {
        final List<ZipCodeEntity> recordset = this.findAllByOrderById();

        if (recordset.size() > 0) {
            repository.deleteAll();
            return true;
        } else {
            return false;
        }
    }
}
