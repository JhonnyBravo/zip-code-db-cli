package zip_code_db_cli.domain.repository.zip_code;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import zip_code_db_cli.domain.model.ZipCodeEntity;
import zip_code_db_cli.test.CreateTestRecords;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZipCodeRepositoryTest {
    @Autowired
    private ZipCodeRepository repository;
    @Autowired
    private CreateTestRecords createTestRecords;

    @Before
    public void setUp() throws Exception {
        repository.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    // テーブルが空である場合
    @Test
    public void findAllOrderById実行時に空のリストが返されること() throws Exception {
        final List<ZipCodeEntity> recordset = repository.findAllByOrderById();
        assertThat(recordset.size(), is(0));
    }

    @Test
    public void saveAll実行時にレコードを一括登録できること() {
        repository.saveAll(createTestRecords.get());

        final List<ZipCodeEntity> recordset = repository.findAllByOrderById();
        assertThat(recordset.size(), is(2));

        final ZipCodeEntity record1 = recordset.get(0);
        assertThat(record1.getZipCode(), is("0600000"));
        assertThat(record1.getPrefecture(), is("北海道"));
        assertThat(record1.getCity(), is("札幌市中央区"));
        assertThat(record1.getArea(), is("以下に掲載がない場合"));

        final ZipCodeEntity record2 = recordset.get(1);
        assertThat(record2.getZipCode(), is("0600042"));
        assertThat(record2.getPrefecture(), is("北海道"));
        assertThat(record2.getCity(), is("札幌市中央区"));
        assertThat(record2.getArea(), is("大通西（１～１９丁目）"));
    }

    // テーブルが空ではない場合
    @Test
    public void deleteAll実行時にレコードを一括削除できること() {
        repository.saveAll(createTestRecords.get());
        repository.deleteAll();

        final List<ZipCodeEntity> recordset = repository.findAllByOrderById();
        assertThat(recordset.size(), is(0));
    }

}
