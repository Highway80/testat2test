package de.hrw.swep.biblio.service.gegenstaende;

import de.hrw.swep.biblio.service.IllegalStateTransition;
import de.hrw.swep.biblio.service.benutzer.Benutzer;

public class Verloren implements Ausleihstatus {
	Gegenstand g;

	public Verloren(Gegenstand g) {
		this.g = g;
	}

	@Override
	public void ausleihen(Benutzer user) {
		throw new IllegalStateTransition();

	}

	@Override
	public void zurueckgeben() {
		g.setState(new Frei(g));
		// Annahme Verlorens Buch kann wiedergefunden werden

	}

	@Override
	public void verloren() {
		throw new IllegalStateTransition();

	}

}
