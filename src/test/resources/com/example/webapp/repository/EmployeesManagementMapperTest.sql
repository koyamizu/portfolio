INSERT INTO employees(id,password,name,birth,tel,address,authority) VALUES
(1001,'hogehoge','hoge','1980-01-01','090-1111-1111','福岡県福岡市東区テスト1-1','ADMIN')
,(1002,'fugafuga','fuga','1990-01-01','090-2222-2222','福岡県福岡市中央区テスト1-1','USER')
,(1003,'piyopiyo','piyo','2000-01-01','090-3333-3333','福岡県福岡市南区テスト1-1','USER')
;

INSERT INTO
  shift_schedules(id,employee_id,date,scheduled_start,scheduled_end)
VALUES
  (1,1001,'2025-04-01','06:00:00','09:00:00')
 ;