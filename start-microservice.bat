@echo off
:menu
cls
echo =========================================
echo          Microservices Control Panel
echo =========================================
echo [1] Start All Microservices
echo [2] Stop All Microservices
echo [3] Exit
echo =========================================
set /p choice="Enter your choice: "

if "%choice%"=="1" (
    echo Stopping any running Java processes...
    taskkill /F /IM java.exe

    echo Starting Eureka Server...
    start cmd /k "cd /d D:\hide\code\eureka-server\eureka-server && mvn spring-boot:run"

    timeout /t 5

    echo Starting API Gateway...
    start cmd /k "cd /d D:\hide\code\api-gateway\api-gateway && mvn spring-boot:run"

    timeout /t 5

    echo Starting User Service...
    start cmd /k "cd /d D:\hide\code\user-service\user-service && mvn spring-boot:run"

    timeout /t 5

    echo Starting Order Service...
    start cmd /k "cd /d D:\hide\code\order-service\order-service && mvn spring-boot:run"

    echo All microservices started successfully!
    pause
    goto menu
)

if "%choice%"=="2" (
    echo Stopping all running microservices...
    taskkill /F /IM java.exe
    echo All microservices stopped successfully!
    pause
    goto menu
)

if "%choice%"=="3" (
    echo Exiting...
    exit
)

echo Invalid choice! Please enter 1, 2, or 3.
pause
goto menu
