CREATE SCHEMA IF NOT EXISTS schedulerdb;
-- -----------------------------------------------------
-- Table `schedulerdb`.`user`
-- -----------------------------------------------------
create table schedulerdb.user
(
    id              int          auto_increment comment 'ID',
    login_id        varchar(45)  not null comment '로그인 아이디',
    password        varchar(100) not null comment '비밀번호',
    name            varchar(45)  null comment '이름',
    phone_number    varchar(15)  null comment '휴대폰 번호',
    token_key       varchar(100) null comment 'Token',
    status          varchar(10)  null comment '사용자 상태 ( ENABLE , DISABLE , DELETE )',
    create_datetime datetime     null comment '생성 시간',
    modify_datetime datetime     null comment '수정 시간'
);

alter table schedulerdb.user
    add primary key (id);


-- -----------------------------------------------------
-- Table `schedulerdb`.`scheduler`
-- -----------------------------------------------------
create table schedulerdb.scheduler
(
    id              int                    auto_increment comment 'ID',
    user_id         int                    not null,
    date            varchar(8)             null comment '날짜',
    date_year       varchar(4)             null comment '년',
    date_month      varchar(2)             null comment '월',
    date_day        varchar(2)             null comment '일',
    time            varchar(8)             null comment '시간',
    time_hour       varchar(2)             null comment '시',
    time_min        varchar(2)             null comment '분',
    is_alarm        TINYINT(1) default 0 null comment '알람 허용 여부 ( 1: 알림 허용 , 0 : 알림 미허용 )',
    alarm_type      varchar(45)            null comment '알람 울림 시간 타입',
    alarm_time      varchar(45)            null comment '알람까지 시간',
    is_repeat       TINYINT(1) default 0 null comment '반복 여부 ( 1 : 반복  ,  0 : 미반복 )',
    repeat_type     varchar(10)            null comment '반복 타입',
    is_holiday      TINYINT(1) default 0 null comment '공휴일 제외 여부 ( 1 : 제외 , 0 : 미제외)',
    memo            LONGTEXT               null,
    create_datetime datetime               null,
    modify_datetime datetime               null,
    constraint fk_scheduler_user
        foreign key (user_id) references schedulerdb.user (id)
);

alter table schedulerdb.scheduler
    add primary key (id);

-- -----------------------------------------------------
-- Table `schedulerdb`.`holiday`
-- -----------------------------------------------------
create table schedulerdb.holiday
(
    id              int auto_increment,
    name            varchar(45)            null comment '공휴일명',
    memo            varchar(100)           null comment '공휴일 내용',
    date            varchar(8)             null comment '날짜',
    date_year       varchar(4)             null,
    date_month      varchar(2)             null,
    date_day        varchar(2)             null,
    is_common       TINYINT(1) default 0 null comment '공통 공휴일( 1 : 공통 공휴일 , 0 : 특정 사용자 공휴일 )',
    user_id         int                    null,
    create_datetime datetime               null,
    modify_datetime datetime               null
);

alter table schedulerdb.holiday
    add primary key (id);




create table schedulerdb.sample_user
(
    id           int auto_increment,
    name         varchar(40) null,
    phone_number varchar(15) null,
    constraint sample_user_id_uindex
        unique (id)
);

alter table schedulerdb.sample_user
    add primary key (id);

