![Running Devices - TradeChart 2026-02-18 12-49-34 (online-video-cutter com)](https://github.com/user-attachments/assets/5ce412d3-347f-403c-a7c1-d589db6e09c6)📈 TradeChart
Denbora Errealeko Negoziazio Simulagailua Android-erako

TradeChart Android-erako negoziazio-simulaziorako aplikazio bat da, merkatuko datuak denbora errealean bistaratzen dituena. Proiektua praktika profesional onenak jarraituz eraikia dago, hala nola Clean Architecture eta MVVM, oinarri sendo, eskalagarri eta mantentzen erraza bermatzeko.

Garapenean teknologia moderno eta sendoak erabili dira, besteak beste: Kotlin, Jetpack Compose, Hilt eta Retrofit.

✨ Ezaugarri Nagusiak
📊 Kandela Grafikoak (Candlestick)

Merkatuko datuen bistaratze dinamikoa eta zehatza, MPAndroidChart liburutegiaren bidez.

🔄 Zuzeneko Eguneraketak

Prezioen eta grafikoaren eguneraketa jarraitua Kotlin Coroutines & Flow erabiliz, UI erreaktibo eta arina lortzeko.

🏛️ Arkitektura Garbia + MVVM

Geruza bakoitza argi bereizita:

Negozio-logika

Datu-kudeaketa

Erabiltzaile-interfazea

Kodea desakoplatua, testagarria eta mantentzen erraza.

💉 Dependentzia Injekzioa

Hilt erabiliz dependentzien kudeaketa eraginkorra eta segurua.

🌐 Benetako API baten Kontsumoa

Merkatuko API erreal eta konfiguragarri batera konektatzen da, Retrofit eta OkHttp erabiliz.

📱 UI Moderno eta %100 Compose

Interfazea guztiz eraikia dago Jetpack Compose erabiliz — UI deklaratiboa, modularra eta erreaktiboa.

🏗️ Proiektuaren Arkitektura

TradeChart-ek Clean Architecture printzipioak jarraitzen ditu, arduren bereizketa argia eta eskalagarritasuna bermatzeko.

com.example.tradechart
│
├── data
│   ├── remote         # DTO-ak, Retrofit zerbitzua (API)
│   └── repository     # Repository inplementazioak
│
├── domain
│   ├── model          # Negozio entitateak (adib. Candle)
│   ├── repository     # Repository interfazeak
│   └── usecase        # Negozio-logika (UseCases)
│
└── presentation
    ├── ui             # Compose bistak, ViewModel-ak eta nabigazioa
    └── components     # UI osagai berrerabilgarriak

📌 Geruzen Azalpena
🎨 presentation (UI Geruza)

Composable-ak

ViewModel-ak

Nabigazioa

UI egoera-kudeaketa

Domain geruzarekin soilik komunikatzen da.

🧠 domain (Negozio Geruza)

Negozio-arau puruak

UseCase-ak

Android-ekiko dependentziarik gabe

Aplikazioaren bihotza da.

💾 data (Datu Geruza)

API urrunekoa (Retrofit)

Etorkizunean: Room edo beste iturri lokal batzuk

Repository inplementazioak

Domain geruzari datuak hornitzen dizkio.

🛠️ Stack Teknologikoa
Kategoria	Teknologia
Lengoaia	Kotlin
UI	Jetpack Compose
Grafikoak	MPAndroidChart
Arkitektura	MVVM + Clean Architecture + Repository Pattern
Asinkronia	Coroutines & Flow
DI	Hilt
Sarea	Retrofit + OkHttp
Nabigazioa	Navigation Compose
🚀 Nola Hasi
1️⃣ Klonatu biltegia
git clone https://URL_DEL_REPOSITORIO.git

2️⃣ Ireki proiektua Android Studio-n
3️⃣ Sinkronizatu Gradle

Itxaron dependentzia guztiak deskargatu arte.

4️⃣ Exekutatu aplikazioa

Sakatu Shift + F10 edo egin klik Run botoian.

🎉 Prest! Kandela-grafikoa martxan ikusiko duzu zure emuladorean edo gailuan.

📈 Helburua

TradeChart ez da soilik grafiko bat erakusten duen aplikazioa —
arkitektura garbi eta profesional baten adibide praktikoa da, Android garapenean praktika moderno eta eskalagarriak erakusteko diseinatua.



![Running Devices - TradeChart 2026-02-18 12-49-34 (online-video-cutter com)](https://github.com/user-attachments/assets/43f3575f-a229-441e-b9a0-3a9b85b2e35c)
