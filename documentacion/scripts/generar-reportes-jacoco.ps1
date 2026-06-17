$ErrorActionPreference = "Continue"

$servicios = @(
    "api-gateway\api-gateway",
    "ms-usuarios\ms-usuarios",
    "ms-inventario\ms-inventario",
    "ms-pedidos\ms-pedidos",
    "ms-envios\ms-envios",
    "ms-notificaciones\ms-notificaciones"
)

foreach ($servicio in $servicios) {
    Write-Host ""
    Write-Host "========================================"
    Write-Host "Ejecutando pruebas con Docker Java 21 en $servicio"
    Write-Host "========================================"

    $ruta = Join-Path (Get-Location) $servicio

    docker run --rm `
        -v "${ruta}:/app" `
        -w /app `
        maven:3.9.9-eclipse-temurin-21 `
        mvn clean test
}

Write-Host ""
Write-Host "Reportes generados. Revisa:"
Write-Host "target\site\jacoco\index.html"