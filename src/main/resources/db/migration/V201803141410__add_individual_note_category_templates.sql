CREATE TABLE individual_note_category_templates
(
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   created_at DATETIME DEFAULT current_timestamp,
   updated_at DATETIME DEFAULT current_timestamp,
   code VARCHAR(254) NOT NULL,
   name VARCHAR(254) NOT NULL,
   url VARCHAR(254) NOT NULL,
   banner_url VARCHAR(254) NOT NULL
);
ALTER TABLE individual_note_category_templates COMMENT = '私のことのカテゴリマスタ';

ALTER TABLE `individual_note_categories`
  ADD individual_note_category_template_id  BIGINT NULL;

ALTER TABLE `individual_note_categories` ADD INDEX `individual_note_category_template_id` (`individual_note_category_template_id`);

ALTER TABLE `individual_note_categories`
	ADD CONSTRAINT `individual_note_category_template_fk` FOREIGN KEY (`individual_note_category_template_id`)
	REFERENCES `individual_note_category_templates` (`id`)
	ON UPDATE CASCADE ON DELETE CASCADE;