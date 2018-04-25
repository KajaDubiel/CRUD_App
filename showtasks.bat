call runcrud
if "%ERRORLEVEL%" == "0" goto startbrowser
echo.
echo Can not run crud
goto failed

:startbrowser
start chrome.exe http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto missioncompleted
echo.
echo some troubles with starting browser
goto failed

:missioncompleted
echo.
echo showing tasks completed
goto end

:failed
echo.
echo showing tasks failed

:end
echo.
echo the end of showing tasks