package de.javamaps;

/**
 * Global Constants class for Settings, Paths and shared static Strings
 */
public final class ApplicationConstants {
    private ApplicationConstants() {
        //empty private constructor to prevent initialization of constants class
    }

    public static final class Parser {
        public final static boolean FORCE_PARSING = true;
        public static final String JSON_FILE_PATH = "/data/germany.json";
        public static final String PREPROCESSED_VERTEXES_PATH = "preproccessed_vertexes";

        private Parser() {
            //empty private constructor to prevent initialization of constants class
        }

        public static final class OSM {
            public static final String ELEMENT_TYPE_NODE = "node";
            public static final CharSequence ELEMENT_TYPE_WAY = "way";

            private OSM() {
                //empty private constructor to prevent initialization of constants class
            }

            public static final class AdditionalAttributes {
                public static final String NAME = "name";

                private AdditionalAttributes() {
                    //empty private constructor to prevent initialization of constants class
                }
            }
        }
    }
}
