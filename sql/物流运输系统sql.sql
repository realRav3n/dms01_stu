#drop database logistics;
create database logistics;
alter database logistics character set GBK;


create table `user`
(
	`user_name` varchar(20) primary key,
    `password` varchar(20) not null,
    `address` varchar(20) not null,
    `phone` varchar(20) not null,
    `user_type` int not null,
    index adrs (`address`)
);
#drop table `user`;

create table `transport`
(
	`transport_id` int(11) auto_increment primary key,
    `transport_name` varchar(20) not null,
    `dispatch` varchar(20) not null,
    `destination` varchar(20) not null,
	`recipient` varchar(20) not null,
    `recipient_phone` varchar(20) not null,
	`now_handle` varchar(20) not null,
	`now_handle_phone` varchar(20) not null,
	`transport_type` int not null,
	foreign key (`recipient`) references `user`(`user_name`),
	foreign key (`now_handle`) references `user`(`user_name`),
    foreign key (`destination`) references `user`(`address`)
);
#drop table `transport`;

create table `log`
(
	`log_id` int(11) auto_increment primary key,
    `transport_id` int(11) not null,
    `time` datetime not null,
    `transport_type` int not null,
    `handle` varchar(20) not null,
    `next_handle` varchar(20) not null,
    `next_handle_phone` varchar(20) not null,
	foreign key (`transport_id`) references `transport`(`transport_id`),
    foreign key (`handle`) references `user`(`user_name`),
    foreign key (`next_handle`) references `user`(`user_name`),
    index t_id (`transport_id`)
);
#drop table `log`;
/*
insert into `user`
values('yjr','111','Wuhan','123456',1);
insert into `user`
values('sj','111','Changzhou','123456',1);
insert into `user`
values('xzy','111','Anqing','123456',1);
insert into `user`
values('customer','111','Anqing','123456',2);
select * from `user`;

insert into `transport`(`name`,`dispatch`,`destination`,`recipient`,`now_handle`,`type`)
values('coat','yjr','Anqing','xzy','yjr',0);
select * from `transport`;
#delete from `transport` where `id`=1;


insert into `log`(`transport_id`,`time`,`type`,`handle`,`next_handle`)
values(2,'2022-12-1 08:00:00',1,'yjr','sj');
insert into `log`(`transport_id`,`time`,`type`,`handle`,`next_handle`)
values(2,'2022-12-2 08:00:00',1,'sj','xzy');
insert into `log`(`transport_id`,`time`,`type`,`handle`,`next_handle`)
values(2,'2022-12-3 08:00:00',2,'xzy','customer');
select * from `log`;
#delete from `log` where `id`=3;


update `transport` 
set `type`=3
where `id`=1;

select `destination` from `transport`
where `id`=2;
select `time`,`type`,`handle` from `log`
where `transport_id`=2;
*/
create view `transport_details`(`transport_id`,`time`,`transport_type`,`next_handle`,`next_handle_phone`)
as
select `transport_id`,`time`,`transport_type`,`next_handle`,`next_handle_phone` from `log`;
#drop view `transport_details`;
select * from `transport_details`;

create view `handle_transport`(`transport_id`,`destination`,`recipent`,`recipent_phone`,`now_handle`,`now_handle_phone`)
as
select `transport_id`,`destination`,`recipient`,`recipient_phone`,`now_handle`,`now_handle_phone` from `transport`;
#drop view `handle_transport`;
select * from `handle_transport`;

create view `my_transport`(`transport_id`,`recipient`,`transport_name`,`transport_type`,`now_handle`,`now_handle_phone`)
as
select `transport_id`,`recipient`,`transport_name`,`transport_type`,`now_handle`,`now_handle_phone` from `transport`;
#drop view `my_transport`;
select * from `my_transport`;

delimiter $
create trigger `t_message_update`
after insert on `log` for each row
begin
	if(new.`transport_type`=1) then
	update `transport`
    set `now_handle`=new.`next_handle`,`transport_type`=new.`transport_type`,`now_handle_phone`=new.`next_handle_phone`
    where `transport_id`=new.`transport_id`;
    else
    update `transport`
    set `transport_type`=new.`transport_type`
    where `transport_id`=new.`transport_id`;
    end if;
end$
delimiter ;
#drop trigger `t_message_update`;

delimiter $
create procedure `save_user`(in nme varchar(20),in pwd varchar(20),in adrs varchar(20),in phone varchar(20),in tp int(11),out res int)
begin
	if((select `user_name` from `user` where `user_name`=nme) is null) 
    then 
    insert into `user` values(nme,pwd,adrs,phone,tp);
    set res=1;
    else
    set res=0;
    end if;
end$
delimiter ;
#drop procedure `save_user`;
call `save_user`('yjr','111','HuBei','111111',1,@res);
call `save_user`('xzy','111','AnHui','222222',1,@res);
call `save_user`('sj','111','HeNan','333333',1,@res);
call `save_user`('customer','111','AnHui','444444',2,@res);
call `save_user`('zc','111','GuangZhou','555555',2,@res);
select @res;
select * from `user`;


delimiter $
create procedure `save_transport`(in nme varchar(20),in dp varchar(20),in dt varchar(20),in r varchar(20),in rp varchar(20),in nh varchar(20),in nhp varchar(20),in tp int(11))
begin
    insert into `transport`(`transport_name`,`dispatch`,`destination`,`recipient`,`recipient_phone`,`now_handle`,`now_handle_phone`,`transport_type`)
    values(nme,dp,dt,r,rp,nh,nhp,tp);
end$
delimiter ;
#drop procedure `save_transport`;
call `save_transport`('cup','yjr','AnHui','customer','444444','yjr','111111',0);
select * from `transport`;


delimiter $
create procedure `save_log`(in t_id int(11),in tm datetime,in tp int(11),in hd varchar(20),in nh varchar(20),in nhp varchar(20),out res int) 
begin
	declare nhp varchar(20) default '';
    select `phone` into nhp from user where `user_name`=nh;
    if(nhp='') then set res=0;
    else set res=1;
    end if;
    if(res=1) then
    insert into `log`(`transport_id`,`time`,`transport_type`,`handle`,`next_handle`,`next_handle_phone`)
    values(t_id,tm,tp,hd,nh,nhp);
    end if;
end$
delimiter ;
#drop procedure `save_log`;
call `save_log`(1,'2022-12-1 08:00:00',1,'yjr','sj','333333',@res);
call `save_log`(1,'2022-12-2 08:00:00',1,'sj','xzy','222222',@res);
call `save_log`(1,'2022-12-3 08:00:00',2,'xzy','customer','444444',@res);
select res;
select * from log;


delimiter $
create procedure `receive`(in t_id int(11),in nme varchar(20),out res int)
begin
	declare tp int default -1;
    select `transport_type` into tp from `transport` 
    where `transport_id`=t_id and `recipient`=nme;
    if(tp!=-1) 
    then
		update `transport`
        set `transport_type`=3,`now_handle`=nme,`now_handle_phone`=''
        where `transport_id`=t_id;
        set res=1;
	else
		set res=0;
    end if;
end$
delimiter ;
call `receive`(1,'customer',@res);
select @res;


delimiter $
create procedure `login_check`(in nme varchar(20),in pwd varchar(20),in tp int,out res int)
begin
	declare t int default 0;
    select `user_type` into t from `user`
    where `user_name`=nme and `password`=pwd;
    if (t=tp) then set res=1;
    else set res=0;
    end if;
    if (res=1) then
		select * from `user` where `user_name`=nme;
	end if;
end$
delimiter ;
#drop procedure `login_check`;
call `login_check`('zc','111',2,@res);
select @res;


delimiter $
create procedure `get_details`(in t_id int(11),in nme varchar(20),in tp int,out res int)
begin
	declare rp varchar(20) default '';
    select `recipient` into rp from `transport` where `transport_id`=t_id;
    if (rp!=nme) then
		if tp=1 then set res=1;
        else set res=0;
        end if;
    else set res=1;
    end if;
    if (res=1) then
    select `time`,`transport_type`,`next_handle`,`next_handle_phone` from `transport_details`
    where `transport_id`=t_id;
    end if;
end$
delimiter ;
#drop procedure `get_details`;
call `get_details`(1,'customer',2,@res);
select @res;

delimiter $
create procedure `get_handle_transport`(in nme varchar(20))
begin
	select `transport_id`,`destination`,`recipent`,`recipent_phone`
    from `handle_transport`
    where `now_handle`=nme;
end$
delimiter ;
#drop procedure `get_handle_transport`;
call `get_handle_transport`('customer');

delimiter $
create procedure `get_my_transport`(in nme varchar(20))
begin
	select `transport_id`,`transport_name`,`now_handle`,`now_handle_phone`,`transport_type`
    from `my_transport`
    where `now_handle`=nme
    order by `transport_type` desc;
end$
delimiter ;
#drop procedure `get_my_transport`;
call `get_my_transport`('customer');