SELECT * FROM continents;
SELECT * FROM countries;
SELECT * FROM country_languages;

SELECT c.name AS con_name, co.name AS coun_name, co.area FROM continents AS c
JOIN countries AS co
ON (co.name LIKE CONCAT("%", SUBSTR(c.name, 2, 1), "%"));