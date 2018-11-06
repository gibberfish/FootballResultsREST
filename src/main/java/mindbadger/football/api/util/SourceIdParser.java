package mindbadger.football.api.util;

public class SourceIdParser {
    public static String parseSeasonDivisionId (String sourceId) {
        String[] idSplit = sourceId.split("_");
        return idSplit[0];
    }

    public static String parseFixtureDate (String sourceId) {
        String[] idSplit = sourceId.split("_");
        return idSplit[1];
    }

    public static Integer parseSeasonId (String sourceId) {
        String seasonDivisionId = SourceIdParser.parseSeasonDivisionId(sourceId);
        String[] idSplit = seasonDivisionId.split("-");
        return Integer.parseInt(idSplit[0]);
    }
    public static String parseDivisionId (String sourceId) {
        String seasonDivisionId = SourceIdParser.parseSeasonDivisionId(sourceId);
        String[] idSplit = seasonDivisionId.split("-");
        return idSplit[1];
    }
}
