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

    static int Grafico[][];
    static int via[][];
    static int rt[][];
    static int v;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Inserisci numero di nodi: ");
        v = Integer.parseInt(br.readLine());

        Grafico = new int[v][v];
        via = new int[v][v];
        rt = new int[v][v];
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                if (i == j) {
                    Grafico[i][j] = 0;
                } else {
                    Grafico[i][j] = 9999;
                }
            }
        }

        for (int i = 0; i < v; i++) {
            System.out.println("Dati nodo " + (i + 1) + ":");
            
            System.out.print("Destinazione: ");
            int d = Integer.parseInt(br.readLine()) - 1;
            
            System.out.print("Costo: ");
            int c = Integer.parseInt(br.readLine());
            
            Grafico[i][d] = c;
            Grafico[d][i] = c;
        }
        Inizio();

        System.out.println("Tabella: ");
        Visualizzatabella();
    }

    static void Visualizzatabella() {
        Aggiorna();
        Printtabella();
        System.out.println();
    }

    static void Aggiornatabella(int source) {
        for (int i = 0; i < v; i++) {
            if (Grafico[source][i] != 9999) {
                int dist = Grafico[source][i];
                for (int j = 0; j < v; j++) {
                    int inter_dist = rt[i][j];
                    if (via[i][j] == source) {
                        inter_dist = 9999;
                    }
                    if (dist + inter_dist < rt[source][j]) {
                        rt[source][j] = dist + inter_dist;
                        via[source][j] = i;
                    }
                }
            }
        }
    }

    static void Aggiorna() {
        int k = 0;
        for (int i = 0; i < 4 * v; i++) {
            Aggiornatabella(k);
            k++;
            if (k == v) {
                k = 0;
            }
        }
    }

    static void Inizio() {
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                if (i == j) {
                    rt[i][j] = 0;
                    via[i][j] = i;
                } else {
                    rt[i][j] = 9999;
                    via[i][j] = 100;
                }
            }
        }
    }

    static void Printtabella() {
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                System.out.print("Dist: " + rt[i][j] + "    ");
            }
            System.out.println();
        }
    }
}

