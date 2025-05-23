
-- 사용자
INSERT INTO user (birth_date, personal_info, created_at, updated_at, email, full_name, password, phone_number, provider, provider_id, username, membership, role) VALUES
  ('2000-01-31', 1, '2025-04-05 07:00:30', '2025-04-05 07:00:30', 'test1@example.com', 'sunghui1', '$2a$10$r7jHY0.XZ0rpcKDYyS7vD.EIW7oh3y0qf4cJ2uJmznWD/PXDpEv.u', NULL, NULL, NULL, 'testuser1', 'SEED', 'MEMBER'),
  ('2000-01-31', 1, '2025-04-05 07:00:30', '2025-04-05 07:00:30', 'test2@example.com', 'sunghui2', '$2a$10$r7jHY0.XZ0rpcKDYyS7vD.EIW7oh3y0qf4cJ2uJmznWD/PXDpEv.u', NULL, NULL, NULL, 'testuser2', 'SEED', 'MEMBER'),
  ('2000-01-31', 1, '2025-04-05 07:00:30', '2025-04-05 07:00:30', 'test3@example.com', 'sunghui3', '$2a$10$r7jHY0.XZ0rpcKDYyS7vD.EIW7oh3y0qf4cJ2uJmznWD/PXDpEv.u', NULL, NULL, NULL, 'testuser3', 'SEED', 'MEMBER'),
  ('2000-01-31', 1, '2025-04-05 07:00:30', '2025-04-05 07:00:30', 'test4@example.com', 'sunghui4', '$2a$10$r7jHY0.XZ0rpcKDYyS7vD.EIW7oh3y0qf4cJ2uJmznWD/PXDpEv.u', NULL, NULL, NULL, 'testuser4', 'SEED', 'MEMBER'),
  ('2000-01-31', 1, '2025-04-05 07:00:30', '2025-04-05 07:00:30', 'test5@example.com', 'sunghui5', '$2a$10$r7jHY0.XZ0rpcKDYyS7vD.EIW7oh3y0qf4cJ2uJmznWD/PXDpEv.u', NULL, NULL, NULL, 'testuser5', 'SEED', 'MEMBER'),
  ('2000-01-31', 1, '2025-04-05 07:00:30', '2025-04-05 07:00:30', 'test6@example.com', 'jieun1', '$2a$10$r7jHY0.XZ0rpcKDYyS7vD.EIW7oh3y0qf4cJ2uJmznWD/PXDpEv.u', NULL, NULL, NULL, 'testuser6', 'SEED', 'MEMBER'),
  ('2000-01-31', 1, '2025-04-05 07:00:30', '2025-04-05 07:00:30', 'test7@example.com', 'jieun2', '$2a$10$r7jHY0.XZ0rpcKDYyS7vD.EIW7oh3y0qf4cJ2uJmznWD/PXDpEv.u', NULL, NULL, NULL, 'testuser7', 'SEED', 'MEMBER'),
  ('2000-01-31', 1, '2025-04-05 07:00:30', '2025-04-05 07:00:30', 'test8@example.com', 'jieun3', '$2a$10$r7jHY0.XZ0rpcKDYyS7vD.EIW7oh3y0qf4cJ2uJmznWD/PXDpEv.u', NULL, NULL, NULL, 'testuser8', 'SEED', 'MEMBER'),
  ('2000-01-31', 1, '2025-04-05 07:00:30', '2025-04-05 07:00:30', 'test9@example.com', 'jieun4', '$2a$10$r7jHY0.XZ0rpcKDYyS7vD.EIW7oh3y0qf4cJ2uJmznWD/PXDpEv.u', NULL, NULL, NULL, 'testuser9', 'SEED', 'MEMBER'),
  ('2000-01-31', 1, '2025-04-05 07:00:30', '2025-04-05 07:00:30', 'test10@example.com', 'jieun5', '$2a$10$r7jHY0.XZ0rpcKDYyS7vD.EIW7oh3y0qf4cJ2uJmznWD/PXDpEv.u', NULL, NULL, NULL, 'testuser10', 'SEED', 'MEMBER');

-- 카테고리
INSERT INTO category (created_at, id, updated_at, bottom_category, middle_category, top_category)
VALUES
    ('2025-04-05 07:34:30', 1, '2025-04-05 07:34:30', 'SNEAKERS', 'SHOES', 'MALE'),
    ('2025-04-05 07:34:30', 2, '2025-04-05 07:34:30', 'DRESS_SHOES', 'SHOES', 'MALE'),
    ('2025-04-05 07:34:30', 3, '2025-04-05 07:34:30', 'DRESS_SHOES', 'SHOES', 'FEMALE'),
    ('2025-04-05 07:34:30', 4, '2025-04-05 07:34:30', 'HOODIE', 'TOP', 'MALE'),
    ('2025-04-05 07:34:30', 5, '2025-04-05 07:34:30', 'KNIT_SWEATER', 'TOP', 'FEMALE'),
    ('2025-04-05 07:34:30', 6, '2025-04-05 07:34:30', 'DRESS_SHOES', 'PANT', 'FEMALE'),
    ('2025-04-05 07:34:30', 7, '2025-04-05 07:34:30', 'MIDI_SKIRT', 'SKIRT', 'FEMALE'),
    ('2025-04-05 07:34:30', 8, '2025-04-05 07:34:30', 'DENIM_PANTS', 'PANT', 'MALE'),
    ('2025-04-05 07:34:30', 9, '2025-04-05 07:34:30', 'WINTER_COAT', 'TOP', 'MALE'),
    ('2025-04-05 07:34:30', 10, '2025-04-05 07:34:30', 'LEATHER_RIDERS_JACKET', 'TOP', 'FEMALE');

-- 상품 상태
INSERT INTO product_condition (id, created_at, condition_category, updated_at) VALUES
   (1, '2025-04-05 07:00:30', 'UNOPENED', '2025-04-05 07:00:30'),
   (2, '2025-04-05 07:00:30', 'LIKE_NEW', '2025-04-05 07:00:30'),
   (3, '2025-04-05 07:00:30', 'GOOD', '2025-04-05 07:00:30'),
   (4, '2025-04-05 07:00:30', 'FAIR', '2025-04-05 07:00:30'),
   (5, '2025-04-05 07:00:30', 'POOR', '2025-04-05 07:00:30');


-- 상품
INSERT INTO product (is_active, user_id, is_free, is_sold, price, created_at, product_condition_id, include_shipping, like_count, updated_at, description, name) VALUES
     (1, 1, 0, 0, 15000, '2025-04-05 07:00:31', 1, 1, 100, '2025-04-05 07:00:31', '스케이트보더들이 사랑하는 튼튼한 신발이에요.', '아디다스 슈퍼스타'),
     (1, 2, 0, 0, 10000, '2025-04-05 07:00:32', 2, 0, 10, '2025-04-05 07:00:32', '고급스러운 디자인의 남성 정장 구두입니다.', '금강제화 남성 구두'),
     (1, 3, 0, 0, 5000, '2025-04-05 07:00:33', 3, 1, 1, '2025-04-05 07:00:33', '편안하고 세련된 여성용 로퍼입니다.', '탠디 여성 로퍼'),
     (1, 4, 0, 0, 10000, '2025-04-05 07:00:34', 4, 1, 2, '2025-04-05 07:00:34', '캐주얼하게 입기 좋은 남성 후디입니다.', '챔피온 남성 후디'),
     (1, 5, 0, 0, 2000, '2025-04-05 07:00:35', 5, 1, 3, '2025-04-05 07:00:35', '부드럽고 따뜻한 여성 니트 스웨터입니다.', '자라 여성 니트 스웨터'),
     (1, 6, 0, 0, 10000, '2025-04-05 07:00:36', 1, 1, 4, '2025-04-05 07:00:36', '트렌디하고 편안한 여성 와이드 팬츠입니다.', '유니클로 여성 와이드 팬츠'),
     (1, 7, 0, 0, 15000, '2025-04-05 07:00:37', 2, 1, 5, '2025-04-05 07:00:37', '사랑스러운 디자인의 여성 스커트입니다.', '에잇세컨즈 여성 스커트'),
     (1, 8, 0, 0, 2000, '2025-04-05 07:00:38', 3, 1, 11, '2025-04-05 07:00:38', '클래식한 디자인의 남성 데님 팬츠입니다.', '리바이스 남성 데님 팬츠'),
     (1, 9, 0, 0, 20000, '2025-04-05 07:00:39', 4, 1, 12, '2025-04-05 07:00:39', '겨울철 따뜻하고 세련된 남성 코트입니다.', '지오지아 남성 코트'),
     (1, 10, 0, 0, 20000, '2025-04-05 07:00:40', 5, 1, 13, '2025-04-05 07:00:40', '스타일리시한 여성용 재킷입니다.', 'H&M 여성 재킷'),
     (1, 1, 0, 0, 20000, '2025-04-05 07:00:41', 1, 1, 14, '2025-04-05 07:00:41', '스케이트보더들이 사랑하는 튼튼한 신발이에요.', '아디다스 슈퍼스타'),
     (1, 2, 0, 0, 20000, '2025-04-05 07:00:42', 2, 1, 15, '2025-04-05 07:00:42', '고급스러운 디자인의 남성 정장 구두입니다.', '금강제화 남성 구두'),
     (1, 3, 0, 0, 2000, '2025-04-05 07:00:43', 3, 1, 16, '2025-04-05 07:00:43', '편안하고 세련된 여성용 로퍼입니다.', '탠디 여성 로퍼'),
     (1, 4, 0, 0, 10000, '2025-04-05 07:00:44', 4, 1, 22, '2025-04-05 07:00:44', '캐주얼하게 입기 좋은 남성 후디입니다.', '챔피온 남성 후디'),
     (1, 5, 0, 0, 2000, '2025-04-05 07:00:45', 5, 1, 261, '2025-04-05 07:00:45', '부드럽고 따뜻한 여성 니트 스웨터입니다.', '자라 여성 니트 스웨터'),
     (1, 6, 0, 0, 10000, '2025-04-05 07:00:46', 1, 1, 163, '2025-04-05 07:00:46', '트렌디하고 편안한 여성 와이드 팬츠입니다.', '유니클로 여성 와이드 팬츠'),
     (1, 7, 0, 0, 10000, '2025-04-05 07:00:47', 2, 1, 161, '2025-04-05 07:00:47', '사랑스러운 디자인의 여성 스커트입니다.', '에잇세컨즈 여성 스커트'),
     (1, 8, 0, 0, 5000, '2025-04-05 07:00:48', 3, 1, 263, '2025-04-05 07:00:48', '클래식한 디자인의 남성 데님 팬츠입니다.', '리바이스 남성 데님 팬츠'),
     (1, 9, 0, 0, 2000, '2025-04-05 07:00:49', 4, 1, 246, '2025-04-05 07:00:49', '겨울철 따뜻하고 세련된 남성 코트입니다.', '지오지아 남성 코트'),
     (1, 10, 0, 0, 5000, '2025-04-05 07:00:50', 5, 0, 26, '2025-04-05 07:00:50', '스타일리시한 여성용 재킷입니다.', 'H&M 여성 재킷'),
     (1, 1, 0, 0, 20000, '2025-04-05 07:00:51', 1, 1, 644, '2025-04-05 07:00:51', '스케이트보더들이 사랑하는 튼튼한 신발이에요.', '아디다스 슈퍼스타'),
     (1, 2, 0, 0, 2000, '2025-04-05 07:00:52', 2, 1, 35, '2025-04-05 07:00:52', '고급스러운 디자인의 남성 정장 구두입니다.', '금강제화 남성 구두'),
     (1, 3, 0, 0, 15000, '2025-04-05 07:00:53', 3, 1, 42, '2025-04-05 07:00:53', '편안하고 세련된 여성용 로퍼입니다.', '탠디 여성 로퍼'),
     (1, 4, 0, 0, 2000, '2025-04-05 07:00:54', 4, 1, 13, '2025-04-05 07:00:54', '캐주얼하게 입기 좋은 남성 후디입니다.', '챔피온 남성 후디'),
     (1, 5, 0, 0, 5000, '2025-04-05 07:00:55', 5, 1, 56, '2025-04-05 07:00:55', '부드럽고 따뜻한 여성 니트 스웨터입니다.', '자라 여성 니트 스웨터'),
     (1, 6, 0, 0, 10000, '2025-04-05 07:00:56', 1, 0, 765, '2025-04-05 07:00:56', '트렌디하고 편안한 여성 와이드 팬츠입니다.', '유니클로 여성 와이드 팬츠'),
     (1, 7, 0, 0, 5000, '2025-04-05 07:00:57', 2, 1, 23, '2025-04-05 07:00:57', '사랑스러운 디자인의 여성 스커트입니다.', '에잇세컨즈 여성 스커트'),
     (1, 8, 0, 0, 20000, '2025-04-05 07:00:58', 3, 1, 4, '2025-04-05 07:00:58', '클래식한 디자인의 남성 데님 팬츠입니다.', '리바이스 남성 데님 팬츠'),
     (1, 9, 0, 0, 2000, '2025-04-05 07:00:59', 4, 1, 53, '2025-04-05 07:00:59', '겨울철 따뜻하고 세련된 남성 코트입니다.', '지오지아 남성 코트'),
     (1, 10, 0, 0, 20000, '2025-04-05 07:01:00', 5, 1, 24, '2025-04-05 07:01:00', '스타일리시한 여성용 재킷입니다.', 'H&M 여성 재킷'),
     (1, 1, 0, 0, 2000, '2025-04-05 07:01:01', 1, 1, 64, '2025-04-05 07:01:01', '스케이트보더들이 사랑하는 튼튼한 신발이에요.', '아디다스 슈퍼스타'),
     (1, 2, 0, 0, 5000, '2025-04-05 07:01:02', 2, 1, 34, '2025-04-05 07:01:02', '고급스러운 디자인의 남성 정장 구두입니다.', '금강제화 남성 구두'),
     (1, 3, 0, 0, 10000, '2025-04-05 07:01:03', 3, 1, 23, '2025-04-05 07:01:03', '편안하고 세련된 여성용 로퍼입니다.', '탠디 여성 로퍼'),
     (1, 4, 0, 0, 20000, '2025-04-05 07:01:04', 4, 1, 531, '2025-04-05 07:01:04', '캐주얼하게 입기 좋은 남성 후디입니다.', '챔피온 남성 후디'),
     (1, 5, 0, 0, 5000, '2025-04-05 07:01:05', 5, 1, 131, '2025-04-05 07:01:05', '부드럽고 따뜻한 여성 니트 스웨터입니다.', '자라 여성 니트 스웨터'),
     (1, 6, 0, 0, 15000, '2025-04-05 07:01:06', 1, 1, 43, '2025-04-05 07:01:06', '트렌디하고 편안한 여성 와이드 팬츠입니다.', '유니클로 여성 와이드 팬츠'),
     (1, 7, 0, 0, 15000, '2025-04-05 07:01:07', 2, 1, 646, '2025-04-05 07:01:07', '사랑스러운 디자인의 여성 스커트입니다.', '에잇세컨즈 여성 스커트'),
     (1, 8, 0, 0, 10000, '2025-04-05 07:01:08', 3, 0, 35, '2025-04-05 07:01:08', '클래식한 디자인의 남성 데님 팬츠입니다.', '리바이스 남성 데님 팬츠'),
     (1, 9, 0, 0, 20000, '2025-04-05 07:01:09', 4, 0, 64, '2025-04-05 07:01:09', '겨울철 따뜻하고 세련된 남성 코트입니다.', '지오지아 남성 코트'),
     (1, 10, 0, 0, 2000, '2025-04-05 07:01:10', 5, 1, 35, '2025-04-05 07:01:10', '스타일리시한 여성용 재킷입니다.', 'H&M 여성 재킷'),
     (1, 1, 0, 0, 20000, '2025-04-05 07:01:11', 1, 0, 72, '2025-04-05 07:01:11', '스케이트보더들이 사랑하는 튼튼한 신발이에요.', '아디다스 슈퍼스타'),
     (1, 2, 0, 0, 5000, '2025-04-05 07:01:12', 2, 1, 34, '2025-04-05 07:01:12', '고급스러운 디자인의 남성 정장 구두입니다.', '금강제화 남성 구두'),
     (1, 3, 0, 0, 5000, '2025-04-05 07:01:13', 3, 1, 54, '2025-04-05 07:01:13', '편안하고 세련된 여성용 로퍼입니다.', '탠디 여성 로퍼'),
     (1, 4, 0, 0, 15000, '2025-04-05 07:01:14', 4, 0, 65, '2025-04-05 07:01:14', '캐주얼하게 입기 좋은 남성 후디입니다.', '챔피온 남성 후디'),
     (1, 5, 0, 0, 15000, '2025-04-05 07:01:15', 5, 1, 75, '2025-04-05 07:01:15', '부드럽고 따뜻한 여성 니트 스웨터입니다.', '자라 여성 니트 스웨터'),
     (1, 6, 0, 0, 10000, '2025-04-05 07:01:16', 1, 0, 86, '2025-04-05 07:01:16', '트렌디하고 편안한 여성 와이드 팬츠입니다.', '유니클로 여성 와이드 팬츠'),
     (1, 7, 0, 0, 10000, '2025-04-05 07:01:17', 2, 0, 64, '2025-04-05 07:01:17', '사랑스러운 디자인의 여성 스커트입니다.', '에잇세컨즈 여성 스커트'),
     (1, 8, 0, 0, 2000, '2025-04-05 07:01:18', 3, 1, 76, '2025-04-05 07:01:18', '클래식한 디자인의 남성 데님 팬츠입니다.', '리바이스 남성 데님 팬츠'),
     (1, 9, 0, 0, 10000, '2025-04-05 07:01:19', 4, 1, 864, '2025-04-05 07:01:19', '겨울철 따뜻하고 세련된 남성 코트입니다.', '지오지아 남성 코트'),
     (1, 10, 0, 0, 5000, '2025-04-05 07:01:20', 5, 0, 325, '2025-04-05 07:01:20', '스타일리시한 여성용 재킷입니다.', 'H&M 여성 재킷');
-- 이미지
INSERT INTO product_image (id, created_at, product_id, updated_at, image_url) VALUES
      (1, '2025-04-21 23:30:04', 1, '2025-04-21 23:30:04', 'https://picsum.photos/id/775/350/450'),
      (2, '2025-04-21 23:30:04', 2, '2025-04-21 23:30:04', 'https://picsum.photos/id/91/360/360'),
      (3, '2025-04-21 23:30:04', 3, '2025-04-21 23:30:04', 'https://picsum.photos/id/1055/350/450'),
      (4, '2025-04-21 23:30:04', 4, '2025-04-21 23:30:04', 'https://picsum.photos/id/940/340/510'),
      (5, '2025-04-21 23:30:04', 5, '2025-04-21 23:30:04', 'https://picsum.photos/id/492/400/500'),
      (6, '2025-04-21 23:30:04', 6, '2025-04-21 23:30:04', 'https://picsum.photos/id/10/300/400'),
      (7, '2025-04-21 23:30:04', 7, '2025-04-21 23:30:04', 'https://picsum.photos/id/1035/340/510'),
      (8, '2025-04-21 23:30:04', 8, '2025-04-21 23:30:04', 'https://picsum.photos/id/1025/340/510'),
      (9, '2025-04-21 23:30:04', 9, '2025-04-21 23:30:04', 'https://picsum.photos/id/1014/320/480'),
      (10, '2025-04-21 23:30:04', 10, '2025-04-21 23:30:04', 'https://picsum.photos/id/1012/280/420'),
      (11, '2025-04-21 23:30:04', 11, '2025-04-21 23:30:04', 'https://picsum.photos/id/1005/320/480'),
      (12, '2025-04-21 23:30:04', 12, '2025-04-21 23:30:04', 'https://picsum.photos/id/1001/280/420'),
      (13, '2025-04-21 23:30:04', 13, '2025-04-21 23:30:04', 'https://picsum.photos/id/856/340/510'),
      (14, '2025-04-21 23:30:04', 14, '2025-04-21 23:30:04', 'https://picsum.photos/id/839/360/360'),
      (15, '2025-04-21 23:30:04', 15, '2025-04-21 23:30:04', 'https://picsum.photos/id/838/300/400'),
      (16, '2025-04-21 23:30:04', 16, '2025-04-21 23:30:04', 'https://picsum.photos/id/836/350/450'),
      (17, '2025-04-21 23:30:04', 17, '2025-04-21 23:30:04', 'https://picsum.photos/id/832/300/400'),
      (18, '2025-04-21 23:30:04', 18, '2025-04-21 23:30:04', 'https://picsum.photos/id/823/350/450'),
      (19, '2025-04-21 23:30:04', 19, '2025-04-21 23:30:04', 'https://picsum.photos/id/822/320/480'),
      (20, '2025-04-21 23:30:04', 20, '2025-04-21 23:30:04', 'https://picsum.photos/id/817/360/360'),
      (21, '2025-04-21 23:30:04', 21, '2025-04-21 23:30:04', 'https://picsum.photos/id/338/350/450'),
      (22, '2025-04-21 23:30:04', 22, '2025-04-21 23:30:04', 'https://picsum.photos/id/804/320/480'),
      (23, '2025-04-21 23:30:04', 23, '2025-04-21 23:30:04', 'https://picsum.photos/id/786/340/510'),
      (24, '2025-04-21 23:30:04', 24, '2025-04-21 23:30:04', 'https://picsum.photos/id/778/360/360'),
      (25, '2025-04-21 23:30:04', 25, '2025-04-21 23:30:04', 'https://picsum.photos/id/777/360/360'),
      (26, '2025-04-21 23:30:04', 26, '2025-04-21 23:30:04', 'https://picsum.photos/id/768/340/510'),
      (27, '2025-04-21 23:30:04', 27, '2025-04-21 23:30:04', 'https://picsum.photos/id/758/360/360'),
      (28, '2025-04-21 23:30:04', 28, '2025-04-21 23:30:04', 'https://picsum.photos/id/726/400/500'),
      (29, '2025-04-21 23:30:04', 29, '2025-04-21 23:30:04', 'https://picsum.photos/id/685/280/420'),
      (30, '2025-04-21 23:30:04', 30, '2025-04-21 23:30:04', 'https://picsum.photos/id/669/350/450'),
      (31, '2025-04-21 23:30:04', 31, '2025-04-21 23:30:04', 'https://picsum.photos/id/665/300/400'),
      (32, '2025-04-21 23:30:04', 32, '2025-04-21 23:30:04', 'https://picsum.photos/id/661/300/400'),
      (33, '2025-04-21 23:30:04', 33, '2025-04-21 23:30:04', 'https://picsum.photos/id/646/280/420'),
      (34, '2025-04-21 23:30:04', 34, '2025-04-21 23:30:04', 'https://picsum.photos/id/633/400/500'),
      (35, '2025-04-21 23:30:04', 35, '2025-04-21 23:30:04', 'https://picsum.photos/id/628/300/400'),
      (36, '2025-04-21 23:30:04', 36, '2025-04-21 23:30:04', 'https://picsum.photos/id/604/320/480'),
      (37, '2025-04-21 23:30:04', 37, '2025-04-21 23:30:04', 'https://picsum.photos/id/550/280/420'),
      (38, '2025-04-21 23:30:04', 38, '2025-04-21 23:30:04', 'https://picsum.photos/id/548/340/510'),
      (39, '2025-04-21 23:30:04', 39, '2025-04-21 23:30:04', 'https://picsum.photos/id/535/400/500'),
      (40, '2025-04-21 23:30:04', 40, '2025-04-21 23:30:04', 'https://picsum.photos/id/513/320/480'),
      (41, '2025-04-21 23:30:04', 41, '2025-04-21 23:30:04', 'https://picsum.photos/id/473/280/420'),
      (42, '2025-04-21 23:30:04', 42, '2025-04-21 23:30:04', 'https://picsum.photos/id/455/320/480'),
      (43, '2025-04-21 23:30:04', 43, '2025-04-21 23:30:04', 'https://picsum.photos/id/449/400/500'),
      (44, '2025-04-21 23:30:04', 44, '2025-04-21 23:30:04', 'https://picsum.photos/id/447/360/360'),
      (45, '2025-04-21 23:30:04', 45, '2025-04-21 23:30:04', 'https://picsum.photos/id/399/360/360'),
      (46, '2025-04-21 23:30:04', 46, '2025-04-21 23:30:04', 'https://picsum.photos/id/815/350/450'),
      (47, '2025-04-21 23:30:04', 47, '2025-04-21 23:30:04', 'https://picsum.photos/id/326/400/500'),
      (48, '2025-04-21 23:30:04', 48, '2025-04-21 23:30:04', 'https://picsum.photos/id/103/300/400'),
      (49, '2025-04-21 23:30:04', 49, '2025-04-21 23:30:04', 'https://picsum.photos/id/65/280/420'),
      (50, '2025-04-21 23:30:04', 50, '2025-04-21 23:30:04', 'https://picsum.photos/id/64/400/500');


-- 태그
INSERT INTO product_tag (id, created_at, product_id, updated_at, tag_name) VALUES
       (1, '2025-04-05 07:00:31', 1, '2025-04-05 07:00:31', '스니커즈'),
       (2, '2025-04-05 07:00:31', 1, '2025-04-05 07:00:31', '남성신발'),
       (3, '2025-04-05 07:00:32', 2, '2025-04-05 07:00:32', '정장구두'),
       (4, '2025-04-05 07:00:32', 2, '2025-04-05 07:00:32', '남성정장'),
       (5, '2025-04-05 07:00:33', 3, '2025-04-05 07:00:33', '로퍼'),
       (6, '2025-04-05 07:00:33', 3, '2025-04-05 07:00:33', '여성신발'),
       (7, '2025-04-05 07:00:34', 4, '2025-04-05 07:00:34', '후드티'),
       (8, '2025-04-05 07:00:34', 4, '2025-04-05 07:00:34', '캐주얼'),
       (9, '2025-04-05 07:00:35', 5, '2025-04-05 07:00:35', '니트'),
       (10, '2025-04-05 07:00:35', 5, '2025-04-05 07:00:35', '여성의류'),
       (11, '2025-04-05 07:00:36', 6, '2025-04-05 07:00:36', '와이드팬츠'),
       (12, '2025-04-05 07:00:36', 6, '2025-04-05 07:00:36', '여성하의'),
       (13, '2025-04-05 07:00:37', 7, '2025-04-05 07:00:37', '스커트'),
       (14, '2025-04-05 07:00:37', 7, '2025-04-05 07:00:37', '여성하의'),
       (15, '2025-04-05 07:00:38', 8, '2025-04-05 07:00:38', '데님팬츠'),
       (16, '2025-04-05 07:00:38', 8, '2025-04-05 07:00:38', '남성청바지'),
       (17, '2025-04-05 07:00:39', 9, '2025-04-05 07:00:39', '코트'),
       (18, '2025-04-05 07:00:39', 9, '2025-04-05 07:00:39', '겨울아우터'),
       (19, '2025-04-05 07:00:40', 10, '2025-04-05 07:00:40', '자켓'),
       (20, '2025-04-05 07:00:40', 10, '2025-04-05 07:00:40', '여성아우터'),
       (21, '2025-04-05 07:00:41', 11, '2025-04-05 07:00:41', '스니커즈'),
       (22, '2025-04-05 07:00:41', 11, '2025-04-05 07:00:41', '남성신발'),
       (23, '2025-04-05 07:00:42', 12, '2025-04-05 07:00:42', '정장구두'),
       (24, '2025-04-05 07:00:42', 12, '2025-04-05 07:00:42', '포멀룩'),
       (25, '2025-04-05 07:00:43', 13, '2025-04-05 07:00:43', '로퍼'),
       (26, '2025-04-05 07:00:43', 13, '2025-04-05 07:00:43', '여성슈즈'),
       (27, '2025-04-05 07:00:44', 14, '2025-04-05 07:00:44', '후드티'),
       (28, '2025-04-05 07:00:44', 14, '2025-04-05 07:00:44', '스트릿패션'),
       (29, '2025-04-05 07:00:45', 15, '2025-04-05 07:00:45', '니트'),
       (30, '2025-04-05 07:00:45', 15, '2025-04-05 07:00:45', '여성의류'),
       (31, '2025-04-05 07:00:46', 16, '2025-04-05 07:00:46', '와이드팬츠'),
       (32, '2025-04-05 07:00:46', 16, '2025-04-05 07:00:46', '여성하의'),
       (33, '2025-04-05 07:00:47', 17, '2025-04-05 07:00:47', '스커트'),
       (34, '2025-04-05 07:00:47', 17, '2025-04-05 07:00:47', '트렌디'),
       (35, '2025-04-05 07:00:48', 18, '2025-04-05 07:00:48', '데님팬츠'),
       (36, '2025-04-05 07:00:48', 18, '2025-04-05 07:00:48', '캐주얼'),
       (37, '2025-04-05 07:00:49', 19, '2025-04-05 07:00:49', '코트'),
       (38, '2025-04-05 07:00:49', 19, '2025-04-05 07:00:49', '가을아우터'),
       (39, '2025-04-05 07:00:50', 20, '2025-04-05 07:00:50', '자켓'),
       (40, '2025-04-05 07:00:50', 20, '2025-04-05 07:00:50', '여성자켓'),
       (41, '2025-04-05 07:00:51', 21, '2025-04-05 07:00:51', '스니커즈'),
       (42, '2025-04-05 07:00:51', 21, '2025-04-05 07:00:51', '운동화'),
       (43, '2025-04-05 07:00:52', 22, '2025-04-05 07:00:52', '정장구두'),
       (44, '2025-04-05 07:00:52', 22, '2025-04-05 07:00:52', '남성정장'),
       (45, '2025-04-05 07:00:53', 23, '2025-04-05 07:00:53', '로퍼'),
       (46, '2025-04-05 07:00:53', 23, '2025-04-05 07:00:53', '여성캐주얼'),
       (47, '2025-04-05 07:00:54', 24, '2025-04-05 07:00:54', '후드티'),
       (48, '2025-04-05 07:00:54', 24, '2025-04-05 07:00:54', '남성상의'),
       (49, '2025-04-05 07:00:55', 25, '2025-04-05 07:00:55', '니트'),
       (50, '2025-04-05 07:00:55', 25, '2025-04-05 07:00:55', '여성상의'),
       (51, '2025-04-05 07:00:56', 26, '2025-04-05 07:00:56', '운동화'),
       (52, '2025-04-05 07:00:56', 26, '2025-04-05 07:00:56', '캐주얼'),
       (53, '2025-04-05 07:00:57', 27, '2025-04-05 07:00:57', '포멀룩'),
       (54, '2025-04-05 07:00:57', 27, '2025-04-05 07:00:57', '남성슈즈'),
       (55, '2025-04-05 07:00:58', 28, '2025-04-05 07:00:58', '스트릿패션'),
       (56, '2025-04-05 07:00:58', 28, '2025-04-05 07:00:58', '트렌디'),
       (57, '2025-04-05 07:00:59', 29, '2025-04-05 07:00:59', '가을아우터'),
       (58, '2025-04-05 07:00:59', 29, '2025-04-05 07:00:59', '여성자켓'),
       (59, '2025-04-05 07:01:00', 30, '2025-04-05 07:01:00', '남성상의'),
       (60, '2025-04-05 07:01:00', 30, '2025-04-05 07:01:00', '여성상의'),
       (61, '2025-04-05 07:01:01', 31, '2025-04-05 07:01:01', '스니커즈'),
       (62, '2025-04-05 07:01:01', 31, '2025-04-05 07:01:01', '남성신발'),
       (63, '2025-04-05 07:01:02', 32, '2025-04-05 07:01:02', '정장구두'),
       (64, '2025-04-05 07:01:02', 32, '2025-04-05 07:01:02', '남성정장'),
       (65, '2025-04-05 07:01:03', 33, '2025-04-05 07:01:03', '로퍼'),
       (66, '2025-04-05 07:01:03', 33, '2025-04-05 07:01:03', '여성신발'),
       (67, '2025-04-05 07:01:04', 34, '2025-04-05 07:01:04', '후드티'),
       (68, '2025-04-05 07:01:04', 34, '2025-04-05 07:01:04', '캐주얼'),
       (69, '2025-04-05 07:01:05', 35, '2025-04-05 07:01:05', '니트'),
       (70, '2025-04-05 07:01:05', 35, '2025-04-05 07:01:05', '여성의류'),
       (71, '2025-04-05 07:01:06', 36, '2025-04-05 07:01:06', '와이드팬츠'),
       (72, '2025-04-05 07:01:06', 36, '2025-04-05 07:01:06', '여성하의'),
       (73, '2025-04-05 07:01:07', 37, '2025-04-05 07:01:07', '스커트'),
       (74, '2025-04-05 07:01:07', 37, '2025-04-05 07:01:07', '여성하의'),
       (75, '2025-04-05 07:01:08', 38, '2025-04-05 07:01:08', '데님팬츠'),
       (76, '2025-04-05 07:01:08', 38, '2025-04-05 07:01:08', '남성청바지'),
       (77, '2025-04-05 07:01:09', 39, '2025-04-05 07:01:09', '코트'),
       (78, '2025-04-05 07:01:09', 39, '2025-04-05 07:01:09', '겨울아우터'),
       (79, '2025-04-05 07:01:10', 40, '2025-04-05 07:01:10', '자켓'),
       (80, '2025-04-05 07:01:10', 40, '2025-04-05 07:01:10', '여성아우터'),
       (81, '2025-04-05 07:01:11', 41, '2025-04-05 07:01:11', '스니커즈'),
       (82, '2025-04-05 07:01:11', 41, '2025-04-05 07:01:11', '남성신발'),
       (83, '2025-04-05 07:01:12', 42, '2025-04-05 07:01:12', '정장구두'),
       (84, '2025-04-05 07:01:12', 42, '2025-04-05 07:01:12', '남성정장'),
       (85, '2025-04-05 07:01:13', 43, '2025-04-05 07:01:13', '로퍼'),
       (86, '2025-04-05 07:01:13', 43, '2025-04-05 07:01:13', '여성신발'),
       (87, '2025-04-05 07:01:14', 44, '2025-04-05 07:01:14', '후드티'),
       (88, '2025-04-05 07:01:14', 44, '2025-04-05 07:01:14', '스트릿패션'),
       (89, '2025-04-05 07:01:15', 45, '2025-04-05 07:01:15', '니트'),
       (90, '2025-04-05 07:01:15', 45, '2025-04-05 07:01:15', '여성의류'),
       (91, '2025-04-05 07:01:16', 46, '2025-04-05 07:01:16', '와이드팬츠'),
       (92, '2025-04-05 07:01:16', 46, '2025-04-05 07:01:16', '여성하의'),
       (93, '2025-04-05 07:01:17', 47, '2025-04-05 07:01:17', '스커트'),
       (94, '2025-04-05 07:01:17', 47, '2025-04-05 07:01:17', '트렌디'),
       (95, '2025-04-05 07:01:18', 48, '2025-04-05 07:01:18', '데님팬츠'),
       (96, '2025-04-05 07:01:18', 48, '2025-04-05 07:01:18', '캐주얼'),
       (97, '2025-04-05 07:01:19', 49, '2025-04-05 07:01:19', '코트'),
       (98, '2025-04-05 07:01:19', 49, '2025-04-05 07:01:19', '가을아우터'),
       (99, '2025-04-05 07:01:20', 50, '2025-04-05 07:01:20', '자켓'),
       (100, '2025-04-05 07:01:20', 50, '2025-04-05 07:01:20', '여성자켓');



-- 카테고리 관계
INSERT INTO product_category_relation (id, created_at, product_id, category_id, updated_at) VALUES
        (1, '2025-04-05 07:00:31', 1, 1, '2025-04-05 07:00:31'),
        (2, '2025-04-05 07:00:32', 2, 2, '2025-04-05 07:00:32'),
        (3, '2025-04-05 07:00:33', 3, 3, '2025-04-05 07:00:33'),
        (4, '2025-04-05 07:00:34', 4, 4, '2025-04-05 07:00:34'),
        (5, '2025-04-05 07:00:35', 5, 5, '2025-04-05 07:00:35'),
        (6, '2025-04-05 07:00:36', 6, 6, '2025-04-05 07:00:36'),
        (7, '2025-04-05 07:00:37', 7, 7, '2025-04-05 07:00:37'),
        (8, '2025-04-05 07:00:38', 8, 8, '2025-04-05 07:00:38'),
        (9, '2025-04-05 07:00:39', 9, 9, '2025-04-05 07:00:39'),
        (10, '2025-04-05 07:00:40', 10, 10, '2025-04-05 07:00:40'),
        (11, '2025-04-05 07:00:41', 11, 1, '2025-04-05 07:00:41'),
        (12, '2025-04-05 07:00:42', 12, 2, '2025-04-05 07:00:42'),
        (13, '2025-04-05 07:00:43', 13, 3, '2025-04-05 07:00:43'),
        (14, '2025-04-05 07:00:44', 14, 4, '2025-04-05 07:00:44'),
        (15, '2025-04-05 07:00:45', 15, 5, '2025-04-05 07:00:45'),
        (16, '2025-04-05 07:00:46', 16, 6, '2025-04-05 07:00:46'),
        (17, '2025-04-05 07:00:47', 17, 7, '2025-04-05 07:00:47'),
        (18, '2025-04-05 07:00:48', 18, 8, '2025-04-05 07:00:48'),
        (19, '2025-04-05 07:00:49', 19, 9, '2025-04-05 07:00:49'),
        (20, '2025-04-05 07:00:50', 20, 10, '2025-04-05 07:00:50'),
        (21, '2025-04-05 07:00:51', 21, 1, '2025-04-05 07:00:51'),
        (22, '2025-04-05 07:00:52', 22, 2, '2025-04-05 07:00:52'),
        (23, '2025-04-05 07:00:53', 23, 3, '2025-04-05 07:00:53'),
        (24, '2025-04-05 07:00:54', 24, 4, '2025-04-05 07:00:54'),
        (25, '2025-04-05 07:00:55', 25, 5, '2025-04-05 07:00:55'),
        (26, '2025-04-05 07:00:56', 26, 6, '2025-04-05 07:00:56'),
        (27, '2025-04-05 07:00:57', 27, 7, '2025-04-05 07:00:57'),
        (28, '2025-04-05 07:00:58', 28, 8, '2025-04-05 07:00:58'),
        (29, '2025-04-05 07:00:59', 29, 9, '2025-04-05 07:00:59'),
        (30, '2025-04-05 07:01:00', 30, 10, '2025-04-05 07:01:00'),
        (31, '2025-04-05 07:01:01', 31, 1, '2025-04-05 07:01:01'),
        (32, '2025-04-05 07:01:02', 32, 2, '2025-04-05 07:01:02'),
        (33, '2025-04-05 07:01:03', 33, 3, '2025-04-05 07:01:03'),
        (34, '2025-04-05 07:01:04', 34, 4, '2025-04-05 07:01:04'),
        (35, '2025-04-05 07:01:05', 35, 5, '2025-04-05 07:01:05'),
        (36, '2025-04-05 07:01:06', 36, 6, '2025-04-05 07:01:06'),
        (37, '2025-04-05 07:01:07', 37, 7, '2025-04-05 07:01:07'),
        (38, '2025-04-05 07:01:08', 38, 8, '2025-04-05 07:01:08'),
        (39, '2025-04-05 07:01:09', 39, 9, '2025-04-05 07:01:09'),
        (40, '2025-04-05 07:01:10', 40, 10, '2025-04-05 07:01:10'),
        (41, '2025-04-05 07:01:11', 41, 1, '2025-04-05 07:01:11'),
        (42, '2025-04-05 07:01:12', 42, 2, '2025-04-05 07:01:12'),
        (43, '2025-04-05 07:01:13', 43, 3, '2025-04-05 07:01:13'),
        (44, '2025-04-05 07:01:14', 44, 4, '2025-04-05 07:01:14'),
        (45, '2025-04-05 07:01:15', 45, 5, '2025-04-05 07:01:15'),
        (46, '2025-04-05 07:01:16', 46, 6, '2025-04-05 07:01:16'),
        (47, '2025-04-05 07:01:17', 47, 7, '2025-04-05 07:01:17'),
        (48, '2025-04-05 07:01:18', 48, 8, '2025-04-05 07:01:18'),
        (49, '2025-04-05 07:01:19', 49, 9, '2025-04-05 07:01:19'),
        (50, '2025-04-05 07:01:20', 50, 10, '2025-04-05 07:01:20');


INSERT INTO profile (
    user_id,
    nickname,
    gender,
    age_range,
    height,
    weight,
    profile_image,
    address,
    postal_code,
    bank_name,
    account_number,
    eco_points,
    created_at,
    updated_at
) VALUES
      (1, '빵돌이', 'MALE', '20-29', 175.0, 70.0, 'https://picsum.photos/id/129/350/450', '서울특별시 강남구', '06000', '신한은행', '110-123-456789', 0.0, NOW(), NOW()),
      (2, '집콕대왕', 'MALE', '20-29', 175.0, 70.0, 'https://picsum.photos/id/63/350/450', '서울특별시 강남구', '06000', '신한은행', '110-123-456789', 0.0, NOW(), NOW()),
      (3, '밥도둑냥이', 'MALE', '20-29', 175.0, 70.0, 'https://picsum.photos/id/106/350/450', '서울특별시 강남구', '06000', '신한은행', '110-123-456789', 0.0, NOW(), NOW()),
      (4, '멍때리미', 'MALE', '20-29', 175.0, 70.0, 'https://picsum.photos/id/130/350/450', '서울특별시 강남구', '06000', '신한은행', '110-123-456789', 0.0, NOW(), NOW()),
      (5, '라면 요정', 'MALE', '20-29', 175.0, 70.0, 'https://picsum.photos/id/163/350/450', '서울특별시 강남구', '06000', '신한은행', '110-123-456789', 0.0, NOW(), NOW()),
      (6, '집콕 대왕', 'FEMALE', '20-29', 165.0, 55.0, 'https://picsum.photos/id/177/350/450', '서울특별시 마포구', '04000', '국민은행', '123-45-678901', 0.0, NOW(), NOW()),
      (7, '낮잠 천재', 'FEMALE', '20-29', 165.0, 55.0, 'https://picsum.photos/id/250/350/450', '서울특별시 마포구', '04000', '국민은행', '123-45-678901', 0.0, NOW(), NOW()),
      (8, '간식 탐험가', 'FEMALE', '20-29', 165.0, 55.0, 'https://picsum.photos/id/305/350/450', '서울특별시 마포구', '04000', '국민은행', '123-45-678901', 0.0, NOW(), NOW()),
      (9, '새벽 감성러', 'FEMALE', '20-29', 165.0, 55.0, 'https://picsum.photos/id/342/350/450', '서울특별시 마포구', '04000', '국민은행', '123-45-678901', 0.0, NOW(), NOW()),
      (10, '넷플릭서', 'FEMALE', '20-29', 165.0, 55.0, 'https://picsum.photos/id/365/350/450', '서울특별시 마포구', '04000', '국민은행', '123-45-678901', 0.0, NOW(), NOW());
