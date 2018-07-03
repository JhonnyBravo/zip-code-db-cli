package zip_code_db_cli;

public interface TZipCodeState {
    /**
    * 終了コードとメッセージの初期化コマンドを実装する。
    */
    public void initStatus();

    /**
    * 実行するコマンドを実装する。
    */
    public void runCommand();

    /**
    * エラーが発生した場合に実行するコマンドを実装する。
    */
    public void errorTerminate();
}
