CREATE TABLE message_categories
(
  id         BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  created_at DATETIME                    DEFAULT now(),
  updated_at DATETIME                    DEFAULT now(),
  name       VARCHAR(254)       NOT NULL
);
ALTER TABLE message_categories
  COMMENT = 'メッセージカテゴリ';

CREATE TABLE messages
(
  id                  BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  created_at          DATETIME                    DEFAULT now(),
  updated_at          DATETIME                    DEFAULT now(),
  account_id          BIGINT             NOT NULL,
  message_category_id BIGINT             NOT NULL,
  title               VARCHAR(254)       NOT NULL,
  comment             TEXT,
  CONSTRAINT messages_message_categories_id_fk FOREIGN KEY (message_category_id) REFERENCES message_categories (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
ALTER TABLE messages
  COMMENT = 'メッセージ';


CREATE TABLE message_attachments
(
  id         BIGINT PRIMARY KEY NOT NULL,
  created_at DATETIME DEFAULT now(),
  updated_at DATETIME DEFAULT now(),
  message_id BIGINT             NOT NULL,
  file_type  VARCHAR(254),
  name       VARCHAR(254),
  file_path  VARCHAR(1000)      NOT NULL,
  memo       TEXT,
  CONSTRAINT message_attachments_messages_id_fk FOREIGN KEY (message_id) REFERENCES messages (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
CREATE UNIQUE INDEX message_attachments_id_uindex
  ON message_attachments (id);
ALTER TABLE message_attachments
  COMMENT = 'メッセージの添付ファイル';