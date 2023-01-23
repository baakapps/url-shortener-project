CREATE TABLE url_shortener.range (
                                  id    enum('1') PRIMARY KEY,
                                  start INT UNSIGNED NOT NULL,
                                  end   INT UNSIGNED NOT NULL,
                                  CHECK (start <= end)
);

INSERT INTO url_shortener.range(id, start, end) VALUES (1, 0, 1000);
DELIMITER //
create trigger before_range_update
    before update
    on url_shortener.range
    for each row
BEGIN
    DECLARE errorMessage VARCHAR(150);
    SET @lastEndValue = (SELECT end FROM url_shortener.range WHERE id=1);

    IF @lastEndValue IS NOT NULL AND NEW.start <= @lastEndValue THEN
        SET errorMessage = CONCAT('The new start value ',
                                  NEW.start,
                                  ' must be greater than end value ',
                                  @lastEndValue);
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = errorMessage;
    END IF;
END;

CREATE USER 'range_app'@'%' IDENTIFIED BY 'p_range_app';
GRANT ALL PRIVILEGES ON url_shortener.* TO 'range_app'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;