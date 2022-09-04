# Mutants
Proyecto para detectar genes mutantes y obtener estadísticas de estos análisis

-----------------------------------------------------------------------------------------------------------------------------------------------------------
DIAGRAMA DE LA ARQUITECTURA DE LOS SERVICIOS

![WhatsApp Image 2022-09-04 at 12 42 06 PM](https://user-images.githubusercontent.com/51482164/188326524-6119c46f-53c9-4d51-9b59-790e14da24c5.jpeg)

Se construyeron los servicios REST en spring boot conectado a una base de datos DYNAMODB y se despliegan los servicios en AWS Elastic Beanstalk configurando 3 instancias en un autoscaling group 

-----------------------------------------------------------------------------------------------------------------------------------------------------------
ARQUITECTURA HEXAGONAL

se elije esta arquitectura para separar completamente las responsabilidades de las capas y dejar un dominio completamente aislado de otras capas y tecnologias siguiendo el principio de inversión de dependencias de solid ademas aplicando clean code y en la organizacion de las capas tenemos:

* Infraestructure : en donde realizamos todas las configuraciones , control de excepciones , conexiones externas a la base de datos y por ultimo exponemos el controlador de la api
* Application : en donde exponemos los casos de uso de la aplicacion y realizamos el llamado al dominio para ejecutar las operaciones
* Dominio : en donde aislamos toda la logica de negocio incluyendo excepciones de negocio y los calculos necesarios en la logica de negocio

-----------------------------------------------------------------------------------------------------------------------------------------------------------
EL PROYECTO CONTIENE :

- src/main: con la estructura del codigo de los servicios rest
- src/test: con las pruebas unitarias y de integracion las cuales superan un coverage del 80% cada una de las pruebas completamente funcional y aportando valor al negocio

![fe0623ac-dfc3-417a-b4df-726c921aec2f](https://user-images.githubusercontent.com/51482164/188326962-106b728d-bbb2-483e-b1b1-27d26db0dea7.jpg)

- Pruebas de carga jmeter: la cual contiene un archivo de configuracion de pruebas de carga para el servicio api/stats con el que se evaluo el soporte agresivo de peticiones en un segundo

- Collection de postman para pruebas de los servicios: en el cual se deja la collection usada en postman para las pruebas del servicio

-----------------------------------------------------------------------------------------------------------------------------------------------------------
TECNOLOGIAS USADAS :

- Java 11
- AWS Elastic Beanstalk ( el cual se despliega con un grupo de autoescalado de hasta 3 instancias para soportar una mayor cantidad de peticiones 
![WhatsApp Image 2022-09-04 at 11 40 48 AM](https://user-images.githubusercontent.com/51482164/188324620-7b76e09a-716e-488a-8402-686c91df7dd0.jpeg)
- AWS DynamoDB (se elije esta por su propiedad de llave valor con la cual no se podrian repetir secuencias de adn evaluadas y existiria un solo registro de cada una)
- Spring boot
- Postman
- Junit5
- Jmeter 5.5
- MockMvc

-----------------------------------------------------------------------------------------------------------------------------------------------------------
SERVICIOS EXPUESTOS EN LA API CONSTRUIDA:

* http://analysismutant-env.eba-4przmm2n.us-east-1.elasticbeanstalk.com/api/stats   -> GET -> nos permite consultar la estadistica de mutantes positivos en los analisis
* http://analysismutant-env.eba-4przmm2n.us-east-1.elasticbeanstalk.com/api/mutant  -> POST -> nos permite analizar una secuencia de adn y determinar si es mutante guardando un registro en la bd 

EJEMPLO DE BODY PARA CONSUMO servicio /api/mutant: 
* { "dna":["ATGCGA","CAGTAC","TTATGT","AGAAGG","AAAATA","TCACTA"]}
* { “dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]}

/api/mutant cuenta con 3 posibles codigos de respuesta en representacion de la logica de negocio
* 400 que representa un bad request cuando se registra una matrix invalida o se detectan caracteres invalidos en la matrix
* 200 cuando el adn analizado pertenece a un mutante
* 403 cuando el adn analizado no pertenece a un mutante

-----------------------------------------------------------------------------------------------------------------------------------------------------------
CONFIGURACIÓN PARA EJECUTAR EL PROYECTO LOCALMENTE:

-clonar el proyecto en la maquina local
-ejecutar el comando: gradle clean build este es el encargado de crear el ejecutable .jar dentro de la carpeta build/libs/
-ejecutar en la raiz del proyecto el comando: java -jar build/libs/mutantes-0.0.1-SNAPSHOT.jar
-tendremos el proyecto corriendo localmente y podremos acceder a el por medio de la base localhost:5000/api/...

-----------------------------------------------------------------------------------------------------------------------------------------------------------
CONSUMO DE LOS SERVICIOS 

* Servicio para realizar el analisis de mutantes
http://analysismutant-env.eba-4przmm2n.us-east-1.elasticbeanstalk.com/api/mutant  [POST]


ESCENARIO ES MUTANTE:
RESPUESTA : 200
Body consumo : { "dna":["ATGCGA","CAGTAC","TTATGT","AGAAGG","AAAATA","TCACTA"]}

![WhatsApp Image 2022-09-04 at 1 14 18 PM](https://user-images.githubusercontent.com/51482164/188327793-1c1e639b-ab2e-498b-81b5-f3c7df1363f6.jpeg)

ESCENARIO NO ES MUTANTE:

Body consumo : { "dna":["ATGCGA","CAGTAC","TTATGT","AGATGG","AATGTA","TCACTA"]}
RESPUESTA : 403

![WhatsApp Image 2022-09-04 at 1 17 39 PM](https://user-images.githubusercontent.com/51482164/188327835-254fd415-aabe-4ea8-b449-b7072d1c8683.jpeg)

ESCENARIO CARACTERES INVALIDOS
Body consumo : { "dna":["ATGCGA","CAGTAC","TTATGT","AGATGG","AATGTA","TCACHT"]}
RESPUESTA 400

![WhatsApp Image 2022-09-04 at 1 21 00 PM](https://user-images.githubusercontent.com/51482164/188327955-d310bce6-2812-4b48-bdff-e4ecade251bc.jpeg)


ESCENARIO MATRIX NO CUADRADA
Body consumo : { "dna":["ATGCGA","CAGTAC","TTATGT","AGATGG","AATGTA","TCACT"]}
RESPUESTA 400

![WhatsApp Image 2022-09-04 at 1 19 14 PM](https://user-images.githubusercontent.com/51482164/188327892-8c3aab21-318b-4fe6-8fee-2944e5d2e116.jpeg)

* Servicio para recoger las estadisticas de los analisis realizados y guardados en la bd 
http://analysismutant-env.eba-4przmm2n.us-east-1.elasticbeanstalk.com/api/stats  [GET]

Body response : {
    "count_mutant_dna": 20,
    "count_human_dna": 31,
    "ratio": 0.6451612903225806
}

![WhatsApp Image 2022-09-04 at 1 29 25 PM](https://user-images.githubusercontent.com/51482164/188328280-b3e03dd9-2413-44a8-a21f-22badc7dda46.jpeg)


Para el consumo de estos servicios existe una carpeta como se describio en la estructura del proyecto en la cual esta la collection de postman para probar


-----------------------------------------------------------------------------------------------------------------------------------------------------------

NOTA : en el algoritmo ismutant se realizo una validación para hacer el algoritmo lo mas efectivo posible ya que no importa cuantas secuencias mutantes existan 
cuando consiga mas de una inmediatamente deja de buscar en el resto de la secuencia y detecta que si es un alien.

el proyecto cuenta con pruebas unitarias, de integración y de carga



