CREATE TABLE settings
(
  id         BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  created_at DATETIME                    DEFAULT now(),
  updated_at DATETIME                    DEFAULT now(),
  email      VARCHAR(254)       NOT NULL
);
ALTER TABLE settings
  COMMENT = 'システム設定';
INSERT settings (email) VALUES ('jjaji80@gmail.com');


