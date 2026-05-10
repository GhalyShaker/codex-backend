package com.codex.backend.system.api;

public final class ApiPaths {

    private ApiPaths() {}

    // Auth domain
    public static final String AUTH = "/auth";

    // Admin domain
    public static final String ADMIN = "/admin";

    // Data domain
    public static final String DATA = "/data";

    // Identification / User domain
    public static final String IDENTIFICATION = "/identification";

    // Civil Archive domain
    public static final String CIVIL_ARCHIVE = "/civil-archive";

    // =========================
    // Civil Archive entities
    // =========================
    public static final class CivilArchive {

        private CivilArchive() {}

        // Person resource
        public static final class PersonPath {
            private PersonPath() {}

            // base paths
            public static final String BASE = "/persons";
            public static final String TABLE = "/table";  // CRUD
            public static final String VIEW = "/view";    // read-only projections

            // Example full paths (to use in controllers)
            public static final String TABLE_BASE = BASE + TABLE; // /persons/table
            public static final String VIEW_BASE = BASE + VIEW;   // /persons/view
        }

        // Add other resources here in the future
        // e.g., Family, Document, etc.
    }

    // You can do the same for Data domain
    public static final class Data {
        private Data() {}

        public static final String BASE = "/data";

        public static final class Reference {
            private Reference() {}
            public static final String BASE = Data.BASE + "/reference";
        }
    }
}