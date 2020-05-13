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
Совпадает с шагом 3 раздела "Запуск с помощью docker-compose"
##### ***Шаг 4***
Остановка контейнера с базой данных
```shell script
docker stop $(docker ps --filter "name=postgres_container" -q)
```
