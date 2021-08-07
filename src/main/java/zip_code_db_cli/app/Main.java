package zip_code_db_cli.app;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 * {@link Application} を起動する。
 */
public class Main {
  /**
   * {@link Application} を起動する。
   */
  public static void main(String[] args) {
    final Weld weld = new Weld();
    int status = 0;

    try (WeldContainer container = weld.initialize()) {
      final Application app = container.select(Application.class).get();
      status = app.main(args);
    }

    System.exit(status);
  }
}
