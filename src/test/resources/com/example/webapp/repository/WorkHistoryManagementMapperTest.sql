INSERT INTO
  employees (id,password, name, birth, tel, address, authority)
VALUES
  (1001,'hogehoge','hoge','1980-01-01','090-1111-1111','福岡県福岡市東区テスト1-1','ADMIN')
  ,(1002,'fugafuga','fuga','1990-01-01','090-2222-2222','福岡県福岡市中央区テスト1-1','USER')
  ,(1003,'piyopiyo','piyo','2000-01-01','090-3333-3333','福岡県福岡市南区テスト1-1','USER')
;

INSERT INTO
  shift_schedules(id,employee_id,date,scheduled_start,scheduled_end)
VALUES
--  欠勤、理由表示 (is_approve=true)
  (1,1001,'2025-04-01','06:00:00','09:00:00')
--  欠勤、未承認 (is_approve=false)
  ,(2,1002,'2025-04-01','06:00:00','09:00:00')
--  欠勤、承認忘れ (is_approve is null)
  ,(3,1003,'2025-04-01','06:00:00','09:00:00')
--  欠勤、理由不明（reason_id is null）→つまり無断欠勤
  ,(4,1001,'2025-05-01','06:00:00','09:00:00')
--  出勤
  ,(5,1002,'2025-05-01','06:00:00','09:00:00')
  ,(6,1002,'2025-05-11','06:00:00','09:00:00')  
;

INSERT INTO
  time_records(employee_id,date,clock_in,clock_out,created_at,updated_at)
VALUES
  (1002,'2025-05-01','05:59:59','09:01:01','2025-01-01 00:00:00','2025-12-31 00:00:00')
  ,(1002,'2025-05-11','05:59:59','09:01:01','2025-01-01 00:00:00','2025-12-31 00:00:00')
;

INSERT INTO
  absence_reasons (id,name)
VALUES
  (1,'理由1')
  ,(2,'理由2')
;

INSERT INTO
  absence_applications (id,shift_id, reason_id, detail, is_approve)
VALUES
  (1,1,1,"詳細1",true)
  ,(2,2,1,"詳細1",false)
  ,(3,3,2,"詳細2",null)
;