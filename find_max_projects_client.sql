SELECT *
FROM client
WHERE (
    SELECT COUNT(CLIENT_ID)
    FROM project
    WHERE client.id = project.CLIENT_ID
    ) = (
       SELECT MAX(COUNTING)
FROM( 
    SELECT COUNT(CLIENT_ID) AS COUNTING, CLIENT_ID
    FROM project
    GROUP BY CLIENT_ID
) AS T 
    );