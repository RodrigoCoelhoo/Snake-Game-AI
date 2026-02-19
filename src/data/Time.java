package data;
public class Time {
	private long milliseconds;

	public Time(long milliseconds)
	{
		this.milliseconds = milliseconds;
	}

	public Time(String time)
	{
		this.milliseconds = toMillis(time);
	}

	public long getMilliseconds() { return milliseconds; }
	public void setMilliseconds(long millis) { milliseconds = millis; }

	
	/** 
	 * @param milliseconds
	 */
	public void add(long milliseconds)
	{
		this.milliseconds += milliseconds;
	}

	/**
	 * @return a formated string representing the milliseconds in mm:ss:SSS
	 */
	public String formatTime() 
	{
		long minutes = (milliseconds / (1000 * 60)) % 60;
		long seconds = (milliseconds / 1000) % 60;
		long millis = milliseconds % 1000;

		String formattedTime = String.format("%02d:%02d:%03d", minutes, seconds, millis);
	
		return formattedTime;
	}

	/**
	 * Calculates the number of milliseconds
	 * @param formattedTime		String representation of time mm:ss:SSS
	 * @return a long representing the milliseconds
	 */
    public long toMillis(String formattedTime) 
	{
        String[] parts = formattedTime.split(":");
        long minutes = Long.parseLong(parts[0].trim());
        long seconds = Long.parseLong(parts[1].trim());
        long millis = Long.parseLong(parts[2].trim());

        return (minutes * 60 * 1000) + (seconds * 1000) + millis;
    }
}
