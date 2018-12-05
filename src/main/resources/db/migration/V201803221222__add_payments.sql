CREATE TABLE payments
(
  id         BIGINT PRIMARY KEY NOT NULL,
  created_at DATETIME DEFAULT current_timestamp,
  updated_at DATETIME DEFAULT current_timestamp,
  account_id BIGINT             NOT NULL,
  paydate    DATETIME           NOT NULL
  COMMENT '支払日',
  amount     INT COMMENT '支払金額',
  CONSTRAINT payments_accounts_id_fk FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
CREATE UNIQUE INDEX payments_id_uindex
  ON payments (id);
ALTER TABLE payments
  COMMENT = '入金情報';

ALTER TABLE shop_pay_histories
  ADD planed_amount INT NOT NULL
COMMENT '支払予定金額';
ALTER TABLE shop_pay_histories
  ADD payed BOOLEAN DEFAULT FALSE  NOT NULL;

CREATE TABLE shop_pay_history_payments (
  id                  BIGINT PRIMARY KEY  NOT NULL,
  created_at          DATETIME DEFAULT current_timestamp,
  updated_at          DATETIME DEFAULT current_timestamp,
  shop_pay_history_id BIGINT              NOT NULL,
  payment_id          BIGINT              NOT NULL,
  amount              INT                 NOT NULL
  COMMENT '金額'
);
CREATE UNIQUE INDEX shop_pay_history_payments_id_uindex
  ON shop_pay_history_payments (id);
ALTER TABLE shop_pay_history_payments
  COMMENT = '支払ログ';
