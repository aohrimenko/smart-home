# 🏡 Smart Home Microservices Application

This project was created as part of a technical challenge:  
**Build a complete, functional microservices architecture with REST APIs in under a week (~4 days).**

The idea behind the project is a **futuristic smart home**, capable of making intelligent decisions based on sensor data such as:
- heart rate
- blood pressure
- number of people at the door
- presence of elderly people

These decisions control indoor devices like **lights**, **climate**, and **music**.

This system can later be extended with **AI/ML** logic to further enhance decision-making.

---

## 🚀 Features

✅ Client-facing REST API  
✅ Microservice-based architecture  
✅ Inter-service communication via Kafka  
✅ Basic security (MITM protection using API key headers)  
✅ Exception handling using `@RestControllerAdvice`  
✅ Docker-ready setup  
✅ Swagger documentation for all services  
✅ Clean and modular code organization

---

## 🧠 Project Structure

- `theme-engine-service`: Entry point for receiving input (door data) and making configuration decisions
- `theme-dispatcher-service`: Kafka consumer that dispatches configuration to relevant microservices
- `climate-service`, `music-service`, `light-service`: Device-specific microservices
- `docker/`: Scripts for starting/cleaning up the entire stack with Docker Compose

---

## 📡 Communication Flow

1. `theme-engine-service` receives a REST request with sensor data.
2. Based on the data, it determines a suitable `HomeTheme` (e.g., `PARTY`, `CHILL`, etc.).
3. It wraps up device configuration into a **Kafka event** and sends it to a common topic.
4. `theme-dispatcher-service` consumes the event and breaks it down into service-specific events.
5. Each microservice listens to its own topic and updates internal configuration using `AtomicReference`.
6. `theme-engine-service` can then aggregate this data by querying `theme-dispatcher-service`, which forwards the request to each microservice using REST and API keys for security.

---

## 🧪 Swagger Endpoints

You can preview the APIs via Swagger UI (when run in **IDE / local** mode):

| Service               | URL                                                       |
|-----------------------|------------------------------------------------------------|
| Theme Engine          | http://localhost:8080/api/smart-home/swagger-ui/index.html |
| Theme Dispatcher      | http://localhost:8090/api/theme-dispatcher/swagger-ui/index.html |
| Climate Service       | http://localhost:8092/api/swagger-ui/index.html            |
| Music Service         | http://localhost:8093/api/swagger-ui/index.html            |
| Light Service         | http://localhost:8094/api/swagger-ui/index.html            |

> ⚠️ When running via Docker, only `theme-engine-service`’s Swagger is directly accessible.

---

## 🐳 Running with Docker

Each service builds a Docker image automatically during `mvn install`.

That means you have to do manually ```mvn clean install``` for each and every project!


### ▶️ Start everything


```./docker/start.sh```

❌ Clean all containers and volumes
```./docker/stop.sh```
### Endpoints:

**GET** http://localhost:8080/api/smart-home/service/health - returns the state of all the services(theme dispatcher included)

**GET** http://localhost:8080/api/smart-home/service - gets(from microservices and all) the current active configuration

**POST** http://localhost:8080/api/smart-home/service - posts data for(possibly) new configuration for the devices.

And the ```/actuator``` endpoint is available, which can be used for real **depends_on** dependency in the docker-compose.yaml
example request:
```
{
    "hearthRate": 90,
    "bloodPressureData": {
        "lowNumber": 85,
        "highNumber": 135
    },
    "doorNoiseData": {
        "probableElderRelations": true,
        "personCount": 1,
        "sexDiversity": false
    }
}
```
### Note:

I am aware that there's a **standalone kafka docker image**(instead of the bundle with the zookeeper this project uses) - there're a lot of improvements that could be done in time too, please don't be too judgemental and thank you!

And special warm thanks to ChatGPT for helping me pull out this awesome document formatting with icons and stuff(along with the insane amount of time saved)!