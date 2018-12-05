ALTER TABLE bereaves
  DROP confirmed;

ALTER TABLE bereaves
  ADD died_date DATE NULL

COMMENT '入力死亡日';
ALTER TABLE accounts
  ADD died_confirmed BOOLEAN DEFAULT 0 NOT NULL
COMMENT '死亡確認済み';

