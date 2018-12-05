CREATE TABLE health_files
(
  id         bigint PRIMARY KEY NOT NULL,
  account_id bigint             NOT NULL,
  created_at datetime DEFAULT current_timestamp,
  updated_at datetime DEFAULT current_timestamp,
  file_path  varchar(254)       NOT NULL,
  file_type  varchar(254)       NOT NULL,
  file_name  varchar(254)       NOT NULL,
  file_size  int                NOT NULL,
  CONSTRAINT health_files_accounts_id_fk FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
CREATE UNIQUE INDEX health_files_id_uindex
  ON health_files (id);
ALTER TABLE health_files
  COMMENT = 'お薬のことのファイル';