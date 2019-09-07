package pt;

public enum WeatherDataType {
	YEAR,
	MONTH,
	DAY,
	HOUR,
	MINUTE,
	TEMPERATURE,
	RELATIVE_HUMIDITY,
	SEA_LVL_PRESSURE,
	TOTAL_PRECIPITATION,
	TOTAL_CLOUD_COVER,
	SUNSHINE_DURATION,
	SHORTWAVE_RADIATION,
	WIND_SPEED,
	WIND_DIRECTION,
	WIND_GUST;
	
	public static WeatherDataType fromInteger(int x) {
		switch(x) {
		case 0: 
			return WeatherDataType.TEMPERATURE;
		case 1: 
			return WeatherDataType.RELATIVE_HUMIDITY;
		case 2: 
			return WeatherDataType.SEA_LVL_PRESSURE;
		case 3: 
			return WeatherDataType.TOTAL_PRECIPITATION;
		case 4: 
			return WeatherDataType.TOTAL_CLOUD_COVER;
		case 5: 
			return WeatherDataType.SUNSHINE_DURATION;
		case 6: 
			return WeatherDataType.SHORTWAVE_RADIATION;
		case 7: 
			return WeatherDataType.WIND_SPEED;
		case 8: 
			return WeatherDataType.WIND_DIRECTION;
		case 9: 
			return WeatherDataType.WIND_GUST;
		default:
			return null;
		}
	}

}


