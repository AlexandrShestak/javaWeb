CREATE TABLE scand.news
(
    news_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    news_text VARCHAR(2000) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    commentator_username VARCHAR(20) NOT NULL
);
ALTER TABLE scand.news ADD CONSTRAINT unique_news_id UNIQUE (news_id);