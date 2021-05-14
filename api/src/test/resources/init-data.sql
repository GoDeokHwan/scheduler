INSERT INTO schedulerdb.user(id, login_id, password, name, phone_number, token_key, status, create_datetime, modify_datetime)
VALUES (1, 'test001', '12341234', '테스터1', '01025553155', null, 'ENABLE', NOW(), NOW());
INSERT INTO schedulerdb.user(id, login_id, password, name, phone_number, token_key, status, create_datetime, modify_datetime)
VALUES (2, 'test002', '12341234', '테스터2', '01025553155', null, 'ENABLE', NOW(), NOW());

INSERT INTO schedulerdb.scheduler(id, user_id, date, date_year, date_month, date_day, time, time_hour, time_min, is_alarm, alarm_type, alarm_time, is_repeat, repeat_type, is_holiday, memo, create_datetime, modify_datetime)
VALUES (1, 1, '20210511', '2012', '05', '11', '1530', '15', '30', 1, 'MINUTE', '30', 0, null, 0, 'TEST', NOW(), NOW());
INSERT INTO schedulerdb.scheduler(id, user_id, date, date_year, date_month, date_day, time, time_hour, time_min, is_alarm, alarm_type, alarm_time, is_repeat, repeat_type, is_holiday, memo, create_datetime, modify_datetime)
VALUES (2, 1, '20210511', '2012', '05', '11', '1530', '15', '30', 1, 'HOUR', '10', 0, null, 0, 'TEST2', NOW(), NOW());

INSERT INTO schedulerdb.holiday(id, name, memo, date, date_year, date_month, date_day, is_common, user_id, create_datetime, modify_datetime)
values (1, '테스트1', '테스트 메모1', '20210513', '2021', '05', '13', 1, 1, NOW(), NOW());
INSERT INTO schedulerdb.holiday(id, name, memo, date, date_year, date_month, date_day, is_common, user_id, create_datetime, modify_datetime)
values (2, '테스트2', '테스트 메모2', '20210514', '2021', '05', '14', 0, 1, NOW(), NOW());
