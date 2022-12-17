package zip_code_db_cli.app;

import java.util.ArrayList;
import java.util.List;

/** CLI コマンドの使用法を表示する。 */
public class Usage implements Runnable {
  /** CLI コマンドの使用法を表示する。 */
  @Override
  public void run() {
    final List<String> optionList = new ArrayList<>();

    optionList.add("引数に指定したプロパティファイルの設定内容に従って CSV を読込み、 MySQL へ郵便番号データを一括登録します。");
    optionList.add("--import, -i <path>: 読込み対象とするプロパティファイルのパスを指定します。");

    optionList.forEach(
        option -> {
          System.out.println(option);
        });
  }
}
