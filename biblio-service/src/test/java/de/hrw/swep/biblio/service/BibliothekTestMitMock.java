package de.hrw.swep.biblio.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.*;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;

import de.hrw.swep.biblio.persistence.DBReadInterface;
import de.hrw.swep.biblio.persistence.dto.*;
import de.hrw.swep.biblio.service.benutzer.Benutzer;
import de.hrw.swep.biblio.service.gegenstaende.*;

/**
 * Testet die Bibliotheks-Klasse mit einem Mock-Objekt
 * 
 * @author Sandra
 *
 */
public class BibliothekTestMitMock {

	Bibliothek bib;
	DBReadInterface dbi;

	@Before
	public void setup() {

		dbi = Mockito.mock(DBReadInterface.class);

		Set<BuchDTO> buchDTOSet = new HashSet<BuchDTO>();

		buchDTOSet.add(new BuchDTO(1, "Ghostwriter", "Titel", "Frei"));
		when(dbi.getBuchByAutor("Ghostwriter")).thenReturn(buchDTOSet);
		when(dbi.getBuchByTitle("Testbuch"))
				.thenReturn(Sets.newSet(new BuchDTO(0, "Ghostwriter", "Testbuch", "Verloren")));
		when(dbi.getBenutzerByName("Superstar")).thenReturn(
				Sets.newSet(new BenutzerDTO(77, "Superstar", "Normal"), new BenutzerDTO(78, "Superstar", "Gesperrt")));
		when(dbi.getBenutzerById(77)).thenReturn(new BenutzerDTO(77, "Superstar", "Normal"));
		when(dbi.getBenutzerById(78)).thenReturn(new BenutzerDTO(78, "Superstar", "Gesperrt"));
		when(dbi.getBenutzerById(99)).thenReturn(new BenutzerDTO(99, "Ninetynine", "Normal"));

		bib = new Bibliothek();
		bib.setDb(dbi);

	}

	/**
	 * Testet, ob ein Buch mit gegebenem Titel gefunden wird.
	 */
	@Test
	public void testSucheBuchNachTitel() {
		Set<Buch> buchSet = bib.sucheBuchNachTitel("Testbuch");

		assertEquals(1, buchSet.size());
		for (Buch buch : buchSet) {
			assertEquals("Ghostwriter", buch.getAutor());
			assertTrue(buch.getState() instanceof Verloren);
		}

	}

	/**
	 * Testet, ob ein Buch mit gegebenem Autor gefunden wird.
	 */
	@Test
	public void testSucheBuchNachAutor() {
		Set<Buch> buchSet = bib.sucheBuchNachAutor("Ghostwriter");
		assertEquals(1, buchSet.size());
		for (Buch buch : buchSet) {
			assertEquals("Ghostwriter", buch.getAutor());
			assertTrue(buch.getState() instanceof Frei);
		}
	}

	/**
	 * Testet, ob ein Benutzer mit gegebenem Namen gefunden wird.
	 */
	@Test
	public void testSucheBenutzerNachName() {

		Set<Benutzer> userSet = bib.sucheBenutzerNachName("Superstar");
		assertEquals(2, userSet.size());

		for (Benutzer user : userSet) {

			if (user.getName().equals("Superstar")) {
				if (user.getId() == 77 && user.isNormal())
					continue;
				if (user.getId() == 78 && user.isGesperrt())
					continue;
			}
			fail("User nicht gefunden");

		}

	}

	/**
	 * Testet, ob ein Benutzer mit einer gegebenen ID gefunden wird.
	 */
	@Test
	public void testSucheBenutzerNachId() {

		Benutzer user = bib.sucheBenutzerNachId(99);
		assertEquals(99, user.getId());
		assertEquals("Ninetynine", user.getName());
		assertTrue(user.isNormal());

	}

}
