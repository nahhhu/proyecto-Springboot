## TechLab E-commerce API (CRUD productos)
Proyecto que aplica los conocimientos obtenidos en el bootcamp de *talento tech*, enfocado en la gestion de un inventario de productos electronicos. Aunque el objetivo inicial era un CRUD básico con Spring y JPa, decidi implementar la arquitectura DTO para que el sistema sea robusto y escalable.

### Tecnologias Utilizadas 
* *java 21/25*: Aprovechando y poniendo en práctica las últimas versiones del lenguaje.
* *Spring Boot*: Framework principal para el desarrollo.
* *Spring Data JPA*: Para la persistencia y gestion de la base de datos MySQL.
* *MySQL*: Base de datos relacional.
* *MapStruct*: Implementado para el mapeo automatico entre entidades y DTOs.
* *OpenAPI 3 / Swagger*: Documentación interactiva de la API.
* *Maven*: Gestión de dependencias.

### Arquitectura y patrones.
* Java Records: Utilizados para crear DTOs inmutables, garantizando que los datos no se modifiquen durante el transporte.
* Separación de Resposabilidades: El controlador nunca habla directamente con la base de datos; siempre pasa por una capa de servicio y utiliza mappers para pproteger las entidades.
* Inyeccion de Dependencias: Uso de constructores para una mejor testeabilidad y desacoplamiento de componentes.

