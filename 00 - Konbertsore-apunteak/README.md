# Proiektua: konbertsore-apunteak
## Laburpena
Errepositorio hau Java-n egindako kontsola-aplikazio txiki bat da, fitxategiak (.TXT, .XML, .JSON, .CSV) kudeatzeko/sortzeko, balidazio oinarrizkoekin eta menu interaktibo batekin.

## Karpeten eta fitxategien egitura (laburpena)
- [.vscode/settings.json](.vscode/settings.json) — VSCode-rako Java proiektuaren konfigurazioa (iturburu-bideak, bin irteera eta erreferentziatutako liburutegiak).

- src/ (Java iturburu-kodea)
  - [src/main/App.java](src/main/App.java) — Klase nagusia eta menu nagusia. Fitxategi motaren arabera azpimenuak exekutatzen ditu. Erreferentzia:[`main.App`](src/main/App.java).
  - [src/main/error/ErroreenKudeaketa.java](src/main/error/ErroreenKudeaketa.java) — Fitxategiak egiaztatzeko/sortzeko/irakurtzeko/idazteko/ezabatzeko utilitateak eta NAN existitzen den egiaztatzeko funtzioak CSV/XML-n. Erreferentzia: [`main.error.ErroreenKudeaketa`](src/main/error/ErroreenKudeaketa.java).
  - [src/main/io/KudeatzaileaTXT.java](src/main/io/KudeatzaileaTXT.java) — .TXT-rako menu eta eragiketak. Erreferentzia: [`main.io.KudeatzaileaTXT`](src/main/io/KudeatzaileaTXT.java).
  - [src/main/io/KudeatzaileaXML.java](src/main/io/KudeatzaileaXML.java) — .XML-rako menu eta eragiketak. Erreferentzia: [`main.io.KudeatzaileaXML`](src/main/io/KudeatzaileaXML.java).
  - [src/main/io/KudeatzaileaJSON.java](src/main/io/KudeatzaileaJSON.java) — .JSON-rako menu eta eragiketak. Erreferentzia: [`main.io.KudeatzaileaJSON`](src/main/io/KudeatzaileaJSON.java).
  - [src/main/io/KudeatzaileaCSV.java](src/main/io/KudeatzaileaCSV.java) — .CSV-rako menu eta eragiketak. Erreferentzia: [`main.io.KudeatzaileaCSV`](src/main/io/KudeatzaileaCSV.java).
  - [src/main/model/Ikaslea.java](src/main/model/Ikaslea.java) — Ikaslearen datu-eredua (NAN, izena, abizenak, adina, helbidea) eraikitzailean balidazioekin. Erreferentzia [`main.model.Ikaslea`](src/main/model/Ikaslea.java).
  - [src/main/utils/Filtroak.java](src/main/utils/Filtroak.java) — Balidazio funtzio berrerabilgarriak (isnumeric, isDNI, isIzena, isAdina, isHelbidea, eta abar). Erreferentzia: [`main.utils.Filtroak`](src/main/utils/Filtroak.java).
  - [src/main/utils/Gehigarriak.java](src/main/utils/Gehigarriak.java) — Kontsola utilitateak (scanner partekatua, koloreak, pantaila garbitu, pausa). Erreferentzia: [`main.utils.Gehigarriak`](src/main/utils/Gehigarriak.java).

- resources/ eta src/resources/ (txantiloiak/adibideak)
  - resources/csv/, resources/json/, resources/txt/, resources/xml/ — sarrerako/irteerako fitxategiak gordetzeko karpetak. (Ez dago fitxategi zehatzik lib karpetan.)

- bin/ (konpilatutako irteera)
  - Konpilatutako klaseentzako egitura (bin/main/...). VSCode/Java-k hemen kokatzen ditu .class fitxategiak.

- lib/ (mendekotasunak)
  - Kanpoko jar fitxategientzako karpeta (une honetan hutsik dago erantsitako errepositorioan).

## Qué hace cada parte (resumen funcional)
- **Menuak (.TXT/.XML/.JSON/.CSV):** [src/main/io](src/main/io) karpetako kudeatzaileek aukera hauek eskaintzen dituzte (sortu, zerrendatu, irakurri, gehitu, eguneratu, ezabatu, bihurtu). Uneko kodean eragiketa zehatzak *TODO* gisa markatuta daude edo mezu gisa inprimatuta; menuen egitura prest dago logika zehatza inplementatzeko.
- Balidazioak: [`main.utils.Filtroak`](src/main/utils/Filtroak.java) formatuak balidatzen ditu (DNI/NAN, izenak, adinak, helbideak).

- Eredua: [`main.model.Ikaslea`](src/main/model/Ikaslea.java) ikaslearen erregistroa kapsulatzen du eta IllegalArgumentException jaurtitzen du balidazioa gainditzen ez bada.

- Erroreak eta I/O: [`main.error.ErroreenKudeaketa`](src/main/error/ErroreenKudeaketa.java) fitxategien existentzia egiaztatzeko, irakurtzeko/idazteko, sortzeko/ezabatzeko eta NAN bilatzeko funtzioak ditu CSV/XML-n.

## Nola konpilatu eta exekutatu (proiektuaren erroan)
- Konpilatu javac erabiliz (.class fitxategiak bin karpetan kokatzen dira):
  - Windows / PowerShell:
    - javac -d bin -sourcepath src (find . -name "*.java" -print) (egokitu komandoa shellaren arabera)
  - Alternatiba zuzena:
    - mkdir bin
    - javac -d bin src/main/*/.java
- Exekutatu:
  - java -cp bin main.App
- VSCode-n: erabili Java hedapena; konfigurazioa hemen dago: [.vscode/settings.json](.vscode/settings.json).

## Oharrak eta hurrengo pauso gomendatuak
- Inplementatu irakurketa/idazketa eta konbertsio logika zehatza [src/main/io](src/main/io) (ahora mayormente menús) klaseetan (oraindik menuak nagusiki).
- Gehitu unitate-probak (ez daude sartuta).
- Gehitu adibideak resources/ karpetan eskuzko probak errazteko.

## Fitxategien zerrenda azkarra (estekak)
- [.vscode/settings.json](.vscode/settings.json)  
- [src/main/App.java](src/main/App.java) — [`main.App`](src/main/App.java)  
- [src/main/error/ErroreenKudeaketa.java](src/main/error/ErroreenKudeaketa.java) — [`main.error.ErroreenKudeaketa`](src/main/error/ErroreenKudeaketa.java)  
- [src/main/io/KudeatzaileaTXT.java](src/main/io/KudeatzaileaTXT.java) — [`main.io.KudeatzaileaTXT`](src/main/io/KudeatzaileaTXT.java)  
- [src/main/io/KudeatzaileaXML.java](src/main/io/KudeatzaileaXML.java) — [`main.io.KudeatzaileaXML`](src/main/io/KudeatzaileaXML.java)  
- [src/main/io/KudeatzaileaJSON.java](src/main/io/KudeatzaileaJSON.java) — [`main.io.KudeatzaileaJSON`](src/main/io/KudeatzaileaJSON.java)  
- [src/main/io/KudeatzaileaCSV.java](src/main/io/KudeatzaileaCSV.java) — [`main.io.KudeatzaileaCSV`](src/main/io/KudeatzaileaCSV.java)  
- [src/main/model/Ikaslea.java](src/main/model/Ikaslea.java) — [`main.model.Ikaslea`](src/main/model/Ikaslea.java)  
- [src/main/utils/Filtroak.java](src/main/utils/Filtroak.java) — [`main.utils.Filtroak`](src/main/utils/Filtroak.java)  
- [src/main/utils/Gehigarriak.java](src/main/utils/Gehigarriak.java) — [`main.utils.Gehigarriak`](src/main/utils/Gehigarriak.java)

