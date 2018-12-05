CREATE TABLE device_passwords (
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id BIGINT                             NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  name       VARCHAR(254)                       NOT NULL,
  password   VARCHAR(254)                       NOT NULL
  COMMENT '暗号化して保存したパスワード(複合可能)',
  CONSTRAINT to_device_passwords_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '端末パスワード'
  CHARSET = utf8;

CREATE INDEX to_device_passwords_account_fk
  ON device_passwords (account_id);


CREATE TABLE phone_company_passwords (
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id BIGINT                             NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  password   VARCHAR(254)                       NOT NULL
  COMMENT '暗号化して保存したパスワード(複合可能)',
  CONSTRAINT to_phone_company_passwords_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '携帯電話会社のパスワード one to one'
  CHARSET = utf8;

CREATE INDEX to_phone_company_passwords_account_fk
  ON phone_company_passwords (account_id);

CREATE TABLE emails (
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id BIGINT                             NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  email      VARCHAR(254)                       NOT NULL,
  password   VARCHAR(254)                       NOT NULL
  COMMENT '暗号化して保存したパスワード(複合可能)',
  CONSTRAINT to_emails_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT 'メールアドレス'
  CHARSET = utf8;

CREATE INDEX to_emails_account_fk
  ON emails (account_id);

CREATE TABLE webs (
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id BIGINT                             NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  name       VARCHAR(254)                       NOT NULL,
  web_id     VARCHAR(254)                       NULL,
  password   VARCHAR(254)                       NOT NULL
  COMMENT '暗号化して保存したパスワード(複合可能)',
  memo       TEXT                               NULL,
  CONSTRAINT to_webs_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '利用ウェブサイト'
  CHARSET = utf8;

CREATE INDEX to_webs_account_fk
  ON webs (account_id);

CREATE TABLE healths (
  id                                 BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id                         BIGINT                             NOT NULL,
  created_at                         DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at                         DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  allergy                            TEXT                               NULL
  COMMENT 'アレルギー',
  blood_type                         VARCHAR(10)                        NULL
  COMMENT '血液型',
  medical_history                    TEXT                               NULL
  COMMENT '病歴・持病',
  allergic_exists_medicine           TEXT                               NULL
  COMMENT 'アレルギーのある薬',
  regular_hospital                   TEXT                               NULL
  COMMENT 'かかりつけの病院',
  want_significant_sick_announcement BOOLEAN                            NULL
  COMMENT '重大な病気の告知をして欲しいか否か',
  want_resuscitate                   BOOLEAN                            NULL
  COMMENT '延命治療を希望するかどうか',
  want_organ_donation                BOOLEAN                            NULL
  COMMENT '臓器提供を希望するか',
  has_donor_card                     BOOLEAN                            NULL
  COMMENT 'ドナーカードの有無',
  donor_card_location                VARCHAR(254) COMMENT 'ドナーカードの場所',
  CONSTRAINT to_healths_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '健康に関して one to one'
  CHARSET = utf8;

CREATE INDEX to_healths_account_fk
  ON healths (account_id);

CREATE TABLE medicines (
  id                BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id        BIGINT                             NOT NULL,
  created_at        DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  name              VARCHAR(254)                       NULL,
  prescription_date DATE                               NULL
  COMMENT '処方した日',
  hospital          VARCHAR(254)                       NULL
  COMMENT '処方病院',
  doctor            VARCHAR(254)                       NULL
  COMMENT '医師名',
  medicine_amount   VARCHAR(254)                       NULL
  COMMENT '薬の量',
  drink_how_to      TEXT                               NULL
  COMMENT '薬の飲み方と回数',
  prescription_days VARCHAR(254)                       NULL
  COMMENT '処方日数',
  CONSTRAINT to_medicines_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT 'お薬手帳'
  CHARSET = utf8;

CREATE INDEX to_medicines_account_fk
  ON medicines (account_id);

CREATE TABLE addresses (
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id BIGINT                             NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  name       VARCHAR(254)                       NULL,
  post_code1 VARCHAR(10)                        NULL,
  post_code2 VARCHAR(10)                        NULL,
  CONSTRAINT to_addresses_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '過去に住んだことのある住所'
  CHARSET = utf8;

CREATE INDEX to_addresses_account_fk
  ON addresses (account_id);

CREATE TABLE real_estate (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id    BIGINT                             NOT NULL,
  created_at    DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at    DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  address       VARCHAR(254)                       NULL,
  parcel_number VARCHAR(254)                       NULL
  COMMENT '地番/家屋番号',
  land_category VARCHAR(254)                       NULL
  COMMENT '地目/種類・製造',
  equity        VARCHAR(254)                       NULL
  COMMENT '持ち分',
  memo          TEXT                               NULL
  COMMENT '使用管理状況など',
  CONSTRAINT to_real_estate_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '過去に住んだことのある住所'
  CHARSET = utf8;

CREATE INDEX to_real_estate_account_fk
  ON real_estate (account_id);

CREATE TABLE banks (
  id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id          BIGINT                             NOT NULL,
  created_at          DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at          DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  name                VARCHAR(254)                       NULL
  COMMENT '金融機関名',
  branch_name         VARCHAR(254)                       NULL
  COMMENT '支店名',
  deposit_type        VARCHAR(254)                       NULL
  COMMENT '預貯金の種類',
  bank_account_number VARCHAR(254)                       NULL
  COMMENT '口座番号/記号番号',
  holder              VARCHAR(254)                       NULL
  COMMENT '名義人',
  web_id              VARCHAR(254)                       NULL
  COMMENT 'WEB用ID',
  bank_usage          TEXT                               NULL
  COMMENT '口座の使用用途',
  CONSTRAINT to_banks_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '預貯金'
  CHARSET = utf8;

CREATE INDEX to_banks_account_fk
  ON banks (account_id);

CREATE TABLE automatic_withdrawals (
  id              BIGINT AUTO_INCREMENT PRIMARY KEY,
  bank_id         BIGINT                             NOT NULL,
  created_at      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  expense_item    VARCHAR(254)                       NULL
  COMMENT '費目',
  withdrawal_date VARCHAR(254)                       NULL
  COMMENT '引き落とし日',
  memo            TEXT                               NULL
  COMMENT '備考',
  CONSTRAINT to_bank_fk
  FOREIGN KEY (bank_id) REFERENCES banks (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '自動引き落とし'
  CHARSET = utf8;

CREATE INDEX to_bank_fk
  ON automatic_withdrawals (bank_id);

CREATE TABLE certificate_accounts (
  id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id          BIGINT                             NOT NULL,
  created_at          DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at          DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  name                VARCHAR(254)                       NULL
  COMMENT '証券会社名',
  bank_account_number VARCHAR(254)                       NULL
  COMMENT '口座番号',
  holder              VARCHAR(254)                       NULL
  COMMENT '名義人',
  web_id              VARCHAR(254)                       NULL
  COMMENT 'ウェブID',
  contact_address     VARCHAR(254)                       NULL
  COMMENT '連絡先',
  memo                TEXT                               NULL
  COMMENT '備考',
  CONSTRAINT to_certificate_accounts_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '証券口座'
  CHARSET = utf8;

CREATE INDEX to_certificate_accounts_account_fk
  ON certificate_accounts (account_id);

CREATE TABLE stocks (
  id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id            BIGINT                             NOT NULL,
  created_at            DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at            DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  certificate_company   VARCHAR(254)                       NULL
  COMMENT '証券会社',
  brand                 VARCHAR(254)                       NULL
  COMMENT '銘柄',
  certificate_no        VARCHAR(254)                       NULL
  COMMENT '証券番号等',
  quantity              VARCHAR(254)                       NULL
  COMMENT '数量',
  is_custody_management BOOLEAN                            NULL
  COMMENT '管理,保護預かりの有無',
  memo                  TEXT                               NULL
  COMMENT '備考',
  CONSTRAINT to_stocks_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '株式'
  CHARSET = utf8;

CREATE INDEX to_stocks_account_fk
  ON stocks (account_id);

CREATE TABLE financial_assets (
  id              BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id      BIGINT                             NOT NULL,
  created_at      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  name            VARCHAR(254)                       NULL
  COMMENT '名称・銘柄・内容',
  holder          VARCHAR(254)                       NULL
  COMMENT '名義人',
  company         VARCHAR(254)                       NULL
  COMMENT '証券会社・金融機関・取扱会社',
  symbol_no       VARCHAR(254)                       NULL
  COMMENT '記号番号など',
  contact_address VARCHAR(254)                       NULL
  COMMENT '連絡先',
  memo            TEXT                               NULL
  COMMENT '使用・管理状況等',
  CONSTRAINT to_financial_assets_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT 'その他金融資産'
  CHARSET = utf8;

CREATE INDEX to_financial_assets_account_fk
  ON financial_assets (account_id);

CREATE TABLE deposits (
  id             BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id     BIGINT                             NOT NULL,
  created_at     DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at     DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  category       VARCHAR(254)                       NULL
  COMMENT '種類・保険会社',
  symbol_no      VARCHAR(254)                       NULL
  COMMENT '保険証書記号番号等',
  certificate_no VARCHAR(254)                       NULL
  COMMENT '証券番号等',
  memo           TEXT                               NULL
  COMMENT '使用・管理状況等',
  CONSTRAINT to_deposits_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '預金'
  CHARSET = utf8;

CREATE INDEX to_deposits_account_fk
  ON deposits (account_id);

CREATE TABLE stores (
  id               BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id       BIGINT                             NOT NULL,
  created_at       DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at       DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  contract_company VARCHAR(254)                       NULL
  COMMENT '契約会社',
  contact_address  VARCHAR(254)                       NULL
  COMMENT '連絡先',
  post_code1       VARCHAR(10)                        NULL,
  post_code2       VARCHAR(10)                        NULL,
  address          VARCHAR(254)                       NULL,
  memo             TEXT                               NULL
  COMMENT '保管しているもの',
  CONSTRAINT to_stores_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '貸金庫/レンタル倉庫/トランクルームなど'
  CHARSET = utf8;

CREATE INDEX to_stores_account_fk
  ON stores (account_id);

CREATE TABLE lend_moneys (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id    BIGINT                             NOT NULL,
  created_at    DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at    DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  name          VARCHAR(254)                       NULL
  COMMENT '貸した相手',
  tel           VARCHAR(254)                       NULL,
  post_code1    VARCHAR(10)                        NULL,
  post_code2    VARCHAR(10)                        NULL,
  address       VARCHAR(254)                       NULL
  COMMENT '貸した相手の住所',
  lend_date     DATE                               NULL
  COMMENT '貸した日',
  amount        VARCHAR(254)                       NULL
  COMMENT '金額',
  exists_deed   BOOLEAN                            NULL
  COMMENT '証書の有無',
  storing_place VARCHAR(254)                       NULL
  COMMENT '保管場所',
  balance       VARCHAR(254)                       NULL
  COMMENT '残高',
  memmo         TEXT                               NULL,
  CONSTRAINT to_lend_moneys_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '貸しているお金'
  CHARSET = utf8;

CREATE INDEX to_lend_moneys_account_fk
  ON lend_moneys (account_id);

CREATE TABLE loan (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id    BIGINT                             NOT NULL,
  created_at    DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at    DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  loan_target   VARCHAR(254)                       NULL
  COMMENT '借入先',
  tel           VARCHAR(254)                       NULL,
  post_code1    VARCHAR(10)                        NULL,
  post_code2    VARCHAR(10)                        NULL,
  address       VARCHAR(254)                       NULL,
  loan_date     DATE                               NULL,
  loan_amount   VARCHAR(254)                       NULL
  COMMENT '借入額',
  refund_method VARCHAR(254)                       NULL
  COMMENT '返済方法',
  loan_balance  VARCHAR(254)                       NULL
  COMMENT '借入残高',
  loan_purpose  TEXT                               NULL
  COMMENT '借入目的',
  CONSTRAINT to_loan_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '資産について'
  CHARSET = utf8;

CREATE INDEX to_lend_fk
  ON loan (account_id);

CREATE TABLE collateralise (
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  loan_id    BIGINT                             NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  name       VARCHAR(254)                       NOT NULL,
  CONSTRAINT to_collateralise_loan_fk
  FOREIGN KEY (loan_id) REFERENCES loan (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '借金・ローン'
  CHARSET = utf8;

CREATE INDEX to_collateralise_loan_fk
  ON collateralise (loan_id);

CREATE TABLE assurance_debts (
  id                       BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id               BIGINT                             NOT NULL,
  created_at               DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at               DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  assurance_date           DATE                               NULL
  COMMENT '保証した日',
  assurance_price          VARCHAR(254)                       NULL
  COMMENT '保証した金額',
  assurance_target         VARCHAR(254)                       NULL
  COMMENT '主債務者',
  creditor                 VARCHAR(254)                       NULL
  COMMENT '債権者',
  creditor_contact_address VARCHAR(254)                       NULL
  COMMENT '債権者の連絡先',
  CONSTRAINT to_assurance_debts_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE

)
  COMMENT '保証債務'
  CHARSET = utf8;

CREATE INDEX to_assurance_debts_account_fk
  ON assurance_debts (account_id);


CREATE TABLE credit_cards (
  id              BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id      BIGINT                             NOT NULL,
  created_at      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  name            VARCHAR(254)                       NULL,
  brand           VARCHAR(254)                       NULL
  COMMENT 'クレジットカードのブランド',
  card_no         VARCHAR(254)                       NULL,
  contact_address VARCHAR(254)                       NULL
  COMMENT '紛失時の連絡先',
  web_id          VARCHAR(254)                       NULL,
  memo            TEXT                               NULL,
  CONSTRAINT to_credit_cards_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT 'クレジットカード'
  CHARSET = utf8;

CREATE INDEX to_credit_cards_account_fk
  ON credit_cards (account_id);

CREATE TABLE electronic_moneys (
  id              BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id      BIGINT                             NOT NULL,
  created_at      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  name            VARCHAR(254)                       NULL,
  no              VARCHAR(254)                       NULL,
  contact_address VARCHAR(254)                       NULL,
  CONSTRAINT to_electronic_moneys_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '電子マネー'
  CHARSET = utf8;

CREATE INDEX to_electronic_moneys_account_fk
  ON electronic_moneys (account_id);

CREATE TABLE pensions (
  id              BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id      BIGINT                             NOT NULL,
  created_at      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  pension_no      VARCHAR(254)                       NULL
  COMMENT '公的年金の基礎年金番号',
  pension_type    VARCHAR(254)                       NULL
  COMMENT '加入したことのある年金の種類',
  private_pension VARCHAR(254)                       NULL
  COMMENT '私的年金の名称',
  contact_address VARCHAR(254)                       NULL,
  memo            TEXT                               NULL,
  CONSTRAINT to_pensions_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '年金'
  CHARSET = utf8;

CREATE INDEX to_pensions_account_fk
  ON pensions (account_id);

CREATE TABLE crypto_currencies (
  id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id           BIGINT                             NOT NULL,
  created_at           DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at           DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  market               VARCHAR(254)                       NULL
  COMMENT '取引所・ウォレット',
  crypto_currency_type VARCHAR(254)                       NULL
  COMMENT '保有仮想通貨の種類',
  amount               VARCHAR(254)                       NULL
  COMMENT '仮想通貨の額',
  crypto_currency_id   VARCHAR(254)                       NULL,
  password             VARCHAR(254)                       NULL
  COMMENT '暗号化されたパスワード',
  memo                 TEXT                               NULL,
  CONSTRAINT to_crypto_currencies_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '仮想通貨について'
  CHARSET = utf8;

CREATE INDEX to_crypto_currencies_account_fk
  ON crypto_currencies (account_id);

CREATE TABLE treasures (
  id                BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id        BIGINT                             NOT NULL,
  created_at        DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  name              VARCHAR(254)                       NULL
  COMMENT '種類名称',
  storing_place     VARCHAR(254)                       NULL
  COMMENT '保管場所',
  processing_method VARCHAR(254)                       NULL
  COMMENT '自分の死後の処分の方法',
  memo              TEXT                               NULL,
  CONSTRAINT to_treasures_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '宝物やコレクションについて'
  CHARSET = utf8;

CREATE INDEX to_treasures_account_fk
  ON treasures (account_id);

CREATE TABLE funerals (
  id              BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id      BIGINT                             NOT NULL,
  created_at      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  is_reservation  BOOLEAN                            NULL
  COMMENT '準備の有無',
  funeral_fee     VARCHAR(254)                       NULL
  COMMENT '葬儀代',
  company         VARCHAR(254)                       NULL
  COMMENT '予約してある葬儀会社の名称',
  contact_address VARCHAR(254)                       NULL,
  religion_type   VARCHAR(254)                       NULL
  COMMENT '宗派',
  memo            TEXT                               NULL,
  CONSTRAINT to_funerals_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '葬儀について one to one'
  CHARSET = utf8;

CREATE INDEX to_funerals_account_fk
  ON funerals (account_id);

CREATE TABLE funeral_lists (
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id BIGINT                             NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  name       VARCHAR(254)                       NULL,
  post_code1 VARCHAR(10)                        NULL,
  post_code2 VARCHAR(10)                        NULL,
  address    VARCHAR(254)                       NULL,
  tel        VARCHAR(254)                       NULL,
  CONSTRAINT to_funeral_lists_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '葬儀に呼ぶ人リスト'
  CHARSET = utf8;

CREATE INDEX to_funeral_lists_account_fk
  ON funeral_lists (account_id);

CREATE TABLE graves (
  id                      BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id              BIGINT                             NOT NULL,
  created_at              DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at              DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  is_prepared             BOOLEAN                            NULL
  COMMENT '準備の有無',
  person_want_inheritance VARCHAR(254)                       NULL
  COMMENT '墓を継承して欲しい人',
  name                    VARCHAR(254)                       NULL,
  post_code1              VARCHAR(10)                        NULL,
  post_code2              VARCHAR(10)                        NULL,
  address                 VARCHAR(254)                       NULL,
  contact_address         VARCHAR(254)                       NULL,
  grave_user              VARCHAR(254)                       NULL
  COMMENT '墓地使用者',
  purchase_store_name     VARCHAR(254)                       NULL
  COMMENT '購入店名',
  contract_date           DATE                               NULL
  COMMENT '契約日',
  religion_type           VARCHAR(254)                       NULL
  COMMENT '宗派',
  management_status       VARCHAR(254)                       NULL
  COMMENT '管理状況',
  memo                    TEXT                               NULL,
  cineration_method       VARCHAR(254)                       NULL
  COMMENT '納骨方法の要望',
  CONSTRAINT to_graves_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT 'お墓について one to one'
  CHARSET = utf8;

CREATE INDEX to_graves_account_fk
  ON graves (account_id);


CREATE TABLE testaments (
  id               BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id       BIGINT                             NOT NULL,
  created_at       DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at       DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  exists_testament BOOLEAN                            NULL
  COMMENT '遺言書の有無',
  testament_type   VARCHAR(254)                       NULL
  COMMENT '遺言書の種類',
  storing_place    VARCHAR(254)                       NULL
  COMMENT '保管場所',
  CONSTRAINT to_testaments_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '遺言書について one to one'
  CHARSET = utf8;

CREATE INDEX to_testaments_account_fk
  ON testaments (account_id);

CREATE TABLE specialist_for_testaments (
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id BIGINT                             NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  name       VARCHAR(254)                       NULL,
  name2      VARCHAR(254)                       NULL
  COMMENT '名称',
  post_code1 VARCHAR(10)                        NULL,
  post_code2 VARCHAR(10)                        NULL,
  address    VARCHAR(254)                       NULL,
  tel        VARCHAR(254)                       NULL,
  CONSTRAINT to_specialist_for_testaments_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT '遺言書について 相談・依頼した専門家について'
  CHARSET = utf8;

CREATE INDEX to_specialist_for_testaments_account_fk
  ON specialist_for_testaments (account_id);


CREATE TABLE pets (
  id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id           BIGINT                             NOT NULL,
  created_at           DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at           DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  name                 VARCHAR(254)                       NULL,
  gender               BOOLEAN                            NULL
  COMMENT '性別(true=オス,false=メス)',
  feed                 VARCHAR(254)                       NULL
  COMMENT 'エサ',
  exists_pedigree_form BOOLEAN                            NULL
  COMMENT '血統書の有無',
  pedigree_form_no     VARCHAR(254)                       NULL
  COMMENT '登録番号',
  CONSTRAINT to_pets_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT 'ペットの基本情報'
  CHARSET = utf8;

CREATE INDEX to_pets_account_fk
  ON pets (account_id);

CREATE TABLE hospital_for_pets (
  id              BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id      BIGINT                             NOT NULL,
  created_at      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  disease_name    VARCHAR(254)                       NULL
  COMMENT '病名',
  hospital        VARCHAR(254)                       NULL
  COMMENT 'かかりつけの動物病院',
  contact_address VARCHAR(254)                       NULL,
  CONSTRAINT to_hospital_for_pets_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT 'ペットの病気/病院'
  CHARSET = utf8;

CREATE INDEX to_hospital_for_pets_account_fk
  ON hospital_for_pets (account_id);

CREATE TABLE insurance_for_pets (
  id             BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id     BIGINT                             NOT NULL,
  created_at     DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at     DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  company        VARCHAR(254)                       NULL
  COMMENT '保険会社名',
  tel            VARCHAR(254)                       NULL,
  billing_method VARCHAR(254)                       NULL
  COMMENT '請求方法',
  memo           TEXT                               NULL
  COMMENT '保険の内容'
  ,
  CONSTRAINT to_insurance_for_pets_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  COMMENT 'ペット保険'
  CHARSET = utf8;

CREATE INDEX to_insurance_for_pets_account_fk
  ON insurance_for_pets (account_id);

CREATE TABLE salon_for_pets (
  id              BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id      BIGINT                             NOT NULL,
  created_at      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  updated_at      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  name            VARCHAR(254)                       NULL,
  contact_address VARCHAR(254)                       NULL,

  CONSTRAINT to_salon_for_pets_account_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)

  COMMENT 'ペット行きつけのサロン・トリミング・しつけ教室など'
  CHARSET = utf8;

CREATE INDEX to_salon_for_pets_account_fk
  ON salon_for_pets (account_id);
