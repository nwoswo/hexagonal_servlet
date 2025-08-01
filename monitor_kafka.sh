#!/bin/bash

# Script para monitorear topics de Kafka en tiempo real
TOPIC=${1:-"orden-created"}
ALL_TOPICS=${2:-false}

# Colores para output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
GRAY='\033[0;37m'
NC='\033[0m' # No Color

echo -e "${GREEN}=== Monitor de Kafka ===${NC}"

if [ "$ALL_TOPICS" = "true" ] || [ "$1" = "--all" ]; then
    echo -e "${YELLOW}Monitoreando todos los topics...${NC}"
    
    # Monitorear orden-created
    echo -e "\n📊 ${CYAN}Topic: orden-created${NC}"
    docker exec ordenes-kafka kafka-console-consumer \
        --bootstrap-server localhost:9092 \
        --topic orden-created \
        --from-beginning \
        --property print.timestamp=true \
        --property print.key=true \
        --property print.value=true \
        --property print.partition=true \
        --property print.offset=true
    
else
    echo -e "${YELLOW}Monitoreando topic: $TOPIC${NC}"
    echo -e "${GRAY}Presiona Ctrl+C para salir${NC}"
    
    # Monitorear topic específico
    docker exec ordenes-kafka kafka-console-consumer \
        --bootstrap-server localhost:9092 \
        --topic $TOPIC \
        --from-beginning \
        --property print.timestamp=true \
        --property print.key=true \
        --property print.value=true \
        --property print.partition=true \
        --property print.offset=true
fi

echo -e "\n${YELLOW}Comandos útiles:${NC}"
echo -e "${GRAY}  ./monitor_kafka.sh orden-created${NC}"
echo -e "${GRAY}  ./monitor_kafka.sh item-orden-created${NC}"
echo -e "${GRAY}  ./monitor_kafka.sh orden-cancelled${NC}"
echo -e "${GRAY}  ./monitor_kafka.sh --all${NC}" 