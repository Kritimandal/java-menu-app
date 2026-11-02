public enum Preference {
    YES, NO;

    public String toString(){
        /**
         * @return a prettified version of the relevant enum constant
         *  "Yes" for Yes and "No" for No.
         */
        String prettified = "Yes";
        switch (this){
            case YES -> prettified = "Yes";
            case NO -> prettified = "No";
        }
        return prettified;
    }
}

enum HotPreference {
    YES, NO;
    public String toString(){
        /**
         * @return a prettified version of the relevant enum constant
         *  "Hot" for NO and "Iced" for YES.
         */
        String prettified = "Iced";
        switch (this){
            case NO -> prettified = "Hot";
            case YES -> prettified = "Iced";
        }
        return prettified;
    }
}