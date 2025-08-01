# Maven vs Gradle - Comparación

Este proyecto incluye configuraciones tanto para **Maven** (`pom.xml`) como para **Gradle** (`build.gradle`) para que puedas ver las diferencias.

## 📋 **Estructura de Archivos**

### Maven
- `pom.xml` - Configuración del proyecto
- `mvn` - Comando para ejecutar Maven

### Gradle
- `build.gradle` - Configuración del proyecto
- `settings.gradle` - Configuración del proyecto
- `gradle.properties` - Propiedades del proyecto
- `gradlew.bat` - Wrapper de Gradle para Windows
- `./gradlew` - Comando para ejecutar Gradle

## 🔄 **Comandos Equivalentes**

| Acción | Maven | Gradle |
|--------|-------|--------|
| **Compilar** | `mvn compile` | `./gradlew compileJava` |
| **Ejecutar Tests** | `mvn test` | `./gradlew test` |
| **Limpiar** | `mvn clean` | `./gradlew clean` |
| **Compilar + Limpiar** | `mvn clean compile` | `./gradlew clean compileJava` |
| **Ejecutar App** | `mvn spring-boot:run` | `./gradlew bootRun` |
| **Generar JAR** | `mvn package` | `./gradlew bootJar` |
| **Instalar** | `mvn install` | `./gradlew installDist` |

## 📊 **Diferencias Principales**

### **1. Sintaxis**

#### Maven (pom.xml)
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

#### Gradle (build.gradle)
```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
}
```

### **2. Plugins**

#### Maven
```xml
<plugins>
    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
    </plugin>
</plugins>
```

#### Gradle
```groovy
plugins {
    id 'org.springframework.boot' version '3.2.0'
    id 'io.spring.dependency-management' version '1.1.4'
}
```

### **3. Configuración de Compilación**

#### Maven
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <source>17</source>
        <target>17</target>
    </configuration>
</plugin>
```

#### Gradle
```groovy
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
```

### **4. Configuración de MapStruct**

#### Maven
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct-processor</artifactId>
                <version>1.5.5.Final</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

#### Gradle
```groovy
dependencies {
    implementation 'org.mapstruct:mapstruct:1.5.5.Final'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'
}

compileJava {
    options.compilerArgs = [
        '-Amapstruct.defaultComponentModel=spring'
    ]
}
```

## 🚀 **Cómo Usar Gradle**

### **1. Ejecutar con Gradle Wrapper**
```bash
# Compilar
./gradlew compileJava

# Ejecutar aplicación
./gradlew bootRun

# Generar JAR
./gradlew bootJar

# Ejecutar tests
./gradlew test
```

### **2. Ejecutar con Docker**
```bash
# Iniciar servicios
docker-compose up -d

# Ejecutar aplicación con Gradle
./gradlew bootRun
```

## 📈 **Ventajas de Cada Uno**

### **Maven**
- ✅ **Más maduro** y estable
- ✅ **Mejor documentación** y soporte
- ✅ **IDE más compatible** (IntelliJ, Eclipse)
- ✅ **Sintaxis XML** más estructurada
- ❌ **Más verboso** para configuraciones complejas

### **Gradle**
- ✅ **Más flexible** y expresivo
- ✅ **Mejor rendimiento** en builds grandes
- ✅ **Sintaxis Groovy/Kotlin** más legible
- ✅ **Mejor para proyectos complejos**
- ❌ **Curva de aprendizaje** más pronunciada

## 🎯 **Recomendación**

- **Usa Maven** si:
  - Es un proyecto simple
  - Tu equipo está familiarizado con Maven
  - Necesitas máxima compatibilidad con IDEs

- **Usa Gradle** si:
  - Es un proyecto complejo
  - Necesitas builds más rápidos
  - Quieres más flexibilidad en la configuración

## 📝 **Notas**

1. **Ambos archivos están configurados** para el mismo proyecto
2. **Puedes usar cualquiera** de los dos
3. **Los resultados son idénticos** - solo cambia la herramienta de build
4. **Gradle Wrapper** permite usar Gradle sin instalarlo globalmente 