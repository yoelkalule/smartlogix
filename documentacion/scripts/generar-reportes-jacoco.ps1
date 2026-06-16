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
    Write-Host "Ejecutando pruebas en $servicio"
    Write-Host "========================================"

    Push-Location $servicio

    mvn clean test

    Pop-Location
}

Write-Host ""
Write-Host "Reportes generados. Busca archivos en:"
Write-Host "target\site\jacoco\index.html"