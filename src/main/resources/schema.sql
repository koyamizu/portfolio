--↓本番用

-- 外部キー制約の削除（※必要に応じて使用）
-- ALTER TABLE absence_applications DROP FOREIGN KEY fk_absence_applications_shift_id;
-- ALTER TABLE absence_applications DROP FOREIGN KEY fk_absence_applications_reason_id;
-- ALTER TABLE shift_schedules DROP FOREIGN KEY fk_shift_schedules_employee_id;
-- ALTER TABLE shift_requests DROP FOREIGN KEY fk_shift_requests_employee_id;
-- ALTER TABLE time_records DROP FOREIGN KEY fk_time_records_employee_id;
-- ALTER TABLE time_records DROP FOREIGN KEY fk_time_records_employee_date;

DROP TABLE IF EXISTS time_records;
DROP TABLE IF EXISTS absence_applications;
DROP TABLE IF EXISTS shift_requests;
DROP TABLE IF EXISTS shift_schedules;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS absence_reasons;

CREATE TABLE absence_reasons (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(15) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uq_absence_reasons UNIQUE (name)
);

CREATE TABLE employees (
    id INT NOT NULL AUTO_INCREMENT,
    password CHAR(60) NOT NULL,
    name VARCHAR(30) NOT NULL UNIQUE,
    birth DATE NOT NULL,
    tel VARCHAR(13) NOT NULL,
    address VARCHAR(50) NOT NULL,
    authority ENUM('ADMIN', 'USER') NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    password_updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
)
    AUTO_INCREMENT=1001
;

CREATE TABLE shift_schedules (
    id INT NOT NULL AUTO_INCREMENT,
    employee_id INT NOT NULL,
    date DATE NOT NULL,
    scheduled_start TIME NOT NULL,
    scheduled_end TIME NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_shift_schedules_employee_id FOREIGN KEY (employee_id)
        REFERENCES employees(id),
    CONSTRAINT uq_shift_schedules_employee_date UNIQUE (employee_id, date)
);

CREATE TABLE shift_requests (
    id INT NOT NULL AUTO_INCREMENT,
    employee_id INT NOT NULL,
    date DATE NOT NULL,
    scheduled_start TIME NOT NULL,
    scheduled_end TIME NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_shift_requests_employee_id FOREIGN KEY (employee_id)
        REFERENCES employees(id)
        ,CONSTRAINT uq_shift_requests_employee_date UNIQUE (employee_id, date)
);

CREATE TABLE absence_applications (
    id INT NOT NULL AUTO_INCREMENT,
    shift_id INT NOT NULL UNIQUE,
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

CREATE TABLE time_records (
    employee_id INT NOT NULL,
    date DATE NOT NULL,
    clock_in TIME NOT NULL,
    clock_out TIME DEFAULT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (date, employee_id)
    ,CONSTRAINT fk_time_records_employee_date FOREIGN KEY (employee_id,date)
        REFERENCES shift_schedules(employee_id,date)
        ,CONSTRAINT uq_time_records_employee_date UNIQUE (employee_id, date)
);

----↓開発用
--DROP TABLE IF EXISTS employees;
--DROP TABLE IF EXISTS shift_requests;
--DROP TABLE IF EXISTS shift_schedules;
--DROP TABLE IF EXISTS time_records;
--DROP TABLE IF EXISTS absence_applications;
--DROP TABLE IF EXISTS absence_reasons;
--
--
--CREATE TABLE
--  shift_requests
--LIKE
--  origin_shift_requests
--;
--
----従業員テーブルの作成
--CREATE TABLE
--  employees
--LIKE
--  origin_employees
--;
--
--CREATE TABLE
--  shift_schedules
--LIKE
--  origin_shift_schedules
--;
--
--CREATE TABLE
--  time_records
--LIKE
--  origin_time_records
--;
--
--CREATE TABLE
--  absence_applications
--LIKE
--  origin_absence_applications
--;
--
--CREATE TABLE
--  absence_reasons
--LIKE
--  origin_absence_reasons
--;
--
----パスワード
----"yoshizuka01","koga09","kurosaki21","togo13","komorie30","hakozaki02"
--
--INSERT INTO
--  shift_requests
--  SELECT
--    *
--  FROM
--    origin_shift_requests
--;
--
--INSERT INTO
--  employees
--SELECT
--  *
--FROM
--  origin_employees
--;
--
--INSERT INTO
--  shift_schedules
--SELECT
--  *
--FROM
--  origin_shift_schedules
--;
--
--INSERT INTO
--  time_records
--SELECT
--  *
--FROM
--  origin_time_records
--;
--
--INSERT INTO
--  absence_applications
--  SELECT
--    *
--  FROM
--    origin_absence_applications
--;
--
--INSERT INTO
--  absence_reasons
--  SELECT
--    *
--  FROM
--    origin_absence_reasons
--;
