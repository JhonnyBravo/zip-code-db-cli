package zip_code_db_cli.domain.repository.zip_code;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import zip_code_db_cli.domain.model.ZipCodeEntity;

/**
 * zip_code テーブルのレコード操作を管理する。
 */
@Repository
public interface ZipCodeRepository extends CrudRepository<ZipCodeEntity, Integer> {
    /**
     * @return recordset 登録されているレコードを全件取得し、 ID 昇順に並べ替えて返す。
     */
    List<ZipCodeEntity> findAllByOrderById();
}
