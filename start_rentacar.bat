@echo off
title RentaCar - Ecosistema de Microservicios
color 0A

echo ===================================================
echo   Rentacar
echo ===================================================

echo.
echo [1/4] Iniciando contenedor de MySQL...
docker-compose up -d mysql-db
echo Esperando 12 segundos para asegurar que el motor de BD este listo...
timeout /t 12 /nobreak > NUL

echo.
echo [2/4] Levantando Eureka Server (Service Registry)...
start "Eureka Server" cmd /k "cd eureka-server && title Eureka Server && mvn spring-boot:run"
echo Esperando 15 segundos para que Eureka este operativo...
timeout /t 15 /nobreak > NUL

echo.
echo [3/4] Levantando API Gateway...
start "API Gateway" cmd /k "cd api-gateway && title API Gateway && mvn spring-boot:run"
echo Esperando 8 segundos...