show
databases;
use
bocchi_the_twitter;
show
tables;

CREATE TABLE tweets
(
    id      INT(11) AUTO_INCREMENT,
    user_id INT(11) NOT NULL,
    text    VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO tweets (user_id, text)
VALUES ('miku3', 'atari');

INSERT INTO users (id,name,password)
VALUES ('ai20', 'hoshino', 'aiai');

INSERT INTO users (id,name,password)
VALUES ('kita194', 'kita', '');

DELETE FROM users WHERE id='ai20';
