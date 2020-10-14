package materiel;

import java.util.Random;

class GenererRef {

    String tab[] = new String[10];
    String chaineRef = "";
    String chaineRefTM = "";

    public String genererRef() {
        Random rd = new Random();

        for (int i = 0; i < 10; i++) {
            tab[i] = "" + rd.nextInt(10);
            chaineRef += tab[i];
        }
        //System.out.println(chaine);
        return chaineRef;
    }

//    public String genererRefTM() {
//        Random rd = new Random();
//
//        for (int i = 0; i < 10; i++) {
//            tab[i] = "" + rd.nextInt(10);
//            chaineRefTM += tab[i];
//        }
//        //System.out.println(chaine);
//        return chaineRefTM;
//    }
}
