package zip_code_db_cli;

public interface ImportResource<T> {
    /**
     * 新規レコードを登録する。
     *
     * @param model 登録対象とするモデルオブジェクトを指定する。
     * @return status
     *         <ul>
     *         <li>true: 登録に成功したことを表す。</li>
     *         <li>false: 登録に失敗したことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean create(T model) throws Exception;

    /**
     * レコードを削除する。
     *
     * @return status
     *         <ul>
     *         <li>true: 削除に成功したことを表す。</li>
     *         <li>false: 削除に失敗したことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean delete() throws Exception;
}
