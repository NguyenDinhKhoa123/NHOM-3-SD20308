USE master;
ALTER DATABASE PolyCafeDB SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
DROP DATABASE PolyCafeDB;

CREATE DATABASE PolyCafeDB;
GO

USE PolyCafeDB;
GO

CREATE TABLE users (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    fullname NVARCHAR(255),
    phone VARCHAR(20),
    role VARCHAR(20) CHECK (role IN ('admin','staff','customer')) DEFAULT 'staff',
    active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE()
);

CREATE INDEX idx_users_email ON users(email);


CREATE TABLE category (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    active BIT DEFAULT 1
);

CREATE INDEX idx_category_name ON category(name);

CREATE TABLE drinks (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    image VARCHAR(500),
    description NVARCHAR(MAX),
    active BIT DEFAULT 1,
    category_id BIGINT,

    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE INDEX idx_drinks_category ON drinks(category_id);
CREATE INDEX idx_drinks_name ON drinks(name);

CREATE TABLE bills (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    code VARCHAR(100) NOT NULL UNIQUE,
    create_date DATETIME DEFAULT GETDATE(),
    total DECIMAL(10,2) DEFAULT 0,

    status VARCHAR(20)
    CHECK (status IN ('pending','confirmed','paid','cancelled'))
    DEFAULT 'pending',

    user_id BIGINT,

    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_bills_user_id ON bills(user_id);
CREATE INDEX idx_bills_status ON bills(status);


CREATE TABLE bill_details (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    quantity INT NOT NULL CHECK (quantity > 0),
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),

    bill_id BIGINT,
    drink_id BIGINT,

    FOREIGN KEY (bill_id) REFERENCES bills(id) ON DELETE CASCADE,
    FOREIGN KEY (drink_id) REFERENCES drinks(id)
);

CREATE INDEX idx_billdetails_bill ON bill_details(bill_id);
CREATE INDEX idx_billdetails_drink ON bill_details(drink_id);


-- USERS
INSERT INTO users (email, password, fullname, phone, role) VALUES
('admin@gmail.com','123456','Admin','0900000001','admin'),
('staff1@gmail.com','123456','Staff 1','0900000002','staff'),
('customer1@gmail.com','123456','Customer 1','0900000003','customer');


-- CATEGORY
INSERT INTO category (name, active) VALUES
(N'Cà phê', 1),
(N'Trà trái cây', 1),
(N'Trà sữa', 1),
(N'Đá xay (Ice Blended)', 1),
(N'Nước ép tươi', 1),
(N'Soda & Mojito', 1);

-- DRINKS - FIXED VERSION WITH CORRECT IMAGE NAMES
INSERT INTO drinks (name, price, image, description, category_id, active) VALUES
-- Nhóm 1: Cà phê (ID: 1)
(N'Cà phê Đen Đá', 20000, 'cafe_den.jpg', N'Đậm đà truyền thống', 1, 1),
(N'Cà phê Sữa Đá', 25000, 'cafe_sua.jpg', N'Ngọt ngào hương vị Việt', 1, 1),
(N'Bạc Xỉu', 29000, 'bac_xiu.jpg', N'Nhiều sữa ít cà phê', 1, 1),
(N'Cappuccino', 45000, 'cappuccino.jpg', N'Hương vị Ý nhẹ nhàng', 1, 1),
(N'Latte Art', 45000, 'latte.jpg', N'Sữa nóng vẽ nghệ thuật', 1, 1),
(N'Americano', 35000, 'americano.jpg', N'Cà phê pha loãng kiểu Mỹ', 1, 1),
(N'Cà phê Muối', 35000, 'cafe_muoi.jpg', N'Hot trend vị mặn nhẹ', 1, 1),
(N'Cà phê Trứng', 40000, 'cafe_trung.jpg', N'Béo ngậy đặc sản Hà Nội', 1, 1),

-- Nhóm 2: Trà trái cây (ID: 2)
(N'Trà Đào Cam Sả', 39000, 'tra_dao.jpg', N'Thanh mát giải nhiệt', 2, 1),
(N'Trà Vải Khiếm Thạch', 39000, 'tra_vai.jpg', N'Ngọt thanh vị vải', 2, 1),
(N'Trà Dâu Tây', 35000, 'tra_dau.jpg', N'Dâu tươi Đà Lạt', 2, 1),
(N'Trà Cham Sa', 45000, 'tra_cam_xa.jpg', N'Hương vị mùa hè', 2, 1),
(N'Trà Hoa Cúc Mật Ong', 32000, 'tra_hoa_cuc.jpg', N'Thư giãn tinh thần', 2, 1),
(N'Trà Ô Long', 49000, 'tra_OLong.jpg', N'Sen thơm bùi', 2, 1),
(N'Trà Ổi', 35000, 'tra_oi.jpg', N'Đặc sản trà trái cây', 2, 1),
(N'Trà Xoài', 35000, 'tra_xoai.jpg', N'Màu sắc bắt mắt', 2, 1),

-- Nhóm 3: Trà sữa (ID: 3)
(N'Trà Sữa Truyền Thống', 30000, 'ts_truyen_thong.jpg', N'Đậm vị trà', 3, 1),
(N'Trà Sữa Trân Châu Đen', 35000, 'ts_tran_chau.jpg', N'Dai giòn sần sật', 3, 1),
(N'Trà Sữa Matcha', 38000, 'ts_matcha.jpg', N'Trà xanh Nhật Bản', 3, 1),
(N'Trà Sữa Khoai Môn', 38000, 'ts_khoai_mon.jpg', N'Màu tím mộng mơ', 3, 1),
(N'Trà Sữa Socola', 38000, 'ts_socola.jpg', N'Vị đắng nhẹ quyến rũ', 3, 1),
(N'Trà Sữa Thái Xanh', 25000, 'ts_thai_xanh.jpg', N'Thơm mùi thảo mộc', 3, 1),
(N'Trà Sữa Thái Đỏ', 25000, 'ts_thai_do.jpg', N'Vị trà đặc trưng', 3, 1),
(N'Trà Sữa Nướng', 40000, 'ts_nuong.jpg', N'Vị caramel cháy', 3, 1),
(N'Sữa Tươi Trân Châu Đường Đen', 45000, 'sua_tuoi_duong_den.jpg', N'Cực phẩm ăn khách', 3, 1),

-- Nhóm 4: Đá xay (ID: 4)
(N'Matcha Đá Xay', 45000, 'matcha_ice.jpg', N'Kem tươi béo ngậy', 4, 1),
(N'Chocolate Đá Xay', 45000, 'choco_ice.jpg', N'Đậm vị cacao', 4, 1),
(N'Coffee Đá Xay', 40000, 'coffee_ice.jpg', N'Tỉnh táo tức thì', 4, 1),
(N'Cookie Đá Xay', 49000, 'cookie_ice.jpg', N'Bánh Oreo giòn tan', 4, 1),
(N'Blueberry Đá Xay', 45000, 'blue_ice.jpg', N'Việt quất chua ngọt', 4, 1),
(N'Chanh Dây Đá Xay', 35000, 'passion_ice.jpg', N'Giải nhiệt mùa hè', 4, 1),
(N'Caramel Đá Xay', 45000, 'caramel_ice.jpg', N'Ngọt ngào vị đường cháy', 4, 1),
(N'Dâu Tây Đá Xay', 45000, 'strawberry_ice.jpg', N'Dâu tươi xay mịn', 4, 1),

-- Nhóm 5: Nước ép (ID: 5)
(N'Nước ép Cam', 35000, 'ep_cam.jpg', N'Cung cấp Vitamin C', 5, 1),
(N'Nước ép Thơm', 30000, 'ep_thom.jpg', N'Hỗ trợ tiêu hóa', 5, 1),
(N'Nước ép Cà rốt', 30000, 'ep_carot.jpg', N'Tốt cho mắt', 5, 1),
(N'Nước ép Dưa hấu', 30000, 'ep_dua_hau.jpg', N'Thanh mát cơ thể', 5, 1),
(N'Nước ép Táo', 35000, 'ep_tao.jpg', N'Ngọt thanh tự nhiên', 5, 1),
(N'Nước ép Bưởi', 40000, 'ep_buoi.jpg', N'Hỗ trợ giảm cân', 5, 1),
(N'Nước ép Cóc', 25000, 'ep_coc.jpg', N'Chua cay hấp dẫn', 5, 1),
(N'Nước ép Ổi', 25000, 'ep_oi.jpg', N'Nhiều vitamin', 5, 1),

-- Nhóm 6: Soda & Mojito (ID: 6)
(N'Soda Blue Ocean', 30000, 'soda_blue.jpg', N'Màu xanh đại dương', 6, 1),
(N'Mojito Chanh Dây', 35000, 'mojito_passion.jpg', N'Bạc hà mát lạnh', 6, 1),
(N'Soda Ý Dâu Tây', 30000, 'soda_straw.jpg', N'Ngọt ngào sảng khoái', 6, 1),
(N'Mojito Bạc Hà', 35000, 'mojito_mint.jpg', N'Vị truyền thống', 6, 1),
(N'Soda Kiwi', 30000, 'soda_kiwi.jpg', N'Hương vị châu Âu', 6, 1),
(N'Soda Kiwi', 30000, 'soda_kiwi.jpg', N'Màu xanh lá tươi mát', 6, 1),
(N'Mojito Đào', 35000, 'mojito_peach.jpg', N'Thơm mùi đào miếng', 6, 1),
(N'Soda Chanh Muối', 25000, 'soda_lemon.jpg', N'Bù khoáng cơ thể', 6, 1),
(N'Soda Cherry', 35000, 'soda_cherry.jpg', N'Sắc đỏ quyến rũ', 6, 1);

-- BILLS
INSERT INTO bills (code, total, status, user_id)
VALUES
('BILL001', 75000, 'paid', 2),
('BILL002', 60000, 'pending', 3),
('BILL003', 90000, 'confirmed', 2);

-- BILL DETAILS
INSERT INTO bill_details (quantity, price, bill_id, drink_id) VALUES
(1, 25000, 1, 2),
(1, 30000, 1, 3),
(1, 20000, 1, 1),
(2, 30000, 2, 3);

USE PolyCafeDB;
GO
SELECT * FROM users;
SELECT * FROM category;
SELECT * FROM bill_details;
SELECT * FROM bills;
SELECT * FROM drinks;

