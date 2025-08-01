# Colección de Postman - API de Órdenes

## 📋 Descripción

Esta colección de Postman contiene casos de prueba completos para la API de Órdenes desarrollada con arquitectura hexagonal. La colección está organizada en 6 secciones principales que cubren todos los escenarios de prueba.

## 🚀 Instalación

### 1. Importar la Colección

1. Abre Postman
2. Haz clic en **Import**
3. Selecciona el archivo `Ordenes_API_Collection.json`
4. La colección se importará automáticamente

### 2. Configurar Variables

La colección usa variables para facilitar las pruebas:

- **`baseUrl`**: URL base de la API (por defecto: `http://localhost:9000`)
- **`ordenId`**: ID de la orden creada (se actualiza automáticamente)
- **`numeroOrden`**: Número de la orden (se actualiza automáticamente)
- **`clienteId`**: ID del cliente para pruebas

### 3. Verificar Variables

1. En Postman, ve a la pestaña **Variables**
2. Verifica que `baseUrl` esté configurado correctamente
3. Las variables `ordenId` y `numeroOrden` se actualizarán automáticamente

## 📁 Estructura de la Colección

### 1. Crear Orden
- ✅ **Crear Orden Válida**: Crea una orden con múltiples items
- ❌ **Crear Orden Sin Items**: Prueba validación de items requeridos
- ❌ **Crear Orden Email Inválido**: Prueba validación de email
- ❌ **Crear Orden Precio Negativo**: Prueba validación de precio

### 2. Obtener Orden
- ✅ **Obtener Orden por ID**: Obtiene orden específica
- ✅ **Obtener Orden por Número**: Obtiene orden por número
- ❌ **Obtener Orden Inexistente**: Prueba manejo de errores
- ✅ **Obtener Todas las Órdenes**: Lista todas las órdenes
- ✅ **Obtener Órdenes por Cliente**: Filtra por cliente

### 3. Agregar Item a Orden
- ✅ **Agregar Item Válido**: Agrega item a orden existente
- ❌ **Agregar Item a Orden Inexistente**: Prueba error de orden no encontrada
- ❌ **Agregar Item Cantidad Cero**: Prueba validación de cantidad

### 4. Cancelar Orden
- ✅ **Cancelar Orden Válida**: Cancela orden existente
- ❌ **Cancelar Orden Inexistente**: Prueba error de orden no encontrada
- ❌ **Cancelar Orden Sin Motivo**: Prueba validación de motivo

### 5. Casos de Error
- ❌ **JSON Inválido**: Prueba manejo de JSON malformado
- ❌ **Método No Permitido**: Prueba métodos HTTP no soportados
- ❌ **Endpoint No Existente**: Prueba rutas inexistentes

### 6. Casos de Rendimiento
- ✅ **Crear Orden con Muchos Items**: Prueba rendimiento con 5 items
- ✅ **Obtener Todas las Órdenes**: Prueba rendimiento con datos

## 🧪 Cómo Ejecutar las Pruebas

### Ejecución Manual

1. **Preparación**: Ejecuta primero "Crear Orden Válida"
2. **Variables**: El `ordenId` se actualizará automáticamente
3. **Secuencia**: Ejecuta las pruebas en el orden recomendado

### Ejecución Automática

1. **Runner**: Usa Postman Runner para ejecutar toda la colección
2. **Configuración**: 
   - Delay: 1000ms entre requests
   - Iterations: 1
   - Log responses: ✅

### Orden Recomendado de Ejecución

```
1. Crear Orden Válida
2. Obtener Orden por ID
3. Obtener Orden por Número
4. Agregar Item Válido
5. Obtener Orden por ID (verificar item agregado)
6. Cancelar Orden Válida
7. Obtener Orden por ID (verificar estado cancelado)
```

## 📊 Casos de Prueba Detallados

### Casos Positivos (✅)

| Caso | Método | Endpoint | Descripción |
|------|--------|----------|-------------|
| Crear Orden Válida | POST | `/api/v1/ordenes` | Crea orden con 2 items |
| Obtener por ID | GET | `/api/v1/ordenes/{id}` | Obtiene orden específica |
| Obtener por Número | GET | `/api/v1/ordenes/numero/{numero}` | Obtiene por número |
| Obtener Todas | GET | `/api/v1/ordenes` | Lista todas las órdenes |
| Obtener por Cliente | GET | `/api/v1/ordenes/cliente/{clienteId}` | Filtra por cliente |
| Agregar Item | POST | `/api/v1/ordenes/{id}/items` | Agrega item a orden |
| Cancelar Orden | POST | `/api/v1/ordenes/{id}/cancelar` | Cancela orden |

### Casos Negativos (❌)

| Caso | Método | Endpoint | Código Esperado |
|------|--------|----------|-----------------|
| Orden Sin Items | POST | `/api/v1/ordenes` | 400 |
| Email Inválido | POST | `/api/v1/ordenes` | 400 |
| Precio Negativo | POST | `/api/v1/ordenes` | 400 |
| Orden Inexistente | GET | `/api/v1/ordenes/{id}` | 404 |
| Item Cantidad Cero | POST | `/api/v1/ordenes/{id}/items` | 400 |
| Cancelar Sin Motivo | POST | `/api/v1/ordenes/{id}/cancelar` | 400 |
| JSON Inválido | POST | `/api/v1/ordenes` | 400 |
| Método No Permitido | PUT | `/api/v1/ordenes` | 405 |

## 🔧 Configuración Avanzada

### Variables de Entorno

Puedes crear diferentes entornos:

**Desarrollo:**
```
baseUrl: http://localhost:9000
```

**Producción:**
```
baseUrl: https://api.tuempresa.com
```

### Scripts Automáticos

La colección incluye scripts que:

1. **Pre-request**: Registra el nombre del request
2. **Test**: 
   - Valida código de estado
   - Valida tiempo de respuesta
   - Actualiza variables automáticamente

### Personalización

Para agregar nuevos casos de prueba:

1. **Copia** un request existente
2. **Modifica** el endpoint y datos
3. **Actualiza** la descripción
4. **Ajusta** las validaciones en el script de test

## 📈 Métricas de Prueba

### Cobertura de Funcionalidades

- ✅ **CRUD Órdenes**: 100%
- ✅ **Validaciones**: 100%
- ✅ **Manejo de Errores**: 100%
- ✅ **Casos Límite**: 100%

### Validaciones Incluidas

- ✅ Campos requeridos
- ✅ Formato de email
- ✅ Precios positivos
- ✅ Cantidades mayores a 0
- ✅ Items no vacíos
- ✅ Motivo de cancelación

## 🐛 Troubleshooting

### Problemas Comunes

1. **Error de Conexión**
   - Verifica que la aplicación esté corriendo en `localhost:9000`
   - Revisa que no haya firewall bloqueando

2. **Variables No Actualizadas**
   - Ejecuta primero "Crear Orden Válida"
   - Verifica que el script de test esté funcionando

3. **Errores 404**
   - Verifica que los endpoints coincidan con tu implementación
   - Revisa la configuración de rutas en Spring Boot

4. **Errores de Validación**
   - Verifica que los datos de prueba cumplan las validaciones
   - Revisa los mensajes de error en la respuesta

### Logs Útiles

```bash
# Ver logs de la aplicación
tail -f logs/application.log

# Ver logs de Postman
Console en Postman (F12)
```

## 📝 Notas Importantes

1. **Datos de Prueba**: Los datos son ficticios y seguros para pruebas
2. **Base de Datos**: Usa H2 en desarrollo (se reinicia cada vez)
3. **Eventos**: Los requests generan eventos Kafka/RabbitMQ
4. **Validaciones**: Todas las validaciones están activas
5. **Rendimiento**: Los tiempos de respuesta deben ser < 2 segundos

## 🔄 Actualizaciones

Para mantener la colección actualizada:

1. **Nuevos Endpoints**: Agrega requests para nuevos endpoints
2. **Nuevas Validaciones**: Actualiza casos de prueba
3. **Cambios de API**: Modifica URLs y datos según cambios
4. **Nuevos Errores**: Agrega casos para nuevos códigos de error

---

**¡Listo para probar tu API de Órdenes con arquitectura hexagonal!** 🚀 