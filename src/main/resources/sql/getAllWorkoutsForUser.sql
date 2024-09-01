
SELECT "WkID" AS workoutId,
       "WkName" AS workoutName,
       "WkDateCompleted" AS completionDate,
       "WkUserID" AS userId,
       s."SetGroupID" AS setGroupId,
       "ExName" AS exercise,
       COUNT("SetID") AS setCount,
       ARRAY_AGG("SetRepetitions") AS repetitions,
       ARRAY_AGG("SetWeight") AS weights,
       ARRAY_AGG("SetRPE") AS intensities,
       ARRAY_AGG("SetRest") AS restPeriods,
       "UnitName" AS unit,
       "UnitShortName" AS unitShortName
FROM public."tblWorkouts" AS w
         LEFT JOIN public."tblSetGroups" AS sg
                   ON "WkID" = "SetGroupWkID"
         LEFT JOIN public."tblSets" AS s
                   ON sg."SetGroupID" = s."SetGroupID"
         LEFT JOIN public."tblExercises" AS ex
                   ON s."SetExID" = ex."ExID"
         LEFT JOIN public."tblUnit" AS u
                   ON "SetUnitID" = "UnitID"
WHERE "WkUserID"::text = :userId
GROUP BY "WkID",completionDate, userId, s."SetGroupID", "ExName", unit, unitShortName;