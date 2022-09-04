# Mutants
Proyecto para detectar genes mutantes y obtener estadísticas de estos análisis

DIAGRAMA DE LA ARQUITECTURA DE LOS SERVICIOS

![WhatsApp Image 2022-09-04 at 12 42 06 PM](https://user-images.githubusercontent.com/51482164/188326524-6119c46f-53c9-4d51-9b59-790e14da24c5.jpeg)

Se construyeron los servicios REST en spring boot conectado a una base de datos DYNAMODB y se despliegan los servicios en AWS Elastic Beanstalk configurando 3 instancias en un autoscaling group 

ARQUITECTURA DE CODIGO HEXAGONAL

se elije esta arquitectura para separar completamente las responsabilidades de las capas y dejar un dominio completamente aislado de otras capas y tecnologias siguiendo el principio de inversión de dependencias de solid y aplicando clean code y en la organizacion de las capas tenemos

* Infraestructure : en donde realizamos todas las configuraciones , control de excepciones , conexiones externas a la base de datos y por ultimo exponemos el controlador de la api
* Application : en donde exponemos los casos de uso de la aplicacion y realizamos el llamado al dominio para ejecutar las operaciones
* Dominio : en donde aislamos toda la logica de negocio incluyendo excepciones de negocio y los calculos necesarios en la logica de negocio

EL PROYECTO CONTIENE :

- src/main: con la estructura del codigo de los servicios rest
- src/test: con las pruebas unitarias y de integracion las cuales superan un coverage del 80% cada una de las pruebas completamente funcional y aportando valor al negocio

![fe0623ac-dfc3-417a-b4df-726c921aec2f](https://user-images.githubusercontent.com/51482164/188326962-106b728d-bbb2-483e-b1b1-27d26db0dea7.jpg)

- Pruebas de carga jmeter: la cual contiene un archivo de configuracion de pruebas de carga para el servicio api/stats con el que se evaluo el soporte agresivo de peticiones en un segundo

- Collection de postman para pruebas de los servicios: en el cual se deja la collection usada en postman para las pruebas del servicio

Tecnologias usadas:

- Java 11
- AWS Elastic Beanstalk
- AWS DynamoDB (se elije esta por su propiedad de llave valor con la cual no se podrian repetir secuencias de adn evaluadas y existiria un solo registro de cada una)
- Spring boot
- Postman
- Junit5
- Jmeter 5.5
- MockMvc


Servicios expuestos en la api construida:

* http://analysismutant-env.eba-4przmm2n.us-east-1.elasticbeanstalk.com/api/stats   -> GET -> nos permite consultar la estadistica de mutantes positivos en los analisis
* http://analysismutant-env.eba-4przmm2n.us-east-1.elasticbeanstalk.com/api/mutant  -> POST -> nos permite analizar una secuencia de adn y determinar si es mutante guardando un registro en la bd

![WhatsApp Image 2022-09-04 at 11 40 48 AM](https://user-images.githubusercontent.com/51482164/188324620-7b76e09a-716e-488a-8402-686c91df7dd0.jpeg)
