ALTER TABLE `messages`
	CHANGE COLUMN `message_type` `message_type` VARCHAR(100) NULL DEFAULT NULL COLLATE 'utf8_general_ci' AFTER `comment`,
	CHANGE COLUMN `title` `title` VARCHAR(254) NOT NULL COLLATE 'utf8_general_ci' AFTER `message_category_id`,
	CHANGE COLUMN `comment` `comment` TEXT NULL COLLATE 'utf8_general_ci' AFTER `title`;


