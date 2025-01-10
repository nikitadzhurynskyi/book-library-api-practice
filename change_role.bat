@echo off
setlocal enabledelayedexpansion
for /f "tokens=1,2 delims==" %%A in (.env) do (

    if "%%A" neq "" (
        set "%%A=%%B"
    )
)


set CONTAINER_NAME=book-library-api-practice-db_service-1

set USER_NAME=%1
set ROLE_NAME=%2
set ACTION=%3

if "%USER_NAME%"=="" (
    echo Usage: %~nx0 ^<username^> ^<ROLE_USER^|ROLE_ADMIN^> ^<add^|remove^>
    exit /b 1
)

if "%ROLE_NAME%"=="" (
    echo Usage: %~nx0 ^<username^> ^<ROLE_USER^|ROLE_ADMIN^> ^<add^|remove^>
    exit /b 1
)

if "%ACTION%"=="" (
    echo Usage: %~nx0 ^<username^> ^<ROLE_USER^|ROLE_ADMIN^> ^<add^|remove^>
    exit /b 1
)

if /i "%ROLE_NAME%" NEQ "ROLE_USER" if /i "%ROLE_NAME%" NEQ "ROLE_ADMIN" (
    echo Error: Invalid role. Only ROLE_USER or ROLE_ADMIN are allowed.
    exit /b 1
)
set FIND_USER_ID=SELECT id FROM user_entity WHERE email = '%USER_NAME%';


if "%ACTION%"=="add" (
    set SQL="INSERT INTO user_role_entity (user_id, role_id) %SQL% SELECT id, '%ROLE_NAME%' FROM user_entity WHERE email = '%USER_NAME%' ON CONFLICT DO NOTHING;"
)

if "%ACTION%"=="remove" (
    set SQL="DELETE FROM user_role_entity WHERE user_id = (SELECT id FROM user_entity WHERE email = '%USER_NAME%') AND role_id = '%ROLE_NAME%';"
)


docker exec -it %CONTAINER_NAME% psql -U %DB_USER% -d %DB_NAME% -c %SQL%

echo "Role modified successfully."