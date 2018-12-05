ALTER TABLE accounts
  ADD COLUMN died_confirmed_admin BOOLEAN DEFAULT 0 NOT NULL COMMENT '死亡確認済み(管理者)';