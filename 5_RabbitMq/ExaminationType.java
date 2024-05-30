public enum ExaminationType {
    KNEE,
    HIP,
    ELBOW;

    @Override
    public String toString() {
        switch (this) {
            case HIP -> {
                return "hip";
            }
            case KNEE -> {
                return "knee";
            }
            case ELBOW -> {
                return "elbow";
            }
            default -> {
                return "";
            }
        }
    }
}
