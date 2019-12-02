package zip_code_db_cli.domain.repository.zip_code;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import zip_code_db_cli.domain.model.ZipCode;

@Mapper
public interface ZipCodeRepository {
    /**
     * @return recordset テーブルからレコードを全件取得する。
     * @throws Exception
     *             {@link java.lang.Exception}
     */
    public List<ZipCode> findAll() throws Exception;

    /**
     * テーブルへレコードを一括登録する。
     *
     * @param contents
     *            登録対象とするレコードセットを指定する。
     * @return status
     *         <ul>
     *         <li>true: レコードの登録に成功したことを表す。</li>
     *         <li>false: レコードが登録されなかったことを表す。</li>
     *         </ul>
     * @throws Exception
     *             {@link java.lang.Exception}
     */
    public boolean create(@Param("list") List<ZipCode> contents) throws Exception;

    /**
     * テーブルからレコードを全件削除する。
     *
     * @return status
     *         <ul>
     *         <li>true: レコードの削除に成功したことを表す。</li>
     *         <li>false: レコードが削除されなかったことを表す。</li>
     *         </ul>
     * @throws Exception
     *             {@link java.lang.Exception}
     */
    public boolean deleteAll() throws Exception;

    /**
     * ID のカウンターを 1 にリセットする。
     * 
     * @return status
     * @throws Exception
     *             {@link java.lang.Exception}
     */
    public boolean resetId() throws Exception;
}
