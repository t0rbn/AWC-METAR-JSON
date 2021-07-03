package xyz.torben.awc.json.records;

public record MetarResponse(
        String rawText,
        double tempCelsius,
        double dewPointCelsius,
        double windDirectionDegrees,
        double windSpeedKnots,
        double visibilityMiles,
        double altimeterHG,
        String skyCoverage
) {
}
