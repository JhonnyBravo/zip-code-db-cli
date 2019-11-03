package zip_code_db_cli.domain.service.zip_code;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import java_itamae_contents.domain.model.ContentsAttribute;
import zip_code_db_cli.domain.model.ZipCode;
import zip_code_db_cli.domain.repository.connection.ConnectionRepository;
import zip_code_db_cli.domain.repository.connection.ConnectionRepositoryImpl;
import zip_code_db_cli.domain.repository.zip_code.ZipCodeRepository;
import zip_code_db_cli.domain.repository.zip_code.ZipCodeRepositoryImpl;

public class ZipCodeServiceImpl implements ZipCodeService {
    private final ContentsAttribute attr;
    private final ConnectionRepository cr;
    private final ZipCodeRepository zcr;

    /**
     * @param attr DB の接続情報を記載したファイルの情報を納めた ConnectionAttibute を指定する。
     */
    public ZipCodeServiceImpl(ContentsAttribute attr) {
        this.attr = attr;
        cr = new ConnectionRepositoryImpl();
        zcr = new ZipCodeRepositoryImpl();
    }

    @Override
    public List<ZipCode> findAll() throws Exception {
        List<ZipCode> recordset = new ArrayList<>();

        try (Connection connection = cr.getConnection(attr)) {
            recordset = zcr.findAll(connection);
        }

        return recordset;
    }

    @Override
    public boolean create(List<ZipCode> recordset) throws Exception {
        boolean status = false;

        try (Connection connection = cr.getConnection(attr)) {
            status = zcr.create(connection, recordset);
        }

        return status;
    }

    @Override
    public boolean deleteAll() throws Exception {
        boolean status = false;

        try (Connection connection = cr.getConnection(attr)) {
            final List<ZipCode> recordset = zcr.findAll(connection);

            if (recordset.size() > 0) {
                status = zcr.deleteAll(connection);
            }
        }

        return status;
    }
}
