package zip_code_db_cli.domain.service.zip_code;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import java_itamae_connection.domain.model.ConnectionInfo;
import zip_code_db_cli.domain.model.ZipCode;
import zip_code_db_cli.domain.repository.zip_code.ZipCodeRepository;
import zip_code_db_cli.domain.repository.zip_code.ZipCodeRepositoryImpl;

public class ZipCodeServiceImpl extends ZipCodeService {
    private final ConnectionInfo cnInfo;
    private final ZipCodeRepository zcr;

    /**
     * @param cnInfo DB の接続情報を納めた ConnectionInfo を指定する。
     */
    public ZipCodeServiceImpl(ConnectionInfo cnInfo) {
        this.cnInfo = cnInfo;
        zcr = new ZipCodeRepositoryImpl();
    }

    @Override
    public List<ZipCode> findAll() throws Exception {
        List<ZipCode> recordset = new ArrayList<>();

        try (Connection connection = getConnection(cnInfo)) {
            recordset = zcr.findAll(connection);
        }

        return recordset;
    }

    @Override
    public boolean create(List<ZipCode> recordset) throws Exception {
        boolean status = false;

        try (Connection connection = getConnection(cnInfo)) {
            status = zcr.create(connection, recordset);
        }

        return status;
    }

    @Override
    public boolean deleteAll() throws Exception {
        boolean status = false;

        try (Connection connection = getConnection(cnInfo)) {
            final List<ZipCode> recordset = zcr.findAll(connection);

            if (recordset.size() > 0) {
                status = zcr.deleteAll(connection);
            }
        }

        return status;
    }
}
