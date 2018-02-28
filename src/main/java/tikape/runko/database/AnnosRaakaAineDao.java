/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Annos;
import tikape.runko.domain.RaakaAine;
import tikape.runko.domain.AnnosRaakaAine;
/**
 *
 * @author Sami
 */
public class AnnosRaakaAineDao implements Dao<AnnosRaakaAine, Integer> {

    private Database database;
    private Dao<Annos, Integer> annosDao;
    private Dao<RaakaAine, Integer> raakaAineDao;

    public AnnosRaakaAineDao(Database database, Dao<Annos, Integer> annosDao, Dao<RaakaAine, Integer> raakaAineDao) {
        this.database = database;
        this.annosDao = annosDao;
        this.raakaAineDao = raakaAineDao;
    }
    
    @Override
    public AnnosRaakaAine findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE id = ?");
//        PreparedStatement stmt = connection.prepareStatement("SELECT Annos.nimi AS annos, RaakaAine.nimi AS nimi, AnnosRaakaAine.maara AS maara,"
//                + " AnnosRaakaAine.annos_id, AnnosRaakaAine.raaka_aine_id, AnnosRaakaAine.jarjestys AS jarjestys"
//                + " FROM Annos, RaakaAine, AnnosRaakaAine WHERE AnnosRaakaAine.raaka_aine_id = RaakaAine.id"
//                + " AND AnnosRaakaAine.annos_id = Annos.id AND annos_id = ?");

        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();                
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

//        List<AnnosRaakaAine> annosRaakaAineet = new ArrayList<>();
//        while (rs.next()) {
            
        Annos annos = annosDao.findOne(rs.getInt("annos_id"));
        RaakaAine raakaAine = raakaAineDao.findOne(rs.getInt("raaka_aine_id"));
        AnnosRaakaAine annosRaakaAine = new AnnosRaakaAine(rs.getInt("jarjestys"), rs.getString("maara"), rs.getString("ohje"), annos, raakaAine);

//            annosRaakaAineet.add(annosRaakaAine);
//        }

        rs.close();
        stmt.close();
        connection.close();

        return annosRaakaAine;

    }

    public List<AnnosRaakaAine> findSelected(Integer key) throws SQLException {

        Connection connection = database.getConnection();
//        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM AnnosRaakaAine");
        PreparedStatement stmt = connection.prepareStatement("SELECT Annos.nimi AS annos, RaakaAine.nimi AS nimi, AnnosRaakaAine.maara AS maara,"
                + " AnnosRaakaAine.ohje AS ohje, AnnosRaakaAine.annos_id, AnnosRaakaAine.raaka_aine_id, AnnosRaakaAine.jarjestys AS jarjestys"
                + " FROM Annos, RaakaAine, AnnosRaakaAine WHERE AnnosRaakaAine.raaka_aine_id = RaakaAine.id"
                + " AND AnnosRaakaAine.annos_id = Annos.id AND annos_id = ?");

        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();                
//        boolean hasOne = rs.next();
//        if (!hasOne) {
//            return null;
//        }

        List<AnnosRaakaAine> annosRaakaAineet = new ArrayList<>();
        while (rs.next()) {
            
            Annos annos = annosDao.findOne(rs.getInt("annos_id"));
            RaakaAine raakaAine = raakaAineDao.findOne(rs.getInt("raaka_aine_id"));
            AnnosRaakaAine annosRaakaAine = new AnnosRaakaAine(rs.getInt("jarjestys"), rs.getString("maara"), rs.getString("ohje"), annos, raakaAine);
//            Integer id = rs.getInt("id");
//            String nimi = rs.getString("nimi");

            annosRaakaAineet.add(annosRaakaAine);
        }

        rs.close();
        stmt.close();
        connection.close();

        return annosRaakaAineet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    @Override
    public List<AnnosRaakaAine> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AnnosRaakaAine save(AnnosRaakaAine object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
