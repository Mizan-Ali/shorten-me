CREATE TABLE url_mapping (
    id INT AUTO_INCREMENT PRIMARY KEY,
    original_url VARCHAR(2048) NOT NULL,
    short_key VARCHAR(10) UNIQUE,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expiration_date TIMESTAMP,
    hits INT DEFAULT 0
);

