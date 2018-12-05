ALTER TABLE users
  ADD last_used_account_id BIGINT NULL
COMMENT '最終使用アカウント';
ALTER TABLE users
  ADD CONSTRAINT users_accounts_id_fk
FOREIGN KEY (last_used_account_id) REFERENCES accounts (id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;