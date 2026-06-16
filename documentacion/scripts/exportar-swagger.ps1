$ErrorActionPreference = "Continue"

$raizProyecto = Resolve-Path (Join-Path $PSScriptRoot "..\..")
$carpetaSalida = Join-Path $raizProyecto "documentacion\swagger"

New-Item -ItemType Directory -Force -Path $carpetaSalida | Out-Null

$servicios = @{
    "ms-usuarios" = "http://localhost:8080/v3/api-docs"
    "ms-inventario" = "http://localhost:8081/v3/api-docs"
    "ms-pedidos" = "http://localhost:8082/v3/api-docs"
    "ms-envios" = "http://localhost:8083/v3/api-docs"
    "ms-notificaciones" = "http://localhost:8084/v3/api-docs"
}

foreach ($servicio in $servicios.GetEnumerator()) {
    $archivo = Join-Path $carpetaSalida ($servicio.Key + "-openapi.json")

    try {
        Invoke-WebRequest `
            -Uri $servicio.Value `
            -OutFile $archivo `
            -UseBasicParsing

        Write-Host "[OK] $($servicio.Key)" -ForegroundColor Green
    }
    catch {
        Write-Host "[ERROR] $($servicio.Key): $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "Archivos guardados en documentacion\swagger" -ForegroundColor Cyan