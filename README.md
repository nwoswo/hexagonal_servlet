# Servicio de Órdenes - Arquitectura Hexagonal

Este proyecto implementa un servicio de gestión de órdenes utilizando arquitectura hexagonal (Ports & Adapters) con Spring Boot 3.2.0.

## 🏗️ Arquitectura

### Tecnologías Utilizadas

- **Spring Boot 3.2.0** - Framework principal
- **Spring Data JPA** - Persistencia de datos
- **H2 Database** - Base de datos en memoria (desarrollo)
- **MySQL** - Base de datos de producción
- **Apache Kafka** - Mensajería asíncrona
- **RabbitMQ** - Colas de mensajes
- **MapStruct** - Mapeo de objetos
- **Lombok** - Reducción de boilerplate
- **Maven** - Gestión de dependencias

### Estructura del Proyecto

```
src/main/java/com/tuempresa/ordenes/
├── domain/                    # Capa de dominio
│   ├── model/                # Entidades de dominio
│   ├── event/                # Eventos de dominio
│   ├── exception/            # Excepciones de dominio
│   └── service/              # Servicios de dominio
├── application/              # Capa de aplicación
│   ├── dto/                 # DTOs de aplicación
│   ├── port/
│   │   ├── in/              # Puertos de entrada
│   │   └── out/             # Puertos de salida
│   └── usecase/             # Casos de uso
├── adapter/                 # Adaptadores
│   ├── inbound/             # Adaptadores de entrada
│   │   ├── rest/            # REST API
│   │   ├── kafka/           # Consumidores Kafka
│   │   └── mq/              # Listeners RabbitMQ
│   └── outbound/            # Adaptadores de salida
│       ├── persistence/     # Persistencia JPA
│       ├── kafka/           # Productores Kafka
│       └── rest/            # Clientes REST
├── infrastructure/          # Configuración de infraestructura
└── shared/                 # Componentes compartidos
```

## 🚀 Inicio Rápido

### Prerrequisitos

- Java 17 o superior
- Maven 3.6+
- Docker Desktop

### Opción 1: Con Docker (Recomendado)

1. **Iniciar el entorno de servicios:**
   ```powershell
   .\start-environment.ps1
   ```

2. **Ejecutar la aplicación:**
   ```bash
   mvn spring-boot:run
   ```

3. **Acceder a la aplicación:**
   - API REST: http://localhost:9000/api/ordenes
   - H2 Console: http://localhost:9000/h2-console
   - RabbitMQ Management: http://localhost:15672

### Opción 2: Sin Docker (Solo H2)

1. **Ejecutar solo la aplicación:**
   ```bash
   mvn spring-boot:run
   ```

2. **Acceder a la aplicación:**
   - API REST: http://localhost:9000/api/ordenes
   - H2 Console: http://localhost:9000/h2-console

## 📋 Servicios Docker

El entorno Docker incluye:

- **MySQL 8.0** - Base de datos principal
- **Apache Kafka** - Mensajería asíncrona
- **RabbitMQ** - Colas de mensajes
- **Zookeeper** - Coordinación de Kafka
- **MockServer** - Servicios externos simulados

### Puertos de Servicios

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| MySQL | 3306 | Base de datos |
| Kafka | 9092 | Mensajería |
| RabbitMQ | 5672 | Colas de mensajes |
| RabbitMQ Management | 15672 | Interfaz web |
| Inventory Service | 8081 | Servicio mock |
| Payment Service | 8082 | Servicio mock |

## 🔧 Configuración

### Perfiles de Spring Boot

- **dev**: H2 Database, configuración de desarrollo
- **prod**: MySQL, configuración de producción

### Variables de Entorno

```bash
# Base de datos
DB_USERNAME=root
DB_PASSWORD=password

# Kafka
KAFKA_BOOTSTRAP_SERVERS=localhost:9092

# RabbitMQ
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=guest
RABBITMQ_PASSWORD=guest

# Servicios externos
INVENTORY_SERVICE_URL=http://localhost:8081/api/inventory
PAYMENT_SERVICE_URL=http://localhost:8082/api/payment
```

## 📚 API Endpoints

### Órdenes

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/ordenes` | Crear nueva orden |
| GET | `/api/ordenes` | Obtener todas las órdenes |
| GET | `/api/ordenes/{id}` | Obtener orden por ID |
| GET | `/api/ordenes/numero/{numero}` | Obtener orden por número |
| GET | `/api/ordenes/cliente/{clienteId}` | Obtener órdenes por cliente |
| POST | `/api/ordenes/{id}/cancelar` | Cancelar orden |
| POST | `/api/ordenes/{id}/items` | Agregar item a orden |

### Ejemplo de Crear Orden

```json
POST /api/ordenes
{
  "clienteId": "CLI-001",
  "clienteNombre": "Juan Pérez",
  "clienteEmail": "juan@example.com",
  "items": [
    {
      "productoId": "PROD-001",
      "productoNombre": "Laptop",
      "productoDescripcion": "Laptop gaming",
      "precioUnitario": 1500.00,
      "cantidad": 1
    }
  ]
}
```

## 🔄 Eventos de Dominio

### Eventos Publicados

- `OrdenCreadaEvent` - Cuando se crea una orden
- `OrdenCanceladaEvent` - Cuando se cancela una orden
- `ItemOrdenCreadoEvent` - Cuando se agrega un item

### Tópicos Kafka

- `orden-created`
- `orden-updated`
- `orden-cancelled`
- `item-orden-created`
- `item-orden-updated`
- `item-orden-deleted`

### Colas RabbitMQ

- `orden-created`
- `orden-cancelled`
- `item-orden-created`

## 🧪 Testing

### Ejecutar Tests

```bash
mvn test
```

### Tests de Integración

```bash
mvn verify
```

## 📊 Monitoreo

### Health Checks

- **Application Health**: http://localhost:9000/actuator/health
- **Database Health**: Verificar conexión H2/MySQL
- **Kafka Health**: Verificar conectividad a localhost:9092
- **RabbitMQ Health**: Verificar conectividad a localhost:5672

### Logs

```bash
# Ver logs de la aplicación
mvn spring-boot:run

# Ver logs de Docker
docker-compose logs -f
```

## 🛠️ Desarrollo

### Compilar

```bash
mvn clean compile
```

### Ejecutar Tests

```bash
mvn test
```

### Generar JAR

```bash
mvn clean package
```

### Ejecutar JAR

```bash
java -jar target/ordenes-1.0.0.jar
```

## 🐳 Comandos Docker Útiles

```bash
# Iniciar servicios
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener servicios
docker-compose down

# Reconstruir servicios
docker-compose up -d --build

# Ver estado de servicios
docker-compose ps
```

## 📝 Principios de Arquitectura Hexagonal

### Separación de Responsabilidades

1. **Domain Layer**: Lógica de negocio pura
2. **Application Layer**: Orquestación de casos de uso
3. **Adapter Layer**: Adaptadores para tecnologías externas

### Puertos y Adaptadores

- **Puertos de Entrada**: Interfaces que definen cómo la aplicación recibe datos
- **Puertos de Salida**: Interfaces que definen cómo la aplicación envía datos
- **Adaptadores de Entrada**: Implementaciones de puertos de entrada
- **Adaptadores de Salida**: Implementaciones de puertos de salida

### Beneficios

- **Independencia de Frameworks**: El dominio no depende de Spring
- **Testabilidad**: Fácil testing de cada capa
- **Mantenibilidad**: Cambios en una capa no afectan otras
- **Escalabilidad**: Fácil agregar nuevos adaptadores

## 🤝 Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.

## 👥 Autores

- **Tu Empresa** - *Desarrollo inicial* - [TuEmpresa](https://github.com/tuempresa)

## 🙏 Agradecimientos

- Spring Boot Team por el excelente framework
- MapStruct por la herramienta de mapeo
- La comunidad de arquitectura hexagonal por las mejores prácticas 