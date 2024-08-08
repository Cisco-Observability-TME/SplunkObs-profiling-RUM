package com.splunk.profiling.workshop;

import static com.splunk.profiling.workshop.Util.sleep;
import static java.lang.Integer.parseInt;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceMain {
    
    //private static Logger LOGGER = LoggerFactory.getLogger(ServiceMain.class);
    
    private final static DoorGame doorGame = new DoorGame();

    public static void main(String[] args) {
        //BackgroundWorker.start();
        //LOGGER.info("This is an info level log message!");
        //LOGGER.info("BackGround Worker Start");
        //LOGGER.info("This is an INFO level log message!");
        //LOGGER.error("This is an ERROR level log message!");
        port(9090);
        staticFiles.location("/public"); // Static files

        get("/new-game", (req, res) -> doorGame.startNew());
        //LOGGER.info("NewGame URL HIT");
        post("/game/:uid/pick/:picked", (req, res) -> {
            //LOGGER.info("Game Picked URL HIT");
            sleep(600);
            String uid = req.params(":uid");
            String picked = req.params(":picked");
            doorGame.pick(uid, parseInt(picked));
            return "OK";
        });
        get("/game/:uid/reveal", (req, res) -> {
            //LOGGER.info("Reveal URL!");
            String uid = req.params(":uid");
            return doorGame.reveal(uid);
        });
        get("/game/:uid/picked/:picked/outcome", (req,res) -> {
            //LOGGER.info("Outcome URL");
            String uid = req.params(":uid");
            String picked = req.params(":picked");
            return doorGame.getOutcome(uid, parseInt(picked));
        });
    }
}
