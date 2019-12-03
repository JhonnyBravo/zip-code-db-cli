package zip_code_db_cli.domain.service.zip_code;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import zip_code_db_cli.domain.model.ZipCode;
import zip_code_db_cli.domain.repository.zip_code.ZipCodeRepository;

public class ZipCodeServiceImpl implements ZipCodeService {
    private final SqlSessionFactory factory;
    private ZipCodeRepository repository;

    public ZipCodeServiceImpl() throws Exception {
        factory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"));
    }

    @Override
    public List<ZipCode> findAll() throws Exception {
        List<ZipCode> recordset = new ArrayList<>();

        try (SqlSession session = factory.openSession()) {
            repository = session.getMapper(ZipCodeRepository.class);
            recordset = repository.findAll();
        }

        return recordset;
    }

    @Override
    public boolean create(List<ZipCode> recordset) throws Exception {
        boolean status = false;

        if (recordset.size() > 0) {
            try (SqlSession session = factory.openSession()) {
                repository = session.getMapper(ZipCodeRepository.class);
                status = repository.create(recordset);
                session.commit();
            }
        }

        return status;
    }

    @Override
    public boolean deleteAll() throws Exception {
        boolean status = false;

        try (SqlSession session = factory.openSession()) {
            repository = session.getMapper(ZipCodeRepository.class);
            status = repository.deleteAll();
            repository.resetId();
            session.commit();
        }

        return status;
    }
}
