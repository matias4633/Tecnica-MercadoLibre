# Magneto API - README

Este repositorio contiene un sistema de analisis de secuencias de ADN que determina si el ADN recibido pertece a un mutante. El sistema está implementado utilizando Java con Spring Boot y MongoDB para la persistencia de datos. 
Proporciona una API REST que permite consultar cadenas de ADN y obtener estadisticas de las caderas procesadas.

## Configuración del Proyecto

### Requisitos Previos
- Java JDK 17 o superior.
- Conexión a Internet para acceder a los datos y ejecutar la API.
- MongoDb local o Mongo Atlas.
- Docker.

### Pasos de Configuración
1. Clona este repositorio en tu máquina local: `git clone https://github.com/matias4633/Tecnica-MercadoLibre.git`
2. Tener instalado Docker desktop y abierto.
3. Abrir una consola en la carpeta raiz del proyecto.

## Ejecución del Proyecto (Local)

1. Tener activo el perfil dev en src/main/resources/application.properties
2. Modificar con sus credenciales locales de mongodb el archivo src/main/resources/application-dev.properties
3. Creacion de imagen de docker: 
   - Compilacion del JAR : .\mvnw.cmd clean package
   - Imagen docker : docker build -t xmenimage .
   - Despliege contenedor :  docker run -p 80:80 --name xmenconteiner -d xmenimage

## Endpoints (DEPLOY VIGENTE)

La API proporciona los siguientes endpoints:

### 1. Obtener datos estadisticos de los ADN procesados:

**Endpoint:** `GET /stats`

**Parámetros:**
- No necesita ningun parametro.

**Ejemplo de Uso:**
```http
GET  https://magnetoapp.fly.dev/stats
```


**Respuesta:**
```json
{
    "count_mutant_dna": 40,
    "count_human_dna": 100,
    "ratio": 0.4
}
```
### 2. Procesar cadena de ADN

**Endpoint:** `POST /mutant`

**Ejemplo de Uso:**
```http
POST https://magnetoapp.fly.dev/mutant
```
**Parámetros:**
- El array de Strings debe contener unicamente los caracteres A , T , C , G y debe representar una matriz cuadrada  NxN con N minimo a 4. 
```json
{
    "dna":[
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
        ]
}
```


**Respuesta 1:**
```json
{
    "mensaje": "Es un mutante.",
    "resultado": "MUTANTE"
}
```

**Respuesta 2:**
```json
{
    "mensaje": "No es un mutante.",
    "resultado": "NO_MUTANTE"
}
```

**Respuesta 3:**
```json
{
    "mensaje": "El ADN recibido es invalido.",
    "resultado": "ADN_INVALIDO"
}
```

**La primera consulta puede tardar por ser un servicio de deploy gratuito la aplicacion entra en suspención cuando esta fuera de uso.**
-
-
**En la ruta xmen/htmlReport/index.html puede visualizar el reporte de Cobertura de codigo generado.**


