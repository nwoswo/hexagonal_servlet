# Script para monitorear topics de Kafka en tiempo real
param(
    [string]$Topic = "orden-created",
    [switch]$AllTopics
)

Write-Host "=== Monitor de Kafka ===" -ForegroundColor Green

if ($AllTopics) {
    Write-Host "Monitoreando todos los topics..." -ForegroundColor Yellow
    
    # Monitorear orden-created
    Write-Host "`n📊 Topic: orden-created" -ForegroundColor Cyan
    docker exec ordenes-kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic orden-created --from-beginning --property print.timestamp=true --property print.key=true --property print.value=true --property print.partition=true --property print.offset=true
    
} else {
    Write-Host "Monitoreando topic: $Topic" -ForegroundColor Yellow
    Write-Host "Presiona Ctrl+C para salir" -ForegroundColor Gray
    
    # Monitorear topic específico
    docker exec ordenes-kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic $Topic --from-beginning --property print.timestamp=true --property print.key=true --property print.value=true --property print.partition=true --property print.offset=true
}

Write-Host "`nComandos útiles:" -ForegroundColor Yellow
Write-Host "  .\monitor_kafka.ps1 -Topic orden-created" -ForegroundColor Gray
Write-Host "  .\monitor_kafka.ps1 -Topic item-orden-created" -ForegroundColor Gray
Write-Host "  .\monitor_kafka.ps1 -Topic orden-cancelled" -ForegroundColor Gray
Write-Host "  .\monitor_kafka.ps1 -AllTopics" -ForegroundColor Gray 