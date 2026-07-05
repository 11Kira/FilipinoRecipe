# FilipinoRecipe (Native Android Client)

A production-grade, native Android application built with modern engineering practices to browse, discover, and manage authentic Filipino recipes. This application serves as the production-native baseline for the platform ecosystem, communicating with a self-hosted cloud API.

## 🛠️ Tech Stack & Architecture
* **Language:** 100% Kotlin
* **UI Framework:** Jetpack Compose (Declarative UI)
* **Architecture:** Clean Architecture + MVVM (Model-View-ViewModel)
* **Asynchronous Flow:** Kotlin Coroutines & StateFlow for reactive, lifecycle-aware state management
* **Networking:** Retrofit / OkHttp with reactive error interceptors
* **Dependency Injection:** Hilt (Lightweight service locator framework)

## 💎 Senior Engineering Highlights
* **Decoupled Architecture:** Enforces a strict separation of concerns across Data, Domain, and Presentation layers, ensuring highly testable and maintainable code.
* **Production Cloud Integration:** Configured to interface seamlessly with a dedicated DigitalOcean infrastructure layer, eliminating the performance penalties and cold-start limitations of traditional free-tier hosting solutions.
* **Reactive State Engine:** Employs stateful UI wrapper paradigms to eliminate composition leaks and handle asynchronous state mutations predictably.
