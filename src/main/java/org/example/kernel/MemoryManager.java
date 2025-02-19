package org.example.kernel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryManager {
    private final int totalMemory;
    private final List<MemoryBlock> memoryBlocks;

    public MemoryManager(int totalMemory) {
        this.totalMemory = totalMemory;
        this.memoryBlocks = new ArrayList<>();
        this.memoryBlocks.add(new MemoryBlock(0, totalMemory, true)); // Initially, all memory is free
    }

    public boolean allocateMemory(UserProcess userProcess, int requiredMemory) {
        Collections.sort(memoryBlocks); // Best-Fit strategy

        for (int i = 0; i < memoryBlocks.size(); i++) {
            MemoryBlock block = memoryBlocks.get(i);

            if (block.isFree && block.size >= requiredMemory) {
                Logger.info("✅ Allocating " + requiredMemory + "MB to Process " + userProcess.pid);

                if (block.size > requiredMemory) {
                    MemoryBlock remainingBlock = new MemoryBlock(block.start + requiredMemory, block.size - requiredMemory, true);
                    memoryBlocks.add(i + 1, remainingBlock);
                }

                block.isFree = false;
                block.size = requiredMemory;
                userProcess.allocatedMemory = block;

                return true;
            }
        }

        Logger.warning("⚠️ Process " + userProcess.pid + " is too large or memory is full.");
        return false;
    }

    public void freeMemory(UserProcess userProcess) {
        if (userProcess.allocatedMemory != null) {
            System.out.println("♻️ Freeing memory of Process " + userProcess.pid);
            userProcess.allocatedMemory.isFree = true;
            mergeFreeBlocks(); // Merge adjacent free blocks
        }
    }

    private void mergeFreeBlocks() {
        memoryBlocks.sort((a, b) -> Integer.compare(a.start, b.start)); // Sort by start address

        for (int i = 0; i < memoryBlocks.size() - 1; i++) {
            MemoryBlock current = memoryBlocks.get(i);
            MemoryBlock next = memoryBlocks.get(i + 1);

            if (current.isFree && next.isFree) {
                System.out.println("🔄 Merging free blocks at " + current.start + "MB and " + next.start + "MB");
                current.size += next.size;
                memoryBlocks.remove(i + 1);
                i--;  // Recheck after merging
            }
        }
    }

    public void printMemoryState() {
        System.out.println("\n📊 Current Memory State:");
        for (MemoryBlock block : memoryBlocks) {
            System.out.println("🔹 Block " + block.start + "MB - " + (block.start + block.size) + "MB | Size: " + block.size + "MB | Free: " + block.isFree);
        }
    }

    static class MemoryBlock implements Comparable<MemoryBlock> {
        int start;
        int size;
        boolean isFree;

        public MemoryBlock(int start, int size, boolean isFree) {
            this.start = start;
            this.size = size;
            this.isFree = isFree;
        }

        @Override
        public int compareTo(MemoryBlock other) {
            return Integer.compare(this.size, other.size); // Best-Fit (smallest free block first)
        }
    }
}
