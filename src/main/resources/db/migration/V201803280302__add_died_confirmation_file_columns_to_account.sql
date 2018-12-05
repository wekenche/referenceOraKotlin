ALTER TABLE `accounts`
  ADD COLUMN died_confirmation_file_path1  VARCHAR(512) NULL;

ALTER TABLE `accounts`
  ADD COLUMN died_confirmation_file_path2  VARCHAR(512) NULL;

ALTER TABLE `accounts`
  ADD COLUMN died_confirmation_file_size1  BIGINT NULL;

ALTER TABLE `accounts`
  ADD COLUMN died_confirmation_file_size2  BIGINT NULL;