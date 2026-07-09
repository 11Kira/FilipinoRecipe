# FilipinoRecipe (Native Android Client)
A production-grade, native Android application built with modern engineering practices to browse, discover, and manage authentic Filipino recipes. This application serves as the production-native baseline for the platform ecosystem, communicating with a self-hosted cloud API and featuring a robust, resilient offline-first architecture.

🛠️ Tech Stack & Architecture
Language: 100% Kotlin

UI Framework: Jetpack Compose (Declarative UI)

Architecture: Clean Architecture + MVVM (Model-View-ViewModel)

Local Persistence: Room Database (Offline-first architecture with Single Source of Truth pattern)

Asynchronous Flow: Kotlin Coroutines & StateFlow for reactive, lifecycle-aware state management

Networking: Retrofit / OkHttp with reactive error interceptors

Cloud Infrastructure & Media: Firebase Cloud Storage (Optimized asset delivery)

Dependency Injection: Hilt (Compile-time safe dependency injection framework built on Dagger)

💎 Senior Engineering Highlights
Offline-First Single Source of Truth (SSOT): Engineered a robust local caching engine using the Room database. The presentation layer strictly observes local database streams rather than raw network responses, ensuring a completely seamless, zero-latency user experience even during network drops or complete offline status.

Optimized Cloud Media Pipeline: Integrated Firebase Cloud Storage to serve high-resolution food photography assets. The app handles remote image tokens efficiently, utilizing a centralized media pipeline designed to minimize bandwidth consumption and leverage intelligent image caching configurations.

Decoupled Architecture: Enforces a strict separation of concerns across Data, Domain, and Presentation layers, ensuring highly testable, decoupled, and maintainable codebases that scale smoothly.

Production Cloud Integration: Configured to interface seamlessly with a dedicated cloud infrastructure layer, eliminating the performance penalties and cold-start limitations of traditional free-tier hosting solutions.

Reactive State Engine: Employs stateful UI wrapper paradigms to eliminate composition leaks and handle asynchronous state mutations predictably across configuration changes.
