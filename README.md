# 🐓 Poultry Management System: CLI Farm Manager

![Java](https://img.shields.io/badge/Java-8%2B-blue)
![SQLite](https://img.shields.io/badge/SQLite-Database-orange)
![Status](https://img.shields.io/badge/Status-Phase_1.5_Ongoing-green)

### 🚨 The Problem

Small-scale poultry farmers often struggle with **manual record-keeping**, leading to:

* ❌ Inaccurate inventory tracking
* ❌ Poor decision-making
* ❌ Lost profits

Paper-based systems make it difficult to analyze production trends, manage feed inventory, or monitor bird health effectively.

### 💡 The Solution

A **Java CLI Management System** that enables farmers to:

* 📝 Track daily farm operations
* 🐥 Manage inventory
* 📊 Generate insightful reports

All through a **structured, menu-driven interface** suitable for non-technical users.

---

## 🔬 System Architecture

Modular Java architecture with separate components for farm data recording, inventory management, reporting, and utility functions.

### Key Results

| Metric               | Score/Impact                                     |
| :------------------- | :----------------------------------------------- |
| Data Accuracy        | High – all inputs validated with confirmation    |
| Inventory Validation | Real-time – prevents overconsumption             |
| Report Generation    | 5 types – daily, weekly, monthly, summary, stats |
| User Interface       | Menu-driven – intuitive navigation               |

---

## 🛠️ Tech Stack

* **Language:** Java 8+
* **Data Storage:** SQLite database (migrated from TXT files)
* **Input Validation:** Comprehensive validation with confirmation workflow
* **Encryption:** Simple substitution cipher for PIN security
* **File Management:** Read/write utilities with error handling

---

## 📊 Visualizations / Components

*Modules and Data Structure*:

### Core Modules

| Module             | Description                                          |
| :----------------- | :--------------------------------------------------- |
| `FarmRecord.java`  | Daily farm data recording and management             |
| `Inventory.java`   | Bird and feed stock control with validation          |
| `Report.java`      | Comprehensive reporting & analytics engine           |
| `SystemUtils.java` | Utility functions (encryption, file I/O, validation) |
| `User.java`        | User registration and authentication system          |
| `Help.java`        | Help system for navigating the CLI                   |

### Data Structure

```
data/
├── FarmRecords.txt    # [date, total_eggs, feed, deaths, comment]
├── Inventory.txt      # [date, total_birds, total_feed]
└── UsersInfo.txt      # [serial, first_name, last_name, username, encrypted_pin]
```

---

## 🚀 How to Run

1. Clone the repository:

```bash
git clone https://github.com/ENJ-digitalhub/Poultry_Farm_Management_System.git
```

2. Navigate to the project:

```bash
cd Poultry_Farm_Management_System
```

3. Compile and run:

```bash
javac PF/*.java PoultryManagementSystem.java
java PoultryManagementSystem
```

---

## ⚠️ Experimental Design & Limitations

**Current Constraints (v1.0)**

1. Broken Egg Analysis: Under development
2. Reporting: Daily statistics formatting incomplete
3. Data Validation: Some edge cases still need handling
4. Database: Migration to SQLite in progress

> Addressed in Phase 1.5.

---

## 🗂️ Planned Enhancements

| Phase | Features / Improvements                                                                              | Status     |
| :---- | :--------------------------------------------------------------------------------------------------- | :--------- |
| 1.5   | Login & PIN authentication, Broken Egg Analysis, SQLite Integration, Improved Reporting & Validation | ✅ Ongoing  |
| 2     | EXE installer, Improved CLI interface, Better UX & error handling                                    | ⚠️ Planned |
| 2.5   | CSV export, Advanced data visualization, Custom reporting, Broken egg analytics                      | ⚠️ Planned |
| 3     | Multi-user support, Predictive analytics, Real-time dashboard                                        | ⚠️ Planned |

---

## 🔄 Data Validation Features ✅

* Input Format Validation (crates_singles)
* Inventory Checks (prevents over-recording)
* Duplicate Prevention (daily records)
* Positive Values Only
* Confirmation Workflow

---

## 📈 Reporting Capabilities 📊

| Report Type        | Description                            |
| :----------------- | :------------------------------------- |
| Daily Report       | Data for the current day               |
| Weekly Report      | Last 7 days with totals & averages     |
| Monthly Report     | Month-to-date with performance summary |
| Summary Report     | Quick overview of production trends    |
| Statistics Summary | Highest/lowest production metrics      |

**Weekly Report Example:**

```
--- Weekly Report (Last 7 Days) ---
Total Crates(Eggs)       : 35(12)
Total Feeds              : 175.5
Total Deaths             : 8
Average Crates(Eggs)/Days: 5(1)
```

---

## 🎯 Target Users

* Small to medium poultry farms (50–10,000 birds)
* Farmers transitioning from paper-based systems
* Agricultural students learning farm management
* Extension officers demonstrating digital record-keeping

---

## 📚 Project Structure

```
Poultry_Farm_Management_System/
├── PF/                              # Java package
│   ├── FarmRecord.java             
│   ├── FileNames.java              
│   ├── Help.java                   
│   ├── Inventory.java              
│   ├── Report.java                           
│   └── SystemUtils.java
├── data/                           
│   ├── FarmRecords.txt            
│   ├── Inventory.txt              
│   └── UsersInfo.txt              
├── PoultryManagementSystem.java    
└── README.md
```

---

## 🛡️ Security Features

* PIN Encryption (simple substitution cipher)
* Data Integrity: file validation and automatic creation
* Input Sanitization to prevent malformed data
* Confirmation System to reduce accidental corruption

---

## 🤝 Contributing

Contributions welcome for:

* Enhanced reporting
* Data export features
* Validation improvements
* Documentation improvements

---

## 📄 License

Currently unlicensed. Contact maintainer for usage rights.

---

## 👤 Author

**Maintainer:** ENJ Digital Hub

**Project Status:** Active Development (Phase 1.5 Ongoing)

**Version:** 1.5 – Current Work in Progress

"Efficient farming begins with organized data management."
