import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class Experiments {

    public static void q3_1(){

        int q = 6571;
        Set<Integer> setQ1 = new HashSet<>();
        Set<Integer> setQ2 = new HashSet<>();
        for (int i=0; i<q; i++) {
            setQ1.add((i*i) % q);
            setQ2.add(((int) Math.pow(-1,i)) * (i*i) % q);
        }
        System.out.println("Q1 size : " + setQ1.size());        // 3286
        System.out.println("Q2 size : " + setQ2.size());        // 6571
    }

    public static void q3_2_a(){

        int m = 6571;
        long p = 1_000_000_007;
        Random rd = new Random();
        for (int i=0; i<100; i++) {
            QPHashTable tbl = new QPHashTable(m, p);
            for (int j=0; j<m; j++) {
                int b = rd.nextInt(100);
                int a = 100 * j + b;
                try {
                    tbl.Insert(new HashTableElement(a, j));
                } catch (IHashTable.TableIsFullException | IHashTable.KeyAlreadyExistsException e) {
                    System.out.println("Exception " + e + " at i = " + i + ", j = " + j);
                }
            }
        }
    }

    public static void q3_2_b(){

        int m = 6571;
        long p = 1_000_000_007;
        Random rd = new Random();
        for (int i=0; i<100; i++) {
            AQPHashTable tbl = new AQPHashTable(m, p);
            for (int j=0; j<m; j++) {
                int b = rd.nextInt(100);
                int a = 100 * j + b;
                try {
                    tbl.Insert(new HashTableElement(a, j));
                } catch (IHashTable.TableIsFullException | IHashTable.KeyAlreadyExistsException e) {
                    System.out.println("Exception " + e + " at i = " + i + ", j = " + j);
                }
            }
        }
    }

    public static double measure_tbl_insertions(IHashTable tbl, int insertions, HashTableElement[] htelems) {

        long startTime = System.nanoTime();
        for (int i=0; i<insertions; i++) {
            try {
                tbl.Insert(htelems[i]);
            } catch (IHashTable.TableIsFullException | IHashTable.KeyAlreadyExistsException e) {
                System.out.println(tbl.getClass() + ": " + "Exception " + e + " at i = " + i);
            }
        }
        long endTime = System.nanoTime();
        return 1d*(endTime - startTime) / 1_000_000_000;
    }


    public static HashTableElement[] make_insertion_series(int insertions) {

        HashTableElement[] htelems = new HashTableElement[insertions];

        Random rd = new Random();
        for (int i = 0; i < insertions; i++) {
            int b = rd.nextInt(100);
            int a = 100 * i + b;
            htelems[i] = new HashTableElement(a, i);
        }
        return htelems;
    }

    public static void q4_1(){
        int m = 10_000_019;
        int p = 1_000_000_007;
        int n = m/2;

        HashTableElement[] htelems = make_insertion_series(n);

        System.out.println("LP: " + measure_tbl_insertions(new LPHashTable(m, p), n, htelems));
        System.out.println("QP: " + measure_tbl_insertions(new QPHashTable(m, p), n, htelems));
        System.out.println("AQP: " + measure_tbl_insertions(new AQPHashTable(m, p), n, htelems));
        System.out.println("DH: " + measure_tbl_insertions(new DoubleHashTable(m, p), n, htelems));
    }

    public static void q4_2(){
        int m = 10_000_019;
        int p = 1_000_000_007;
        int n = (19*m) / 20;

        HashTableElement[] htelems = make_insertion_series(n);

        System.out.println("LP: " + measure_tbl_insertions(new LPHashTable(m, p), n, htelems));
//        System.out.println("QP: " + measure_tbl_insertions(new QPHashTable(m, p), n, htelems));
        System.out.println("AQP: " + measure_tbl_insertions(new AQPHashTable(m, p), n, htelems));
        System.out.println("DH: " + measure_tbl_insertions(new DoubleHashTable(m, p), n, htelems));
    }

    public static void q5() {
        int m = 10_000_019;
        int p = 1_000_000_007;
        int n = m / 2;
        long[] runtimes = new long[6];

        DoubleHashTable tbl = new DoubleHashTable(m, p);

        for (int j=0; j<6; j++) {
            int[] keys = new int[n];
            HashTableElement[] htelems = new HashTableElement[n];

            Random rd = new Random();
            for (int i = 0; i < n; i++) {
                int b = rd.nextInt(100);
                keys[i] = 100 * i + b;
                htelems[i] = new HashTableElement(keys[i], i);
            }

            long startTime = System.nanoTime();
            for (int i = 0; i < n; i++) {
                try {
                    tbl.Insert(htelems[i]);
                } catch (IHashTable.TableIsFullException | IHashTable.KeyAlreadyExistsException e) {
                    System.out.println("insert: " + tbl.getClass() + ": " + "Exception " + e + " at i = " + i);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("insert i: " + i + " (" + htelems[i].GetKey() + ", " + htelems[i].GetValue() + ")" );
                }
            }
            for (int i = 0; i < n; i++) {
                try {
                    tbl.Delete(keys[i]);
                } catch (IHashTable.KeyDoesntExistException e) {
                    System.out.println("del: " + tbl.getClass() + ": " + "Exception " + e + " at i = " + i);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("del i: " + i + " (" + htelems[i].GetKey() + ", " + htelems[i].GetValue() + ") " + e);
                }
            }
            long endTime = System.nanoTime();
            runtimes[j] = endTime - startTime;
        }
        long sum = 0;
        for (int i = 0; i < 3; i++)
            sum += runtimes[i];
        System.out.println("first 3 runs: " + ((1d*(sum)) / 1_000_000_000) );

        sum = 0;
        for (int i = 3; i < 6; i++)
            sum += runtimes[i];
        System.out.println("last 3 runs: " + ((1d*(sum)) / 1_000_000_000) );

//        System.out.println(Arrays.toString(runtimes));
    }

    public static void main(String[] args) {

//        System.out.println("**** q3_1 ****");
//        q3_1();
//        System.out.println("**** q3_2_a ****");
//        q3_2_a();
//        System.out.println("**** q3_2_b ****");
//        q3_2_b();
//        System.out.println("**** 4_1 ****");
        q4_1();
        System.out.println("**** 4_2 ****");
        q4_2();
        System.out.println("**** 5 ****");
        q5();

    }
}
