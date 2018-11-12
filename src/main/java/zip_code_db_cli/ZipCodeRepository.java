package zip_code_db_cli;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * zip_code テーブルのレコード操作を管理する。
 */
@Service
@Repository
public interface ZipCodeRepository extends CrudRepository<ZipCode, Integer> {

}
