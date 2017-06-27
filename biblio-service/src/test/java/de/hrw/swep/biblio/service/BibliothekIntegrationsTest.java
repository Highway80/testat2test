package de.hrw.swep.biblio.service;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.*;

import de.hrw.swep.biblio.persistence.*;
import de.hrw.swep.biblio.service.benutzer.*;
import de.hrw.swep.biblio.service.gegenstaende.Buch;

/**
 * Testet die Bibliotheksklasse mit der echten Datenbank.
 * 
 * @author M. Friedrich
 *
 */
public class BibliothekIntegrationsTest {
	Bibliothek bib;
	DBReadInterface dri;

	@Before
	public void setup() {
		dri = new DAO();
		bib = new Bibliothek();

		bib.setDb(new DAO());
	}

	/**
	 * Testet, ob ein Buch fuer einen gegebenen Titel gefunden wird.
	 */
	@Test
	public void testSucheBuchNachTitel() {
		Set<Buch> buchSet = bib.sucheBuchNachTitel("Lost and Found");

		assertEquals(1, buchSet.size());
		for (Buch buch : buchSet) {
			assertEquals("Karl Kaos", buch.getAutor());
			assertTrue(buch.getState() instanceof Gesperrt);
		}
	}

	/**
	 * Testet, ob ein Buch fuer einen gegebenen Autor gefunden wird.
	 */
	@Test
	public void testSucheBuchNachAutor() {
		Set<Buch> buchSet = bib.sucheBuchNachAutor("Karl Kaos");
		assertEquals(1, buchSet.size());
		for (Buch buch : buchSet) {
			assertEquals("Karl Kaos", buch.getAutor());
			assertEquals("Lost and Found", buch.getTitel());
			assertTrue(buch.getState() instanceof Gesperrt);
		}
	}

	/**
	 * Testet, ob ein Benutzer mit einem gegebenen Namen gefunden wird.
	 */
	@Test
	public void testSucheBenutzerNachName() {

		Set<Benutzer> userSet = bib.sucheBenutzerNachName("Charly Corleone");

		assertEquals(1, userSet.size());
	}

	/**
	 * Testet, ob ein Benutzer mit einer gegebenen ID gefunden wird.
	 */
	@Test
	public void testSucheBenutzerNachId() {

		Benutzer user1 = bib.sucheBenutzerNachId(2);
		assertEquals("Berta Brettschneider", user1.getName());
		assertEquals(true, user1.isGesperrt());
	}

}
