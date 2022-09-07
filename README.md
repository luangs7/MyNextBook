# My Next Book

This is an application to give you a new book to read. We're using the Google Books API.

## 📚 Android tech stack

One of the main goals of this app is too use all the latest libraries and tools available.

### 🧑🏻‍💻 Android development

- Application entirely written in [Kotlin](https://kotlinlang.org)
- Complete migrated to [Jetpack Compose](https://developer.android.com/jetpack/compose)
- Asynchronous processing using [Coroutines](https://kotlin.github.io/kotlinx.coroutines/)
- Dependency injection with [Koin](https://insert-koin.io)
- Data storage using [Datastore](https://developer.android.com/topic/libraries/architecture/datastore)

For more dependencies used in project, please access the
[Dependency File](https://github.com/luangs7/MyNextBook/blob/a3b7b0718c793ca4f511d1cb69dcd6169e065294/buildSrc/src/main/kotlin/Dependencies.kt)

## 🏛 Architecture

This demo architecture is strongly based on
the [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) by Uncle Bob. 
The application also relies heavily in modularization for better separation of concerns
and encapsulation.

Let's take a look in each major module of the application:

* **app** - The Application module. It contains all the initialization logic for the Android
  environment and starts the _Jetpack Navigation Compose Graph_.
* **features** - The module containing all the visual from the application, separated by features
* **domain** - The modules containing the most important part of the application: the business
  logic. This module depends only on itself and all interaction it does is via _dependency
  inversion_.
* **data** - The module containing the data (local, remote, light etc) from the app

This type of architecture protects the most important modules in the app. To achieve this, all the
dependency points to the center, and the modules are organized in a way that
_the more the module is in the center, more important it is_.


## 📃 License

```
Copyright 2022 Luan Silva

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
