# Bill Chatbot Project

Welcome to the Bill Chatbot project! This document provides instructions on how to set up and run the application using IntelliJ IDEA.

## Prerequisites

1.  **Java JDK 17:** Ensure you have JDK version 17 installed.
2.  **IntelliJ IDEA:** Use a recent version of IntelliJ IDEA.

## Setup Instructions

### 1. Open the Project
* Launch IntelliJ IDEA.
* If you have another project open, close it via `File` -> `Close Project`.
* From the welcome screen, click **`Open`**.
* Navigate to and select the project's root folder, then click **`OK`**.
* If prompted, trust the project and accept any default settings.

### 2. Configure the JDK
* Go to `File` -> `Project Structure...`.
* Under **Project Settings**, select **`Project`**.
* Set the **SDK** to your installed **JDK 17**. If it's not listed, you can add it.
* Set the **Language level** to **`SDK default`**.
* Click **`OK`** to save the changes.

### 3. Run the Application
* Wait for IntelliJ to index the project files.
* In the Project Explorer, navigate to `src/main/java/bill/Launcher.java`.
* Right-click on the `Launcher.java` file.
* Select **`Run 'Launcher.main()'`**.

If the setup is successful, the Bill chatbot's graphical user interface (GUI) window will appear.

> [!NOTE]
> The default location for all source code is the `src/main/java` folder. It's recommended to keep your `.java` files here as this is the standard directory structure used by Gradle.