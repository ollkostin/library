package ru.practice.kostin.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class LibraryApplication {

    @Autowired
    private JdbcTemplate template;

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }


    @PostConstruct
    void f() {
        template.execute(
                "DROP TABLE IF EXISTS user;\n" +
                        "DROP TABLE IF EXISTS book;\n" +
                        "CREATE TABLE IF NOT EXISTS user (\n" +
                        "  id    INTEGER PRIMARY KEY AUTO_INCREMENT,\n" +
                        "  username      VARCHAR(30) UNIQUE ,\n" +
                        "  password_hash VARCHAR(255)\n" +
                        ");\n" +
                        "CREATE TABLE IF NOT EXISTS book (\n" +
                        "  isn VARCHAR(13) PRIMARY KEY,\n" +
                        "  name VARCHAR(255) NOT NULL,\n" +
                        "  author VARCHAR(255) NOT NULL ,\n" +
                        "  user_id INTEGER DEFAULT NULL, \n" +
                        "  FOREIGN KEY (user_id) REFERENCES user(id));\n" +
                        "INSERT INTO user (id, username, password_hash) VALUES\n" +
                        "  (1,'user1','$2a$04$wdef30mrmL7L4N/jF9quN.7ad9dnAoAINCBulFRC.w3w6vRU96sBO'),\n" +
                        "  (2,'user2','$2a$04$Fe0O/C22Vew7COTImUXsP.WOSpbAFmkJ7ylm9jyubiXI8.zMy.bOa')\n" +
                        ";\n" +
                        "INSERT INTO book VALUES\n" +
                        "('2-266-11156-6', 'Магистр рассеянных наук', 'И.В. Иванов', 2),\n" +
                        "('3-377-22267-7', 'Забавы с физикой', 'Т.Тит', 2),\n" +
                        "('4-23-11156-6', 'Шерлок Холмс и Доктор Ватсон', 'А.К. Дойль', NULL),\n" +
                        "('8-900-77383-6', 'Поколение Пи', 'В.Пелевин', NULL),\n" +
                        "('2-226-23234-6', 'Thinking in Java', 'Брюс Эккель', 2),\n" +
                        "('7-377-11337-7', 'Забавы с химией', 'Т.Тит', 1),\n" +
                        "('4-22-12257-6', 'Приключения Дениски Кораблева', 'В. Драгунский', NULL),\n" +
                        "('8-800-55353-5', 'Об одолжениях', 'В.В. Ростовщиков', NULL),\n" +
                        "('8-903-65456-9', 'Отрочество', 'М. Горький', NULL),\n" +
                        "('9-327-22267-7', 'Диалектика переходного периода из ниоткуда в никуда', 'В. Пелевин', 1),\n" +
                        "('4-94-11326-1', 'Вий', 'Н.В. Гоголь', NULL),\n" +
                        "('8-32-23383-4', 'Сказка о царе-салтане', 'А.С.Пушкин', NULL),\n" +
                        "('2-789-99991-6', 'Гроза', 'Островский', NULL),\n" +
                        "('3-377-23311-7', 'Удивительная физика', 'А.В.Васильев', 1),\n" +
                        "('4-22-44421-6', '451', 'Р. Брэдбери', NULL)" + ";");
    }
}
