# YourWallet

Криптокошелек для просмотра информации об эфире и токенах:
- собранный [apk файл](https://drive.google.com/file/d/1kgd37yrOL8-k99702fCtS_PeBKPIPjdL/view?usp=sharing)
- краткая [презентация приложения](https://vik_tal.slides.com/vik_tal/deck-2b88fa/fullscreen)

Кошелек использует открытое API [etherscan](https://github.com/EverexIO/Ethplorer/wiki/ethplorer-api).

https://user-images.githubusercontent.com/56442323/122793588-0e48b100-d2c4-11eb-8f6d-565d2d198904.mp4

**Экраны:**
- Вход
- Список токенов и эфира
- Список транзакций
- Детали транзакций
- График курса эфира

**Основные технологии:**
- Язык разработки - Kotlin
- Архитектура - MVVM
- Работа с API - okHttp + moshi
- Kotlin coroutines
- UI - paging3, MPAndroidChart, Navigation, ObjectAnimator
