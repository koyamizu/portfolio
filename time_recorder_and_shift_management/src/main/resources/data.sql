INSERT INTO test_employees_list VALUES
('093001','togo','東郷','080-XXXX-XXXX','福岡県宗像市田熊'),
('093002','yoshizuka','吉塚','080-XXXX-XXXX','福岡県福岡市博多区吉塚本町'),
('093003','koga','古賀','080-XXXX-XXXX','福岡県古賀市天神');

--当日出勤者リスト初期化
INSERT INTO test_shift_and_timestamp2504(date,employee_id) VALUES
(CURRENT_DATE,'093001'),
(CURRENT_DATE+1,'093003');
