package zip_code_db_cli.app;

import java.util.ArrayList;
import java.util.List;

/**
 * CLI コマンドの使用法を表示する。
 */
public class Usage implements Runnable {
  /**
   * CLI コマンドの使用法を表示する。
   */
  @Override
  public void run() {
    final List<String> optionList = new ArrayList<>();

    optionList
        .add("--import, -i <config_path>: config_path に指定した設定ファイルの内容に従って CSV を読込み、 DB へ一括登録する。");

    optionList.forEach(option -> {
      System.out.println(option);
    });
  }

}
