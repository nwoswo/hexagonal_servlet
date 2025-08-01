#!/bin/bash

# Script para probar la aplicación y ver eventos de Kafka
echo "=== Test de Eventos Kafka ==="

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
GRAY='\033[0;37m'
NC='\033[0m' # No Color

# 1. Verificar que la aplicación esté corriendo
echo -e "${YELLOW}1. Verificando estado de la aplicación...${NC}"
if curl -s http://localhost:9000/actuator/health > /dev/null; then
    echo -e "${GREEN}✅ Aplicación corriendo en puerto 9000${NC}"
else
    echo -e "${RED}❌ Aplicación no está corriendo en puerto 9000${NC}"
    echo -e "${YELLOW}Ejecuta: mvn spring-boot:run${NC}"
    exit 1
fi

# 2. Crear una orden para generar evento
echo -e "\n${YELLOW}2. Creando orden para generar evento...${NC}"

# Crear JSON para la orden
cat > /tmp/orden_request.json << 'EOF'
{
    "clienteId": "CLI-TEST-001",
    "clienteNombre": "Usuario Test",
    "clienteEmail": "test@example.com",
    "items": [
        {
            "productoId": "PROD-TEST-001",
            "productoNombre": "Producto Test",
            "productoDescripcion": "Descripción del producto test",
            "precioUnitario": 100.00,
            "cantidad": 2
        }
    ]
}
EOF

# Crear la orden
if response=$(curl -s -X POST http://localhost:9000/api/ordenes \
    -H "Content-Type: application/json" \
    -d @/tmp/orden_request.json); then
    echo -e "${GREEN}✅ Orden creada exitosamente${NC}"
    echo -e "${CYAN}   Respuesta: $response${NC}"
else
    echo -e "${RED}❌ Error al crear orden${NC}"
    exit 1
fi

# 3. Verificar eventos en Kafka
echo -e "\n${YELLOW}3. Verificando eventos en Kafka...${NC}"

# Esperar un momento para que el evento se procese
sleep 2

# Verificar mensajes en el topic orden-created
echo -e "   ${CYAN}Verificando topic 'orden-created'...${NC}"
kafka_messages=$(docker exec ordenes-kafka kafka-console-consumer \
    --bootstrap-server localhost:9092 \
    --topic orden-created \
    --from-beginning \
    --max-messages 5 \
    --timeout-ms 5000 2>/dev/null)

if [ ! -z "$kafka_messages" ]; then
    echo -e "${GREEN}✅ Eventos encontrados en topic 'orden-created':${NC}"
    echo "$kafka_messages" | while IFS= read -r line; do
        echo -e "${GRAY}   $line${NC}"
    done
else
    echo -e "${YELLOW}⚠️  No se encontraron eventos en topic 'orden-created'${NC}"
fi

# 4. Verificar topic item-orden-created
echo -e "\n${YELLOW}4. Verificando topic 'item-orden-created'...${NC}"
item_messages=$(docker exec ordenes-kafka kafka-console-consumer \
    --bootstrap-server localhost:9092 \
    --topic item-orden-created \
    --from-beginning \
    --max-messages 5 \
    --timeout-ms 5000 2>/dev/null)

if [ ! -z "$item_messages" ]; then
    echo -e "${GREEN}✅ Eventos encontrados en topic 'item-orden-created':${NC}"
    echo "$item_messages" | while IFS= read -r line; do
        echo -e "${GRAY}   $line${NC}"
    done
else
    echo -e "${YELLOW}⚠️  No se encontraron eventos en topic 'item-orden-created'${NC}"
fi

# 5. Mostrar información de topics
echo -e "\n${YELLOW}5. Información de topics Kafka:${NC}"
docker exec ordenes-kafka kafka-topics --describe --bootstrap-server localhost:9092 --topic orden-created
docker exec ordenes-kafka kafka-topics --describe --bootstrap-server localhost:9092 --topic item-orden-created

# Limpiar archivo temporal
rm -f /tmp/orden_request.json

echo -e "\n=== Fin del Test ==="
echo -e "${CYAN}Para monitorear en tiempo real, ejecuta:${NC}"
echo -e "${GRAY}docker exec ordenes-kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic orden-created${NC}" 