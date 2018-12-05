CREATE TABLE accounts
(
  id                    BIGINT AUTO_INCREMENT              NOT NULL
    PRIMARY KEY,
  user_id               BIGINT                             NOT NULL,
  created_at            DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at            DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  is_premium            TINYINT(1) DEFAULT '0'             NOT NULL
  COMMENT '有料会員',
  premium_pay_last_date DATE                               NULL
  COMMENT '有料会員の最終支払日'
)
  COMMENT 'アカウント管理 ユーザーは複数のアカウントを持つ'
  CHARSET utf8
  ENGINE = InnoDB;

CREATE INDEX accounts_users_id_fk
  ON accounts (user_id);

CREATE TABLE bereaves
(
  id                BIGINT AUTO_INCREMENT              NOT NULL
    PRIMARY KEY,
  account_id        BIGINT                             NULL
  COMMENT 'ユーザーのID',
  account_target_id BIGINT                             NULL
  COMMENT 'ユーザーのID',
  created_at        DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  confirmed         TINYINT(1) DEFAULT '0'             NULL
  COMMENT 'アカウントに対する死亡確認をしたかどうか',
  CONSTRAINT bereaves_accounts_id_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT bereaves_accounts_id_fk2
  FOREIGN KEY (account_target_id) REFERENCES accounts (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  COMMENT 'アカウントに結びつく情報を共有するテーブル'
  CHARSET utf8
  ENGINE = InnoDB;

CREATE INDEX bereaves_accounts_id_fk
  ON bereaves (account_id);

CREATE INDEX bereaves_accounts_id_fk2
  ON bereaves (account_target_id);

CREATE TABLE categories
(
  id         BIGINT AUTO_INCREMENT              NOT NULL
    PRIMARY KEY,
  name       VARCHAR(254)                       NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL
)
  COMMENT 'カテゴリー'
  CHARSET utf8
  ENGINE = InnoDB;

CREATE TABLE columns
(
  id          BIGINT AUTO_INCREMENT              NOT NULL
    PRIMARY KEY,
  account_id  BIGINT                             NULL
  COMMENT '投稿者のユーザーID',
  title       VARCHAR(254)                       NULL
  COMMENT '投稿タイトル',
  category_id BIGINT                             NULL
  COMMENT 'カテゴリーID',
  contents    TEXT                               NULL
  COMMENT '内容',
  created_at  DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  CONSTRAINT columns_accounts_id_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT columns_categories_id_fk
  FOREIGN KEY (category_id) REFERENCES categories (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  COMMENT 'コラム'
  CHARSET utf8
  ENGINE = InnoDB;

CREATE INDEX columns_accounts_id_fk
  ON columns (account_id);

CREATE INDEX columns_categories_id_fk
  ON columns (category_id);

CREATE TABLE forum_comments
(
  id         BIGINT AUTO_INCREMENT              NOT NULL
    PRIMARY KEY,
  account_id BIGINT                             NOT NULL
  COMMENT 'コメント者のユーザーID',
  contents   TEXT                               NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  forum_id   BIGINT                             NOT NULL,
  CONSTRAINT forum_comments_accounts_id_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  COMMENT '掲示板のコメント'
  CHARSET utf8
  ENGINE = InnoDB;

CREATE INDEX forum_comments_accounts_id_fk
  ON forum_comments (account_id);

CREATE INDEX forum_comments_forums_id_fk
  ON forum_comments (forum_id);

CREATE TABLE forums
(
  id          BIGINT AUTO_INCREMENT              NOT NULL
    PRIMARY KEY,
  account_id  BIGINT                             NOT NULL
  COMMENT '投稿者のユーザーID',
  title       VARCHAR(254)                       NULL
  COMMENT '投稿タイトル',
  category_id BIGINT                             NULL,
  contents    TEXT                               NULL,
  created_at  DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  CONSTRAINT forums_accounts_id_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT forums_categories_id_fk
  FOREIGN KEY (category_id) REFERENCES categories (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  COMMENT '掲示板'
  CHARSET utf8
  ENGINE = InnoDB;

CREATE INDEX forums_accounts_id_fk
  ON forums (account_id);

CREATE INDEX forums_categories_id_fk
  ON forums (category_id);

ALTER TABLE forum_comments
  ADD CONSTRAINT forum_comments_forums_id_fk
FOREIGN KEY (forum_id) REFERENCES forums (id)
  ON UPDATE CASCADE
  ON DELETE CASCADE;

CREATE TABLE individual_note_attachments
(
  id                 BIGINT AUTO_INCREMENT              NOT NULL
    PRIMARY KEY,
  created_at         DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at         DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  individual_note_id BIGINT                             NOT NULL,
  name               VARCHAR(254)                       NOT NULL
  COMMENT 'ファイル名',
  file_type          VARCHAR(20)                        NOT NULL,
  file_path          VARCHAR(512)                       NOT NULL,
  memo               TEXT                               NULL
)
  COMMENT '個人ノートに対する添付ファイル'
  CHARSET utf8
  ENGINE = InnoDB;

CREATE INDEX individual_note_attachments_individual_notes_id_fk
  ON individual_note_attachments (individual_note_id);

CREATE TABLE individual_note_bereaves
(
  id                          BIGINT AUTO_INCREMENT              NOT NULL
    PRIMARY KEY,
  created_at                  DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at                  DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  individual_note_category_id BIGINT                             NOT NULL,
  status                      VARCHAR(20)                        NOT NULL
  COMMENT '承認などのプロセスステータス',
  account_id                  BIGINT                             NOT NULL,
  CONSTRAINT individual_note_bereaves_accounts_id_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  COMMENT '個人ノートの共有設定'
  CHARSET utf8
  ENGINE = InnoDB;

CREATE INDEX individual_note_bereaves_individual_note_categories_id_fk
  ON individual_note_bereaves (individual_note_category_id);

CREATE INDEX individual_note_bereaves_accounts_id_fk
  ON individual_note_bereaves (account_id);

CREATE TABLE individual_note_categories
(
  id         BIGINT AUTO_INCREMENT              NOT NULL
    PRIMARY KEY,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  name       VARCHAR(254)                       NULL,
  account_id BIGINT                             NOT NULL,
  CONSTRAINT individual_note_categories_accounts_id_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  COMMENT '個人ノートのカテゴリ'
  CHARSET utf8
  ENGINE = InnoDB;

CREATE INDEX individual_note_categories_accounts_id_fk
  ON individual_note_categories (account_id);

ALTER TABLE individual_note_bereaves
  ADD CONSTRAINT individual_note_bereaves_individual_note_categories_id_fk
FOREIGN KEY (individual_note_category_id) REFERENCES individual_note_categories (id)
  ON UPDATE CASCADE
  ON DELETE CASCADE;

CREATE TABLE individual_notes
(
  id                          BIGINT AUTO_INCREMENT              NOT NULL
    PRIMARY KEY,
  created_at                  DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at                  DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  title                       VARCHAR(254)                       NULL,
  contents                    TEXT                               NULL,
  individual_note_category_id BIGINT                             NULL,
  account_id                  BIGINT                             NOT NULL,
  CONSTRAINT individual_notes_individual_note_categories_id_fk
  FOREIGN KEY (individual_note_category_id) REFERENCES individual_note_categories (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT individual_notes_accounts_id_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  COMMENT '個人ノート 私のこと'
  CHARSET utf8
  ENGINE = InnoDB;

CREATE INDEX individual_notes_individual_note_categories_id_fk
  ON individual_notes (individual_note_category_id);

CREATE INDEX individual_notes_accounts_id_fk
  ON individual_notes (account_id);

ALTER TABLE individual_note_attachments
  ADD CONSTRAINT individual_note_attachments_individual_notes_id_fk
FOREIGN KEY (individual_note_id) REFERENCES individual_notes (id)
  ON UPDATE CASCADE
  ON DELETE CASCADE;

CREATE TABLE note_attachments
(
  id         BIGINT AUTO_INCREMENT              NOT NULL
    PRIMARY KEY,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  note_id    BIGINT                             NOT NULL,
  file_type  VARCHAR(20)                        NOT NULL,
  file_path  VARCHAR(512)                       NOT NULL,
  name       VARCHAR(254)                       NOT NULL
  COMMENT 'ファイル名',
  memo       TEXT                               NULL
)
  COMMENT 'ノートに対する添付ファイル'
  CHARSET utf8
  ENGINE = InnoDB;

CREATE INDEX note_attachments_notes_id_fk
  ON note_attachments (note_id);

CREATE TABLE note_categories
(
  id         BIGINT AUTO_INCREMENT              NOT NULL
    PRIMARY KEY,
  name       VARCHAR(254)                       NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL
)
  COMMENT 'エンディングノートカテゴリ'
  CHARSET utf8
  ENGINE = InnoDB;

CREATE TABLE notes
(
  id               BIGINT AUTO_INCREMENT              NOT NULL
    PRIMARY KEY,
  account_id       BIGINT                             NOT NULL,
  title            VARCHAR(254)                       NOT NULL,
  contents         TEXT                               NOT NULL,
  note_category_id BIGINT                             NOT NULL,
  created_at       DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at       DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  CONSTRAINT notes_accounts_id_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT notes_note_categories_id_fk
  FOREIGN KEY (note_category_id) REFERENCES note_categories (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  COMMENT 'エンディングノート'
  CHARSET utf8
  ENGINE = InnoDB;

CREATE INDEX notes_accounts_id_fk
  ON notes (account_id);

CREATE INDEX notes_note_categories_id_fk
  ON notes (note_category_id);

ALTER TABLE note_attachments
  ADD CONSTRAINT note_attachments_notes_id_fk
FOREIGN KEY (note_id) REFERENCES notes (id)
  ON UPDATE CASCADE
  ON DELETE CASCADE;

CREATE TABLE prefectures
(
  id         BIGINT AUTO_INCREMENT              NOT NULL
    PRIMARY KEY,
  name       VARCHAR(10)                        NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL
)
  COMMENT '都道府県名'
  CHARSET utf8
  ENGINE = InnoDB;

CREATE TABLE shop_pay_histories
(
  id         BIGINT AUTO_INCREMENT              NOT NULL
    PRIMARY KEY,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  shop_id    BIGINT                             NOT NULL,
  amount     INT DEFAULT '0'                    NOT NULL
  COMMENT '支払額',
  pay_date   DATE                               NOT NULL
  COMMENT '支払日'
)
  COMMENT '代理店への支払履歴'
  CHARSET utf8
  ENGINE = InnoDB;

CREATE INDEX shop_pay_histories_shops_id_fk
  ON shop_pay_histories (shop_id);

CREATE TABLE shops
(
  id         BIGINT AUTO_INCREMENT              NOT NULL
    PRIMARY KEY,
  name       VARCHAR(254)                       NOT NULL,
  code       VARCHAR(254)                       NULL
  COMMENT '紹介コード',
  parent_id  BIGINT                             NULL
  COMMENT '親代理店',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  CONSTRAINT shops_shops_id_fk
  FOREIGN KEY (parent_id) REFERENCES shops (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  COMMENT '代理店'
  CHARSET utf8
  ENGINE = InnoDB;

CREATE INDEX shops_shops_id_fk
  ON shops (parent_id);

ALTER TABLE shop_pay_histories
  ADD CONSTRAINT shop_pay_histories_shops_id_fk
FOREIGN KEY (shop_id) REFERENCES shops (id)
  ON UPDATE CASCADE
  ON DELETE CASCADE;

CREATE TABLE users
(
  id                    BIGINT AUTO_INCREMENT              NOT NULL
    PRIMARY KEY,
  user_type             VARCHAR(10) DEFAULT 'USER'         NOT NULL
  COMMENT 'USER:一般ユーザー ADMIN :管理者',
  name                  VARCHAR(50)                        NULL
  COMMENT '表示する名前(ニックネーム)',
  email                 VARCHAR(254)                       NULL,
  encrypted_password    VARCHAR(1000)                      NULL
  COMMENT '暗号化されたパスワード',
  reset_password_token  VARCHAR(1000)                      NULL
  COMMENT 'パスワードリセットの際に使用するトークン',
  passed_date           DATE                               NULL
  COMMENT '死亡日付',
  confirming_date       DATE                               NULL
  COMMENT '死亡確認中の日付',
  push_id               VARCHAR(254)                       NULL
  COMMENT 'ユーザーID',
  os                    VARCHAR(10)                        NULL,
  shop_id               BIGINT                             NULL
  COMMENT '紹介コードを入力した代理店テーブル',
  created_at            DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at            DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  version_no            INT DEFAULT '0'                    NULL,
  authentication_method VARCHAR(20) DEFAULT 'PASSWORD'     NOT NULL
  COMMENT '認証方法',
  CONSTRAINT id
  UNIQUE (id),
  CONSTRAINT users_shops_id_fk
  FOREIGN KEY (shop_id) REFERENCES shops (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  COMMENT 'ユーザー'
  CHARSET utf8
  ENGINE = InnoDB;

CREATE INDEX users_shops_id_fk
  ON users (shop_id);

ALTER TABLE accounts
  ADD CONSTRAINT accounts_users_id_fk
FOREIGN KEY (user_id) REFERENCES users (id)
  ON UPDATE CASCADE
  ON DELETE CASCADE;

