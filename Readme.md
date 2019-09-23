# Bundesliga

## Inhalt des Pakets
Das Projekt bietet das Java-Paket `mfedevelopement.bundesliga`, das Daten von [OpenLigaDB](https://www.openligadb.de/) empfängt und in Klassen und Listen sortiert. Dabei werden die aktuellen Daten der Bundesliga-Tabelle sowie die aktuellen Begegnungen empfangen und verarbeitet.

Das Paket enthält folgende Klassen
- `Bundesliga` (Hautpklasse, über die die Funktionsaufrufe gesteuert werden)
- `FootballTeam` (Klasse zum Speichern der Daten eines Teams)
- `Match` (Klasse, die zwei Teams und Informationen zu einem Fußballspiel enthält)
- `MatchGoal` (Klasse zum Speichern der Daten zu einem erzielten Tor)
- `OpenLigaDbParser` (Klasse zum Senden der Requests an [OpenLigaDB](https://www.openligadb.de/) und Verarbeiten der Responses)

## Verwendung
Um die aktuellen Daten bzgl. Tabelle und Spiele der Bundesliga zu erhalten, muss zuerst ein neues Objekt vom Typ `Bundesliga` erzeugt werden
```
Bundesliga bundesliga = new Bundesliga();
```
Mit der Erstellung dieses Objekts wird automatisch ein Request gesendet, um die Daten der aktuellen Bundesliga-Tabelle zu erhalten.

### Bundesliga Tabelle
Die Tabelle kann mit folgendem Befehl in einer Liste mit Objekten vom Typ `FootballTeam` gespeichert werden 
```
List<FootballTeam> bundesligaTabelle = bundesliga.getTable();
```

#### Team an bestimmter Position asugeben
Wenn nur ein Team an einer bestimmten Position der Tabelle ausgegeben werden soll, dann kann der Befehl `getTablePos(int position)` verwendet werden. Beispielsweise gibt folgender Funktionsaufruf das Team an der Tabellenposition 18 zurück. 
```
FootballTeam lastTeam = bundesliga.getTablePos(18);
```
Achtung: Der Zählindex für diese Funktion beginnt bei 1! Ein Team an der Position 0 gibt es nicht!
Wenn ein Team an einer Position außerhalb der Tabelle zurückgegeben werden soll, dann wirft die Funktion eine `IndexOutOfBoundsException`. 

#### Anzahl der Teams 
Die Anzahl, wie viele Teams in einer Tabelle gelistet sind, gibt die Funktion `getTableSize()` zurück.
```
int tableSize = bundesliga.getTableSize()
``` 

#### Tabelle updaten
Um die bereits geladene Tabelle zu updaten und damit den Request inkl. Datenverarbeitung neu auszuführen, wird die Funktion `updateTable()` des Objekts vom Typ `Bundesliga` aufgerufen.


### Aktuelle Spiele
Um die aktuellen Begegnungen zu erhalten, wird die Funktion `getMatches()` des Objekts vom Typ `Bundesliga` aufgerufen.
```
List<Match> currentMatches = bundesliga.getMatches();
```
Die zurückgegebenen Spiele gelten dabei für den "aktuellen" Spieltag, wobei der "aktuelle" Spieltag bei OpenLigaDB wechselt, wenn die Hälfte der Zeit zwischen zwei Spieltagen vergangen ist.

Für eine Ausführliche Beschreibung sei an dieser Stelle auf die [GitHub-Seite](https://github.com/OpenLigaDB/OpenLigaDB-Samples) von OpenLigaDB verwiesen, die vielen Beispiele zeigt.

#### Aktuelle Spiele updaten
Um die bereits geladenen Spiele inkl. eventuell verfügbarer Torschützen zu updaten wird die Funktion `updateMatches()` des Objekts vom Typ `Bundesliga` aufgerufen.

### Ausgabe in der Konsole
Zur Ausgabe der Tabelle oder der aktuellen Spiele in der Konsole stehen , die Befehle `printTable(List<FootballTeam>)` und `printMatches(List<Match>)` zur Verfügung. Diese können wie in folgendem Beispiel aufgerufen werden:
```
bundesliga.printTable(bundesliga.getTable());
bundesliga.printMatches(bundesliga.getMatches());
```

Die Ausgabe in der Konsole mithilfe des Befehls `printTable()` sieht dann beispielhaft folgendermaßen aus, wobei nur die ersten vier Teams dargestellt sind
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
Wenn die Spiele bereits stattgefunden haben oder noch laufen, dann werden die Torschützen der gefallenen Tore mit Namen und der Spielminute, in der das Tor gefallen ist, ausgegeben.
Wennd die Spiele hingegen erst noch stattfinden, dann werden nur Datum und Uhrzeit des Anpfiffs sowie die beiden Teams ausgegeben.

### OpenLigaDbParser
Um das Objekt vom Klasse `OpenLigaDbParser`, das vom Objekt der Klasse `Bundesliga` verwendet wird, zu erhalten, wird die Funktion `getOpenLigaDbParser()` aufgerufen. Damit können die Methoden der Klasse `OpenLigaDbParser` direkt aufgerufen werden.
```
OpenLigaDbParser parser = bundesliga.getOpenLigaDbParser()
```

## Probleme und Weiterentwicklungen
Wenn Sie Wünsche und Anregungen zu Korrekturen, Verbesserungen und Weiterentwicklungen haben, dann Erstellen Sie bitte einen neuen [Issue](https://github.com/mjferstl/Bundesliga/issues), wenn noch kein Gleichartiger vorhanden ist.