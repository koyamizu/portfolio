# アプリケーション名

労務管理アプリケーション

# 開発背景

これまで勤務してきた会社の大半は、シフトを紙で管理しており、以下の問題が生じておりました。

- 出勤時にしか提出できないので、出勤日数が少ない従業員などは、翌月の予定がまだはっきりしない段階でシフトを提出しなければならない。
- 筆記用具で記入すると、修正の際に視認性が低下する。
- 紙は紛失の可能性がある。（紛失により、シフトの作り直しが生じたこともあります。）
- 会社によってはシフト表に電話番号が記載されており、個人情報が保護されていない。

そこで私は、シフトのやりとりをデジタル化することにより、これらの問題を解決したいと考えました。
<br>また、シフト管理のみならず、勤怠の打刻、従業員の管理、勤怠履歴の管理などはCRUDアプリケーション作成の良い題材だと思ったので、オールインワンの労務管理アプリケーションの作成を目指しました。
私が過去にアルバイトとして勤務していた銭湯で使用されることを想定しております。

# 使用技術

鍵カッコ内の数字はバージョン

| 項目 | 名称 |  |  |
| --- | --- | --- | --- |
| バックエンド | Java（JDK21） |  |  |
| フロントエンド | HTML | CSS | JavaScript |
| フレームワーク | Spring（3.4.4） |  |  |
| ビルドツール | Gradle |  |  |
| Javaライブラリ | MyBatis（3.0.4） |  Selenide（7.9.3） |  |
| JavaScriptライブラリ | FullCalendar（6.1.14） |  |  |
| データベース | MySQL（9.2） | h2（2.3.232） |  |
| PaaS | Heroku | JawsDB（Herokuアドオン） |  |
| IDE, エディタ | Eclipse（2024 Pleiades） | Visual Studio Code（1.99.3） |  |
| OS | Windows 11 Pro 24H2（訓練校） | Windows 11 Pro 23H2（私用PC：Panasonic Let’s note CF-NX3） | MacOS 15.2（私用PC：MacBook Air）  |
| ブラウザ | Google Chrome（135.0.7049.97） | Safari（18.2） |  |
| バージョン管理システム | Git（クライアントソフトはSourcetree） |  |  |
| その他 | Excel（ER図の作成） | ChatGPT4o, o3（ダミーデータ,  jsファイルの生成） |  |

# ER図

*…主キー

FK…外部キー

矢印の根元が「1」、矢印の矢が「多」
![image](https://github.com/user-attachments/assets/de375d8d-6259-4721-ac35-76319b1bc2d0)


# アプリのURL
9/2追記 現在、複数の企業様にエントリーさせていただいております。同時接続がされた時はDBにコネクション数が上限を超え、アプリが立ち上がらないことがあります。
恐れ入りますが、その際は時間を変えてアクセスいただけると幸いでございます。お手数おかけして申し訳ございません。
[https://work-management-app-7126fd9d36c3.herokuapp.com/](https://work-management-app-7126fd9d36c3.herokuapp.com/)

# テスト用アカウント

ほとんどの日において、「吉塚」か「古賀」のどちらかは出勤しているので、従業員IDとパスワードはこの二名のものを使用すれば、ほぼすべての機能が確認できます。（タイムレコーダーの打刻の時に、ほかの従業員のIDが必要になる可能性があります）

| 名前 | 権限 | 従業員ID | パスワード | 備考 |
| --- | --- | --- | --- | --- |
| 吉塚 | 管理者（admin） | 1001 | yoshizuka01 | 「タイムレコーダー」と「管理者メニュー」に入れる |
| 古賀 | 一般ユーザー（user） | 1002 | koga09 | 初期状態でシフト未提出 |
| 黒崎 | 一般ユーザー（user） | 1003 | kurosaki21 |  |
| 東郷 | 一般ユーザー（user） | 1004 | togo13 |  |
| 小森江 | 一般ユーザー（user） | 1005 | komorie30 |  |
| 箱崎 | 一般ユーザー（user） | 1006 | hakozaki02 | 初期状態で欠勤申請中 |

「新規従業員登録」の機能を試されるときは、以下のデータをお使いください。

| 項目 | データ |
| --- | --- |
| 名前 | 千早 |
| 生年月日 | 2000-01-01 |
| 電話番号 | 030-2000-5000 |
| 住所 | 福岡県福岡市東区千早4丁目94 |
| パスワード | chihaya03 |

# URLパス一覧

{}で囲われている個所はプレゼンテーション層によって動的に生成

| 機能 | HTTPメソッド | URL | 権限 |
| --- | --- | --- | --- |
| ホーム画面 | GET | / |  |
| ログイン画面 | GET | /login |  |
| タイムレコーダー画面 | GET | /time-recorder | 管理者 |
| 打刻画面 | POST | /time-recorder/record | 管理者 |
| 出勤処理 | POST | /time-recorder/clock-in | 管理者 |
| 退勤処理 | POST | /time-recorder/clock-out | 管理者 |
| 管理者メニュー | GET | /admin | 管理者 |
| 従業員メニュー | GET | /user |  |
| 従業員一覧画面 | GET | /employees | 管理者 |
| 従業員詳細画面 | GET | /employees/detail/{employee-id} | 管理者 |
| 従業員登録画面 | GET | /employees/register | 管理者 |
| 従業員編集画面 | GET | /employees/edit/{employee-id} | 管理者 |
| 従業員更新処理 | POST | /employees/update | 管理者 |
| 従業員削除処理 | POST | /employees/delete/{employee-id} | 管理者 |
| パスワードリセット画面 | GET | /password/reset/{employee-id} | 管理者 |
| パスワード作成画面 | POST | /password/create |  |
| パスワード作成/更新処理 | POST | /password/update |  |
| シフト表画面 | GET | /shift |  |
| シフト希望登録画面 | GET | /shift-request |  |
| シフト希望送信処理 | POST | /shift-request/submit |  |
| シフト希望編集画面 | GET | /shift-request/edit |  |
| シフト表作成画面 | GET | /shift-management | 管理者 |
| シフト表作成処理 | POST | /shift-management/submit | 管理者 |
| シフト表編集画面 | GET | /shift-management/edit/{month} | 管理者 |
| 勤怠履歴画面（全従業員） | GET | /work-history/{month} | 管理者 |
| 勤怠履歴画面（個別の従業員） | GET | /work-history/{month}/{employee-id} |  |
| 勤怠履歴編集画面 | GET | /work-history/edit/{date}/{employee-id} | 管理者 |
| 勤怠履歴編集処理 | POST | /work-history/update | 管理者 |
| 欠勤申請一覧 | GET | /absence-application |  |
| 欠勤申請画面 | GET | /absence-application/request |  |
| 申請処理 | POST | /absence-application/submit |  |
| 承認処理 | POST | /absence-application/decision | 管理者 |
| 申請取り消し処理 | POST | /absence-application/delete/{application-id} |  |


# 実装した機能についての説明

## ホーム画面

<img width="1470" height="832" alt="ホーム画面" src="https://github.com/user-attachments/assets/b82d7bb5-9011-4913-a0a7-c6e5784c6199" />

## ログイン画面
<img width="1470" height="830" alt="ログイン画面" src="https://github.com/user-attachments/assets/e859b162-e5a2-46ab-b6f0-d01a076c4c82" />

### エラー１：アクセス権限のないページへの推移

「タイムレコーダー」と「管理者メニュー」に移るには管理者権限が必要です。権限のないアカウントでログインしようとするとエラーページに推移するようにしています。
<img width="1470" height="832" alt="権限のないページに遷移" src="https://github.com/user-attachments/assets/bfa51fbd-dd48-4648-8ac9-d39e7dcfcab7" />

### エラー２：IDまたはパスワード間違い
<img width="1470" height="832" alt="ログインエラー" src="https://github.com/user-attachments/assets/85282375-8e06-42de-8333-bacc4ea7e071" />

## タイムレコーダー画面
<img width="1470" height="832" alt="タイムレコーダートップ" src="https://github.com/user-attachments/assets/eb7f3353-2392-4aa1-879e-fcdf4cb50173" />

### エラー１：出勤予定のない従業員のIDを入力したとき、または存在しない従業員IDを入力したとき
<img width="1470" height="832" alt="従業員IDミスエラー" src="https://github.com/user-attachments/assets/14dd7629-598e-49d5-a902-1ebfa067b6b1" />

## 打刻画面
<img width="1470" height="832" alt="打刻画面" src="https://github.com/user-attachments/assets/4d5f262a-a31e-4a52-b47f-745621492666" />

### エラー１：出勤していないにもかかわらず退勤を押したとき
<img width="1470" height="305" alt="TRエラー①" src="https://github.com/user-attachments/assets/7c1a81a9-54cb-443b-9d9d-98615478983c" />

### エラー２：出勤を重複して押したとき
<img width="1470" height="305" alt="TRエラー②" src="https://github.com/user-attachments/assets/e9990c2a-fbf9-4ae2-a128-896817cb96d4" />

### エラー３：退勤を重複して押したとき
<img width="1470" height="304" alt="TRエラー③" src="https://github.com/user-attachments/assets/60510bee-ec6d-480c-8737-555c7a6cf7c0" />

## 打刻完了画面

### 出勤
<img width="1470" height="308" alt="出勤" src="https://github.com/user-attachments/assets/c07406ff-4bb9-46c4-bb92-eb2b26ac5faa" />

### 退勤
<img width="1470" height="308" alt="退勤" src="https://github.com/user-attachments/assets/57a6c5d3-2840-49d8-bfe3-34c2e3697a0e" />

## シフト表
<img width="1470" height="831" alt="シフト（タイムレコーダー）" src="https://github.com/user-attachments/assets/e66c9049-5e47-4c39-95e3-00440b036a23" />

従業員名にカーソルを合わせると、その従業員のシフトだけ表示されるようにしています。
![従業員名切り替え](https://github.com/user-attachments/assets/affa1444-55f2-44b2-bd0d-a59d312be180)

### ナビゲーションメニューの切り替え
どのページから推移してきたかで、ナビゲーションメニューに表示される内容が変更されるようになっております。
<br><br>
タイムレコーダーから
<img width="1470" height="171" alt="シフトnav（TR）" src="https://github.com/user-attachments/assets/d3f6c49f-2c8a-4781-b743-80782dc1d60b" />
管理者メニューから
<img width="1470" height="171" alt="シフトnav（管理者）" src="https://github.com/user-attachments/assets/fa486341-c823-4a53-95d2-6be4bed098f7" />
従業員メニューから
<img width="1470" height="171" alt="シフトnav（従業員）" src="https://github.com/user-attachments/assets/8b648fef-5413-4abc-9d52-33a7915ecf92" />


## 管理者メニュー

![image 15](https://github.com/user-attachments/assets/db1d34c4-474d-46a4-bd12-c62c0556bb88)

## シフト管理

htmlファイルは「シフト表」と同じものを使用していますが、「シフト表」ページでは「今月のシフト変更」と「来月のシフト作成」は表示されないようにしています。

![image 16](https://github.com/user-attachments/assets/68f241c1-0421-413d-8ea0-b6150972466e)

## 今月のシフト変更

「古賀」を「小森江」に変更する様子です。途中、「東郷」を追加しようとしていますが、6/17のシフトには既に「東郷」があるので、追加できないようになっています。ドラッグ&ドロップでシフトを追加していく部分に関しては、こちらのページ（[https://mr-hamasan.com/homemade-mogumogu-9/](https://mr-hamasan.com/homemade-mogumogu-9/)）を参考にしております。

![レコーディング_2025-06-17_112242_(1)](https://github.com/user-attachments/assets/6fad1490-716a-4011-aeb9-8c4a1df44bcd)

「古賀」から「小森江」に変更されています。

![image 17](https://github.com/user-attachments/assets/2151b80b-3916-4266-8bba-49577c5eb3e5)

## 来月のシフト作成

初期状態では「古賀」が未提出です。

![image 18](https://github.com/user-attachments/assets/b4333494-f10b-41df-a336-f92eea63dde0)

以下は「古賀」のシフトが提出された後、手動で「箱崎」をシフトに追加していき、シフトを作成する様子です。

![レコーディング_2025-06-17_122600](https://github.com/user-attachments/assets/26c2485f-72a8-47c7-bb42-aea872fa9b45)

なお、今回のアプリで使用しているjsファイルのほとんどはChatGPTによって生成しておりますが、「今月のシフト変更」と「来月のシフト作成」で使用しているjsファイル（\main\resources\static\js\shift\create-calendar.js）はChatGPTは使用せずに私自身で記述、編集しております。

## 勤怠履歴（管理者メニュー）

### 5月の全員分の勤怠履歴

![image 19](https://github.com/user-attachments/assets/8250b5b7-a9fc-4912-80c8-99250a64543e)

### 5月の「古賀」の勤怠履歴

![image 20](https://github.com/user-attachments/assets/fc04c620-1719-42ca-b234-8bbd8289064e)

## 勤怠履歴修正画面

![image 21](https://github.com/user-attachments/assets/f565ba70-b026-4eaf-adcc-0a9e2e94eebd)

### 修正後

勤怠履歴を修正した場合、更新後は「更新日時」が赤色になるようにしています。

![image 22](https://github.com/user-attachments/assets/05e9e001-cf2a-4112-8f6c-0124f9bf5140)

## 従業員一覧

![image 23](https://github.com/user-attachments/assets/d940fd94-3912-44c6-93d5-d19d4e0346c0)

## 従業員詳細

![image 24](https://github.com/user-attachments/assets/ab07a513-cdbc-4ab3-8695-ac17411d539b)

電話番号の市外局番については、念のために、現在使用されていない030にしております。

[https://time-space.kddi.com/ict-keywords/kaisetsu/20170725/2052.html#a04](https://time-space.kddi.com/ict-keywords/kaisetsu/20170725/2052.html#a04)

## 従業員情報編集

従業員「吉塚」の住所を、「吉塚本町13-28」から「竹下四丁目16-16」に変更します。

![image 25](https://github.com/user-attachments/assets/8ba1d794-f2ef-4388-a7d9-b0f62bf1fba5)

![image 26](https://github.com/user-attachments/assets/1430d880-8391-4450-8f8e-ede937e7c784)

無事に変更され、「更新日時」も更新されております。

![image 27](https://github.com/user-attachments/assets/4de1aa96-d571-4bb0-9fed-7f82c072acd0)

## 従業員削除

![image 28](https://github.com/user-attachments/assets/9318ad55-3206-4e50-8c5a-702412e7254b)

### エラー１：シフトに登録されている従業員を削除

シフトデータを保存するテーブル（shift_schedules）に外部制約がかかっており、従業員テーブル（employees）のemployee_idカラムを参照しているので、削除できないようになっております。

![image 29](https://github.com/user-attachments/assets/6984fa83-6ba5-4423-8eb1-3c87c2aa6ae9)

## パスワード再設定

![image 30](https://github.com/user-attachments/assets/7b137bc1-e95a-4048-b2ae-9e181481dd58)

![image 31](https://github.com/user-attachments/assets/3644c092-ebbe-4303-841c-46268489f65e)

### エラー１：入力パスワードと確認用パスワードの不一致

![image 32](https://github.com/user-attachments/assets/8f8aafdf-7a50-4594-8f75-1e3ae0e69e8a)

## 新規従業員登録

「従業員情報編集」と同一のHTMLファイルを用いております。Thymeleafで動的に表示を変更しております。

![image 33](https://github.com/user-attachments/assets/4589030f-0b84-4d3d-b992-fbe15aec474e)

こちらも、「パスワード再設定」と同じHTMLファイルを用いております。

![image 34](https://github.com/user-attachments/assets/38867404-e71a-4bb4-bd26-5d4ba7707e3b)

IDは連番で、DBに情報を登録するときにAUTO_INCREMENTで割り振られます。

![image 35](https://github.com/user-attachments/assets/cf3b751f-0c17-4c26-8f54-344b44ac6a56)

### エラー１：Validationチェック

![image 36](https://github.com/user-attachments/assets/6ab91e4b-eb24-48a6-b380-9f8a95eae577)

### エラー２：空欄のフィールドがあったとき

フロントエンドでも制御していますが、JavaのformクラスのフィールドでもNULLを許容しないようにしています。

![image 37](https://github.com/user-attachments/assets/f0123c1f-7337-4e00-8c28-06938a387ecd)

## 欠勤申請一覧（管理者メニュー）

アプリを立ち上げた初期状態では、申請ID5が未承認となっております。

![image 38](https://github.com/user-attachments/assets/c95fd69c-885b-4615-81fb-a182743c8109)

![image 39](https://github.com/user-attachments/assets/df6bfe3c-b7ef-4ee0-8a6b-6ed2dac471c9)

## 従業員メニュー

![image 40](https://github.com/user-attachments/assets/d4daa02a-548b-4b20-92ef-fbc20c3bd3ed)

## 従業員シフト提出

曜日の上にある、チェックボックスによる一括選択機能はChatGPTによって実装しております。

![レコーディング_2025-06-17_122026](https://github.com/user-attachments/assets/4f4cd026-fe10-47fb-84f6-37e605edc8a1)

### エラー１：日付を選択せずに提出する

![image 41](https://github.com/user-attachments/assets/ca09243d-89cc-43bc-9143-b60f0db8e1ec)

## 勤怠履歴（従業員メニュー）

![image 42](https://github.com/user-attachments/assets/81ef5d07-64de-4af5-ad84-98bcc217c763)

## 欠勤申請一覧（従業員メニュー）

![image 43](https://github.com/user-attachments/assets/305cd804-1f16-4c64-9239-108df587e1fa)

## 欠勤申請フォーム

申請する従業員のその月の出勤日しか選べないようにしております。

![image 44](https://github.com/user-attachments/assets/bf000c2b-6cc6-498d-bf5f-af99351e8570)

![image 45](https://github.com/user-attachments/assets/adbfe98a-a81e-44ca-9ce8-3c7c2fe0008f)

![image 46](https://github.com/user-attachments/assets/54ff8350-21e7-4d7b-ab17-0dc5c17f81ed)

![image 47](https://github.com/user-attachments/assets/0bc101b9-15f2-4196-8deb-7929f463f720)

また、当日に欠勤申請を出したときには、タイムレコーダーに申請者の名前が表示されるようにしております。

![image 48](https://github.com/user-attachments/assets/feeb4092-1b13-42cd-b1d9-b1b26c8f528f)

## 欠勤申請削除

![image 49](https://github.com/user-attachments/assets/9ede0ec7-d05e-4b30-8a19-efc7eb5690df)

# 実装予定の機能

- チャット方式の遅刻連絡機能（また、メッセージが届いた時の従業員へのメール送信機能）
- CSSの、Bootstrapへの置き換え
- 給与明細の発行
- シフト表のPDFでの発行、メールでの送信
- 勤怠履歴に給与計算機能
- シフト作成の際、登録状況と連動して各従業員の出勤日数を表示し、人件費計算も行う
- JavaScriptの技術力不足により、シフトの出退勤時間は固定になっているが、汎用性を高めるため、出退勤時間を変動にしたい。
- 上記に伴い、深夜/早朝出勤と残業手当も計算できるようにしたい。

# 開発して感じたこと

- 技術書を何回も読むより、実際に何か作ってみた方が技術の吸収が早いことを実感しました。このアプリの開発を通じて、非常に多くのことを学べました。
- ChatGPTは思ったほど万能ではないことを実感しました。特に、ダミーデータの生成で条件を満たさないデータを出力したり、CSSファイルの整形において、勝手に要素を省くなど、あくまでも補助的な利用にとどまるのが現状だと思いました。
- テストコードを書くことで、無駄なメソッドの存在や、メソッド名の不備に気づけました。様々な視点からコードを見ることの重要性がわかりました。

# 苦労したこと

- テーブル設計を途中で変更したために、影響する箇所を探してひとつずつ修正する作業に時間がかかりました。設計工程の重要性を実感いたしました。
- FullCalendarを組み込む際、もととなるjsファイルはChatGPTによって生成したのですが、カスタマイズに関しては公式ドキュメントやウェブ上の記事を参考にしました。JavaScriptは書いたことがなかったので、手探りで修正していきました。
- 作成していくうちに技術に対する理解が深まっていったため、初期のころに作成した部分に違和感を覚えることが多く、修正箇所があちこちに発生しました。コードの独立性を意識するきっかけになりました。
- CSSファイルを編集しても、意図した幅にならなかったり、意図した要素に色がつかなかったり、といったことがありました。
- テストコードが全然通過しなくてエラーの内容を元に修正し、また通らずに修正し、という作業が頻発しました。一つのメソッドの修正に1時間かかったこともあります。

# 今後の展望

- Springの学習には、『Spring Framework超入門（著：樹下政章/技術評論社）』を使用しました。初学者として学ぶには非常に読みやすい本だったのですが、開発していく中で、この本の内容だけでは不十分であることを実感いたしました。なので、より知識の網羅性の高い『プロになるためのSpring入門（著：土岐孝平/技術評論社）』をもとに、アプリをブラッシュアップできればと思います。
- また、『Spring Framework超入門』ではソフトウェアテストについては扱われていなかったので、ひと通り開発を終えてから『プロになるためのSpring入門』を参考にテストコードを書き始めました。ただ、知識の浅さゆえに、あまり効率のいいコーディングができなかった（時間が非常にかかった）という反省があるので、『Javaエンジニアのためのソフトウェアテスト実践入門（著：斉藤賢哉/技術評論社）』をもとに、テスト手法についての知識を深めていければと思いました。
- 『現場で役立つシステム設計の原則（著：増田亨/技術評論社）』の内容をもとに、現在のトランザクションスクリプトによる設計（データクラスと機能クラスに分ける手続き型のアプローチ）から、ドメインモデルによる設計（データと業務ロジックをひとつのクラスにまとめるオブジェクト指向型のアプローチ）へのリファクタリングをしていきたく思います。
