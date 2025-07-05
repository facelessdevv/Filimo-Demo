# 🎬 Filimo Demo

A modern Android **Movie Explorer** app built with **Jetpack Compose**, designed to simulate a real-world, production-ready architecture. It supports offline access, auto-refreshing movie rows, adaptive layouts, and even Android TV navigation.

> This project demonstrates clean architecture, modularization, and Compose UI best practices.

---

## 📸 Demo Previews

<table>
  <tr>
    <td align="center"><b>🌞 Light Mode</b></td>
    <td align="center"><b>🌚 Dark Mode</b></td>
  </tr>
  <tr>
    <td>
      <img src="https://github.com/facelessdevv/Filimo-Demo/blob/master/light_mode.gif?raw=true" width="300"/>
    </td>
    <td>
      <img src="https://github.com/facelessdevv/Filimo-Demo/blob/master/dark_mode.gif?raw=true" width="300"/>
    </td>
  </tr>
</table>

---

### 📺 Android TV Mode

<img src="https://github.com/facelessdevv/Filimo-Demo/blob/master/android_tv.gif?raw=true" width="500"/>

---

## ✨ Features

- **🧱 Modular Architecture**  
  Clean separation into `app`, `data`, and `domain` layers following Clean Architecture principles.

- **📦 Offline-First Caching**  
  Movie data is persisted locally using **Room**, making the app functional without internet.

- **🔄 Auto-Refreshing Rows**  
  Movie rows update every **10 seconds** with smooth animations. The list resets after reaching the end of available data.

- **🏷️ Dynamic Row Titles**  
  Titles are generated from the API’s `link_text` field, making row names dynamic.

- **❤️ Like Functionality**  
  Users can "like" movies and those likes are stored locally with Room, persisting across sessions.

- **📱 Adaptive UI**  
  Built with **Jetpack Compose**, supporting both portrait and landscape orientations.

- **📺 Android TV Support**  
  Full navigation support using D-pad and focus handling, making it suitable for Android TV devices.

- **📡 Connectivity Feedback**  
  Displays a toast message when the internet connection is lost.

---

## 🏛️ Architecture

This project is built using **Clean Architecture**, with strict separation of concerns and one-way dependencies.

### 🔹 `domain` (Pure Kotlin Module)
- Contains business logic and models (e.g., `MovieItemEntity`, `MovieRow`)
- Defines repository interfaces (the "what")
- No dependencies on Android

### 🔹 `data` (Android Library)
- Implements domain interfaces
- Handles:
  - **Retrofit** API requests
  - **Room** local database
  - Model mapping between API, DB, and domain

### 🔹 `app` (Android App)
- UI built with **Jetpack Compose**
- Contains screens, ViewModels, navigation, and theming
- Only depends on `domain` and is unaware of the `data` implementation

> 📦 Dependency flow: `app → domain ← data`

---

## 🛠️ Tech Stack

| Category           | Tools / Libraries                       |
|--------------------|------------------------------------------|
| Language           | Kotlin                                   |
| UI Framework       | Jetpack Compose                          |
| Architecture       | Clean Architecture, MVVM                 |
| Async & State      | Kotlin Coroutines, Flow                  |
| Dependency Injection | Hilt                                   |
| Networking         | Retrofit, OkHttp                         |
| Persistence        | Room                                     |
| Image Loading      | Coil                                     |
| Navigation         | Compose Navigation                       |
| Testing            | JUnit, MockK, Turbine                    |

---

## 📝 Assumptions

- The movie's `id` is used as the unique key for persistence and navigation.
- When `links.forward` is `null`, the app resets to the original API endpoint.
- Each movie row is limited to a maximum of 15 items.
- The API’s `link_text` field is used as the row title.

---

## 🚀 Future Improvements

- **Use Case Layer**  
  Introduce `GetMoviesUseCase` in the domain layer to encapsulate business logic and keep ViewModels cleaner.

- **Paging Support**  
  Integrate Jetpack Paging 3 for memory-efficient loading of large datasets.

- **UI Testing**  
  Add UI tests using `espresso-compose` to validate interactions and UI behavior.

- **Skeleton Loaders**  
  Implement shimmer/skeleton loaders to improve perceived loading experience.

---

## 🔗 Resources

- 🎬 **Filimo Movie List API**  
  [`https://www.filimo.com/api/fa/v1/movie/movie/list/tagid/1`](https://www.filimo.com/api/fa/v1/movie/movie/list/tagid/1)
