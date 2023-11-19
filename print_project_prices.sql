SELECT ID AS NAME, ( 
    SELECT SUM(SALARY) 
    FROM worker
    WHERE id = (
        SELECT WORKER_ID
        FROM project_worker
        WHERE project_worker.PROJECT_ID = project.ID AND WORKER_ID = id
    )
    )* DATEDIFF(FINISH_DATE,START_DATE)/30 AS PRICE 
FROM project;