@echo off
echo ========================================
echo Compilando SmartLogix
echo ========================================

echo.
echo Compilando API Gateway...
cd api-gateway\api-gateway
call mvn clean package -DskipTests
if errorlevel 1 goto error
cd ..\..

echo.
echo Compilando ms-usuarios...
cd ms-usuarios\ms-usuarios
call mvn clean package -DskipTests
if errorlevel 1 goto error
cd ..\..

echo.
echo Compilando ms-inventario...
cd ms-inventario\ms-inventario
call mvn clean package -DskipTests
if errorlevel 1 goto error
cd ..\..

echo.
echo Compilando ms-pedidos...
cd ms-pedidos\ms-pedidos
call mvn clean package -DskipTests
if errorlevel 1 goto error
cd ..\..

echo.
echo Compilando ms-envios...
cd ms-envios\ms-envios
call mvn clean package -DskipTests
if errorlevel 1 goto error
cd ..\..

echo.
echo Compilando ms-notificaciones...
cd ms-notificaciones\ms-notificaciones
call mvn clean package -DskipTests
if errorlevel 1 goto error
cd ..\..

echo.
echo ========================================
echo Compilacion completada correctamente
echo ========================================
pause
exit /b 0

:error
echo.
echo ========================================
echo Error durante la compilacion
echo ========================================
pause
exit /b 1