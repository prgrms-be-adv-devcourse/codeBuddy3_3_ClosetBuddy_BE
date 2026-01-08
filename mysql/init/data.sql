/* MySQL 8.x / InnoDB / utf8mb4 */

-- =========================
-- 1) user_db (UserService)
-- =========================
CREATE DATABASE IF NOT EXISTS user_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_0900_ai_ci;
USE user_db;

CREATE TABLE IF NOT EXISTS member (
                                      member_id       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                      member_name     VARCHAR(50)  NOT NULL,
    password        VARCHAR(255) NOT NULL,
    member_email    VARCHAR(255) NOT NULL,
    member_address  VARCHAR(255) NOT NULL,
    phonenumber     VARCHAR(20)  NOT NULL,
    role            ENUM('MEMBER','SELLER','ADMIN') NOT NULL DEFAULT 'MEMBER',
    PRIMARY KEY (member_id),
    UNIQUE KEY uk_member_name (member_name),
    UNIQUE KEY uk_member_email (member_email),
    UNIQUE KEY uk_member_phone (phonenumber)
    ) ENGINE=InnoDB;


-- =========================
-- 2) catalog_db (CatalogService)
-- =========================
CREATE DATABASE IF NOT EXISTS catalog_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_0900_ai_ci;
USE catalog_db;

CREATE TABLE IF NOT EXISTS seller (
                                      seller_id  BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                      member_id  BIGINT UNSIGNED NOT NULL, -- user_db.member.member_id (논리 참조)
                                      PRIMARY KEY (seller_id),
    UNIQUE KEY uk_seller_member (member_id)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS store (
                                     store_id    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                     store_name  VARCHAR(100) NOT NULL,
    seller_id   BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (store_id),
    KEY idx_store_seller (seller_id),
    CONSTRAINT fk_store_seller
    FOREIGN KEY (seller_id) REFERENCES seller(seller_id)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS product (
                                       product_id     BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                       product_name   VARCHAR(200) NOT NULL,
    product_price  BIGINT NOT NULL,
    product_stock  INT NOT NULL,
    store_id       BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (product_id),
    KEY idx_product_store (store_id),
    CONSTRAINT fk_product_store
    FOREIGN KEY (store_id) REFERENCES store(store_id)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS category (
                                        category_id    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                        category_name  VARCHAR(50) NOT NULL,
    PRIMARY KEY (category_id),
    UNIQUE KEY uk_category_name (category_name)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS product_category (
                                                product_mapping_id  BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                product_id          BIGINT UNSIGNED NOT NULL,
                                                category_id         BIGINT UNSIGNED NOT NULL,
                                                PRIMARY KEY (product_mapping_id),
    UNIQUE KEY uk_product_category (product_id, category_id),
    KEY idx_pc_category (category_id),
    CONSTRAINT fk_pc_product
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE,
    CONSTRAINT fk_pc_category
    FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE CASCADE
    ) ENGINE=InnoDB;


-- =========================
-- 3) order_db (OrderService)
-- =========================
CREATE DATABASE IF NOT EXISTS order_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_0900_ai_ci;
USE order_db;

CREATE TABLE IF NOT EXISTS orders (
                                      order_id      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                      created_at    DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at    DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    order_status  ENUM('CREATED','PAID','CANCELED','COMPLETED') NOT NULL DEFAULT 'CREATED',
    order_amount  BIGINT NOT NULL,
    member_id     BIGINT UNSIGNED NOT NULL, -- user_db.member.member_id (논리 참조)
    PRIMARY KEY (order_id),
    KEY idx_orders_member_created (member_id, created_at)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS order_item (
                                          order_item_id  BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                          product_id     BIGINT UNSIGNED NOT NULL, -- catalog_db.product.product_id (논리 참조)
                                          order_id       BIGINT UNSIGNED NOT NULL,
                                          order_count    INT NOT NULL,
                                          order_price    BIGINT NOT NULL,
                                          PRIMARY KEY (order_item_id),
    KEY idx_order_item_order (order_id),
    KEY idx_order_item_product (product_id),
    CONSTRAINT fk_order_item_order
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS cart_item (
                                         cart_id      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                         product_id   BIGINT UNSIGNED NOT NULL, -- catalog_db.product.product_id (논리 참조)
                                         member_id    BIGINT UNSIGNED NOT NULL, -- user_db.member.member_id (논리 참조)
                                         cart_stock   INT NOT NULL,
                                         PRIMARY KEY (cart_id),
    UNIQUE KEY uk_cart_member_product (member_id, product_id),
    KEY idx_cart_member (member_id),
    KEY idx_cart_product (product_id)
    ) ENGINE=InnoDB;


-- =========================
-- 4) account_db (AccountService)
-- =========================
CREATE DATABASE IF NOT EXISTS account_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_0900_ai_ci;
USE account_db;

CREATE TABLE IF NOT EXISTS account (
                                       account_id  BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                       balance     BIGINT NOT NULL DEFAULT 0,
                                       member_id   BIGINT UNSIGNED NOT NULL, -- user_db.member.member_id (논리 참조)
                                       PRIMARY KEY (account_id),
    UNIQUE KEY uk_account_member (member_id)
    ) ENGINE=InnoDB;

-- =========================
-- 5) batch_db (BatchService)
-- =========================
CREATE DATABASE IF NOT EXISTS batch_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_0900_ai_ci;
USE batch_db;

CREATE TABLE IF NOT EXISTS settlement (
                                          settle_id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                          fee_amount        BIGINT NOT NULL,
                                          settle_status     ENUM('SCHEDULED','SETTLED','FAILED') NOT NULL DEFAULT 'SCHEDULED',
    total_amount      BIGINT NOT NULL,
    payout_amount     BIGINT NOT NULL,
    created_at        DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    settlement_date   DATETIME(6) NOT NULL,
    store_id          BIGINT UNSIGNED NOT NULL, -- catalog_db.store.store_id (논리 참조)
    PRIMARY KEY (settle_id),
    KEY idx_settlement_store_date (store_id, settlement_date),
    KEY idx_settlement_status_date (settle_status, settlement_date)
    ) ENGINE=InnoDB;