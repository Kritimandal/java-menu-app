public enum MilkType {
    FULL_CREAM,
    SKIM,
    SOY,
    ALMOND,
    OAT,
    CASHEW,
    NO_MILK;

    public String toString(){
        /**
         *
         * @return a prettified version of the relevant MilkType enum constant
         */
        String prettified = "None";
        switch (this){
            case FULL_CREAM -> prettified = "Full-cream";
            case SKIM -> prettified = "Skim";
            case SOY -> prettified = "Soy";
            case ALMOND -> prettified = "Almond";
            case OAT -> prettified = "Oat";
            case CASHEW -> prettified = "Cashew";
            case NO_MILK -> prettified = "None";
        }
        return prettified;
    }
}
