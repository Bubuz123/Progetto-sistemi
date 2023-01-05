/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package progetto;

import java.io.*;

/**
 *
 * @author Newer
 */
public class Progetto {

    static int TabellaOriginale[][]; //Tabella originale contenente i costi dei collegamenti
    static int Intermedio[][]; //Tabella intermedia (Utilizzata per l'ordinamento)
    static int RoutingTable[][]; //Tabella finale
    static int Nodi; //Numero di nodi

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //Lettore input

        System.out.println("Inserisci numero di nodi: ");
        Nodi = Integer.parseInt(br.readLine()); //Chiedo il numero di nodi

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

        for (int i = 0; i < Nodi; i++) { //Chiedo dati per ogni nodo (UN SOLO COLLEGAMENTO A NODO)
            System.out.println("Nodo " + (i + 1) + ":");

            System.out.print("Nodo di destinazione: "); //Chiedo il nodo di destinazione
            int d = Integer.parseInt(br.readLine()) - 1;

            System.out.print("Costo: "); //Chiedo il costo
            int c = Integer.parseInt(br.readLine());

            TabellaOriginale[i][d] = c; //Setto il costo dal nodo alla destinazione
            TabellaOriginale[d][i] = c; //Setto il costo dalla destinazione al nodo
        }

        //Aggiungo i valori alle altre due tabelle
        for (int i = 0; i < Nodi; i++) { //COMMENTARE
            for (int j = 0; j < Nodi; j++) {
                if (i == j) {
                    RoutingTable[i][j] = 0; 
                    Intermedio[i][j] = i;
                } else {
                    RoutingTable[i][j] = 9999;
                    Intermedio[i][j] = 100;
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
        for (int i = 0; i < Nodi; i++) { //COMMENTARE
            if (TabellaOriginale[Nodo][i] != 9999) {
                int dist = TabellaOriginale[Nodo][i];
                for (int j = 0; j < Nodi; j++) {
                    int dist2 = RoutingTable[i][j];
                    if (Intermedio[i][j] == Nodo) {
                        dist2 = 9999;
                    }
                    if (dist + dist2 < RoutingTable[Nodo][j]) {
                        RoutingTable[Nodo][j] = dist + dist2;
                        Intermedio[Nodo][j] = i;
                    }
                }
            }
        }
    }

    static void Aggiorna() { //Ordina la Routing Table
        int k = 0;
        for (int i = 0; i < 4 * Nodi; i++) { //Ordino la tabella 4 volte
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
