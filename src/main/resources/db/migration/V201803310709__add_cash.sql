CREATE TABLE cashes
(
  id               bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  account_id       bigint             NOT NULL,
  created_at       datetime                    DEFAULT current_timestamp,
  updated_at       datetime                    DEFAULT current_timestamp,
  amount           int COMMENT '金額',
  storage_location text COMMENT '保管場所',
  message          text COMMENT '備考',
  CONSTRAINT cashes_accounts_id_fk FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
ALTER TABLE cashes
  COMMENT = '現金(預貯金以外)';