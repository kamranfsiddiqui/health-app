SELECT "WkID" AS workoutId,
       s."SetGroupID" AS setGroupId,
       "SetExID" AS exerciseId,
       "ExName" AS exercise,
       COUNT("SetID") AS setCount,
       ARRAY_AGG("SetRepetitions") AS repetitions,
       ARRAY_AGG("SetWeight") AS weights,
       ARRAY_AGG("SetRPE") AS intensities,
       ARRAY_AGG("SetRest") AS restPeriods
FROM public."tblWorkouts" AS w
LEFT JOIN public."tblSetGroups" AS sg
       ON "WkID" = "SetGroupWkID"
LEFT JOIN public."tblSets" AS s
       ON sg."SetGroupID" = s."SetGroupID"
LEFT JOIN public."tblExercises" AS ex
       ON s."SetExID" = ex."ExID"
WHERE "WkUserID" = '68c283d8-43a4-4b89-8af5-3be8768184fe'
GROUP BY "WkID", s."SetGroupID", "SetExID", "ExName";