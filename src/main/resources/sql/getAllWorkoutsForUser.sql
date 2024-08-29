SELECT "WkID",
       s."SetGroupID",
       COUNT("SetID") AS setCount,
       ARRAY_AGG("SetExID"),
       ARRAY_AGG("SetRepetitions"),
       ARRAY_AGG("SetWeight"),
       ARRAY_AGG("SetRPE"),
       ARRAY_AGG("SetCompletedTime"),
       ARRAY_AGG("SetRest"),
       "ExName"
FROM public."tblWorkouts" AS w
LEFT JOIN public."tblSetGroups" AS sg
       ON "WkID" = "SetGroupWkID"
LEFT JOIN public."tblSets" AS s
       ON sg."SetGroupID" = s."SetGroupID"
LEFT JOIN public."tblExercises" AS ex
       ON s."SetExID" = ex."ExID"
WHERE "WkUserID" = '68c283d8-43a4-4b89-8af5-3be8768184fe'
GROUP BY "WkID", s."SetGroupID", "SetExID", "ExName";