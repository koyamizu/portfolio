-- 外部キー制約の削除（※必要に応じて使用）
-- ALTER TABLE absence_applications DROP FOREIGN KEY fk_absence_applications_shift_id;
-- ALTER TABLE absence_applications DROP FOREIGN KEY fk_absence_applications_reason_id;
-- ALTER TABLE shift_schedules DROP FOREIGN KEY fk_shift_schedules_employee_id;
-- ALTER TABLE shift_requests DROP FOREIGN KEY fk_shift_requests_employee_id;
-- ALTER TABLE time_records DROP FOREIGN KEY fk_time_records_employee_id;
-- ALTER TABLE time_records DROP FOREIGN KEY fk_time_records_date;

-- テーブル削除
DROP TABLE IF EXISTS time_records;
DROP TABLE IF EXISTS shift_requests;
DROP TABLE IF EXISTS shift_schedules;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS absence_applications;
DROP TABLE IF EXISTS absence_reasons;

-- 欠勤理由
CREATE TABLE absence_reasons (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

-- 従業員
CREATE TABLE employees (
    id INT NOT NULL AUTO_INCREMENT,
    password CHAR(60) NOT NULL,
    name VARCHAR(50) NOT NULL,
    birth DATE NOT NULL,
    tel VARCHAR(13) NOT NULL,
    address VARCHAR(150) NOT NULL,
    authority ENUM('ADMIN', 'USER') NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    password_updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

-- シフトスケジュール
CREATE TABLE shift_schedules (
    id INT NOT NULL AUTO_INCREMENT,
    employee_id INT NOT NULL,
    date DATE NOT NULL,
    scheduled_start TIME NOT NULL,
    scheduled_end TIME NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_shift_schedules_employee_id FOREIGN KEY (employee_id)
        REFERENCES employees(id)
);

-- シフト希望
CREATE TABLE shift_requests (
    id INT NOT NULL AUTO_INCREMENT,
    employee_id INT NOT NULL,
    date DATE NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_shift_requests_employee_id FOREIGN KEY (employee_id)
        REFERENCES employees(id)
);

-- 出退勤打刻
CREATE TABLE time_records (
    date DATE NOT NULL,
    employee_id INT NOT NULL,
    clock_in TIME NOT NULL,
    clock_out TIME DEFAULT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (date, employee_id),
    CONSTRAINT fk_time_records_employee_id FOREIGN KEY (employee_id)
        REFERENCES shift_requests(employee_id),
    CONSTRAINT fk_time_records_date FOREIGN KEY (date)
        REFERENCES shift_requests(date)
);

-- 欠勤申請
CREATE TABLE absence_applications (
    id INT NOT NULL AUTO_INCREMENT,
    shift_id INT NOT NULL,
    reason_id INT NOT NULL,
    detail TEXT NOT NULL,
    is_approve TINYINT(1) DEFAULT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_absence_applications_shift_id FOREIGN KEY (shift_id)
        REFERENCES shift_schedules(id),
    CONSTRAINT fk_absence_applications_reason_id FOREIGN KEY (reason_id)
        REFERENCES absence_reasons(id)
);



----ALTER TABLE test_shifts_and_time_records DROP FOREIGN KEY test_shifts_and_time_records_ibfk_1;
----ALTER TABLE test_shifts_and_time_records DROP employee_id;
--DROP TABLE IF EXISTS test_employees;
----DROP TABLE IF EXISTS test_shifts_and_time_records;
--DROP TABLE IF EXISTS test_shift_requests;
--DROP TABLE IF EXISTS test_shift_schedules;
--DROP TABLE IF EXISTS test_time_records;
--DROP TABLE IF EXISTS test_absence_applications;
--DROP TABLE IF EXISTS test_absence_reasons;
--
----CREATE TABLE test_shift_requests (
----    id INT PRIMARY KEY AUTO_INCREMENT
----    ,employee_id INT NOT NULL
----    ,date DATE NOT NULL
----    FOREIGN KEY(employee_id) REFERENCES test_employees(id)
----    )
----;
--
--CREATE TABLE
--  test_shift_requests
--LIKE
--  shift_requests
--;
--
--INSERT INTO
--  test_shift_requests
--  SELECT
--    *
--  FROM
--    shift_requests
--;
--
----従業員テーブルの作成
--CREATE TABLE
--  test_employees
--LIKE
--  employees
--;
--
--INSERT INTO
--  test_employees
--SELECT
--  *
--FROM
--  employees
--;
--
----パスワード
----"yoshizuka01","koga09","kurosaki21","togo13","komorie30","hakozaki02"
--
----シフトテーブルの作成
----CREATE TABLE
----  test_shifts_and_time_records
----LIKE
----  shifts_and_time_records
----;
----
----ALTER TABLE
----  test_shifts_and_time_records
----ADD FOREIGN KEY (employee_id)
----REFERENCES test_employees(id)
----;
----
----INSERT INTO
----  test_shifts_and_time_records
----SELECT
----  *
----FROM
----  shifts_and_time_records
----;
--
--CREATE TABLE
--  test_shift_schedules
--LIKE
--  shift_schedules
--;
--
----ALTER TABLE
----  test_shift_schedules
----ADD FOREIGN KEY (employee_id)
----REFERENCES test_employees(id)
----;
--
--INSERT INTO
--  test_shift_schedules
--SELECT
--  *
--FROM
--  shift_schedules
--;
--
--CREATE TABLE
--  test_time_records
--LIKE
--  temp_time_records
--;
--
--INSERT INTO
--  test_time_records
--SELECT
--  *
--FROM
--  temp_time_records
----WHERE
----  clock_in IS NOT NULL
--;
--
--CREATE TABLE
--  test_absence_applications
--LIKE
--  absence_applications
--;
--
--INSERT INTO
--  test_absence_applications
--  SELECT
--    *
--  FROM
--    absence_applications
--;
--
--CREATE TABLE
--  test_absence_reasons
--LIKE
--  absence_reasons
--;
--
--INSERT INTO
--  test_absence_reasons
--  SELECT
--    *
--  FROM
--    absence_reasons
--;