# Script para ejecutar pruebas de Postman automáticamente
# Requiere: Newman CLI (npm install -g newman)

param(
    [string]$Environment = "dev",
    [string]$BaseUrl = "http://localhost:9000"
)

Write-Host "🚀 Iniciando pruebas de la API de Órdenes..." -ForegroundColor Green
Write-Host "📋 Entorno: $Environment" -ForegroundColor Yellow
Write-Host "🌐 URL Base: $BaseUrl" -ForegroundColor Yellow

# Verificar que Newman esté instalado
try {
    $newmanVersion = newman --version
    Write-Host "✅ Newman CLI encontrado: $newmanVersion" -ForegroundColor Green
} catch {
    Write-Host "❌ Newman CLI no encontrado. Instalando..." -ForegroundColor Red
    Write-Host "Ejecuta: npm install -g newman" -ForegroundColor Yellow
    exit 1
}

# Verificar que la aplicación esté corriendo
Write-Host "🔍 Verificando que la aplicación esté corriendo..." -ForegroundColor Cyan
try {
    $response = Invoke-WebRequest -Uri "$BaseUrl/actuator/health" -Method GET -TimeoutSec 5
    if ($response.StatusCode -eq 200) {
        Write-Host "✅ Aplicación corriendo en $BaseUrl" -ForegroundColor Green
    } else {
        Write-Host "⚠️ Aplicación respondió con código: $($response.StatusCode)" -ForegroundColor Yellow
    }
} catch {
    Write-Host "❌ No se puede conectar a $BaseUrl" -ForegroundColor Red
    Write-Host "💡 Asegúrate de que la aplicación esté corriendo:" -ForegroundColor Yellow
    Write-Host "   gradle bootRun" -ForegroundColor Cyan
    exit 1
}

# Crear archivo de variables de entorno temporal
$envFile = "postman/environment.json"
$envContent = @"
{
    "id": "temp-env-id",
    "name": "Temporary Environment",
    "values": [
        {
            "key": "baseUrl",
            "value": "$BaseUrl",
            "enabled": true
        },
        {
            "key": "ordenId",
            "value": "",
            "enabled": true
        },
        {
            "key": "numeroOrden",
            "value": "",
            "enabled": true
        },
        {
            "key": "clienteId",
            "value": "CLI-001",
            "enabled": true
        }
    ]
}
"@

$envContent | Out-File -FilePath $envFile -Encoding UTF8

# Ejecutar pruebas con Newman
Write-Host "🧪 Ejecutando pruebas de Postman..." -ForegroundColor Cyan

$newmanArgs = @(
    "run",
    "postman/Ordenes_API_Collection.json",
    "--environment", $envFile,
    "--reporters", "cli,json",
    "--reporter-json-export", "postman/test-results.json",
    "--reporter-cli-no-success-assertions",
    "--reporter-cli-no-console",
    "--delay-request", "1000"
)

try {
    newman @newmanArgs
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Todas las pruebas pasaron exitosamente!" -ForegroundColor Green
    } else {
        Write-Host "❌ Algunas pruebas fallaron. Revisa el reporte." -ForegroundColor Red
    }
} catch {
    Write-Host "❌ Error ejecutando las pruebas: $($_.Exception.Message)" -ForegroundColor Red
}

# Limpiar archivo temporal
if (Test-Path $envFile) {
    Remove-Item $envFile
    Write-Host "🧹 Archivo temporal limpiado" -ForegroundColor Gray
}

# Mostrar resumen
if (Test-Path "postman/test-results.json") {
    Write-Host "📊 Generando reporte de resultados..." -ForegroundColor Cyan
    
    try {
        $results = Get-Content "postman/test-results.json" | ConvertFrom-Json
        $totalTests = $results.run.stats.assertions.total
        $passedTests = $results.run.stats.assertions.passed
        $failedTests = $results.run.stats.assertions.failed
        
        Write-Host "📈 Resumen de Pruebas:" -ForegroundColor Green
        Write-Host "   Total: $totalTests" -ForegroundColor White
        Write-Host "   Pasadas: $passedTests" -ForegroundColor Green
        Write-Host "   Fallidas: $failedTests" -ForegroundColor Red
        
        if ($failedTests -gt 0) {
            Write-Host "🔍 Revisa el archivo postman/test-results.json para más detalles" -ForegroundColor Yellow
        }
    } catch {
        Write-Host "⚠️ No se pudo generar el reporte de resultados" -ForegroundColor Yellow
    }
}

Write-Host "🎉 Proceso completado!" -ForegroundColor Green 