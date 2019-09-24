package zip_code_db_cli;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import sql_resource.ConnectionResource;

/**
 * t_zip_code の操作を管理する。
 */
public class ZipCodeController extends ConnectionResource implements ImportResource<List<ZipCode>> {

    /**
     * @param property DB 接続時に使用する設定情報を納めた Map オブジェクトを指定する。
     */
    public ZipCodeController(Map<String, String> property) {
        super(property);
    }

    /**
     * t_zip_code へ新規レコードを一括登録する。
     *
     * @param model 登録対象とするモデルオブジェクトを指定する。
     * @return status
     *         <ul>
     *         <li>true: レコードを登録したことを表す。</li>
     *         <li>false: レコードを登録しなかったことを表す。</li>
     *         </ul>
     */
    @Override
    public boolean create(List<ZipCode> model) throws Exception {
        boolean status = false;
        final Connection cn = getConnection();

        try (PreparedStatement query = cn.prepareStatement(
                "INSERT INTO t_zip_code (jis_code,zip_code,prefecture_phonetic,city_phonetic,area_phonetic,prefecture,city,area,update_flag,reason_flag) VALUES (?,?,?,?,?,?,?,?,?,?)")) {
            cn.setAutoCommit(false);
            long count = 0;

            for (final ZipCode zipcode : model) {
                query.setString(1, zipcode.getJisCode());
                query.setString(2, zipcode.getZipCode());
                query.setString(3, zipcode.getPrefecturePhonetic());
                query.setString(4, zipcode.getCityPhonetic());
                query.setString(5, zipcode.getAreaPhonetic());
                query.setString(6, zipcode.getPrefecture());
                query.setString(7, zipcode.getCity());
                query.setString(8, zipcode.getArea());
                query.setInt(9, zipcode.getUpdateFlag());
                query.setInt(10, zipcode.getReasonFlag());

                query.addBatch();
                count++;

                if (count % 1000 == 0) {
                    query.executeBatch();
                    cn.commit();
                    cn.setAutoCommit(false);
                }
            }

            query.executeBatch();
            cn.commit();

            if (count > 0) {
                status = true;
            }
        } catch (final Exception e) {
            cn.rollback();
            throw e;
        } finally {
            cn.close();
        }

        return status;
    }

    /**
     * t_zip_code からレコードを全件削除する。
     *
     * @return status
     *         <ul>
     *         <li>true: レコードを削除したことを表す。</li>
     *         <li>false: レコードを削除しなかったことを表す。</li>
     *         </ul>
     */
    @Override
    public boolean delete() throws Exception {
        boolean status = false;
        final Connection cn = getConnection();

        try (PreparedStatement query = cn.prepareStatement("DELETE FROM t_zip_code;")) {
            cn.setAutoCommit(false);

            query.addBatch();
            query.addBatch("ALTER TABLE t_zip_code auto_increment=1;");
            query.executeBatch();
            cn.commit();

            status = true;
        } catch (final Exception e) {
            cn.rollback();
            throw e;
        } finally {
            cn.close();
        }

        return status;
    }
}
