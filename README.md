# 📈 StockApp

StockApp is a simple Android application that displays real-time market summaries using the Yahoo Finance API.
The app demonstrates clean architecture, modern Android development practices, and MVVM with Jetpack Compose for UI.

## 🚀 Features

View latest market summary and stock information

Search and filter by company name or symbol

Pull-to-refresh functionality

Detail view for selected stocks

Built using clean architecture principles

## 🧠 Architecture

This project follows Clean Architecture and MVVM, ensuring a clear separation of concerns:

<img width="227" height="376" alt="Screenshot 2025-10-17 at 02 02 59" src="https://github.com/user-attachments/assets/576daaa7-4461-4e40-a8d6-07623ef6809a" />

🛠️ Tech Stack

Kotlin | Jetpack Compose | Coroutines & Flow | MVVM | Hilt (DI) | JUnit + MockK (Testing)

Clean Architecture

## 🧩 Git & Branch Workflow
To maintain a clean and stable codebase, this project follows a protected branch and pull request workflow.

### 🛡️ Branch Protection Rules
✅ Main branch is protected — direct pushes are not allowed.

✅ All changes must go through a Pull Request (PR).

✅ Required status checks must pass before merging, including:

build (GitHub Actions workflow for CI, tests, and formatting)

✅ Linear history is enforced — no merge commits.

✅ Force pushes and branch deletions are blocked.

✅ Pull requests must be up-to-date with the base branch before merging.

## 🧹 Code Quality & CI/CD
This project uses Spotless with Ktlint to maintain consistent code formatting and follow Kotlin best practices.

### 🔍 Code Formatting
Before commit any changes, code style is automatically checked using Spotless.
If the formatting dose not match the project rules, the commit will fail.

**Run checks manually:**
```bash
./gradlew spotlessCheck
```

**Auto-format code:**
```bash
./gradlew spotlessApply
```

### 💡 Pre-commit Hook (Optional)
You can enable a local Git pre-commit hook to automatically verify formatting before every commit:

```bash
echo "🔍 Running Spotless Check..."
./gradlew spotlessCheck
RESULT=$?
if [ $RESULT -ne 0 ]; then
echo "❌ Code formatting check failed! Run './gradlew spotlessApply' to fix."
exit 1
fi
```

Make the hook executable:
chmod +x .git/hooks/pre-commit

### ⚙️ Continuous Integration

CI is powered by GitHub Actions and automatically runs on every push and pull request to verify build success, code formatting, and test coverage.

Currently, only the CI flow is implemented — it performs:

✅ Code style validation using Spotless + Ktlint

✅ Project build verification (./gradlew assembleDebug)

✅ Unit test execution (./gradlew testDebugUnitTest)


## 🔑 Getting Started
1️⃣ Get API Token

To use the Yahoo Finance API, you’ll need a RapidAPI key:

Go to: Yahoo Finance API (RapidAPI)

Click “Subscribe to Test” (free or basic tier)

Copy your API Key from your account’s “API Keys” section

2️⃣ Add API Key

In your local.properties (or .env if configured):

RAPIDAPI_KEY=your_api_key_here


This key will be used by the app to authenticate requests.

## 🧪 Testing

Run unit tests with:

./gradlew testDebugUnitTest
