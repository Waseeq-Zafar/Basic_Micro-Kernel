package org.example.kernel;

public interface DiskScheduler {
    void addRequest(int trackNumber, int pid);
    void processRequests();
}
