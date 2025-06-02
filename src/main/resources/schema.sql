--↓本番用

-- 外部キー制約の削除（※必要に応じて使用）
-- ALTER TABLE test_absence_applications DROP FOREIGN KEY fk_test_absence_applications_shift_id;
-- ALTER TABLE test_absence_applications DROP FOREIGN KEY fk_test_absence_applications_reason_id;
-- ALTER TABLE test_shift_schedules DROP FOREIGN KEY fk_test_shift_schedules_employee_id;
-- ALTER TABLE test_shift_requests DROP FOREIGN KEY fk_test_shift_requests_employee_id;
-- ALTER TABLE test_time_records DROP FOREIGN KEY fk_test_time_records_employee_id;
-- ALTER TABLE test_time_records DROP FOREIGN KEY fk_test_time_records_date;

--DROP TABLE IF EXISTS test_time_records;
--DROP TABLE IF EXISTS test_absence_applications;
--DROP TABLE IF EXISTS test_shift_requests;
--DROP TABLE IF EXISTS test_shift_schedules;
--DROP TABLE IF EXISTS test_employees;
--DROP TABLE IF EXISTS test_absence_reasons;
--
--CREATE TABLE test_absence_reasons (
--    id INT NOT NULL AUTO_INCREMENT,
--    name VARCHAR(255) NOT NULL,
--    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    PRIMARY KEY (id)
--);
--
--CREATE TABLE test_employees (
--    id INT NOT NULL AUTO_INCREMENT,
--    password CHAR(60) NOT NULL,
--    name VARCHAR(50) NOT NULL,
--    birth DATE NOT NULL,
--    tel VARCHAR(13) NOT NULL,
--    address VARCHAR(150) NOT NULL,
--    authority ENUM('ADMIN', 'USER') NOT NULL,
--    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    password_updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    PRIMARY KEY (id)
--);
--
--CREATE TABLE test_shift_schedules (
--    id INT NOT NULL AUTO_INCREMENT,
--    employee_id INT NOT NULL,
--    date DATE NOT NULL,
--    scheduled_start TIME NOT NULL,
--    scheduled_end TIME NOT NULL,
--    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    PRIMARY KEY (id),
--    CONSTRAINT fk_test_shift_schedules_employee_id FOREIGN KEY (employee_id)
--        REFERENCES test_employees(id),
--    CONSTRAINT uq_test_shift_schedules_employee_date UNIQUE (employee_id, date)
--);
--
--CREATE TABLE test_shift_requests (
--    id INT NOT NULL AUTO_INCREMENT,
--    employee_id INT NOT NULL,
--    date DATE NOT NULL,
--    scheduled_start TIME NOT NULL,
--    scheduled_end TIME NOT NULL,
--    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    PRIMARY KEY (id),
--    CONSTRAINT fk_test_shift_requests_employee_id FOREIGN KEY (employee_id)
--        REFERENCES test_employees(id)
----        ,
----    CONSTRAINT uq_test_shift_requests_employee_date UNIQUE (employee_id, date)
--);
--
--CREATE TABLE test_absence_applications (
--    id INT NOT NULL AUTO_INCREMENT,
--    shift_id INT NOT NULL,
--    reason_id INT NOT NULL,
--    detail TEXT NOT NULL,
--    is_approve TINYINT(1) DEFAULT NULL,
--    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    PRIMARY KEY (id),
--    CONSTRAINT fk_test_absence_applications_shift_id FOREIGN KEY (shift_id)
--        REFERENCES test_shift_schedules(id),
--    CONSTRAINT fk_test_absence_applications_reason_id FOREIGN KEY (reason_id)
--        REFERENCES test_absence_reasons(id)
--);
--
--CREATE TABLE test_time_records (
--    employee_id INT NOT NULL,
--    date DATE NOT NULL,
--    clock_in TIME NOT NULL,
--    clock_out TIME DEFAULT NULL,
--    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    PRIMARY KEY (date, employee_id)
--    ,CONSTRAINT fk_test_time_records_employee_date FOREIGN KEY (employee_id,date)
--        REFERENCES test_shift_schedules(employee_id,date)
--);

--↓テスト用

--ALTER TABLE test_shifts_and_time_records DROP FOREIGN KEY test_shifts_and_time_records_ibfk_test_1;
--ALTER TABLE test_shifts_and_time_records DROP employee_id;
DROP TABLE IF EXISTS test_employees;
DROP TABLE IF EXISTS test_shift_requests;
DROP TABLE IF EXISTS test_shift_schedules;
DROP TABLE IF EXISTS test_time_records;
DROP TABLE IF EXISTS test_absence_applications;
DROP TABLE IF EXISTS test_absence_reasons;


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
  time_records
;

INSERT INTO
  test_time_records
SELECT
  *
FROM
  time_records
--WHERE
--  clock_in IS NOT NULL
;

CREATE TABLE
  test_absence_applications
LIKE
  absence_applications
;

INSERT INTO
  test_absence_applications
  SELECT
    *
  FROM
    absence_applications
;

CREATE TABLE
  test_absence_reasons
LIKE
  absence_reasons
;

INSERT INTO
  test_absence_reasons
  SELECT
    *
  FROM
    absence_reasons
;
