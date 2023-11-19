SELECT NAME, BIRTHDAY, (CASE WHEN (BIRTHDAY = (
      SELECT MAX(BIRTHDAY)
      FROM worker
      )) THEN 'Youngest'
        WHEN (BIRTHDAY = (
      SELECT MIN(BIRTHDAY)
      FROM worker
      )) THEN 'Eldest'
   END) AS TYPE
FROM worker
WHERE BIRTHDAY = (
    SELECT MIN(BIRTHDAY)
    FROM worker
) OR BIRTHDAY = (SELECT MAX(BIRTHDAY)
    FROM worker);