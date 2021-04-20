package Object;

import java.util.Random;

public class Constant {
    public Constant() {
    }

    public static String randomName(int d){
        StringBuilder sb=new StringBuilder();

        final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
        final String alphaUpperCase = alpha.toUpperCase(); // A-Z
        final String digits = "0123456789";
        final String ALPHA_NUMERIC = alpha + alphaUpperCase+digits;
        Random random=new Random();

        for(int i=0; i<d; i++){
            int index=(int)(random.nextFloat()*ALPHA_NUMERIC.length());
            sb.append(ALPHA_NUMERIC.charAt(index));
        }

        return sb.toString();
    }
}
