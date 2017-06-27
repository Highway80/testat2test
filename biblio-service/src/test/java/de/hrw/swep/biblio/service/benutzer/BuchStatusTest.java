package de.hrw.swep.biblio.service.benutzer;

import static org.junit.Assert.*;

import org.junit.*;

import de.hrw.swep.biblio.service.IllegalStateTransition;
import de.hrw.swep.biblio.service.gegenstaende.*;

public class BuchStatusTest {
	Buch buch;
	Benutzer user;

	@Before
	public void setUp() throws Exception {

		buch = new Buch("Testbuch", "Ghostwriter");
		buch.setState(new Frei(buch));
		user = new Benutzer(0, "Tester");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFreiesBuchAusleihen() {
		buch.ausleihen(user);
		assertTrue(buch.getState() instanceof Ausgeliehen);

	}

	@Test
	public void testBuchVonGesperrtemUserAusleihen() {
		user.setStatus(new Gesperrt(user));
		buch.ausleihen(user);
		assertTrue(buch.getState() instanceof Frei);
	}

	@Test(expected = IllegalStateTransition.class)
	public void testAusgeliehenesBuchAusleihen() {
		for (int i = 0; i < 2; i++)
			buch.ausleihen(user);

	}

	@Test(expected = IllegalStateTransition.class)
	public void testVerlorenesBuchAusleihen() {
		buch.verloren();
		buch.ausleihen(user);

	}

	@Test
	public void testVerlorenesBuchAusleihen2() {
		buch.verloren();

		try {
			buch.ausleihen(user);

		} catch (IllegalStateTransition e) {
			return;

		}
		fail();

	}

	@Test
	public void testAusgeliehenesBuchZurueckgeben() {
		buch.ausleihen(user);
		assertTrue(buch.getState() instanceof Ausgeliehen);
		buch.zurueckgeben();
		assertTrue(buch.getState() instanceof Frei);

	}

}
