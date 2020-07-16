package gtc_expansion.util;

public class GTCXHelperPipe {
    public enum GTPipeModel {
        SMALL("_small", new int[] { 6, 10 }),
        MED("", new int[] { 4, 12 }),
        LARGE("_large", new int[] { 1, 15 });

        String suffix;
        int[] sizes;

        GTPipeModel(String suffix, int[] sizes) {
            this.suffix = suffix;
            this.sizes = sizes;
        }

        public String getPrefix() {
            return suffix.replace("_", "");
        }

        public String getSuffix() {
            return this.suffix.toLowerCase();
        }

        public int[] getSizes() {
            return this.sizes;
        }
    }
}