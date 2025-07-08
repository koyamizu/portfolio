
INSERT INTO employees (id,password, name, birth, tel, address, authority) VALUES
  (1001,'hogehoge','hoge','1980-01-01','090-1111-1111','福岡県福岡市東区テスト1-1','ADMIN')
  ,(1002,'fugafuga','fuga','1990-01-01','090-2222-2222','福岡県福岡市中央区テスト1-1','USER')
  ;
  
INSERT INTO 
  `shift_schedules` (id,employee_id, date, scheduled_start, scheduled_end)
VALUES
  (1,1001,CURRENT_DATE,'06:00:00','09:00:00')
  ,  (2,1002,CURRENT_DATE,'06:00:00','09:00:00')
  ,  (3,1001,CURRENT_DATE+1,'06:00:00','09:00:00')
--  新しい欠勤申請を出すために用意したシフト
  ,  (4,1002,CURRENT_DATE+1,'06:00:00','09:00:00')
;

INSERT INTO `absence_reasons` (id,name) VALUES
  (1,'理由1'),
  (2,'理由2'),
  (3,'理由3')
;

INSERT INTO absence_applications (id,shift_id, reason_id, detail, is_approve)
VALUES
	(1,1,1,"詳細1",null)
	,(2,2,2,"詳細2",true)
	,(3,3,3,"詳細3",false)
;
