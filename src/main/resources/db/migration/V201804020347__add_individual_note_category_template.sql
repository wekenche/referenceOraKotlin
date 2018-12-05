ALTER TABLE individual_note_categories
  DROP FOREIGN KEY individual_note_category_template_fk;


drop table individual_note_category_templates;

-- auto-generated definition
create table individual_note_category_templates
(
  id          bigint auto_increment
    primary key,
  created_at  datetime default CURRENT_TIMESTAMP null,
  updated_at  datetime default CURRENT_TIMESTAMP null,
  code        varchar(254)                       not null,
  name        varchar(254)                       not null,
  url         varchar(254)                       not null,
  banner_url  varchar(254)                       not null,
  parent_code varchar(254)                       null
)
  comment '私のことのカテゴリマスタ'
  engine = InnoDB
  charset = utf8;


ALTER TABLE individual_note_categories
  ADD CONSTRAINT
  template_fk
FOREIGN KEY (individual_note_category_template_id) REFERENCES individual_note_category_templates (id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;


insert into individual_note_category_templates (code, name, url, banner_url)
values ('001', 'お薬の事', '/medicine', '/images/button/btn_about_drug.png');

insert into individual_note_category_templates (code, name, url, banner_url)
values ('002', '身分証明', '/individualNote/input1?category=$category', '/images/button/btn_about_identification.png');

insert into individual_note_category_templates (code, name, url, banner_url)
values ('003', '宝物やコレクションについて', '/individualNote/input1?category=$category', '/images/button/btn_about_collection.png');

insert into individual_note_category_templates (code, name, url, banner_url)
values ('004', '家のこと', '/individualNote/input1?category=$category', '/images/button/btn_about_house.png');

insert into individual_note_category_templates (code, name, url, banner_url)
values ('005', '自分の仕事について', '/individualNote/input1?category=$category', '/images/button/btn_about_mywork.png');

insert into individual_note_category_templates (code, name, url, banner_url)
values ('006', 'リビングニーズ', '/individualNote/input1?category=$category', '/images/button/btn_about_livingneeds.png');

insert into individual_note_category_templates (code, name, url, banner_url)
values ('007', '期日にてついて', '/individualNote/input1?category=$category', '/images/button/btn_about_limit.png');
