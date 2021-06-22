# YourWallet

Криптокошелек для просмотра информации об эфире и токенах:
собранный [apk файл](https://drive.google.com/file/d/1kgd37yrOL8-k99702fCtS_PeBKPIPjdL/view?usp=sharing),
краткая [презентация приложения](./presentation.html).

Кошелек основан на открытом API [etherscan](https://github.com/EverexIO/Ethplorer/wiki/ethplorer-api).

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
