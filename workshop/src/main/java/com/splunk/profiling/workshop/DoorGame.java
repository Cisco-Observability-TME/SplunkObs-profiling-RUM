package com.splunk.profiling.workshop;

import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.extension.annotations.SpanAttribute;
import io.opentelemetry.extension.annotations.WithSpan;

import static com.splunk.profiling.workshop.Util.sleep;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DoorGame {

    private final DoorChecker gameOfficial = new DoorChecker();
    private final Map<String, GameInfo> games = new ConcurrentHashMap<>();

    @WithSpan(kind = SpanKind.INTERNAL)
    public String startNew() {
        String uuid = UUID.randomUUID().toString();
        Random random = new Random();
        int winningDoor = random.nextInt(3);
        games.put(uuid, new GameInfo(uuid, winningDoor));
        Util.sleep(1500);
        return uuid;
    }

    @WithSpan(kind = SpanKind.INTERNAL)
    public int reveal(String uid) {
        GameInfo gameInfo = games.get(uid);
        return gameInfo.getDoorToReveal();
    }

    @WithSpan(kind = SpanKind.INTERNAL)
    public void pick(String uid, @SpanAttribute("door") int picked) throws Exception {
        System.out.println(">>>>>>> PICKED::"+picked);
        
        //lets sleep for real if door is 3
        if (picked==2){
            sleep(5000);
        }


        //randomly breaks after selecting door #2
        if (picked==1){
            Random random = new Random();
            int randomNumber = random.nextInt(6); // Generates a number between 0 (inclusive) and 6 (exclusive)
            if (randomNumber <=3){
                throw new Exception("Error while opening door - Donkey not found Exception!");
            }
        }

        GameInfo gameInfo = games.get(uid);
        gameInfo.pick(picked);
    }

    @WithSpan(kind = SpanKind.INTERNAL)
    public String getOutcome(String uid, @SpanAttribute("workflow.name") int picked) {
        GameInfo gameInfo = games.get(uid);
        return gameOfficial.isWinner(gameInfo, picked) ? "WIN" : "LOSE";
    }
}
