$ErrorActionPreference = "Stop"

$pomFiles = @(
    "api-gateway\api-gateway\pom.xml",
    "ms-usuarios\ms-usuarios\pom.xml",
    "ms-inventario\ms-inventario\pom.xml",
    "ms-pedidos\ms-pedidos\pom.xml",
    "ms-envios\ms-envios\pom.xml",
    "ms-notificaciones\ms-notificaciones\pom.xml"
)

$jacocoPlugin = @"

            <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>0.8.12</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>report</id>
                        <phase>test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
"@

foreach ($pom in $pomFiles) {
    if (!(Test-Path $pom)) {
        Write-Host "[NO EXISTE] $pom" -ForegroundColor Yellow
        continue
    }

    $content = Get-Content $pom -Raw

    if ($content -match "jacoco-maven-plugin") {
        Write-Host "[YA TIENE JACOCO] $pom" -ForegroundColor Cyan
        continue
    }

    if ($content -notmatch "</plugins>") {
        Write-Host "[ERROR] No encontre </plugins> en $pom" -ForegroundColor Red
        continue
    }

    $content = $content -replace "</plugins>", "$jacocoPlugin`r`n        </plugins>"

    Set-Content -Path $pom -Value $content -Encoding UTF8

    Write-Host "[OK] JaCoCo agregado en $pom" -ForegroundColor Green
}

Write-Host ""
Write-Host "Listo. Ahora ejecuta: findstr /s /i `"jacoco`" pom.xml" -ForegroundColor Cyan