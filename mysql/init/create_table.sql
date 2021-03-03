use zip_code_db;

CREATE TABLE IF NOT EXISTS t_zip_code (
    jis_code VARCHAR(5),
    zip_code VARCHAR(7),
    prefecture_phonetic VARCHAR(100),
    city_phonetic VARCHAR(100),
    area_phonetic VARCHAR(100),
    prefecture VARCHAR(10),
    city VARCHAR(100),
    area VARCHAR(100),
    update_flag INT(1),
    reason_flag INT(1),
    id INT AUTO_INCREMENT,
    PRIMARY KEY (id)
);
