package SoFT.data;

import org.apache.tools.ant.types.selectors.SelectSelector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataStructures
{
    //Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"
    String dbUrl = "jdbc:mysql://localhost:3306/soft";
    //Database Username
    String username = "root";

    //Database Password
    String password = "selenium";

    Connection con;

    public List<Resenar> resenarList = new ArrayList<Resenar>();

    public DataStructures(String owner, boolean allResenar) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Create Connection to DB
        Connection con = DriverManager.getConnection(dbUrl,username,password);
        String query;
        if (allResenar)
            query = "SELECT * FROM soft_resenar";
        else
            query = "SELECT * FROM soft_resenar WHERE RESENAR_OWNER = '" + owner + "'";

        //Create Statement Object
        Statement stmt = con.createStatement();

        // Execute the SQL Query. Store results in ResultSet
        ResultSet rs= stmt.executeQuery(query);

        // Add all Resenar to datastructure
        while (rs.next()){
            this.resenarList.add(new Resenar(rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7)));

        }
        // Get all Arenden for each Resenar
        //

        for (int i = 0; i < this.resenarList.size(); i++)
        {

            query = "SELECT * FROM soft_arenden WHERE FK_RESENAR = " + resenarList.get(i).PK_RESENAR;
            stmt = con.createStatement();
            rs= stmt.executeQuery(query);
            while (rs.next()){
                this.resenarList.get(i).arendeList.add(new FardtjanstArende(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
                Statement infoStmt = con.createStatement();
                String infoQuery = "SELECT * FROM soft_sit_info WHERE FK_RESENAR =" + resenarList.get(i).PK_RESENAR +" AND FK_ARENDE = '" + this.resenarList.get(i).arendeList.get(0).PK_ARENDE + "'";
                ResultSet infoRS = infoStmt.executeQuery(infoQuery);
                while (infoRS.next())
                {
                    resenarList.get(i).arendeList.get(0).sitInformation.FK_RESENAR = this.resenarList.get(i).PK_RESENAR;
                    resenarList.get(i).arendeList.get(0).sitInformation.FK_ARENDE = this.resenarList.get(i).arendeList.get(0).PK_ARENDE;
                    resenarList.get(i).arendeList.get(0).sitInformation.INFO_FTJ_TILLSTAND_ID = infoRS.getString(3);
                    resenarList.get(i).arendeList.get(0).sitInformation.INFO_FTJ_KORTNUMMER = infoRS.getString(4);
                    resenarList.get(i).arendeList.get(0).sitInformation.INFO_FTJ_KORT_ID = infoRS.getString(5);
                    resenarList.get(i).arendeList.get(0).sitInformation.INFO_IDEMIA_FILENAME = infoRS.getString(6);
                }
            }

        }
        // closing DB Connection
        con.close();
    }

    public enum Tables
    {
        TBL_RESENAR, TBL_ARENDEN, TBL_INFO
    }

    public void UpdateRecords(Tables tableSelection) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Create Connection to DB
        Connection con = DriverManager.getConnection(dbUrl,username,password);

        switch (tableSelection)
        {
            case TBL_RESENAR:
                String query = "UPDATE soft_resenar SET RESENAR_CREATED = ?, RESENAR_KUNDNUMMER = ? WHERE PK_RESENAR = ?";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                for (int i = 0; i < resenarList.size(); i++ )
                {
                    preparedStmt.setString(1, resenarList.get(i).RESENAR_CREATED);
                    preparedStmt.setString(2, resenarList.get(i).RESENAR_KUNDNUMMER);
                    preparedStmt.setString(3, resenarList.get(i).PK_RESENAR);
                    preparedStmt.executeUpdate();
                }

                break;
            case TBL_ARENDEN:
                query = "UPDATE soft_arenden SET PK_ARENDE = ?, FK_RESENAR = ?, FK_FORESKRIFT = ?, ARENDE_TYP = ?, ARENDE_STATUS = ?, ARENDE_BESLUT = ?, ARENDE_FOTO = ? WHERE FK_RESENAR = ?";
                PreparedStatement arendePreparedStmt = con.prepareStatement(query);
                for (int i = 0; i < resenarList.size(); i++ ) {
                    for (int j = 0; j < resenarList.get(i).arendeList.size(); j++){
                        arendePreparedStmt.setString(1, resenarList.get(i).arendeList.get(j).PK_ARENDE);
                        arendePreparedStmt.setString(2, resenarList.get(i).arendeList.get(j).FK_RESENAR);
                        arendePreparedStmt.setString(3, resenarList.get(i).arendeList.get(j).FK_FORESKRIFT);
                        arendePreparedStmt.setString(4, resenarList.get(i).arendeList.get(j).ARENDE_TYP);
                        arendePreparedStmt.setString(5, resenarList.get(i).arendeList.get(j).ARENDE_STATUS);
                        arendePreparedStmt.setString(6, resenarList.get(i).arendeList.get(j).ARENDE_BESLUT);
                        arendePreparedStmt.setString(7, resenarList.get(i).arendeList.get(j).ARENDE_FOTO);
                        arendePreparedStmt.setString(8, resenarList.get(i).arendeList.get(j).FK_RESENAR);
                        System.out.println("FK_FORESKRIFT ERROR #1");
                        arendePreparedStmt.executeUpdate();
                    }
                }
                break;
            case TBL_INFO:
                String infoQuery = "UPDATE soft_sit_info SET FK_RESENAR = ?, FK_ARENDE = ?, INFO_FTJ_TILLSTAND_ID = ?, INFO_FTJ_KORTNUMMER = ?, INFO_FTJ_KORT_ID = ?, INFO_IDEMIA_FILENAME = ? WHERE FK_RESENAR = ? AND FK_ARENDE = ?";
                PreparedStatement infoPreparedStmt = con.prepareStatement(infoQuery);
                for (int i = 0; i < resenarList.size(); i++ ) {
                    for (int j = 0; j < resenarList.get(i).arendeList.size(); j++){
                        infoPreparedStmt.setString(1, resenarList.get(i).arendeList.get(j).sitInformation.FK_RESENAR);
                        infoPreparedStmt.setString(2, resenarList.get(i).arendeList.get(j).sitInformation.FK_ARENDE);
                        infoPreparedStmt.setString(3, resenarList.get(i).arendeList.get(j).sitInformation.INFO_FTJ_TILLSTAND_ID);
                        infoPreparedStmt.setString(4, resenarList.get(i).arendeList.get(j).sitInformation.INFO_FTJ_KORTNUMMER);
                        infoPreparedStmt.setString(5, resenarList.get(i).arendeList.get(j).sitInformation.INFO_FTJ_KORT_ID);
                        infoPreparedStmt.setString(6, resenarList.get(i).arendeList.get(j).sitInformation.INFO_IDEMIA_FILENAME);
                        infoPreparedStmt.setString(7, resenarList.get(i).arendeList.get(j).sitInformation.FK_RESENAR);
                        infoPreparedStmt.setString(8, resenarList.get(i).arendeList.get(j).sitInformation.FK_ARENDE);
                        infoPreparedStmt.executeUpdate();
                    }
                }
                break;
            default:
                break;
        }
        // create the java mysql update preparedstatement

        con.close();
    }


    // Resenärsdatastruktur
    //
    // PK_RESENAR: Primary key table soft_resenar
    // RESENAR_KUNDNUMMER: Customer id for resenar
    //
    public class Resenar
    {
        public String PK_RESENAR;
        public String RESENAR_KUNDNUMMER;
        public String RESENAR_CREATED;
        public String RESENAR_OWNER;
        public String RESENAR_NOT_IN_PU;
        public String RESENAR_CREATE_FTJARENDE;
        public String RESENAR_CREATE_SJRARENDE;

        public List<FardtjanstArende> arendeList = new ArrayList<FardtjanstArende>();

        // Default constructor
        //
        public Resenar(String pk_pnr, String resenar_kundnummer, String resenar_create, String resenar_owner, String resenar_not_in_pu, String resenar_create_ftjarende,  String resenar_create_sjrarende)
        {
            this.PK_RESENAR = pk_pnr;
            this.RESENAR_KUNDNUMMER = resenar_kundnummer;
            this.RESENAR_CREATED = resenar_create;
            this.RESENAR_OWNER = resenar_owner;
            this.RESENAR_NOT_IN_PU = resenar_not_in_pu;
            this.RESENAR_CREATE_FTJARENDE = resenar_create_ftjarende;
            this.RESENAR_CREATE_SJRARENDE = resenar_create_sjrarende;
        }

        public void AddArende(String pk_arende, String fk_resenar, String fk_foreskrift, String arende_typ, String arende_status, String arende_beslut, String arende_foto) throws SQLException, ClassNotFoundException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Create Connection to DB
            Connection con = DriverManager.getConnection(dbUrl,username,password);

            this.arendeList.add(new FardtjanstArende(pk_arende,fk_resenar,fk_foreskrift,arende_typ, arende_status, arende_beslut, arende_foto));
            String query = "INSERT INTO soft_arenden (PK_ARENDE, FK_RESENAR, FK_FORESKRIFT, ARENDE_TYP, ARENDE_STATUS, ARENDE_BESLUT, ARENDE_FOTO) VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement arendePreparedStmt = con.prepareStatement(query);

            arendePreparedStmt.setString(1, pk_arende);
            arendePreparedStmt.setString(2, fk_resenar);
            arendePreparedStmt.setString(3, this.arendeList.get(0).FK_FORESKRIFT);
            arendePreparedStmt.setString(4, arende_typ);
            arendePreparedStmt.setString(5, arende_status);
            arendePreparedStmt.setString(6, arende_beslut);
            arendePreparedStmt.setString(7, arende_foto);
            System.out.println("FK_FORESKRIFT ERROR #2");
            arendePreparedStmt.executeUpdate();

            con.close();
        }
        // Method to get Ärenden for Resenär from database
        //
        public void GetArenden()
        {

        }
    }

    // Ärendedatastruktur
    //
    public class FardtjanstArende
    {
        public String PK_ARENDE;
        public String FK_RESENAR;
        public String FK_FORESKRIFT;
        public String ARENDE_TYP;
        public String ARENDE_STATUS;
        public String ARENDE_BESLUT;

        public String ARENDE_FOTO;

        public Foreskrift arendeForeskrift;
        public SitInformation sitInformation;

        public FardtjanstArende(String pk_arende, String fk_resenar, String fk_foreskrift, String arende_typ, String arende_status, String arende_beslut, String arende_foto) throws SQLException, ClassNotFoundException {
            this.PK_ARENDE = pk_arende;
            this.FK_RESENAR = fk_resenar;

            this.ARENDE_TYP = arende_typ;
            this.ARENDE_STATUS = arende_status;
            this.ARENDE_BESLUT = arende_beslut;
            this.ARENDE_FOTO = arende_foto;
            arendeForeskrift = new Foreskrift();
            this.FK_FORESKRIFT = arendeForeskrift.PK_FORESKRIFT;
            this.sitInformation = new SitInformation();
        }

        public FardtjanstArende() throws SQLException, ClassNotFoundException {
            arendeForeskrift = new Foreskrift();
            this.FK_FORESKRIFT = arendeForeskrift.PK_FORESKRIFT;
            this.ARENDE_BESLUT = "Beviljad";
        }

        public boolean GetTillstandData() throws SQLException, ClassNotFoundException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Create Connection to DB
            Connection con = DriverManager.getConnection(dbUrl,username,password);

            String query = "SELECT * FROM soft_sit_info WHERE FK_ARENDE = '" + this.PK_ARENDE + "' AND (INFO_FTJ_TILLSTAND_ID IS NOT NULL OR NOT INFO_FTJ_TILLSTAND_ID = '')";
            Statement stmt = con.createStatement();
            ResultSet rs= stmt.executeQuery(query);

            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
            }

            if (rowCount > 0) {
                con.close();
                return true;
            }
            else {
                con.close();
                return false;
            }
        }

        public boolean GetFardbevisData() throws SQLException, ClassNotFoundException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Create Connection to DB
            Connection con = DriverManager.getConnection(dbUrl,username,password);

            String query = "SELECT * FROM soft_sit_info WHERE FK_ARENDE = '" + this.PK_ARENDE + "' AND INFO_IDEMIA_FILENAME IS NOT NULL";
            Statement stmt = con.createStatement();
            ResultSet rs= stmt.executeQuery(query);

            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
            }

            if (rowCount > 0) {
                con.close();
                return true;
            }
            else {
                con.close();
                return false;
            }
        }

        public void AddInfo(String info_ftj_tillstand_id) throws SQLException, ClassNotFoundException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Create Connection to DB
            Connection con = DriverManager.getConnection(dbUrl,username,password);

            this.sitInformation.INFO_FTJ_TILLSTAND_ID = info_ftj_tillstand_id;
            String query = "INSERT INTO soft_sit_info (FK_RESENAR, FK_ARENDE, INFO_FTJ_TILLSTAND_ID) VALUES(?, ?, ?)";
            PreparedStatement arendePreparedStmt = con.prepareStatement(query);

            arendePreparedStmt.setString(1, this.FK_RESENAR);
            arendePreparedStmt.setString(2, this.PK_ARENDE);
            arendePreparedStmt.setString(3, info_ftj_tillstand_id);
            arendePreparedStmt.executeUpdate();
            con.close();
        }
    }

    public class Foreskrift
    {
        public String PK_FORESKRIFT;
        public String FORESKRIFT_FARDSATT;
        public String FORESKRIFT_ANTAL_LEDSAGARE;
        public String FORESKRIFT_ELMOPED;
        public String FORESKRIFT_ENSAMAKNING;
        public String FORESKRIFT_FRAMSATESPLACERING;
        public String FORESKRIFT_ALLERGIANPASSAT;
        public String FORESKRIFT_HUND;
        public String FORESKRIFT_LANSGRANSDISPENS;
        public String FORESKRIFT_BATTAXI;
        public String FORESKRIFT_BRYGGA_1;
        public String FORESKRIFT_BRYGGA_2;

        public Foreskrift() throws ClassNotFoundException, SQLException {

            Random rand = new Random(); //instance of random class

            Class.forName("com.mysql.cj.jdbc.Driver");
            //Create Connection to DB
            Connection con = DriverManager.getConnection(dbUrl,username,password);

            String query = "SELECT * FROM soft_foreskrift_typer WHERE PK_FORESKRIFT = " + rand.nextInt(105);

            //Create Statement Object
            Statement stmt = con.createStatement();

            // Execute the SQL Query. Store results in ResultSet
            ResultSet rs= stmt.executeQuery(query);

            // Add all Resenar to datastructure
            while (rs.next()){
                this.PK_FORESKRIFT = rs.getString(1);
                this.FORESKRIFT_FARDSATT= rs.getString(2);
                this.FORESKRIFT_ANTAL_LEDSAGARE= rs.getString(3);
                this.FORESKRIFT_ELMOPED= rs.getString(4);
                this.FORESKRIFT_ENSAMAKNING= rs.getString(5);
                this.FORESKRIFT_FRAMSATESPLACERING= rs.getString(6);
                this.FORESKRIFT_ALLERGIANPASSAT= rs.getString(7);
                this.FORESKRIFT_HUND= rs.getString(8);
                this.FORESKRIFT_LANSGRANSDISPENS= rs.getString(9);
                this.FORESKRIFT_BATTAXI= rs.getString(10);
                this.FORESKRIFT_BRYGGA_1= rs.getString(11);
                this.FORESKRIFT_BRYGGA_2= rs.getString(12);
            }
            con.close();
        }
    }

    public class SitInformation
    {
        public String FK_RESENAR;
        public String FK_ARENDE;
        public String INFO_FTJ_TILLSTAND_ID;
        public String INFO_FTJ_KORTNUMMER;
        public String INFO_FTJ_KORT_ID;
        public String INFO_IDEMIA_FILENAME;

        public SitInformation(String info_ftj_tillstand_id)
        {
            this.INFO_FTJ_TILLSTAND_ID = info_ftj_tillstand_id;
        }
        public SitInformation()
        {

        }


    }
}