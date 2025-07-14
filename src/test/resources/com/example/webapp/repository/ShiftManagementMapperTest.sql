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
  (1,1001,'2025-04-01','06:00:00','09:00:00')
  ,(2,1002,'2025-04-01','06:00:00','09:00:00')
  ,(3,1003,'2025-04-01','06:00:00','09:00:00')
  ,(4,1001,'2025-05-01','06:00:00','09:00:00')
  ,(5,1002,'2025-05-01','06:00:00','09:00:00')
  ,(6,1002,'2025-05-11','06:00:00','09:00:00')  
;

INSERT INTO
  time_records(employee_id,date,clock_in,clock_out,created_at,updated_at)
VALUES
  (1001,'2025-05-01','05:59:59','09:01:01','2025-01-01 00:00:00','2025-12-31 00:00:00')
;

--MAKEDATE(YEAR(CURRENT_DATE),DAYOFYEAR(LAST_DAY(CURRENT_DATE))+1)は今月の最終日をDATE型で作成している。
--そこから1日以上28日以下の日付を足していくことで、「翌月のシフト希望」になるようにしている。
--↓クエリに「AND MONTH(date)=MONTH(CURRENT_DATE)+1」という条件があるため、このような操作をしている。
--	<select id="selectRequestByEmployeeId"
--		resultMap="FullCalendarEntityResult">
--		SELECT
--		  id
--		  ,employee_id
--		  ,date
--		  ,scheduled_start
--		  ,scheduled_end
--		FROM
--		  shift_requests
--		WHERE
--		  employee_id = #{employeeId}
--		  AND MONTH(date)=MONTH(CURRENT_DATE)+1
--		ORDER BY date ASC
--		;
--	</select>
INSERT INTO
  shift_requests(id,employee_id,date,scheduled_start,scheduled_end)
VALUES
  (1,1001,MAKEDATE(YEAR(CURRENT_DATE),DAYOFYEAR(LAST_DAY(CURRENT_DATE))+1),'06:00:00','09:00:00')
  ,(2,1001,MAKEDATE(YEAR(CURRENT_DATE),DAYOFYEAR(LAST_DAY(CURRENT_DATE))+15),'06:00:00','09:00:00')
  ,(3,1001,MAKEDATE(YEAR(CURRENT_DATE),DAYOFYEAR(LAST_DAY(CURRENT_DATE))+28),'06:00:00','09:00:00')
  ,(4,1002,MAKEDATE(YEAR(CURRENT_DATE),DAYOFYEAR(LAST_DAY(CURRENT_DATE))+10),'06:00:00','09:00:00')
  ,(5,1002,MAKEDATE(YEAR(CURRENT_DATE),DAYOFYEAR(LAST_DAY(CURRENT_DATE))+20),'06:00:00','09:00:00') 
;
