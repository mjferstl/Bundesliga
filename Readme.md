# Bundesliga

## Inhalt des Pakets
Das Projekt bietet das Java-Paket `mfdevelopement.bundesliga`, das Daten von [OpenLigaDB](https://www.openligadb.de/) empfängt und in Klassen und Listen sortiert. Dabei werden die aktuellen Daten der Bundesliga-Tabelle sowie die aktuellen Begegnungen empfangen und verarbeitet.

Das Paket referenziert auf `org.json`, das im JAR-File [`json_20190722.jar`](https://repo1.maven.org/maven2/org/json/json/20190722/json-20190722.jar), enthalten ist. Weitere Informationen enthält das [GitHub-Repository](https://github.com/stleary/JSON-java) von [stleary](https://github.com/stleary).

Das Paket enthält folgende Klassen
- `Bundesliga` (Hautpklasse, über die die Funktionsaufrufe gesteuert werden)
- `FootballTeam` (Klasse zum Speichern der Daten eines Teams)
- `Match` (Klasse, die zwei Teams und Informationen zu einem Fußballspiel enthält)
- `MatchGoal` (Klasse zum Speichern der Daten zu einem erzielten Tor)
- `OpenLigaDbParser` (Klasse zum Senden der Requests an [OpenLigaDB](https://www.openligadb.de/) und Verarbeiten der Responses)

## Verwendung
Die Datei [`BundesligaDemo.java`](https://github.com/mjferstl/Bundesliga/blob/master/src/mfdevelopement/bundesliga/BundesligaDemo.java) zeigt einige Beispiele zur Verwendung der Bundesliga-Klasse. Einige dieser Beispiele sind im folgenden näher beschrieben.

Um die aktuelle Tabelle und sowie die Spiele der Bundesliga zu erhalten, wird zuerst ein neues Objekt vom Typ `Bundesliga` erzeugt
```
Bundesliga bundesliga = new Bundesliga();
```
Mit der Erstellung dieses Objekts wird automatisch ein Request gesendet, um die Daten der aktuellen Bundesliga-Tabelle zu laden.

### Bundesliga Tabelle
Die Tabelle kann mit folgendem Befehl ausgegeben werden, wobei die Funktion `getTable()` eine Liste mit Objekten der Klasse `FootballTeam` zurückgibt 
```
List<FootballTeam> bundesligaTabelle = bundesliga.getTable();
```

#### Team an bestimmter Position asugeben
Wenn nur ein Team an einer bestimmten Position der Tabelle ausgegeben werden soll, dann kann der Befehl `getTablePos(int position)` der Klasse `Bundesliga` verwendet werden. Beispielsweise gibt folgender Funktionsaufruf das Team an der Tabellenposition 18 zurück. 
```
FootballTeam lastTeam = bundesliga.getTablePos(18);
```
Achtung: Der Zählindex für diese Funktion beginnt bei 1! Ein Team an der Position 0 gibt es nicht!
Wenn ein Team an einer Position außerhalb der Tabelle zurückgegeben werden soll, dann wirft die Funktion eine `IndexOutOfBoundsException`. 

#### Anzahl der Teams 
Die Anzahl, wie viele Teams in der Bundesliga-Tabelle gelistet sind, gibt die Funktion `getTableSize()` zurück.
```
int tableSize = bundesliga.getTableSize()
``` 

#### Tabelle updaten
Um die bereits geladenen Tabelle zu updaten und damit den Request sowie die Datenverarbeitung neu auszuführen, wird die Funktion `updateTable()` des Objekts der Klasse `Bundesliga` aufgerufen.


### Aktuelle Spiele
Um die aktuellen Begegnungen zu erhalten, wird die Funktion `getMatches()` des Objekts der Klasse `Bundesliga` aufgerufen.
```
List<Match> currentMatches = bundesliga.getMatches();
```
Die zurückgegebenen Spiele gelten dabei für den "aktuellen" Spieltag, wobei der "aktuelle" Spieltag bei OpenLigaDB wechselt, wenn die Hälfte der Zeit zwischen zwei Spieltagen vergangen ist.

Für eine ausführliche Beschreibung sei an dieser Stelle auf die [GitHub-Seite](https://github.com/OpenLigaDB/OpenLigaDB-Samples) von OpenLigaDB verwiesen, die Informationen hierzu enthält.

#### Aktuelle Spiele updaten
Um die bereits geladenen Spiele sowie eventuell verfügbare Torschützen zu updaten wird die Funktion `updateMatches()` des Objekts der Klasse `Bundesliga` aufgerufen. Dies Funktion aktualisert die Daten des Objekts.

### Ausgabe in der Konsole
Zur Ausgabe der Tabelle oder der aktuellen Spiele in der Konsole stehen die Befehle `printTable(List<FootballTeam>)` und `printMatches(List<Match>)` zur Verfügung. Diese können wie in folgendem Beispiel vom Objekt der Klasse `Bundesliga` aufgerufen werden.
```
bundesliga.printTable(bundesliga.getTable());
bundesliga.printMatches(bundesliga.getMatches());
```

Die Ausgabe in der Konsole mithilfe des Befehls `printTable()` ist nachfolgend beispielhaft dargestellt, wobei der Ausschnitt nur die ersten vier Teams zeigt.
```
    | Mannschaft                          | Sp | Pkt |  TD |  S |  U |  N | Tor
--- | ----------------------------------- | -- | --- | --- | -- | -- | -- | ---
  1 | RB Leipzig                          |  5 |  13 |  10 |  4 |  1 |  0 |  13
  2 | FC Bayern                           |  5 |  11 |  12 |  3 |  2 |  0 |  16
  3 | Borussia Dortmund                   |  5 |  10 |   8 |  3 |  1 |  1 |  15
  4 | SC Freiburg                         |  5 |  10 |   7 |  3 |  1 |  1 |  11
```

Die zu erwartende Ausgabe mit dem Befehl `printMatches()`:
```
Spiel: 2019-09-20 20:30 FC Schalke 04 vs. 1. FSV Mainz 05       	  2-1	1-0 Serdar 36., 1-1 Karim  Onisiwo 74., 2-1 Harit 89.
Spiel: 2019-09-21 15:30 FC Bayern vs. 1. FC Köln                	  4-0	1-0 Lewandowski 3., 2-0 Lewandowski 48., 3-0 Coutinho(EM) 62., 4-0 Perisic, Ivan 73.
Spiel: 2019-09-21 15:30 Bayer Leverkusen vs. 1. FC Union Berlin 	  2-0	1-0 Kevin Volland 20., 2-0 Alario 25.
```
Wenn die Spiele bereits stattgefunden haben oder begonnen, aber noch nicht beendet sind, dann werden die Torschützen der Tore mit Namen und der Spielminute, in der das Tor gefallen ist, ausgegeben.
Wenn die Spiele hingegen erst noch stattfinden, dann werden nur Datum und Uhrzeit des Anpfiffs sowie die beiden aufeinandertreffenden Teams ausgegeben.

### OpenLigaDbParser
Um das Objekt der Klasse `OpenLigaDbParser`, das vom Objekt der Klasse `Bundesliga` verwendet wird, zu erhalten, wird die Funktion `getOpenLigaDbParser()` aufgerufen. Damit können die Methoden der Klasse `OpenLigaDbParser` direkt aufgerufen werden.
```
OpenLigaDbParser parser = bundesliga.getOpenLigaDbParser()
```

## Probleme und Weiterentwicklungen
Wenn Sie Wünsche und Anregungen zu Korrekturen, Verbesserungen und Weiterentwicklungen haben, dann Erstellen Sie bitte einen neuen [Issue](https://github.com/mjferstl/Bundesliga/issues), wenn noch kein Gleichartiger vorhanden ist.
