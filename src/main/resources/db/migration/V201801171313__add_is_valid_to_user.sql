ALTER TABLE users
  ADD is_valid BOOLEAN DEFAULT FALSE  NULL
COMMENT 'メールアドレス認証済み';