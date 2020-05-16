# Neural Parasite
##### Neural network application programming interface

Api documentation is available by default at
```http request
http://127.0.0.1:8092/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
```  
 
#### Запуск с помощью docker-compose  
*Шаг 0* - установить git, docker-compose, открыть терминал
##### ***Шаг 1***
Склонировать исходники с помощью утилиты git:  
```shell script
git clone https://github.com/AlexanderSayner/NeuralParasite.git
```
##### ***Шаг 2***
Перейти в скаченную папку с проектом
```shell script
cd NeuralParasite
```
##### ***Шаг 3***
Запустить контейнер с приложением
```shell script
docker-compose build && docker-compose up
```
##### ***Шаг 4***
Пройти в браузере по ссылке api приложения  
Команда ctrl+C останавливает приложения
#### Быстрый запуск без контейнеризации основного приложения
Способ удобен для отладки приложения без дополнительной установки СУБД
##### ***Шаг 1***
Запуск контейнера с Postgres
```shell script
docker-compose up -d database
```
##### ***Шаг 2***
Запуск приложение с использованием системы сборки gradle
```shell script
./gradlew bootRun
```
##### ***Шаг 3***
Пройти в браузере по ссылке api приложения  
Команда ctrl+C останавливает приложения
##### ***Шаг 4, Завершение работы***
Остановка контейнера с базой данных. Требуется закрытие всех открытых сеансов работы
```shell script
docker stop $(docker ps --filter "name=postgres_container" -q)
```
В случае слишком долгого выполнения команды stop, если точно не осталось ни одного открытого сеанса используйте команду kill
```shell script
docker container kill $(docker ps -a --filter "name=postgres_container" -q) 
```
##### ***Шаг 5, Возообновление работы***
```shell script
docker start $(docker ps -a --filter "name=postgres_container" -q)
```
#### Удаление контейнеров и образов
Остановка всех контейнеров
```shell script
docker stop $(docker ps -a -q)
```
Удаление всех контейнеров
```shell script
docker rm $(docker ps -a -q)
```
Удаление всех образов
```shell script
docker rmi $(docker images -q)
```
### Начальные данные
```http request
POST /car
```
```json5
{
  "description": "RearDrive",
  "weight": 1572.5,
  "wheelbase": 2.625,
  "frontShareOfWeight": 0.6,
  "rearShareOfWeight": 0.4,
  "gauge": 1.515,
  "wheelWidth": 0.235,
  "tireProfile": 0.45,
  "rimSize": 17,
  "id": 1,
  "model": "EvaDrift"
}
```
```http request
POST /corner
```
```json5
{
  "wheelRotationAngle": 0.07,
  "speed": 13,
  "outerTorque": 200,
  "innerTorque": 150,
  "angleOfFrontHeel": 0.01,
  "angleOfRearHeel": 0.02,
  "id": 1
}
```
```
POST /computation
```
```json5
{
  "car_id": 1,
  "corner_id": 1,
  "speed_meter_per_second": "13",
  "wheels_rotation_radians": "0.07"
}
```
