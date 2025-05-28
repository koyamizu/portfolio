--ALTER TABLE test_shifts_and_time_records DROP FOREIGN KEY test_shifts_and_time_records_ibfk_1;
--ALTER TABLE test_shifts_and_time_records DROP employee_id;
DROP TABLE IF EXISTS test_employees;
--DROP TABLE IF EXISTS test_shifts_and_time_records;
DROP TABLE IF EXISTS test_shift_requests;
DROP TABLE IF EXISTS test_shift_schedules;
DROP TABLE IF EXISTS test_time_records;

--CREATE TABLE test_shift_requests (
--    id INT PRIMARY KEY AUTO_INCREMENT
--    ,employee_id INT NOT NULL
--    ,date DATE NOT NULL
--    FOREIGN KEY(employee_id) REFERENCES test_employees(id)
--    )
--;

CREATE TABLE
  test_shift_requests
LIKE
  shift_requests
;

INSERT INTO
  test_shift_requests
  SELECT
    *
  FROM
    shift_requests
;

--従業員テーブルの作成
CREATE TABLE
  test_employees
LIKE
  employees
;

INSERT INTO
  test_employees
SELECT
  *
FROM
  employees
;

--パスワード
--"yoshizuka01","koga09","kurosaki21","togo13","komorie30","hakozaki02"

--シフトテーブルの作成
--CREATE TABLE
--  test_shifts_and_time_records
--LIKE
--  shifts_and_time_records
--;
--
--ALTER TABLE
--  test_shifts_and_time_records
--ADD FOREIGN KEY (employee_id)
--REFERENCES test_employees(id)
--;
--
--INSERT INTO
--  test_shifts_and_time_records
--SELECT
--  *
--FROM
--  shifts_and_time_records
--;

CREATE TABLE
  test_shift_schedules
LIKE
  shift_schedules
;

--ALTER TABLE
--  test_shift_schedules
--ADD FOREIGN KEY (employee_id)
--REFERENCES test_employees(id)
--;

INSERT INTO
  test_shift_schedules
SELECT
  *
FROM
  shift_schedules
;

CREATE TABLE
  test_time_records
LIKE
  temp_time_records
;

INSERT INTO
  test_time_records
SELECT
  *
FROM
  temp_time_records
--WHERE
--  clock_in IS NOT NULL
;