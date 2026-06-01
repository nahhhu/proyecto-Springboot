## TechLab E-commerce API (CRUD productos)
Proyecto que aplica los conocimientos obtenidos en el bootcamp de *talento tech*, enfocado en la gestion de un inventario de productos electronicos. Aunque el objetivo inicial era un CRUD básico con Spring y JPa, decidi ir un poco más allá.

### Tecnologias Utilizadas 
* **java 21/25**: Aprovechando y poniendo en práctica las últimas versiones del lenguaje.
* **Spring Boot**: Framework principal para el desarrollo.
* **Spring Data JPA**: Para la persistencia y gestion de la base de datos MySQL.
* **MySQL**: Base de datos relacional.
* **MapStruct**: Implementado para el mapeo automatico entre entidades y DTOs.
* **OpenAPI 3 / Swagger**: Documentación interactiva de la API.
* **Maven**: Gestión de dependencias.

### Arquitectura y patrones.
* **Java Records**: Utilizados para crear DTOs inmutables, garantizando que los datos no se modifiquen durante el transporte.
* **Separación de Resposabilidades**: El controlador nunca habla directamente con la base de datos, siempre pasa por una capa de servicio y utiliza mappers para proteger las entidades.
* **Inyeccion de Dependencias**: Uso de constructores para una mejor testeabilidad y desacoplamiento de componentes.

### Características Principales
* **CRUD Completo**: Creación, lectura, actualización y eliminación de productos electrónicos.
* **Validación de datos**: Implementación de validaciones para asegurar que los precios, stock y nombres sean correctos antes de persistir.
* **Documentación Interactiva**: Podes probar todos los endpoints a través de la interfaz de swagger.
* **Manejo de Excepciones**: Sistema centralizado para capturar errores y devolver respuestan claras a los clientes.

### Instalación y Configuración
Para correr el proyecto localmente, hay que configurar las siguientes variables de entorno:

* *DB_URL*: URL de conexión a MySQL
* *DB_USER*: Usuario de la base de datos.
* *DB_PASSWORD*: Contraseña de la base de datos.

### Documentación de la API
Una vez que el proyecto esté corriendo, puedes acceder a la documentación interactiva en:  http://localhost:8080/swagger-ui/index.html

###En proceso:
* **Aseguramiento de calidad**: Desarrollo de test unitarios con Mockito en proceso para garantizar la estabilidad del sistema frente a futuros cambios o refactorizaciones.
  
### Proximos pasos:
Este proyecto es el punto de partida para mi transición hacia el desarrollo backend profesional. Planeo evolucionar esta API implementando los siguientes estándares de la industria:
* Seguridad (Spring Security + JWT): Planeo implementar un sistema de autenticación mediante tokens JWT, definiendo roles de usuario y protegiento endpoints criticos. 
* Contenerizacion (Docker): Creacion de un entorno de despliegue reproducible mediante Docker, permitiendo levantar la base de datos y la aplicación con un solo comando.
