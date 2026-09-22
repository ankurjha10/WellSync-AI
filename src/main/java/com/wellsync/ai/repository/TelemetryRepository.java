package com.wellsync.ai.repository;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.wellsync.ai.dto.TelemetryData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TelemetryRepository {

    private final InfluxDBClient influxDBClient;

    public void save(TelemetryData data) {
        try {
            WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

            Point point = Point.measurement("telemetry")
                    .addTag("wellId", data.getWellId().toString())
                    .addField("temperatureC", data.getTemperatureC())
                    .addField("pressurePsi", data.getPressurePsi())
                    .addField("viscosityCp", data.getViscosityCp())
                    .addField("pumpRpm", data.getPumpRpm())
                    .addField("rodLoadLbs", data.getRodLoadLbs())
                    .addField("spm", data.getSpm())
                    .addField("vfdFrequencyHz", data.getVfdFrequencyHz())
                    .addField("strokeLengthIn", data.getStrokeLengthIn())
                    .addField("steamPressurePsi", data.getSteamPressurePsi())
                    .addField("steamTemperatureC", data.getSteamTemperatureC())
                    .addField("productionRateBopd", data.getProductionRateBopd())
                    .addField("pumpEfficiencyPercent", data.getPumpEfficiencyPercent())
                    .time(data.getTimestamp(), WritePrecision.MS);

            writeApi.writePoint(point);
            log.debug("Successfully wrote telemetry to InfluxDB for well: {}", data.getWellId());
        } catch (Exception e) {
            log.error("Failed to write telemetry to InfluxDB: {}", e.getMessage(), e);
        }
    }

    public List<TelemetryData> getHistory(UUID wellId, String timeRange) {
        String flux = String.format(
            "from(bucket: \"telemetry\") " +
            "|> range(start: -%s) " +
            "|> filter(fn: (r) => r._measurement == \"telemetry\" and r.wellId == \"%s\") " +
            "|> pivot(rowKey:[\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\") " +
            "|> sort(columns: [\"_time\"], desc: false) " +
            "|> limit(n: 500)", // Prevent payload explosion
            timeRange, wellId.toString()
        );

        List<TelemetryData> result = new ArrayList<>();
        
        try {
            List<FluxTable> tables = influxDBClient.getQueryApi().query(flux);
            for (FluxTable table : tables) {
                for (FluxRecord record : table.getRecords()) {
                    TelemetryData data = TelemetryData.builder()
                            .wellId(wellId)
                            .timestamp(record.getTime())
                            .temperatureC(getDouble(record, "temperatureC"))
                            .pressurePsi(getDouble(record, "pressurePsi"))
                            .viscosityCp(getDouble(record, "viscosityCp"))
                            .pumpRpm(getDouble(record, "pumpRpm"))
                            .rodLoadLbs(getDouble(record, "rodLoadLbs"))
                            .spm(getDouble(record, "spm"))
                            .vfdFrequencyHz(getDouble(record, "vfdFrequencyHz"))
                            .strokeLengthIn(getDouble(record, "strokeLengthIn"))
                            .steamPressurePsi(getDouble(record, "steamPressurePsi"))
                            .steamTemperatureC(getDouble(record, "steamTemperatureC"))
                            .productionRateBopd(getDouble(record, "productionRateBopd"))
                            .pumpEfficiencyPercent(getDouble(record, "pumpEfficiencyPercent"))
                            .build();
                    result.add(data);
                }
            }
        } catch (Exception e) {
            log.error("Failed to query historical telemetry from InfluxDB: {}", e.getMessage(), e);
        }
        
        return result;
    }
    
    private Double getDouble(FluxRecord record, String field) {
        Object value = record.getValueByKey(field);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return 0.0;
    }
}
