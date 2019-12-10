package Classes;

import Beans.userBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;


public class dbUtils {

    private static String dbUrl = "jdbc:mysql://localhost:3306/sistemasanitario";
    private static String dbUser = "root";
    private static String dbPassword = "";

    /**
     *
     * @param codiceFiscale codice fiscale dell'utente
     * @param password password da controllare
     * @return 1 se codice fiscale e password coincidono, 0 altrimenti, -2 se non ci si riesce a connettere con il db
     */
    public static int checkUser(String codiceFiscale, String password) {
        int correct = 1;
        Statement statement;
        String query = "select * from utenti where codiceFiscale = '" + codiceFiscale + "' and password = '" + password + "'";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            if( !result.next() ) {
                correct = 0;
            }
            statement.close();
            connection.close();
        } catch (Exception ex) {
            correct = -2;
        }
        return correct;
    }




    /**
     *
     * @param codiceFiscale codice fiscale dell'utente di cui si vogliono i dati
     * @param user userBean dell'utente da "riempire"
     * @return status, -1 se non esiste il codice fiscale nel db, 0 se va tutto bene, -2 se non ci si riesce a connettere al db
     */
    public static int getUserData(String codiceFiscale, userBean user) {
        int status = 0;
        Statement statement;
        String query = "select * from " +
                       "utenti " +
                       "where utenti.codiceFiscale = '" + codiceFiscale + "'";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            if( !result.next() ) {
                status = -1;
            } else {
                //riempio l'userBean
                user.setCodiceFiscale(result.getString("codiceFiscale"));
                user.setNome(result.getString("nome"));
                user.setCognome(result.getString("cognome"));
                user.setDataNascita(result.getDate("dataNascita"));
                user.setLuogoNascita(result.getString("luogoNascita"));
                user.setFoto(result.getString("foto"));
                user.setSesso(result.getInt("sesso"));
                user.setMedicoDiBase(result.getInt("codiceMedicoDiBase"));
                user.setEmail(result.getString("email"));
                user.setProvincia(result.getString("provincia"));
                int codiceMedico = getCodiceMedico(codiceFiscale);
                if ( codiceMedico != -1 ) {
                    user.setIsDoctor(true);
                    user.setCodiceMedico(codiceMedico);
                }
                else {
                    user.setIsDoctor(false);
                    user.setCodiceMedico(-1);
                }

                statement.close();
                connection.close();
            }
        } catch (Exception ex) {
            status = -2;
        }
        return status;
    }



    /**
     *
     * @param codiceMedico codice medico del medico di cui si vogliono i dati
     * @param doctor userBean del medico da "riempire"
     * @return status, -1 se non esiste il codice medico nel db, 0 se va tutto bene, -2 se non ci si riesce a connettere al db
     */
    public static int getDoctorData(int codiceMedico, userBean doctor) {
        //prendo il codice fiscale del medico con questo codice medico
        int status = 0;
        Statement statement;
        String query = "select utenti.codiceFiscale from " +
                       "utenti, medicidibase " +
                       "where utenti.codiceFiscale = medicidibase.codiceFiscale and medicidibase.codiceMedico = '" + codiceMedico + "'";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            if( !result.next() ) {
                status = -1;
            } else {
                getUserData(result.getString("codiceFiscale"), doctor);

                statement.close();
                connection.close();
            }
        } catch (Exception ex) {
            status = -2;
        }
        return status;
    }



    /**
     *
     * @param codiceFiscale codice fiscale
     * @return restituisce il codiceMedico se è un medico, -1 altrimenti, -2 se non ci si riesce a connettere al db
     */
    public static int getCodiceMedico(String codiceFiscale) {
        int codiceMedico = -1;
        Statement statement;
        String query = "select codiceMedico from medicidibase where codiceFiscale = '" + codiceFiscale + "'";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            if( result.next() ) {
                codiceMedico = result.getInt("codiceMedico");
            }
            statement.close();
            connection.close();
        } catch (Exception ex) {
            codiceMedico = -2;
        }
        return codiceMedico;
    }



    /**
     *
     * @param provincia provincia
     * @param listaMedici lista medici dove andranno inseriti i medici della provincia
     * @return restituisce 0 se va tutto bene, -2 se non ci si riesce a connettere al db
     */
    public static int getListaMediciPerProfincia(String provincia, List<Integer> listaMedici) {
        int status = -1;
        Statement statement;
        String query = "select codiceMedico " +
                       "from medicidibase, utenti " +
                       "where medicidibase.codiceFiscale = utenti.codiceFiscale and provincia = '" + provincia + "'";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            while ( result.next() ) {
                listaMedici.add(result.getInt("codiceMedico"));
            }
            
            statement.close();
            connection.close();
        } catch (Exception ex) {
            status = -2;
        }
        return status;
    }



    /**
     *
     * @param codiceFiscale codice fiscale dell'utente
     * @param codiceMedico codice del nuovo medico di base
     * @return restituisce 0 se va tutto bene, -2 se non ci si riesce a connettere al db
     */
    public static int setMedicoDiBase(String codiceFiscale, int codiceMedico) {
        int status = 0;
        Statement statement;
        String query = "update utenti " +
                       "set codiceMedicoDiBase = " + codiceMedico + " " +
                       "where codiceFiscale = '" + codiceFiscale + "'";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.commit();

            statement.close();
            connection.close();
        } catch (Exception ex) {
            status = -2;
        }
        return status;
    }



    /**
     *
     * @param codiceFiscale codice fiscale dell'utente da aggiornare
     * @param nuovaFoto nome della nuova immagine di profilo
     * @return restituisce il nome della vecchia immagine profilo, "-1" se non esiste il codice fiscale nel db, "-2" se non ci si riesce a connettere al db
     */
    public static String setNewPhoto(String codiceFiscale, String nuovaFoto) {
        String status = "";
        Statement statement;

        String query = "select foto " +
                       "from utenti " +
                       "where codiceFiscale = '" + codiceFiscale + "'";
        try {
            //prendo il nome vecchio
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            if ( result.next() ) {
                status = result.getString("foto");
                statement.close();
                connection.close();

                //aggiorno con quello nuovo
                connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                statement = connection.createStatement();
                query = "update utenti " +
                        "set foto = '" + nuovaFoto + "' " +
                        "where codiceFiscale = '" + codiceFiscale + "'";
                statement.executeUpdate(query);

                statement.close();
                connection.close();
            } else {
                status = "-1";
            }
        } catch (Exception ex) {
            status = "-2";
        }
        return status;
    }
}
