class prob_0551 {
    public boolean checkRecord(String s) {
        return s.indexOf("A") == s.lastIndexOf("A") && !s.contains("LLL");
    }
}