package fr.ntimeu.lab;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class App {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();
        ActorRef counter = system.actorOf(Counter.props());

        for (int i = 0; i < 25; ++i) {
            new Thread(() -> counter.tell(new Counter.Message(), ActorRef.noSender())).start();
        }
    }

    private static class Counter extends AbstractActor {
        private int counter = 0;

        static Props props() {
            return Props.create(Counter.class);
        }

        @Override
        public Receive createReceive() {
            return ReceiveBuilder
                    .create()
                    .match(Message.class, message -> counter++)
                    .build();
        }

        private static class Message {
        }
    }
}
