package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.OpiskelijaDao;
import tikape.runko.database.AnnosDao;
import tikape.runko.database.AnnosRaakaAineDao;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.domain.RaakaAine;
import tikape.runko.domain.Annos;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:smoothiet.db");
//        Database database = new Database("jdbc:sqlite:opiskelijat.db");
        database.init();

//        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);
        AnnosDao annosDao = new AnnosDao(database);
        RaakaAineDao raakaAineDao = new RaakaAineDao(database);
        AnnosRaakaAineDao annosRaakaAineDao = new AnnosRaakaAineDao(database, annosDao, raakaAineDao);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        // Listaa raaka-aineet
        get("/ainekset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("ainekset", raakaAineDao.findAll());

            return new ModelAndView(map, "ainekset");
        }, new ThymeleafTemplateEngine());
        
        // Lisää uusi raaka-aine
        post("/ainekset", (req, res) -> {
            String nimi = req.queryParams("nimi");
            raakaAineDao.save(new RaakaAine(null, nimi));
            
            res.redirect("/ainekset");
            return "";
        });
        
        // Listaa valitun annoksen raaka-aineet ja määrät
        get("/annokset/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            
            map.put("annos", annosDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("aineet", annosRaakaAineDao.findSelected(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "annos");
        }, new ThymeleafTemplateEngine());
        
        // Listaa smoothiet
        get("/smoothiet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", annosDao.findAll());

            return new ModelAndView(map, "smoothiet");
        }, new ThymeleafTemplateEngine());

        // Lisää uusi smoothie
        post("/smoothiet", (req, res) -> {
            String nimi = req.queryParams("nimi");
            annosDao.save(new Annos(null, nimi));
            
            res.redirect("/smoothiet");
            return "";
        });
        
//        get("/opiskelijat", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelijat", opiskelijaDao.findAll());
//
//            return new ModelAndView(map, "opiskelijat");
//        }, new ThymeleafTemplateEngine());

//        get("/opiskelijat/:id", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));
//
//            return new ModelAndView(map, "opiskelija");
//        }, new ThymeleafTemplateEngine());
    }
}
