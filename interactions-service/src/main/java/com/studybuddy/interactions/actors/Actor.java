package com.studybuddy.interactions.actors;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public abstract class Actor {
    private final BlockingQueue<ActorMessage> mailbox = new LinkedBlockingQueue<>();
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread worker;
    protected final Logger logger = Logger.getLogger(this.getClass().getName());
    private final String name;

    protected Actor(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void start() {
        if (running.compareAndSet(false, true)) {
            worker = new Thread(this::loop, "actor-" + name);
            worker.start();
            logger.info(() -> "[" + name + "] started");
        }
    }

    private void loop() {
        try {
            while (running.get()) {
                ActorMessage msg = mailbox.take();
                try {
                    onReceive(msg);
                } catch (Throwable t) {
                    logger.severe(() -> "[" + name + "] error processing message: " + t.getMessage());
                    // Simple behaviour: swallow error (supervision to add later)
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        running.set(false);
        if (worker != null) {
            worker.interrupt();
        }
        logger.info(() -> "[" + name + "] stopped");
    }

    public void tell(ActorMessage msg) {
        mailbox.offer(msg);
    }

    protected abstract void onReceive(ActorMessage msg) throws Exception;
}
