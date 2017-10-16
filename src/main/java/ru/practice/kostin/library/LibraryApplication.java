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
                    "DROP TABLE IF EXISTS user;" +
                        "DROP TABLE IF EXISTS book;" +
                        "CREATE TABLE IF NOT EXISTS user (" +
                        "  id    INTEGER PRIMARY KEY AUTO_INCREMENT," +
                        "  username      VARCHAR(30) UNIQUE ," +
                        "  password_hash VARCHAR(255)" +
                        ");\n" +
                        "CREATE TABLE IF NOT EXISTS book (" +
                        "  isn VARCHAR(13) PRIMARY KEY," +
                        "  name VARCHAR(255) NOT NULL," +
                        "  author VARCHAR(255) NOT NULL," +
                        "  user_id INTEGER DEFAULT NULL," +
                        "CONSTRAINT fk_book_user " +
                        "FOREIGN KEY (user_id) REFERENCES user(id)" +
                        "ON DELETE SET NULL);\n" +
                        "INSERT INTO user (id, username, password_hash) VALUES" +
                        "  (1,'user1','$2a$04$wdef30mrmL7L4N/jF9quN.7ad9dnAoAINCBulFRC.w3w6vRU96sBO')," +
                        "  (2,'user2','$2a$04$Fe0O/C22Vew7COTImUXsP.WOSpbAFmkJ7ylm9jyubiXI8.zMy.bOa');" +
                        "INSERT INTO book VALUES " +
                        "('2-266-11156-6', 'Магистр рассеянных наук', 'И.В. Иванов', 2)," +
                        "('3-377-22267-7', 'Забавы с физикой', 'Т.Тит', 2)," +
                        "('4-23-11156-6', 'Шерлок Холмс и Доктор Ватсон', 'А.К. Дойль', NULL)," +
                        "('8-900-77383-6', 'Поколение Пи', 'В.Пелевин', NULL)," +
                        "('2-226-23234-6', 'Thinking in Java', 'Брюс Эккель', 2)," +
                        "('7-377-11337-7', 'Забавы с химией', 'Т.Тит', 1)," +
                        "('4-22-12257-6', 'Приключения Дениски Кораблева', 'В. Драгунский', NULL)," +
                        "('8-800-55353-5', 'Об одолжениях', 'В.В. Ростовщиков', NULL)," +
                        "('8-903-65456-9', 'Отрочество', 'М. Горький', NULL)," +
                        "('9-327-22267-7', 'Диалектика переходного периода из ниоткуда в никуда', 'В. Пелевин', 1)," +
                        "('4-94-11326-1', 'Вий', 'Н.В. Гоголь', NULL)," +
                        "('8-32-23383-4', 'Сказка о царе-салтане', 'А.С.Пушкин', NULL)," +
                        "('2-789-99991-6', 'Гроза', 'Островский', NULL)," +
                        "('3-377-23311-7', 'Удивительная физика', 'А.В.Васильев', 1)," +
                        "('4-22-44421-6', '451', 'Р. Брэдбери', NULL);");
    }
}
