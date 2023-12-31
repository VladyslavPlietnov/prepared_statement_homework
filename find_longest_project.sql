SELECT ID, DATEDIFF(FINISH_DATE, START_DATE)/30 AS MONTHS
FROM project
WHERE DATEDIFF(FINISH_DATE, START_DATE) = (
    SELECT MAX(DIFF)
    FROM(
        SELECT DATEDIFF(FINISH_DATE, START_DATE) AS DIFF, ID
        FROM project
        GROUP BY ID
    ) AS T
);