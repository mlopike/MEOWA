CREATE TABLE IF NOT EXISTS `Location` (
    `location_id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(255),
    `long`        DECIMAL(9,6),
    `lat`         DECIMAL(9,6),
    PRIMARY KEY(`location_id`)
);

CREATE TABLE IF NOT EXISTS `Meteo-metrics` (
    `measure_id`          INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    `location_id`         INTEGER UNSIGNED NOT NULL,
    `temperature`         DECIMAL(3,1),
    `apparent_temperature` DECIMAL(3,1),
    `weather_code`        INTEGER,
    `precipitation`       DECIMAL(4,1),
    `surface_pressure`    DECIMAL(6,1),
    `wind_speed`          DECIMAL(4,1),
    `wind_direction`      CHAR(2),
    `relative_humidity`   INTEGER,
    `measure_t`           DATETIME,
    `add_t`               DATETIME,
    `upd_t`               DATETIME,
    `del_t`               DATETIME,
    PRIMARY KEY(`measure_id`)
);

CREATE TABLE IF NOT EXISTS `Country` (
    `country_id`   INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    `country_code` CHAR(2),
    `country_name` VARCHAR(255),
    PRIMARY KEY(`country_id`)
);

CREATE TABLE IF NOT EXISTS `Source` (
    `source_id`   INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    `source_name` VARCHAR(255),
    `source_icon` VARCHAR(2048),
    `source_url`  VARCHAR(2048),
    PRIMARY KEY(`source_id`)
);

CREATE TABLE IF NOT EXISTS `News` (
    `news_id`     INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    `source_id`   INTEGER UNSIGNED NOT NULL,
    `country_id`  INTEGER UNSIGNED NOT NULL,
    `title`       VARCHAR(500),
    `description` TEXT,
    `content`     TEXT,
    `pub_t`       DATETIME,
    `add_t`       DATETIME,
    `upd_t`       DATETIME,
    `del_t`       DATETIME,
    PRIMARY KEY(`news_id`)
);

CREATE TABLE IF NOT EXISTS `Images` (
    `image_id`  INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    `news_id`   INTEGER UNSIGNED NOT NULL,
    `image_url` VARCHAR(2048),
    `add_t`     DATETIME,
    `upd_t`     DATETIME,
    `del_t`     DATETIME,
    PRIMARY KEY(`image_id`)
);

-- Внешние ключи
ALTER TABLE `Meteo-metrics`
ADD FOREIGN KEY(`location_id`) REFERENCES `Location`(`location_id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `News`
ADD FOREIGN KEY(`source_id`) REFERENCES `Source`(`source_id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `News`
ADD FOREIGN KEY(`country_id`) REFERENCES `Country`(`country_id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `Images`
ADD FOREIGN KEY(`news_id`) REFERENCES `News`(`news_id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

-- Уникальный индекс для защиты от дублей в Meteo-metrics
ALTER TABLE `Meteo-metrics`
ADD UNIQUE INDEX `uq_location_measure_time` (`location_id`, `measure_t`);