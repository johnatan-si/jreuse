package Util;


public class Similarity {

    public static float checkSimilarity(String sString1, String sString2) throws Exception {

        // Se as strings têm tamanho distinto, obtêm a similaridade de todas as
        // combinações em que tantos caracteres quanto a diferença entre elas são
        // inseridos na string de menor tamanho. Retorna a similaridade máxima
        // entre todas as combinações, descontando um percentual que representa
        // a diferença em número de caracteres.
        if(sString1.length() != sString2.length()) {
            int iDiff = Math.abs(sString1.length() - sString2.length());
            int iLen = Math.max(sString1.length(), sString2.length());
            String sBigger, sSmaller, sAux;

            if(iLen == sString1.length()) {
                sBigger = sString1;
                sSmaller = sString2;
            }
            else {
                sBigger = sString2;
                sSmaller = sString1;
            }

            float fSim, fMaxSimilarity = Float.MIN_VALUE;
            for(int i = 0; i <= sSmaller.length(); i++) {
                sAux = sSmaller.substring(0, i) + sBigger.substring(i, i+iDiff) + sSmaller.substring(i);
                fSim = checkSimilaritySameSize(sBigger,  sAux);
                if(fSim > fMaxSimilarity)
                    fMaxSimilarity = fSim;
            }
            return fMaxSimilarity - (1f * iDiff) / iLen;

        // Se as strings têm o mesmo tamanho, simplesmente compara-as caractere
        // a caractere. A similaridade advém das diferenças em cada posição.
        } else
            return checkSimilaritySameSize(sString1, sString2);
    }

    protected static float checkSimilaritySameSize(String sString1, String sString2) throws Exception {

        if(sString1.length() != sString2.length())
            throw new Exception("Strings devem ter o mesmo tamanho!");

        int iLen = sString1.length();
        int iDiffs = 0;

        // Conta as diferenças entre as strings
        for(int i = 0; i < iLen; i++)
            if(sString1.charAt(i) != sString2.charAt(i))
                iDiffs++;

        // Calcula um percentual entre 0 e 1, sendo 0 completamente diferente e
        // 1 completamente igual
        return 1f - (float) iDiffs / iLen;
    }
//.java
   /* public static void main(String[] args) {
        try {
            System.out.println("'product' vs 'productdc' = " + checkSimilarity("product", "productdo"));
            /*System.out.println("'cidade' vs 'cdade' = " + checkSimilarity("cidade", "cdade"));
            System.out.println("'cidade' vs 'ciDade' = " + checkSimilarity("cidade", "ciDade"));
            System.out.println("'cidade' vs 'cdiade' = " + checkSimilarity("cidade", "cdiade"));
            System.out.println("'cidade' vs 'edadic' = " + checkSimilarity("cidade", "edadic"));
            System.out.println("'cidade' vs 'CIDADE' = " + checkSimilarity("cidade", "CIDADE"));
            System.out.println("'cidade' vs 'CIdADE' = " + checkSimilarity("cidade", "CIdADE"));
            System.out.println("'cidade' vs 'CdADE' = " + checkSimilarity("cidade", "CdADE"));*/
      /*  } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}