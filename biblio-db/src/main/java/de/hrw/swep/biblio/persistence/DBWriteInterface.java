package de.hrw.swep.biblio.persistence;

/**
 * Schnittstelle zum schreibenden Datenbankzugriff
 * 
 * @author A. Kreuzer
 *
 */
public interface DBWriteInterface {

  /**
   * Fügt einen neuen Benutzer hinzu
   * @param name Der Name des Benutzers
   * @param status Der Status des Benutzers
   * @throws Exception 
   */
  void addBenutzer(String name, String status) throws Exception;

  /**
   * Fügt einen neues Buch hinzu
   * @param author Der Name des Authors, der das Buch verfasst hat
   * @param titel Der Titel des Buches
   * @param status Der Status des Buches
   * @throws Exception
   */
  void addBuch(String author, String titel, String status) throws Exception;

}
