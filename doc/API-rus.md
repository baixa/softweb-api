# Документация по развертываемому API

## Запросы

### Application (Приложения)

<details>
  <summary><code>GET</code> <code><b>/v1/application</b></code> <code>(получить информацию о всех приложениях)</code></summary>

##### Параметры

| name   | type | required | description                     |
|--------|------|----------|---------------------------------|
| `page` | int  | true     | Every page contains 10 elements |

##### Ответы

| http code  | content-type     | response                              |
|------------|------------------|---------------------------------------|
| `200`      | application/json | JSON view of List<Application> object |

##### Пример запроса

> ```javascript
>  curl --location 'http://localhost:8080/v1/application?page=0'
> ```

</details>

<details>
  <summary><code>GET</code> <code><b>/v1/application</b></code> <code>(получить информацию о всех приложениях соответствующих категории)</code></summary>

##### Параметры

| name   | type | required | description                     |
|--------|------|----------|---------------------------------|
| `page` | int  | true     | Every page contains 10 elements |

##### Ответы

| http code  | content-type     | response                              |
|------------|------------------|---------------------------------------|
| `200`      | application/json | JSON view of List<Application> object |

##### Пример запроса

> ```javascript
>  curl --location 'http://localhost:8080/v1/application?page=0'
> ```

</details>

<details>
  <summary><code>GET</code> <code><b>/v1/application/{id}</b></code> <code>(получить информацию о приложении по его id)</code></summary>

##### Параметры

| name | type | required | description                |
|------|------|----------|----------------------------|
| `id` | int  | true     | Id of required application |

##### Ответы

| http code | content-type     | response                        |
|-----------|------------------|---------------------------------|
| `200`     | application/json | JSON view of Application object |


##### Пример запроса

> ```javascript
>  curl --location 'http://localhost:8080/v1/application/4'
> ```

</details>

### User (Пользователи)

<details>
  <summary><code>GET</code> <code><b>/v1/user</b></code> <code>(получить информацию о всех пользователях)</code></summary>

##### Параметры

| name   | type | required | description                     |
|--------|------|----------|---------------------------------|
| `page` | int  | true     | Every page contains 10 elements |

##### Ответы

| http code  | content-type     | response                       |
|------------|------------------|--------------------------------|
| `200`      | application/json | JSON view of List<User> object |

##### Пример запроса

> ```javascript
>  curl --location 'http://localhost:8080/v1/user?page=0'
> ```

</details>

<details>
  <summary><code>GET</code> <code><b>/v1/user/{id}</b></code> <code>(получить информацию о пользователе по его id)</code></summary>

##### Параметры

| name | type | required | description         |
|------|------|----------|---------------------|
| `id` | int  | true     | Id of required user |

##### Ответы

| http code | content-type     | response                 |
|-----------|------------------|--------------------------|
| `200`     | application/json | JSON view of User object |


##### Пример запроса

> ```javascript
>  curl --location 'http://localhost:8080/v1/user/4'
> ```

</details>

### Image (Изображения для приложений)


<details>
  <summary><code>GET</code> <code><b>/v1/image/{fileName}</b></code> <code>(получить изображение по имени файла)</code></summary>

##### Параметры

| name       | type   | required | description            |
|------------|--------|----------|------------------------|
| `fileName` | string | true     | Name of required image |

##### Ответы

| http code | content-type             | response       |
|-----------|--------------------------|----------------|
| `200`     | application/octet-stream | Required image |


##### Пример запроса

> ```javascript
>  curl --location 'http://localhost:8080/v1/image/test.jpg'
> ```

</details>

### Installer (Установщик для приложений)


<details>
  <summary><code>GET</code> <code><b>/v1/installer/{fileName}</b></code> <code>(получить установщик по имени файла)</code></summary>

##### Параметры

| name       | type   | required | description           |
|------------|--------|----------|-----------------------|
| `fileName` | string | true     | Name of required file |

##### Ответы

| http code | content-type             | response      |
|-----------|--------------------------|---------------|
| `200`     | application/octet-stream | Required file |


##### Пример запроса

> ```javascript
>  curl --location 'http://localhost:8080/v1/installer/mockoon.exe'
> ```

</details>


### License (Лицензии приложений)

<details>
  <summary><code>GET</code> <code><b>/v1/license</b></code> <code>(получить информацию о всех лицензиях)</code></summary>

##### Параметры

| name   | type | required | description                     |
|--------|------|----------|---------------------------------|
| `page` | int  | true     | Every page contains 10 elements |

##### Ответы

| http code  | content-type     | response                          |
|------------|------------------|-----------------------------------|
| `200`      | application/json | JSON view of List<License> object |

##### Пример запроса

> ```javascript
>  curl --location 'http://localhost:8080/v1/license?page=0'
> ```

</details>

<details>
  <summary><code>GET</code> <code><b>/v1/license/{id}</b></code> <code>(получить информацию о лицензии по её id)</code></summary>

##### Параметры

| name | type   | required | description              |
|------|--------|----------|--------------------------|
| `id` | string | true     | Code of required license |

##### Ответы

| http code | content-type     | response                    |
|-----------|------------------|-----------------------------|
| `200`     | application/json | JSON view of License object |


##### Пример запроса

> ```javascript
>  curl --location 'http://localhost:8080/v1/license/AAL'
> ```

</details>

### Operating system (Операционные системы)

<details>
  <summary><code>GET</code> <code><b>/v1/operatingSystem</b></code> <code>(получить информацию о всех поддерэиваемыъ ОС)</code></summary>

##### Параметры

| name | type | required | description |
|------|------|----------|-------------|
|      |      |          |             |

##### Ответы

| http code  | content-type     | response                                  |
|------------|------------------|-------------------------------------------|
| `200`      | application/json | JSON view of List<OperatingSystem> object |

##### Пример запроса

> ```javascript
>  curl --location 'http://localhost:8080/v1/operatingSystem'
> ```

</details>

<details>
  <summary><code>GET</code> <code><b>/v1/operatingSystem/{id}</b></code> <code>(получить информацию о  id)</code></summary>

##### Параметры

| name | type | required | description                     |
|------|------|----------|---------------------------------|
| `id` | int  | true     | Id of required operating system |

##### Ответы

| http code | content-type     | response                            |
|-----------|------------------|-------------------------------------|
| `200`     | application/json | JSON view of OperatingSystem object |


##### Пример запроса

> ```javascript
>  curl --location 'http://localhost:8080/v1/operatingSystem/4'
> ```

</details>

------------------------------------------------------------------------------------------
