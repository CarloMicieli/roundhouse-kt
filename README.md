# roundhouse-kt

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
![GitHub last commit](https://img.shields.io/github/last-commit/CarloMicieli/roundhouse-kt)
[![CI with Gradle](https://github.com/CarloMicieli/roundhouse-kt/actions/workflows/ci.yml/badge.svg)](https://github.com/CarloMicieli/roundhouse-kt/actions/workflows/ci.yml)
[![codecov](https://codecov.io/gh/CarloMicieli/roundhouse-kt/branch/main/graph/badge.svg?token=DHN9KCK3GA)](https://codecov.io/gh/CarloMicieli/roundhouse-kt)

A web application for model railways collectors with spring boot and webflux.

## Requirements

* Java 21 
* Git
* Docker

## How to run

### Database

To run the `postgres` database:

```bash
  docker run -it --rm --name roundhouse-db-dev \
    -e POSTGRES_PASSWORD=mysecretpassword \
    -e POSTGRES_DB=roundhouse \
    -p 5432:5432 \
    postgres:15.1-alpine
```

### Start the server

```bash
  git clone https://github.com/CarloMicieli/roundhouse-kt.git
  cd roundhouse-kt
  
  ./gradlew web:bootRun  --args='--spring.profiles.active=local'
  
      .   ____          _            __ _ _
     /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
    ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
     \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
      '  |____| .__|_| |_|_| |_\__, | / / / /
     =========|_|==============|___/=/_/_/_/
     :: Spring Boot ::                (v3.0.6)
```

### Docker 

To build the docker image run:

```bash
  ./gradlew bootBuildImage
```

To start the application with docker compose:

```bash
  docker compose up 
```

## Contributing

Contributions are always welcome!

See `CONTRIBUTING.md` for ways to get started.

Please adhere to this project's `code of conduct`.

### Conventional commits

This repository is following the conventional commits practice.

#### Enforcing using git hooks

```bash
  chmod +x .githooks/commit-msg
  git config core.hooksPath .githooks
```

The hook itself can be found in `.githooks/commit-msg`.

#### Using Commitizen

Install [commitizen](https://github.com/commitizen-tools/commitizen)

```bash
  pip install commitizen
```

and then just use it

```bash
  cz commit
```

## License

[Apache 2.0 License](https://choosealicense.com/licenses/apache-2.0/)

```
   Copyright 2021 Carlo Micieli

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
