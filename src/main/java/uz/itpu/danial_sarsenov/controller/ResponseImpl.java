    package uz.itpu.danial_sarsenov.controller;

    public class ResponseImpl implements Response {
        private final String message;
        private final boolean ok;
        private final boolean exit;

        public ResponseImpl(String message, boolean ok, boolean exit) {
            this.message = message;
            this.ok = ok;
            this.exit = exit;
        }

        public ResponseImpl(String message) {
            this(message, true, false);
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public boolean isOk() {
            return ok;
        }

        @Override
        public boolean isExit() {
            return exit;
        }
    }
