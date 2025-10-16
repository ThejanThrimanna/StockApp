:

📈 StockApp

StockApp is a simple Android application that displays real-time market summaries using the Yahoo Finance API
.
The app demonstrates clean architecture, modern Android development practices, and MVVM with Jetpack Compose for UI.

🚀 Features

View latest market summary and stock information

Search and filter by company name or symbol

Pull-to-refresh functionality

Detail view for selected stocks

Built using clean architecture principles

🧠 Architecture

This project follows Clean Architecture and MVVM, ensuring a clear separation of concerns:

── stockapp/
├── data/
│   ├── model/
│   ├── network/
│   └── repository/
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
├── presentation/
│   ├── list/
│   ├── detail/
│   └── components/
├── di/
└── utils/

🛠️ Tech Stack

Kotlin

Jetpack Compose

Coroutines & Flow

MVVM

Hilt (DI)

JUnit + MockK (Testing)

Clean Architecture

🔑 Getting Started
1️⃣ Get API Token

To use the Yahoo Finance API, you’ll need a RapidAPI key:

Go to: Yahoo Finance API (RapidAPI)

Click “Subscribe to Test” (free or basic tier)

Copy your API Key from your account’s “API Keys” section

2️⃣ Add API Key

In your local.properties (or .env if configured):

RAPIDAPI_KEY=your_api_key_here


This key will be used by the app to authenticate requests.

🧪 Testing

Run unit tests with:

./gradlew testDebugUnitTest
