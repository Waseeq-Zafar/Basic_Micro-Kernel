🧠 Basic Micro Kernel Simulation
📌 Description
This project simulates a basic microkernel system with inter-process communication (IPC), process scheduling, and disk scheduling. The system executes five different processes, each performing specific tasks and demonstrating fundamental operating system concepts like process dependencies, file operations, message passing, and track-based disk access.

🔹 Processes Overview
1️⃣ Sum Calculator (Process 1)
Computes the sum of the first N natural numbers. Simulates a basic computation-heavy process.

2️⃣ File Writer (Process 2)
Writes text output to a file. Also simulates disk activity by requesting random track access using a First-Come, First-Served (FCFS) Disk Scheduling Algorithm.
➡ This mimics low-level disk operations such as seeking to a track and writing data, reflecting how OS kernels handle actual I/O.

3️⃣ File Reader (Process 3) (Depends on Process 2)
Reads content from the file created by Process 2. Demonstrates process synchronization by waiting for Process 2 to complete before execution.

4️⃣ Pipeline Sender (Process 4)
Sends a message via an IPC (Inter-Process Communication) mechanism. Simulates a producer-consumer pipeline by transmitting data to Process 5.

5️⃣ Receiver (Process 5) (Depends on Process 4)
Listens for and receives messages from Process 4. Prints the received message, demonstrating communication between processes.

💽 Disk Scheduling
This simulation includes a basic FCFS Disk Scheduler, which models how a disk head moves to requested tracks.
Each track request (e.g., from FileWriterProcess) causes the disk head to move, and the total movement is tracked for stats:

Head movement is shown step-by-step (e.g., Moving head from 25 to 76).

Total head movement is computed and displayed.

Helps visualize how disk access impacts performance in OS scheduling.

🚀 How to Run
Clone the repository

bash
Copy
Edit
git clone <repo-url>
cd <repo-folder>
Update FileWriterProcess.java

In src/main/java/org/example/processes/FileWriterProcess.java, locate the startUserInputHandler() method and update the file path string to your absolute target/classes directory.

Compile and Run

Open in your preferred Java IDE (IntelliJ IDEA, Eclipse, VS Code).
Run Main.java to simulate execution of all 5 processes with memory allocation, CPU scheduling, and disk I/O.

