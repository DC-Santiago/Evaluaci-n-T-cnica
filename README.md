# Sistema de Gestión de Usuarios y Matriz de Accesos

Este proyecto es una solución Full-Stack diseñada para la administración de usuarios y la asignación dinámica de permisos sobre una matriz de módulos y sistemas. La arquitectura está dividida en un backend robusto basado en microservicios/APIs REST y un frontend reactivo e interactivo.

---

## Descripción del Proyecto

El sistema permite gestionar un catálogo de usuarios y controlar, de manera individual, qué accesos de **Lectura** y **Escritura** poseen sobre los diferentes módulos maestros de la organización (ej. Ventas, Compras, Clientes dentro de entornos ERP o CRM). 

### Características Clave:
* **Backend:** Arquitectura limpia en capas (Controller, Service, Repository, DTO) con Spring Boot.
* **Frontend:** Aplicación SPA moderna con Angular utilizando Componentes Standalone y peticiones optimizadas mediante `fetch` asíncrono.
* **Persistencia:** Base de datos relacional con mapeos de integridad mediante Hibernate / JPA.
* **Interactividad:** Guardado y revocación de permisos en tiempo real desde la interfaz mediante checkboxes, con buscador dinámico integrado.

---

## Requisitos Previos

Antes de comenzar con la instalación, asegúrate de tener instalado el siguiente software en tu equipo:

* **Java JDK 17** o superior.
* **Node.js** (Versión 18 o superior) & **npm** (Versión 9 o superior).
* **Angular CLI** (Versión 17 o superior).
* **SQL Server** (2019 o superior) junto con **SQL Server Management Studio (SSMS)**.
* Un IDE de desarrollo (ej. **NetBeans**, IntelliJ IDEA o VS Code).

---

## Configuración de Variables de Entorno

Tanto el Backend como el Frontend requieren configuraciones específicas de entorno para comunicarse de manera correcta:

### Backend (`src/main/resources/application.properties`)
Modifica las siguientes líneas en tu archivo de propiedades de Spring Boot con las credenciales de tu servidor local de SQL Server:

```properties
# Puerto de ejecución del servidor
server.port=8080

# Configuración de la conexión a SQL Server
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=db_permisos;encrypt=true;trustServerCertificate=true;
spring.datasource.username=TU_USUARIO_SQL
spring.datasource.password=TU_CONTRASEÑA_SQL
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

# Configuración de Hibernate / JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect

Ejecutar Migraciones y Cargar Datos de Prueba
El backend está configurado con la propiedad ddl-auto=update, lo que significa que Spring Boot creará las tablas automáticamente en SQL Server al arrancar por primera vez.

1. Inicializar la Base de Datos
Abre SQL Server Management Studio (SSMS).

Conéctate a tu servidor local y ejecuta el siguiente comando para crear el contenedor:

CREATE DATABASE db_permisos;
GO

2. Cargar Datos de Prueba (Seeders)
Una vez que el backend corra por primera vez y genere la estructura de tablas, ejecuta el siguiente script SQL en tu base de datos para cargar los catálogos maestros obligatorios y un usuario de prueba:

USE db_permisos;
GO

-- 1. Insertar Sistemas Maestros
INSERT INTO Sistema (nombre, descripcion) VALUES 
('ERP', 'Enterprise Resource Planning'),
('CRM', 'Customer Relationship Management');

-- 2. Insertar Módulos Relacionados (Sustituir el 'sistema_id' con los IDs generados)
INSERT INTO Modulo (nombre, descripcion, sistema_id) VALUES 
('Ventas', 'Módulo de gestión de ventas e ingresos', 1),
('Compras', 'Módulo de adquisiciones y proveedores', 1),
('Inventario', 'Control de stock y almacenes', 1),
('Clientes', 'Administración de cartera de clientes', 2),
('Oportunidades', 'Seguimiento de prospectos comerciales', 2);

-- 3. Insertar Usuario de Prueba
INSERT INTO Usuario (nombre, correo, activo) VALUES 
('Juan Pérez', 'juan@test.com', 1);

Pasos para Instalar y Levantar la App
Sigue este orden estricto para asegurar el correcto despliegue local del ecosistema:

Paso 1: Levantar el Backend (Spring Boot)
Abre el proyecto de la carpeta del backend en tu IDE (NetBeans o similar).

Deja que el gestor de paquetes (Maven) descargue las dependencias necesarias descritas en el pom.xml.

Ejecuta la aplicación haciendo clic en Run o desde la terminal raíz del backend usando:

mvn spring-boot:run

El backend estará disponible escuchando peticiones en: http://localhost:8080

Paso 2: Instalar y Levantar el Frontend (Angular)
Abre una terminal y colócate en la carpeta raíz del proyecto frontend:

cd frontend/evaluacion-front

Instala todos los módulos y dependencias de Node estructuradas para el cliente:

npm install

Ejecuta el servidor de desarrollo local de Angular:

ng serve

Abre tu navegador web favorito e ingresa a la siguiente dirección:

http://localhost:4200

Instrucciones de Uso en el Navegador
Al entrar a http://localhost:4200, serás redirigido automáticamente al listado de usuarios.

Identifica al usuario Juan Pérez y da clic en el botón ⚙️ Gestionar Permisos.

En la pantalla de la Matriz de Accesos, utiliza el buscador dinámico para filtrar aplicaciones y marca/desmarca los checkboxes de ¿Puede Leer? o ¿Puede Escribir?. Los cambios se impactarán de inmediato en SQL Server de forma asíncrona.

---DECISIONES---
Documentación de Decisiones Técnicas
1. Stack Tecnológico Elegido
Backend: Java con Spring Boot (JPA/Hibernate) y SQL Server.

Por qué: Es el ecosistema con el que tengo mayor dominio. Spring Boot ofrece una gran robustez para la gestión de transacciones (esencial en una matriz de permisos) y SQL Server garantiza la integridad de datos empresariales.

Frontend: Angular (Standalone Components).

Por qué: La arquitectura de componentes independientes permite un desarrollo modular y eficiente. Facilita la gestión de estados asíncronos y la vinculación bidireccional de datos, ideal para interfaces dinámicas como la matriz de permisos.

2. Suposiciones Realizadas
Regla de Negocio: Se asumió que el permiso de "Escritura" es un nivel superior que hereda automáticamente la capacidad de "Lectura". Por tanto, el sistema impide la escritura aislada.

Integridad: Se asume que el sistema administrativo tiene la capacidad de gestionar el ciclo de vida completo de los módulos, implementando borrado en cascada para evitar registros huérfanos.

Entorno: Se asume que el backend (localhost:8080) y el frontend (localhost:4200) comparten el mismo dominio local para el desarrollo inicial.

3. ¿Qué agregaría o cambiaría con más tiempo?
Seguridad: Implementar Spring Security con JWT para autenticación real y autorización basada en roles (RBAC) en los endpoints.

Logs y Monitoreo: Agregar un sistema de logs centralizado para rastrear quién cambió qué permiso y en qué momento (auditoría).

Pruebas Unitarias: Incluir una suite de pruebas con JUnit 5 y Mockito para asegurar que la lógica de la matriz de permisos no falle ante casos borde.

Caching: Implementar Redis para cachear el catálogo de módulos y evitar consultas repetitivas a la base de datos en sesiones de usuario activas.