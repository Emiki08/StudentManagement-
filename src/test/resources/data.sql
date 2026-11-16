INSERT INTO students
(name, kana_name, nickname, email, area, age, sex, remark, is_deleted)
VALUES
    ('山田太郎', 'ヤマダタロウ', '太郎', 'yamada@example.com', '東京', 25, '男性', 'Javaコース受講生', FALSE),
    ('伊藤花', 'イトウハナ', '花ちゃん', 'ito@example.com', '大阪', 22, '女性', 'AWSコース受講生', FALSE);

INSERT INTO students_courses
(student_id, course_name, course_start_at, course_end_at)
VALUES
    (1, 'Javaコース', '2026-07-19 11:21:29', '2027-07-19 11:21:29'),
    (2, 'AWSコース', '2026-07-19 11:21:29', '2027-07-19 11:21:29');