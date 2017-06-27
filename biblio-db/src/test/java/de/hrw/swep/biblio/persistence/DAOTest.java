package de.hrw.swep.biblio.persistence;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.sql.SQLException;
import java.util.Set;

import org.dbunit.*;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.*;

import de.hrw.swep.biblio.persistence.dto.*;

/**
 * Testklasse fuer den Datenbankzugriff
 * 
 * @author M. Friedrich
 *
 */
public class DAOTest {

  private IDatabaseTester databaseTester;
  private DAO dao;

  /**
   * Bringt die Datenbank in einen definierten Ausgangszustand
   * 
   * @throws Exception
   */
  @Before
  public void setup() throws Exception {
    dao = new DAO();
    databaseTester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver",
        "jdbc:hsqldb:file:../biblio-db/database/bibdb", "sa", "");
    databaseTester.setDataSet(new FlatXmlDataSetBuilder().build(new File("full.xml")));
    databaseTester.onSetup();
  }

  /**
   * Testet das Abrufen eines bestimmten Nutzers nach der Nutzer-ID
   */
  @Test
  public void testGetUserById() {
    BenutzerDTO b = dao.getBenutzerById(1);
    assertEquals("Adalbert Alt", b.getName());
    assertEquals("Normal", b.getStatus());
  }

  /**
   * Testet das Abrufen eines Benutzers mit einem gegebenen Namen.
   */
  @Test
  public void testGetBenutzerByName() {
    Set<BenutzerDTO> bSet = dao.getBenutzerByName("Berta Brettschneider");
    assertEquals(1, bSet.size());
    for (BenutzerDTO b : bSet) {
      assertEquals(2, b.getId());
      assertEquals("Gesperrt", b.getStatus());

    }

  }

  /**
   * Testet das Abrufen aller Buecher eines Autors
   */
  @Test
  public void testGetBuchByAutor() {
    Set<BuchDTO> buchSet = dao.getBuchByAutor("Malte Mohn");
    assertEquals(1, buchSet.size());
    for (BuchDTO buch : buchSet) {
      assertEquals("Klatsch", buch.getTitel());
      assertEquals("Ausgeliehen", buch.getStatus());
      assertEquals(3, buch.getId());

    }

  }

  /**
   * Testet das Abrufen eines Buches nach dem Titel.
   */
  @Test
  public void testGetBuchByTitle() {
    Set<BuchDTO> buchSet = dao.getBuchByTitle("Lost and Found");
    assertEquals(1, buchSet.size());

    for (BuchDTO buch : buchSet) {
      assertEquals("Karl Kaos", buch.getAutor());
      assertEquals("Verloren", buch.getStatus());
      assertEquals(1, buch.getId());
    }
  }

  /**
   * Testet ob ein neuer Benutzer in der Datenbank angelegt werden kann
   * 
   * @throws SQLException
   * 
   * @throws Exception
   */
  @Test
  public void testAddBenutzer() throws SQLException, Exception {

    dao.addBenutzer("Testuser", "Normal");
    IDataSet expected = new FlatXmlDataSetBuilder().build(new File("userAdded.xml"));
    IDataSet actual = databaseTester.getConnection().createDataSet();
    Assertion.assertEquals(expected, actual);
  }

  /**
   * Testet ob ein neues Buch angelegt werden kann
   * 
   * @throws Exception
   * @throws SQLException
   */
  @Test
  public void testAddBuch() throws SQLException, Exception {
    dao.addBuch("Ghostwriter", "working title", "Verloren");
    IDataSet expected = new FlatXmlDataSetBuilder().build(new File("bookAdded.xml"));
    IDataSet actual = databaseTester.getConnection().createDataSet();
    Assertion.assertEquals(expected, actual);
  }

}
