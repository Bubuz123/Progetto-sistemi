/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package progetto;

import java.io.*;
import java.util.Scanner;

/**
 *
 * @author Newer
 */
public class Progetto {

    static int TabellaOriginale[][]; //Tabella originale contenente i costi dei collegamenti
    static int Intermedio[][]; //Tabella intermedia (Utilizzata per l'ordinamento) (Contiene i collegamenti)
    static int RoutingTable[][]; //Tabella finale
    static int Nodi = 0; //Numero di nodi

    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //Lettore input

        File myObj = new File("test.csv"); //File csv da cui legge i nodi
        Scanner s = new Scanner(myObj);
        String str = "";
        while (s.hasNextLine()) {
            str += s.nextLine(); //Salva i collegamenti
            Nodi++; //Conta quanti nodi ci sono
        }
        s.close();


        //Creazione del grafico
        TabellaOriginale = new int[Nodi][Nodi]; //Inizializzo la tabella orginale
        Intermedio = new int[Nodi][Nodi]; //Inizializzo la tabella intermedia
        RoutingTable = new int[Nodi][Nodi]; //Inizializzo la tabella finale
        
        for (int i = 0; i < Nodi; i++) { //Aggiungo i valori alla tabella orginale
            for (int j = 0; j < Nodi; j++) {
                if (i == j) {
                    TabellaOriginale[i][j] = 0; //Setto a 0 il costo dei nodi per andare a loro stessi
                } else {
                    TabellaOriginale[i][j] = 9999; //Setto il resto dei costi ad "Infinito"
                }
            }
        }

        String[] split = str.split(";"); //Creo un vettore contenente ogni linea
        for (int i = 0; i < Nodi; i++) { //Prendo i costi per ogni nodo
            
            String[] split2 = split[i].split(","); //Prendo ogni parte di ogni linea
            int x = Integer.parseInt(split2[0])-1; //Prendo l'origine
            int d = Integer.parseInt(split2[1])-1; //Prendo la destinazione
            int c = Integer.parseInt(split2[2]); //Prendo il costo

            TabellaOriginale[x][d] = c; //Setto il costo dal nodo alla destinazione
            TabellaOriginale[d][x] = c; //Setto il costo dalla destinazione al nodo
        }

        //Aggiungo i valori alle altre due tabelle
        for (int i = 0; i < Nodi; i++) {
            for (int j = 0; j < Nodi; j++) {
                if (i == j) {
                    RoutingTable[i][j] = 0; //Metto il costo per loro stessi a 0
                    Intermedio[i][j] = i; //Segno i collegamenti
                } else {
                    RoutingTable[i][j] = 9999; //Setto il resto dei costi ad "Infinito"
                    Intermedio[i][j] = 100; //Segno un collegamento impossibile
                }
            }
        }

        System.out.println("Tabella: ");
        Visualizzatabella(); //Ordina e printa la Routing Table
    }

    static void Visualizzatabella() { //Ordina e printa la Routing Table
        Aggiorna(); //Ordina la Routing Table
        Printtabella(); //Printa la Routing Table
        System.out.println();
    }

    static void Aggiornatabella(int Nodo) { //Passaggio intermedio per l'ordinamento
        for (int i = 0; i < Nodi; i++) {
            if (TabellaOriginale[Nodo][i] != 9999) { //Se il costo non è infinito
                int dist = TabellaOriginale[Nodo][i]; //Prendo il costo
                for (int j = 0; j < Nodi; j++) {
                    int dist2 = RoutingTable[i][j]; //Prendo il costo della Routing Table
                    if (dist + dist2 < RoutingTable[Nodo][j] && Intermedio[i][j] != Nodo) { //Se i costi sono minori di quelli attuali e non è collegato con se stesso
                        RoutingTable[Nodo][j] = dist + dist2; //Li setto
                        Intermedio[Nodo][j] = i; //E creo il collegamento 
                    }
                }
            }
        }
    }

    static void Aggiorna() { //Ordina la Routing Table
        int k = 0;
        for (int i = 0; i < 4 * Nodi; i++) { //Ordino la tabella 4 volte per essere sicuro (numero minimo)
            Aggiornatabella(k); //Passaggio intermedio per l'ordinamento
            k++;
            if (k == Nodi) { //Quando raggiunge il nodo finale ricomincia da 0
                k = 0;
            }
        }
    }

    static void Printtabella() { //Printa la Routing Table

        for (int k = 1; k < Nodi + 1; k++) { //Print riga iniziale con il numero dei nodi
            System.out.print("  " + k + "  ");
        }
        System.out.println();
        for (int i = 0; i < Nodi; i++) { //Ciclo per il print dei costi di ogni riga
            System.out.print(i + 1 + ": "); //Print colonna iniziale con il numero dei nodi
            for (int j = 0; j < Nodi; j++) {
                System.out.print(RoutingTable[i][j] + "    "); //print del costo
            }
            System.out.println();
        }
    }
}
