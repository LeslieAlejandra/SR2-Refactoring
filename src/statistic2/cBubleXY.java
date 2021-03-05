//ordenacion con metodo de burbuja ya sea por x o por y
package statistic2;

public class cBubleXY extends aBuble {
    //IVM 08/03/2003 
    //quite void, es constructor de la clase

    public cBubleXY() {
    }

    public void ordena(context_ctx ctx1) {
        context_ctx ctx=ctx1;
        cElement L2;
        float vx;
        float vx1;
        float vy;
        float vy1;
        List lista2 = ctx.m_list;
        cElement L1 = lista2.getL();
        while (L1 != null) {
            L2 = L1.getNext();
            while (L2 != null) {
                if (ctx.opcion == 1) {
                    vx = L1.getNumerox();
                    vx1 = L2.getNumerox();
                    if (vx > vx1) {
                        vy = L1.getNumeroy();
                        vy1 = L2.getNumeroy();
                        L1.setNumeros(vx1, vy1);
                        L2.setNumeros(vx, vy);
                    }
                } else {
                    vy = L1.getNumeroy();
                    vy1 = L2.getNumeroy();
                    if (vy > vy1) {
                        vx = L1.getNumerox();
                        vx1 = L2.getNumerox();
                        L1.setNumeros(vx1, vy1);
                        L2.setNumeros(vx, vy);
                    }
                }
                L2 = L2.getNext();
            }
            float pruebax = L1.getNumerox();
            float pruebay = L1.getNumeroy();
            System.out.println("x " + pruebax + " y " + pruebay);
            L1 = L1.getNext();
        }
        cBuble valor = new cBuble();
        valor.setNumero(lista2);
        ctx.m_list = lista2;
        ctx.m_list = lista2;
    }
}
