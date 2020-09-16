package org.barnhorse.sts.lib;

import com.badlogic.gdx.Game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class TestEventQueue {
    public static void main(String[] args) throws InterruptedException, IOException {

        BlockingQueue<GameEvent> queue = new LinkedBlockingDeque<>();
        queue.put(new GameEvent("test1"));
        queue.put(new GameEvent("test2"));

        String logPath = "/home/lu/testlog";
        Writer writer = new BufferedWriter(new FileWriter(logPath, true));
        Thread eventThread = new Thread(new EventLoggerThread(queue, writer));

        eventThread.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String event = scanner.nextLine();
            if (event.equals("stop")) {
                eventThread.interrupt();
                break;
            } else {
                queue.put(new GameEvent(event));
            }
        }

        writer.close();
        eventThread.join();
    }
}
