alter table settings
  add column normal_fee int comment '月額課金費用';

alter table settings
  add column discounted_fee int comment '割引後月額課金費用';

alter table settings
  add column veritrans_group_id varchar(254) comment 'ベリトランスのグループID'

