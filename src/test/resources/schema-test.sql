--ALTER TABLE test_absence_applications DROP FOREIGN KEY fk_test_absence_applications_shift_id;
--ALTER TABLE test_absence_applications DROP FOREIGN KEY fk_test_absence_applications_reason_id;
--ALTER TABLE test_shift_schedules DROP FOREIGN KEY fk_test_shift_schedules_employee_id;
--ALTER TABLE test_shift_requests DROP FOREIGN KEY fk_test_shift_requests_employee_id;
--ALTER TABLE test_time_records DROP FOREIGN KEY fk_test_time_records_employee_id;
--ALTER TABLE test_time_records DROP FOREIGN KEY fk_test_time_records_employee_date;
--
--DROP TABLE IF EXISTS test_time_records;
--DROP TABLE IF EXISTS test_absence_applications;
--DROP TABLE IF EXISTS test_shift_requests;
--DROP TABLE IF EXISTS test_shift_schedules;
--DROP TABLE IF EXISTS test_employees;
--DROP TABLE IF EXISTS test_absence_reasons;

CREATE TABLE test_absence_reasons (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE test_employees (
    id INT NOT NULL AUTO_INCREMENT(1007),
    password CHAR(60) NOT NULL,
    name VARCHAR(50) NOT NULL UNIQUE,
    birth DATE NOT NULL,
    tel VARCHAR(13) NOT NULL,
    address VARCHAR(150) NOT NULL,
    authority ENUM('ADMIN', 'USER') NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    password_updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE test_shift_schedules (
    id INT NOT NULL AUTO_INCREMENT,
    employee_id INT NOT NULL,
    date DATE NOT NULL,
    scheduled_start TIME NOT NULL,
    scheduled_end TIME NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_test_shift_schedules_employee_id FOREIGN KEY (employee_id)
        REFERENCES test_employees(id),
    CONSTRAINT uq_test_shift_schedules_employee_date UNIQUE (employee_id, date)
);

CREATE TABLE test_shift_requests (
    id INT NOT NULL AUTO_INCREMENT,
    employee_id INT NOT NULL,
    date DATE NOT NULL,
    scheduled_start TIME NOT NULL,
    scheduled_end TIME NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_test_shift_requests_employee_id FOREIGN KEY (employee_id)
        REFERENCES test_employees(id)
        ,CONSTRAINT uq_test_shift_requests_employee_date UNIQUE (employee_id, date)
);

CREATE TABLE test_absence_applications (
    id INT NOT NULL AUTO_INCREMENT,
    shift_id INT NOT NULL UNIQUE,
    reason_id INT NOT NULL,
    detail TEXT NOT NULL,
    is_approve BOOLEAN DEFAULT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_test_absence_applications_shift_id FOREIGN KEY (shift_id)
        REFERENCES test_shift_schedules(id),
    CONSTRAINT fk_test_absence_applications_reason_id FOREIGN KEY (reason_id)
        REFERENCES test_absence_reasons(id)
);

CREATE TABLE test_time_records (
    employee_id INT NOT NULL,
    date DATE NOT NULL,
    clock_in TIME NOT NULL,
    clock_out TIME DEFAULT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (date, employee_id)
    ,CONSTRAINT fk_test_time_records_employee_date FOREIGN KEY (employee_id,date)
        REFERENCES test_shift_schedules(employee_id,date)
);

