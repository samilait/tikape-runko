package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.AnnosDao;
import tikape.runko.database.AnnosRaakaAineDao;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.domain.RaakaAine;
import tikape.runko.domain.Annos;
import tikape.runko.domain.AnnosRaakaAine;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:smoothiet.db");
//        database.init();

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
            map.put("ainekset", raakaAineDao.findAll());


            return new ModelAndView(map, "smoothiet");
        }, new ThymeleafTemplateEngine());

        // Lisää uusi smoothie
        post("/smoothiet", (req, res) -> {
            String nimi = req.queryParams("nimi");
            if (nimi != null) {
                annosDao.save(new Annos(null, nimi));
            } else {
            
                // Smoothie ja raaka-aine id:t => x.findOne(id) => Luokat annosRaakaAine -oliolle
                Annos annos = annosDao.findOne(Integer.parseInt(req.queryParams("smoothie")));
                RaakaAine raakaAine = raakaAineDao.findOne(Integer.parseInt(req.queryParams("raakaAine")));
                Integer jarjestys = Integer.parseInt(req.queryParams("jarjestys"));
                String maara = req.queryParams("maara");            
                String ohje = req.queryParams("ohje");

                AnnosRaakaAine annosRaakaAine = new AnnosRaakaAine(jarjestys, maara, ohje, annos, raakaAine);
                annosRaakaAineDao.save(annosRaakaAine);
            }
            res.redirect("/smoothiet");
            return "";
        });
        
        // Tilasto raaka-aineittain
        get("/tilastot", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("tilastot", annosRaakaAineDao.statistics());

            return new ModelAndView(map, "tilastot");
        }, new ThymeleafTemplateEngine());

    }
}
