SELECT *
FROM tes_tabel;
SELECT id,
  CASE
    SUBSTR(nama_belakang, 1, 1)
    WHEN 'Z' THEN 'Initial Z'
    WHEN 'S' THEN 'Initial S'
    WHEN 'M' THEN 'Initial M'
  END AS 'Inisial',
  CASE
    gaji
    WHEN 7000000 THEN 'Woke'
  END
FROM tes_tabel;
SELECT gaji,
  CASE
    WHEN gaji BETWEEN 6000000 AND 7000000 THEN 'Nice'
    WHEN gaji BETWEEN 4000000 AND 5000000 THEN 'Good'
    ELSE 'I Dont Know'
  END AS 'Status'
FROM tes_tabel
ORDER BY gaji;
SELECT UPPER(gaji + 100)
FROM tes_tabel;