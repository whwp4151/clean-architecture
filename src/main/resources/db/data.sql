insert into account (name) values ('홍길동1');
insert into account (name) values ('홍길동2');

insert into activity (timestamp, owner_account_id, source_account_id, target_account_id, amount)
values ('2018-08-08 08:00:00.0', 1, 1, 2, 500);

insert into activity (timestamp, owner_account_id, source_account_id, target_account_id, amount)
values ('2018-08-08 08:00:00.0', 2, 1, 2, 500);

insert into activity (timestamp, owner_account_id, source_account_id, target_account_id, amount)
values ('2018-08-09 10:00:00.0', 1, 2, 1, 1000);

insert into activity (timestamp, owner_account_id, source_account_id, target_account_id, amount)
values ('2018-08-09 10:00:00.0', 2, 2, 1, 1000);

insert into activity (timestamp, owner_account_id, source_account_id, target_account_id, amount)
values ('2019-08-09 09:00:00.0', 1, 1, 2, 1000);

insert into activity (timestamp, owner_account_id, source_account_id, target_account_id, amount)
values ('2019-08-09 09:00:00.0', 2, 1, 2, 1000);

insert into activity (timestamp, owner_account_id, source_account_id, target_account_id, amount)
values ('2019-08-09 10:00:00.0', 1, 2, 1, 1000);

insert into activity (timestamp, owner_account_id, source_account_id, target_account_id, amount)
values ('2019-08-09 10:00:00.0', 2, 2, 1, 1000);