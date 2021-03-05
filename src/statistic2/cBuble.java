//ordenacion con metodo de burbuja tanto por x como por y
package statistic2;

public class cBuble extends aBuble {
    //IVM 08/03/2003 
    //quite void, es constructor de la clase

    public cBuble() {
    }
    List ordenax;

    //escritura de valores
    public void setNumero(List lista2) {
        ordenax = lista2;
    }

    public List getLista() {
        return ordenax;
    }

    public void ordena(context_ctx ctx1) {
        context_ctx ctx=ctx1;
        Manejo = new cBubleXY();
        cElement inicial;
        cElement Lfin;
        List lista1;
        cElement L1;
        cElement L2;
        float vx;
        float vx1;
        float vy;
        float vy1;
        int a = 0;
        lista1 = ctx.m_list;
        cElement Ltemp;
        cElement ContI;
        ctx.opcion = 1;
        Manejo.ordena(ctx);
        lista1 = ctx.m_list;
        cElement Lini = lista1.getL();
        ContI = Lini.getNext();
        System.out.println("valor de ContI " + Lini.getNumerox());
        while (ContI != null) {
            vx = Lini.getNumerox();
            vx1 = ContI.getNumerox();
            while (vx == vx1) {
                a = 1;
                ContI = ContI.getNext();
                vx1 = ContI.getNumerox();
            }
            if (a != 0) {
                Lfin = ContI.getAnt();
                L1 = Lini;
                while (L1 != ContI) {
                    L2 = L1.getNext();
                    System.out.println("vy1 " + L2.getNumeroy());
                    while (L2 != ContI) {
                        vy = L1.getNumeroy();
                        vy1 = L2.getNumeroy();
                        if (vy > vy1) {
                            System.out.println("vy " + L1.getNumeroy());
                            System.out.println("vy1 " + L2.getNumeroy());
                            vx = L1.getNumerox();
                            vx1 = L2.getNumerox();
                            L1.setNumeros(vx1, vy1);
                            L2.setNumeros(vx, vy);
                        }
                        L2 = L2.getNext();
                    }
                    float pruebax = L1.getNumerox();
                    float pruebay = L1.getNumeroy();
                    System.out.println(pruebax + "  " + pruebay);
                    L1 = L1.getNext();
                }
                a = 0;
            }
            Lini = ContI;
            ContI = ContI.getNext();
        }
        cElement pt = lista1.getL();
        while (pt != null) {
            vx = pt.getNumerox();
            vy = pt.getNumeroy();
            System.out.println("datos= " + vx + "  " + vy);
            pt = pt.getNext();
        }
    }
}
