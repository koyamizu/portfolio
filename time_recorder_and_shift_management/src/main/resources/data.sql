INSERT INTO test_employees_list(password,name,tel,address,authority) VALUES
('togo','東郷','080-XXXX-XXXX','福岡県宗像市田熊','USER'),
('yoshizuka','吉塚','080-XXXX-XXXX','福岡県福岡市博多区吉塚本町','USER'),
('koga','古賀','080-XXXX-XXXX','福岡県古賀市天神','USER'),
('chihaya','千早','090-XXXX-XXXX','福岡県福岡市東区千早','ADMIN');

--当日出勤者リスト初期化
INSERT INTO test_shift_and_timestamp2504(date,employee_id) VALUES
(CURRENT_DATE,1001),
(CURRENT_DATE,1002);
