<h1>FilipinoRecipe (Native Android Client)</h1>
<p>A production-grade, native Android application built with modern engineering practices to browse, discover, and manage authentic Filipino recipes. This application serves as the production-native baseline for the platform ecosystem, communicating seamlessly with a dedicated cloud-hosted API infrastructure.</p>

<h2>🛠️ Tech Stack &amp; Architecture</h2>
<ul>
  <li><strong>Language:</strong> 100% Kotlin</li>
  <li><strong>UI Framework:</strong> Jetpack Compose (Declarative UI)</li>
  <li><strong>Architecture:</strong> Clean Architecture + MVVM (Model-View-ViewModel)</li>
  <li><strong>Local Storage (Offline-First):</strong> Room Persistence Library (Single Source of Truth paradigm via reactive Flow streams)</li>
  <li><strong>Media Asset Pipeline:</strong> Firebase Cloud Storage (Remote hosting and optimized resource serving)</li>
  <li><strong>Asynchronous Flow:</strong> Kotlin Coroutines &amp; StateFlow for reactive, lifecycle-aware state management</li>
  <li><strong>Networking:</strong> Retrofit / OkHttp with reactive error interceptors and network interceptor layers</li>
  <li><strong>Dependency Injection:</strong> Hilt (Compile-time safe Dependency Injection framework built on top of Dagger)</li>
</ul>

<h2>💎 Senior Engineering Highlights</h2>
<ul>
  <li><strong>Offline-First Single Source of Truth (SSOT):</strong> Implements a robust local caching ecosystem using Room. The presentation layer streams data exclusively from local reactive database instances, while the network infrastructure asynchronously fetches and updates data in the background, guaranteeing a zero-latency UI experience even during complete network drops.</li>
  <li><strong>Decoupled Architecture:</strong> Enforces a strict separation of concerns across Data, Domain, and Presentation layers, ensuring highly testable, modular, and maintainable enterprise code.</li>
  <li><strong>Cloud Media Optimization:</strong> Integrates Firebase Cloud Storage to dynamically fetch remote image pointers, reducing backend processing overhead and leveraging client-side caching optimizations to keep network payloads lightweight.</li>
  <li><strong>Reactive State Engine:</strong> Employs stateful UI wrapper paradigms to eliminate composition leaks and handle complex asynchronous state mutations predictably across lifecycle changes.</li>
</ul>
