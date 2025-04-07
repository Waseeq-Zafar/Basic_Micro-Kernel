package org.example.kernel;

import java.util.*;

public class FCFS implements DiskScheduler {

    static class Request {
        int track;
        int pid;

        Request(int track, int pid) {
            this.track = track;
            this.pid = pid;
        }
    }

    private final Queue<Request> queue = new LinkedList<>();
    private final List<Request> processedRequests = new ArrayList<>();

    private int currentHead = 0;
    private int totalHeadMovement = 0;

    @Override
    public void addRequest(int trackNumber, int pid) {
        queue.add(new Request(trackNumber, pid));
        System.out.println("[DISK] 📥 Request added: PID " + pid + " → Track " + trackNumber);
    }

    @Override
    public void processRequests() {
        while (!queue.isEmpty()) {
            Request req = queue.poll();
            int movement = Math.abs(currentHead - req.track);

            System.out.println("[DISK] 🚧 Moving head from " + currentHead + " to " + req.track + " for PID " + req.pid);
            totalHeadMovement += movement;
            currentHead = req.track;

            System.out.println("[DISK] ✅ Access granted to PID " + req.pid + " at Track " + currentHead);
            processedRequests.add(req);
        }
    }

    public void printStats() {
        System.out.println("\n📊 Disk Usage Stats (FCFS):");
        System.out.println("Total Requests Processed: " + processedRequests.size());
        System.out.println("Total Head Movement: " + totalHeadMovement + " tracks");

        for (Request req : processedRequests) {
            System.out.println("→ PID " + req.pid + " accessed track " + req.track);
        }
    }

    // Optional: Reset for next batch or testing
    public void reset() {
        queue.clear();
        processedRequests.clear();
        totalHeadMovement = 0;
        currentHead = 0;
    }
}
