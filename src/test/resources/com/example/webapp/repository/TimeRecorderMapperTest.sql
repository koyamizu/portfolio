INSERT INTO employees (id,password, name, birth, tel, address, authority) VALUES
  (1001,'p', 'hogehoge',   '1986-05-27', '030-1920-8394', '福岡県福岡市博多区', 'ADMIN')
  ,(1002,'q', 'fugafuga',   '1980-01-01', '030-1920-8394', '福岡県福岡市東区', 'USER')
  ,(1003,'r', 'piyopiyo',   '1990-01-01', '030-1920-8394', '福岡県福岡市早良区', 'USER')
  ;

INSERT INTO shift_schedules(id,employee_id,date,scheduled_start,scheduled_end)
 VALUES
  (1,1001,CURRENT_DATE,'06:00:00','09:00:00')
  ,(2,1002,CURRENT_DATE,'06:00:00','09:00:00')
  ,(3,1003,CURRENT_DATE,'06:00:00','09:00:00')
;

INSERT INTO time_records(employee_id,date,clock_in)
VALUES
(1001,CURRENT_DATE,'06:00:00')
;