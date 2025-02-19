Basic Micro Kernel

📌 Description
This project simulates a basic microkernel system with inter-process communication (IPC) and process scheduling. The system executes five different processes, each performing specific tasks and demonstrating fundamental operating system concepts like process dependencies, file operations, and message passing.

🔹 Processes Overview
1️⃣ Sum Calculator (Process 1)

Computes the sum of the first N natural numbers.
Simulates a basic computation-heavy process.
2️⃣ File Writer (Process 2)

Writes text output to a file.
Stores data that will later be used by Process 3.
3️⃣ File Reader (Process 3) (Depends on Process 2)

Reads content from the file created by Process 2.
Demonstrates process synchronization by waiting for Process 2 to complete before execution.
4️⃣ Pipeline Sender (Process 4)

Sends a message via an inter-process communication (IPC) mechanism.
Simulates a producer-consumer pipeline by transmitting data to Process 5.
5️⃣ Receiver (Process 5) (Depends on Process 4)

Listens for and receives messages from Process 4.
Prints the received message, demonstrating communication between processes.

🚀 How to Run

Clone the repository
git clone <repository-url>
cd <repository-folder>

Compile and Run the Program

Open the project in your Java IDE (e.g., IntelliJ IDEA, Eclipse, VS Code).
Run Main.java.
Once executed, the program will automatically schedule and run the five processes based on their dependencies.
