USE alkemy;
CREATE TABLE IF NOT EXISTS news (
  id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NOT NULL,
   content TEXT NOT NULL,
   image VARCHAR(255) NOT NULL,
   category_id BIGINT NOT NULL,
   is_deleted BIT(1) NOT NULL,
   created_at timestamp NOT NULL,
   updated_at timestamp NULL,
   type VARCHAR(255) NOT NULL,
   CONSTRAINT pk_news PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS roles
(
    id          BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NULL,
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    is_deleted  BOOL         NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS users
(
    id         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role_id    BIGINT,
    FOREIGN KEY (role_id) REFERENCES roles (id),
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    photo      VARCHAR(255) NULL,
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    is_deleted BOOL         NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS members (
  id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NOT NULL,
   facebook_url VARCHAR(255) NULL,
   instagram_url VARCHAR(255) NULL,
   linkedin_url VARCHAR(255) NULL,
   image VARCHAR(255) NOT NULL,
   description VARCHAR(255) NULL,
   is_deleted BIT(1) NULL,
   created_at datetime NULL,
   updated_at datetime NULL,
   CONSTRAINT pk_members PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS categories(
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    image VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    is_deleted BOOLEAN DEFAULT 0
);

CREATE TABLE IF NOT EXISTS activities
(
    id         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    content    TEXT         NOT NULL,
    image      VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    is_deleted BOOL         NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS testimonials
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255)          NOT NULL,
    image      VARCHAR(255)          NULL,
    content    VARCHAR(255)          NULL,
    created_at datetime              NULL,
    updated_at datetime              NULL,
    is_deleted BIT(1)                NULL,
    CONSTRAINT pk_testimonials PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS organizations
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NOT NULL,
    image         VARCHAR(255)          NULL,
    address       VARCHAR(255)          NULL,
    phone         INT                   NULL,
    email         VARCHAR(255)          NULL,
    welcome_text  VARCHAR(255)          NULL,
    about_us_text VARCHAR(255)          NULL,
    facebook      VARCHAR(255)          NULL,
    linkedin      VARCHAR(255)          NULL,
    instagram     VARCHAR(255)          NULL,
    created_at    datetime              NULL,
    updated_at    datetime              NULL,
    is_deleted    BIT(1)                NULL,
    CONSTRAINT pk_organizations PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS slides (
   id BIGINT AUTO_INCREMENT NOT NULL,
   image_url VARCHAR(255) NOT NULL,
   text VARCHAR(255) NULL,
   slide_order INT NULL,
   created_at datetime NULL,
   updated_at datetime NULL,
   organization_id BIGINT NOT NULL,
   is_deleted BIT(1) NULL,
   CONSTRAINT pk_slides PRIMARY KEY (id)
);

ALTER TABLE slides ADD CONSTRAINT FK_SLIDES_ON_ORGANIZATION FOREIGN KEY (organization_id) REFERENCES organizations (id);



CREATE TABLE IF NOT EXISTS comments
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    user_id    BIGINT                NULL,
    body       VARCHAR(255)          NOT NULL,
    news_id    BIGINT                NULL,
    created_at datetime              NOT NULL,
    updated_at datetime              NOT NULL,
    is_deleted BIT(1)                NOT NULL,
    CONSTRAINT pk_comments PRIMARY KEY (id)
);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_NEWS FOREIGN KEY (news_id) REFERENCES news (id);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);
   is_deleted BOOLEAN DEFAULT 0,
   CONSTRAINT pk_slides PRIMARY KEY (id)
);

ALTER TABLE slides ADD CONSTRAINT FK_SLIDES_ON_ORGANIZATION FOREIGN KEY (organization_id) REFERENCES organizations (id);

CREATE TABLE contacts (
  id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NOT NULL,
   phone VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL,
   message VARCHAR(255) NOT NULL,
   is_deleted BIT(1) NOT NULL,
   created_at timestamp NULL NOT NULL,
   updated_at timestamp NULL NOT NULL,
   CONSTRAINT pk_contacts PRIMARY KEY (id)
);
CREATE TABLE comments (
  id BIGINT AUTO_INCREMENT NOT NULL,
   user_id BIGINT NOT NULL,
   body VARCHAR(255) NOT NULL,
   news_id BIGINT NOT NULL,
   created_at datetime NOT NULL,
   updated_at datetime NOT NULL,
   is_deleted BIT(1) NOT NULL,
   CONSTRAINT pk_comments PRIMARY KEY (id)
);

ALTER TABLE comments ADD CONSTRAINT FK_COMMENTS_ON_NEWS FOREIGN KEY (news_id) REFERENCES news (id);

ALTER TABLE comments ADD CONSTRAINT FK_COMMENTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);