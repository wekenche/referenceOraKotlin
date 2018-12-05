ALTER TABLE bereaves
  ADD user_id BIGINT NOT NULL;
ALTER TABLE bereaves
  ADD CONSTRAINT bereaves_users_id_fk
FOREIGN KEY (user_id) REFERENCES users (id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
ALTER TABLE bereaves
  DROP FOREIGN KEY bereaves_accounts_id_fk2;
ALTER TABLE bereaves
  DROP account_target_id;
