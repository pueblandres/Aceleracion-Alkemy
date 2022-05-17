USE alkemy;

INSERT INTO roles (name, description)
VALUES ('admin', 'Full application access');

INSERT INTO roles (name, description)
VALUES ('user', 'User level access');

INSERT INTO roles (name, description)
VALUES ('maintainer', 'Can read and update resources');

INSERT INTO roles (name, description, is_deleted)
VALUES ('dev', 'Developer access level',0);

INSERT INTO users (role_id, first_name, last_name, email, password, photo)
VALUES (2, 'Maria', 'Perez', 'mariaperez@mail.com', 'qwerty', 'https://www.somebucket.com/photos/image.jpg');

INSERT INTO users (role_id, first_name, last_name, email, password)
VALUES (3, 'Jose', 'Gonzalez', 'josegonzalez@mail.com', 'zxcvb');

INSERT INTO users (role_id, first_name, last_name, email, password)
VALUES (1, 'Debora', 'Zalazar', 'deborazalar@mail.com', 'asdfgh');

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('miguel', 'berrio', 'miguelberrio@gmail.com', 'mberrio', 1);

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('andres', 'puebla', 'andrespuebla@gmail.com', 'apuebla', 1);

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('cristian', 'fernandez', 'cristianfernandez@gmail.com', 'cfernandez', 1);

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('francisco', 'romero', 'franciscoromero@gmail.com', 'fromero', 1);

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('lucas', 'saleme', 'lucassaleme@gmail.com', 'lsaleme', 1);

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('nicolas', 'pucci', 'nicolaspucci@gmail.com', 'npucci', 1);

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('lucas', 'paoliello', 'lucaspaoliello@gmail.com', 'lpaoliello', 1);

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('bill', 'gates', 'billgates@gmail.com', 'bgates', 1);

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('steve', 'jobs', 'stevejobs@gmail.com', 'sjobs', 1);

INSERT INTO users (first_name, last_name, email, password, role_id,is_deleted)
VALUE ('torvalds', 'linus', 'torvaldslinus@gmail.com','$2a$10$3n2ntpx9zBzMiQh0piZ5KeQH7ajiCG7nXvs67GKUpsP7IrLWPPmeq', 1,0);

INSERT INTO users (first_name, last_name, email, password, role_id,is_deleted)
VALUE ('alejandro', 'abondano', 'alejandroabondano@gmail.com', '$2a$10$3n2ntpx9zBzMiQh0piZ5KeQH7ajiCG7nXvs67GKUpsP7IrLWPPmeq', 1,0);

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('brigite', 'polanco', 'brigitepolanco@gmail.com', 'apuebla', 3);

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('camilo', 'gomez', 'camilogomez@gmail.com', 'cgomez', 4);

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('daniela', 'guzman', 'danielaguzman@gmail.com', 'dguzman', 2);

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('estewil', 'quesada', 'estewilquesada@gmail.com', 'equesada', 3);

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('fabian', 'fino', 'fabianfino@gmail.com', 'ffino', 4);

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('gabriel', 'nieto', 'gabrielnieto@gmail.com', 'gnieto', 2);

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('hugo', 'camargo', 'hugocamargo@gmail.com', 'hcamargo', 3);

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('ivan', 'coral', 'ivancoral@gmail.com', 'icoral', 4);

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUE ('jenny', 'sanchez', 'jennysanchez@gmail.com', 'jsanchez', 3);

INSERT INTO users (first_name, last_name, email, password, role_id)
 VALUE ('daniel', 'cruz', 'danielcruz@gmail.com', 'dcruz', 2);

INSERT INTO activities (name, content, image)
    VALUE ('activity one', 'do activity one content', 'https://www.somebucket.com/activity_one.jpg');

INSERT INTO activities (name, content, image)
    VALUE ('activity two', 'do activity two content', 'https://www.somebucket.com/activity_two.jpg');

INSERT INTO activities (name, content, image)
    VALUE ('activity three', 'do activity three content', 'https://www.somebucket.com/activity_three.jpg');

INSERT INTO activities (name, content, image)
    VALUE ('activity four', 'do activity four content', 'https://www.somebucket.com/activity_four.jpg');

INSERT INTO activities (name, content, image)
    VALUE ('activity five', 'do activity five content', 'https://www.somebucket.com/activity_five.jpg');

INSERT INTO activities (name, content, image)
    VALUE ('activity six', 'do activity six content', 'https://www.somebucket.com/activity_six.jpg');

INSERT INTO activities (name, content, image)
    VALUE ('activity seven', 'do activity seven content', 'https://www.somebucket.com/activity_seven.jpg');

INSERT INTO activities (name, content, image)
    VALUE ('activity eight', 'do activity eight content', 'https://www.somebucket.com/activity_eight.jpg');

INSERT INTO activities (name, content, image)
    VALUE ('activity nine', 'do activity nine content', 'https://www.somebucket.com/activity_nine.jpg');

INSERT INTO activities (name, content, image)
    VALUE ('activity ten', 'do activity ten content', 'https://www.somebucket.com/activity_ten.jpg');

INSERT INTO news (name, content, image, category_id, is_deleted, created_at, updated_at, `type`)
    VALUES('news 1', 'content 1', '/img1.jpg', 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'news');
INSERT INTO news (name, content, image, category_id, is_deleted, created_at, updated_at, `type`)
    VALUES('news 2', 'content 2', '/img2.jpg', 2, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'news');
INSERT INTO news (name, content, image, category_id, is_deleted, created_at, updated_at, `type`)
    VALUES('news 3', 'content 3', '/img3.jpg', 3, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'news');
INSERT INTO news (name, content, image, category_id, is_deleted, created_at, updated_at, `type`)
    VALUES('news 4', 'content 4', '/img4.jpg', 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'news');
INSERT INTO news (name, content, image, category_id, is_deleted, created_at, updated_at, `type`)
    VALUES('news 5', 'content 5', '/img5.jpg', 2, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'news');
INSERT INTO news (name, content, image, category_id, is_deleted, created_at, updated_at, `type`)
    VALUES('news 6', 'content 6', '/img6.jpg', 3, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'news');
INSERT INTO news (name, content, image, category_id, is_deleted, created_at, updated_at, `type`)
    VALUES('news 7', 'content 7', '/img7.jpg', 3, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'news');
INSERT INTO news (name, content, image, category_id, is_deleted, created_at, updated_at, `type`)
    VALUES('news 8', 'content 8', '/img8.jpg', 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'news');
INSERT INTO news (name, content, image, category_id, is_deleted, created_at, updated_at, `type`)
    VALUES('news 9', 'content 9', '/img9.jpg', 2, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'news');
INSERT INTO news (name, content, image, category_id, is_deleted, created_at, updated_at, `type`)
    VALUES('news 10', 'content 10', '/img10.jpg', 3, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'news');

INSERT INTO categories (name, description, image, created_at, updated_at, is_deleted)
    VALUES('category 1', 'description 1', '/img1.jpg', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,0);
INSERT INTO categories (name, description, image, created_at, updated_at, is_deleted)
    VALUES('category 2', 'description 2', '/img2.jpg', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,0);
INSERT INTO categories (name, description, image, created_at, updated_at, is_deleted)
    VALUES('category 3', 'description 3', '/img3.jpg', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,0);

INSERT INTO contacts (name, phone, email, message, is_deleted, created_at,updated_at)
    VALUES('name 1', '1111111111', 'email1@contact.com', "message 1",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO contacts (name, phone, email, message, is_deleted, created_at,updated_at)
    VALUES('name 2', '2222222222', 'email2@contact.com', "message 2",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO contacts (name, phone, email, message, is_deleted, created_at,updated_at)
    VALUES('name 3', '3333333333', 'email3@contact.com', "message 3",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

INSERT INTO organizations (name, image, address, phone, email, welcome_text, about_us_text, facebook, linkedin, instagram, created_at, updated_at, is_deleted)
    VALUES ('Alkemy', 'alkemy.jpg', 'Argentina',35122310, 'alkemy@jejemail', 'Hola', 'Job ready', 'fb/alkemy', 'ln/alkemy', 'insta/alkemy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
INSERT INTO organizations (name, image, address, phone, email, welcome_text, about_us_text, facebook, linkedin, instagram, created_at, updated_at, is_deleted)
    VALUES ('Coca', 'coca.jpg', 'USA',21322310, 'coca@jejemail', 'Hola', 'Gaseosa', 'fb/coca', 'ln/coca', 'insta/coca', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);

INSERT INTO slides (image_url, text, slide_order, organization_id, is_deleted)
    VALUES ('www.jpg.com/worldslides', 'Tercera entrega slides', 3, 1, 0);
INSERT INTO slides (image_url, text, slide_order, organization_id, is_deleted)
    VALUES ('www.jpg.com/worldslides', 'Muchas slides', 1, 1, 0);
INSERT INTO slides (image_url, text, slide_order, organization_id, is_deleted)
    VALUES ('www.jpg.com/worldslides', 'Otras slides', 2, 1, 0);
INSERT INTO slides (image_url, text, slide_order, organization_id, is_deleted)
    VALUES ('www.jpg.com/worldslides', 'Mas slides', 1, 2, 0);

INSERT INTO users (role_id, first_name, last_name, email, password, photo, is_deleted,created_at,updated_at)
    VALUES(1, 'first_name', 'last_name', 'email@email.com', 'password', '/photo.jpg',0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
