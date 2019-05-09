package rc4;

import java.util.Scanner;

public class RC4 {

    int[] baslangicDizisi = new int[256];
    char[] anahtarDizisi = new char[256];

    int baslangicDizisiUzunlugu = baslangicDizisi.length;

    String sifreliMetin = "";
    char[] sifresiCozulmusMetin;

    public void baslangicPermutasyonu(String anahtar) {

        int anahtarUzunlugu = anahtar.length();

        for (int i = 0; i < baslangicDizisiUzunlugu; i++) {
            
            // Baslangıç dizisi : 0 1 2 3 4 5 6 7 .... 255 şeklinde indislerine elemanlar yerleştirildi.
            // Anahtar diziside girilen anahtara göre indislerine elemanlar yerleştirildi. Örneğin KEY olsun; K E Y K E Y K E Y ... K şeklinde
            baslangicDizisi[i] = i;
            anahtarDizisi[i] = anahtar.charAt(i % anahtarUzunlugu);
        }
    }

    public void ksa() {
        int j = 0;
        for (int i = 0; i < baslangicDizisiUzunlugu; i++) {
            j = (j + baslangicDizisi[i] + anahtarDizisi[i]) % 256;
            yerDegistir(baslangicDizisi, i, j);
        }
    }

    public void prgaVeXor(String sifrelenecekMetin) {
        int i = 0, j = 0, k = 0, c = 0;
        int sifrelenecekMetinUzunlugu = sifrelenecekMetin.length();
        sifresiCozulmusMetin = new char[sifrelenecekMetinUzunlugu];
        while (sifrelenecekMetinUzunlugu > 0) {
            i = (i + 1) % 256;
            j = (j + baslangicDizisi[i]) % 256;
            yerDegistir(baslangicDizisi, i, j);

            // sifreli metini oluşturalım
            k = baslangicDizisi[(baslangicDizisi[i] + baslangicDizisi[j]) % 256];
            sifreliMetin = sifreliMetin + (char) (k ^ sifrelenecekMetin.charAt(i - 1));
            sifresiCozulmusMetin[c] = (char) (k ^ sifrelenecekMetin.charAt(i - 1));
            sifrelenecekMetinUzunlugu = sifrelenecekMetinUzunlugu - 1;
            c++;
        }
    }

    public void yerDegistir(int[] degistirilecekDizi, int indis1, int indis2) {
        int yedek = degistirilecekDizi[indis1];
        degistirilecekDizi[indis1] = degistirilecekDizi[indis2];
        degistirilecekDizi[indis2] = yedek;
    }

    public static void main(String[] args) {

        RC4 rc4 = new RC4();

        Scanner klavye = new Scanner(System.in);

        System.out.print("Lütfen Anahtar Giriniz : ");

        String anahtar = klavye.nextLine();

        System.out.print("Lütfen Şifrelenecek Metini Giriniz : ");

        String sifrelenecekMetin = klavye.nextLine();

        System.out.println("\nGirmiş olduğunuz anahtar -----> " + anahtar + " \nGirmiş olduğunuz metin -----> " + sifrelenecekMetin + "\n");

        // Kullanıcının girmiş olduğu anahtara ve metine göre şifreleme işlemlerine başlayalım.
        rc4.baslangicPermutasyonu(anahtar);

        rc4.ksa();

        rc4.prgaVeXor(sifrelenecekMetin);

        // Şifreleme işlemi tamamlandı.
        String sifrelenmisMetin = rc4.sifreliMetin;

        System.out.println("Şifreli Metin -----> " + sifrelenmisMetin);

        // Kullanıcının girmiş olduğu anahtara göre elimizde olan şifreli metini çözmeye çalışalım.
        System.out.print("\nŞifreyi çözmek için anahtar giriniz : ");
        String sifreyiCozecekAnahtar = klavye.nextLine();

        rc4.baslangicPermutasyonu(sifreyiCozecekAnahtar);

        rc4.ksa();

        rc4.prgaVeXor(sifrelenmisMetin);

        char[] sifresiCozulmusMetin = rc4.sifresiCozulmusMetin;

        System.out.print("Şifresi çözülmüş metin ----> ");

        for (int i = 0; i < sifresiCozulmusMetin.length; i++) {
            System.out.print(sifresiCozulmusMetin[i]);
        }
    }
}
