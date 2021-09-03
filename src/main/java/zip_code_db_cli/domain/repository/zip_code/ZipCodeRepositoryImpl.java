package zip_code_db_cli.domain.repository.zip_code;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.Transaction;
import zip_code_db_cli.domain.model.ZipCodeEntity;

public class ZipCodeRepositoryImpl implements ZipCodeRepository {
  private EntityManagerFactory factory;
  private EntityManager manager;

  @PostConstruct
  private void init() {
    this.factory = Persistence.createEntityManagerFactory("zip_code_db_cli");
    this.manager = factory.createEntityManager();
  }

  @PreDestroy
  private void destroy() {
    this.manager.close();
    this.factory.close();
  }

  @Override
  public List<ZipCodeEntity> findAll() throws Exception {
    final String sql = "SELECT e FROM ZipCodeEntity e";
    final TypedQuery<ZipCodeEntity> query = this.manager.createQuery(sql, ZipCodeEntity.class);

    final List<ZipCodeEntity> recordset = query.getResultList();
    return recordset;
  }

  @Override
  public boolean create(List<ZipCodeEntity> recordset) throws Exception {
    boolean status = false;
    long count = 0;

    final String sql = "SELECT Max(e.id) FROM ZipCodeEntity e";
    final TypedQuery<Integer> query = this.manager.createQuery(sql, Integer.class);
    final Optional<Integer> maxId = Optional.ofNullable(query.getSingleResult());
    Integer id = maxId.orElse(0) + 1;

    final Session session = this.manager.unwrap(Session.class);
    final Transaction transaction = session.beginTransaction();

    try {
      for (final ZipCodeEntity zipcode : recordset) {
        zipcode.setId(id);
        session.save(zipcode);
        id++;
        count++;

        if (count % 1000 == 0) {
          session.flush();
          session.clear();
        }
      }

      transaction.commit();

      if (count > 0) {
        status = true;
      }
    } catch (final Exception e) {
      transaction.rollback();
      throw e;
    }

    return status;
  }

  @Override
  public boolean deleteAll() throws Exception {
    boolean status = false;
    final Session session = this.manager.unwrap(Session.class);

    final String sql = "DELETE FROM ZipCodeEntity";
    final Query query = session.createQuery(sql);

    final Transaction transaction = session.beginTransaction();

    try {
      final int count = query.executeUpdate();
      transaction.commit();

      if (count > 0) {
        status = true;
      }
    } catch (final Exception e) {
      transaction.rollback();
      throw e;
    }

    return status;
  }
}
