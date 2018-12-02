package mindbadger.football.api.util;

import org.apache.log4j.Logger;

/*
    IDs for everything but Fixture are formed in the following pattern:
        <season>_<div>_<team>_<fixture date>
        e.g. 2018_1, 2018_1_2, 2018_1__2018-09-10, 2018_1_2_2018-09-10
*/
//TODO Needs a Unit test
public class SourceIdUtils {
    static private Logger LOG = Logger.getLogger (SourceIdUtils.class);

    private static final String SEPARATOR = "_";

    public static String createSeasonDivisionId (Integer seasonNumber, String divisionId) {
        return seasonNumber + SEPARATOR + divisionId;
    }

    public static String createSeasonDivisionTeamId (Integer seasonNumber, String divisionId, String teamId) {
        return createSeasonDivisionId(seasonNumber, divisionId) + SEPARATOR + teamId;
    }

    public static String createFixtureDateId (Integer seasonNumber, String divisionId, String fixtureDate) {
        return createSeasonDivisionId(seasonNumber, divisionId) + SEPARATOR + SEPARATOR + fixtureDate;
    }

    public static String createTeamStatisticsId (Integer seasonNumber, String divisionId, String teamId,
                                                 String fixtureDate) {
        String id = createSeasonDivisionTeamId(seasonNumber, divisionId, teamId) + SEPARATOR + fixtureDate;
        LOG.debug("Creating TeamStatisticId : " + id);
        return id;
    }

    public static Integer parseSeasonId (String sourceId) {
        String[] idSplit = sourceId.split(SEPARATOR);
        return Integer.parseInt(idSplit[0]);
    }

    public static String parseDivisionId (String sourceId) {
        String[] idSplit = sourceId.split(SEPARATOR);
        return idSplit[1];
    }

    public static String parseTeamId(String sourceId) {
        String[] idSplit = sourceId.split(SEPARATOR);
        return idSplit[2];
    }

    public static String parseFixtureDate (String sourceId) {
        String[] idSplit = sourceId.split(SEPARATOR);
        return idSplit[3];
    }

    public static String parseSeasonDivisionId (String sourceId) {
        String[] idSplit = sourceId.split(SEPARATOR);
        return idSplit[0] + SEPARATOR + idSplit[1];
    }

    public static String parseSeasonDivisionTeamId (String sourceId) {
        String[] idSplit = sourceId.split(SEPARATOR);
        return idSplit[0] + SEPARATOR + idSplit[1] + SEPARATOR + idSplit[2];
    }
}
